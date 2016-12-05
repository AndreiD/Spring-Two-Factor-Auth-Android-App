package com.androidadvance.springtwofactorauth.activities;

import android.content.Intent;
import android.os.Bundle;
import android.provider.Settings;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import com.androidadvance.springtwofactorauth.R;
import com.androidadvance.springtwofactorauth.data.remote.TheAPI;
import com.androidadvance.springtwofactorauth.utils.AESHelper;
import com.androidadvance.springtwofactorauth.utils.DialogFactory;
import com.google.firebase.iid.FirebaseInstanceId;
import com.socks.library.KLog;
import java.io.UnsupportedEncodingException;
import java.security.GeneralSecurityException;
import java.util.HashMap;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SettingsActivity extends AppCompatActivity {

  private EditText editText_auth_code;
  private Button button_save;
  private Button button_reset;
  private SettingsActivity mContext;
  private LinearLayout linlayout_settings;

  @Override protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_settings);

    mContext = SettingsActivity.this;
    editText_auth_code = (EditText) findViewById(R.id.editText_auth_code);
    button_save = (Button) findViewById(R.id.button_save);
    button_reset = (Button) findViewById(R.id.button_reset);
    linlayout_settings = (LinearLayout) findViewById(R.id.linlayout_settings);

    if (getSharedPreferences("PREFS", 0).getBoolean("has_auth_code", false)) {
      button_reset.setVisibility(View.VISIBLE);
      linlayout_settings.setVisibility(View.GONE);
    } else {
      button_reset.setVisibility(View.GONE);
      linlayout_settings.setVisibility(View.VISIBLE);
    }

    editText_auth_code.setText("auth3cf23ef");

    button_save.setOnClickListener(new View.OnClickListener() {
      @Override public void onClick(View view) {
        getSharedPreferences("PREFS", 0).edit().putString("auth_code", editText_auth_code.getText().toString()).commit();
        getSharedPreferences("PREFS", 0).edit().putBoolean("has_auth_code", true).commit();

        send_encoded_regkey_to_server();
      }
    });

    button_reset.setOnClickListener(new View.OnClickListener() {
      @Override public void onClick(View view) {
        getSharedPreferences("PREFS", 0).edit().putString("auth_code", "").commit();
        getSharedPreferences("PREFS", 0).edit().putBoolean("has_auth_code", false).commit();
        DialogFactory.createSimpleOkDialog(mContext, getString(R.string.reset_ok), getString(R.string.application_reseted)).show();
      }
    });
  }

  private void send_encoded_regkey_to_server() {

    DialogFactory.showSnackBar(mContext, findViewById(android.R.id.content), "Please wait while we activate your account.").show();

    TheAPI theAPI = TheAPI.Factory.getIstance(mContext);
    HashMap<String, Object> hashMap = new HashMap<>();
    String token = FirebaseInstanceId.getInstance().getToken();
    hashMap.put("google_push_notifications_token", token);

    String clear_hashmap = hashMap.toString();

    String encrypted_text = "";
    try {
      AESHelper.SecretKeys secretKey = AESHelper.generateKeyFromPassword(Settings.Secure.getString(getContentResolver(), Settings.Secure.ANDROID_ID), "thisisa1337salt");
      AESHelper.CipherTextIvMac cipherTextIvMac = AESHelper.encrypt(clear_hashmap, secretKey);

      encrypted_text = new String(cipherTextIvMac.getCipherText());
      KLog.d("Encrypted text is: " + encrypted_text);
      //byte[] decryptBytes = AESHelper.decrypt(cipherTextIvMac, secretKey);
      //KLog.d("Decrypted text is: " + new String(decryptBytes));
    } catch (GeneralSecurityException e) {
      e.printStackTrace();
    } catch (UnsupportedEncodingException e) {
      e.printStackTrace();
    }

    theAPI.registerUser(encrypted_text).enqueue(new Callback<Object>() {
      @Override public void onResponse(Call<Object> call, Response<Object> response) {
        KLog.d("got response" + response.message());

        DialogFactory.createSimpleOkDialog(mContext, "Application Activated", "You will get a push notification every time you try to login").show();
        Intent intent = new Intent(Intent.ACTION_MAIN);
        intent.addCategory(Intent.CATEGORY_HOME);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
      }

      @Override public void onFailure(Call<Object> call, Throwable t) {
        KLog.e("Error >>> " + t.getLocalizedMessage());
        DialogFactory.createGenericErrorDialog(mContext, "Something went terribly wrong while trying to activate your account. Please contact us").show();
      }
    });
  }
}
