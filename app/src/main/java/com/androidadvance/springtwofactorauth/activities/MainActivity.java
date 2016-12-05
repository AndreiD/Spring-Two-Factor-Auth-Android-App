package com.androidadvance.springtwofactorauth.activities;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import com.androidadvance.springtwofactorauth.BaseActivity;
import com.androidadvance.springtwofactorauth.R;
import com.androidadvance.springtwofactorauth.data.remote.TheAPI;
import com.google.firebase.iid.FirebaseInstanceId;
import com.socks.library.KLog;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends BaseActivity {

  private MainActivity mContext;
  private ImageButton imageButton_settings;
  private Button button_yes;
  private Button button_no;
  private String auth_code;
  private TheAPI theAPI;

  public void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);

    setContentView(R.layout.activity_main);
    getSupportActionBar().setElevation(0);

    mContext = MainActivity.this;
    imageButton_settings = (ImageButton) findViewById(R.id.imageButton_settings);
    button_yes = (Button) findViewById(R.id.button_yes);
    button_no = (Button) findViewById(R.id.button_no);

    //if it doesn't have has_auth_code start the settings activity
    if (!getSharedPreferences("PREFS", 0).getBoolean("has_auth_code", false)) {
      startActivity(new Intent(mContext, SettingsActivity.class));
    } else {
      auth_code = getSharedPreferences("PREFS", 0).getString("auth_code", "");
      KLog.d("The AUTH CODE: " + auth_code);
    }

    imageButton_settings.setOnClickListener(new View.OnClickListener() {
      @Override public void onClick(View view) {
        startActivity(new Intent(mContext, SettingsActivity.class));
      }
    });

    String push_notification_token = FirebaseInstanceId.getInstance().getToken();
    KLog.d("Push Notifications TOKEN IS: " + push_notification_token);

    theAPI = TheAPI.Factory.getIstance(getApplicationContext());

    button_yes.setOnClickListener(new View.OnClickListener() {
      @Override public void onClick(View view) {
        KLog.d("confirming login....");
        theAPI.confirmLogin("encrypted_text_here").enqueue(new Callback<Object>() {
          @Override public void onResponse(Call<Object> call, Response<Object> response) {
            KLog.d("server responded ok! " + response.message());
          }

          @Override public void onFailure(Call<Object> call, Throwable t) {
            KLog.e("server failed to respond ! " + t.getLocalizedMessage());
          }
        });
      }
    });

    button_no.setOnClickListener(new View.OnClickListener() {
      @Override public void onClick(View view) {
        KLog.e("NOT confirming login....");
        theAPI.rejectLogin("encrypted_text_here").enqueue(new Callback<Object>() {
          @Override public void onResponse(Call<Object> call, Response<Object> response) {
            KLog.d("server responded ok! " + response.message());
          }

          @Override public void onFailure(Call<Object> call, Throwable t) {
            KLog.e("server failed to respond ! " + t.getLocalizedMessage());
          }
        });
      }
    });

    //Encryption Example....
    //try {
    //  AESHelper.SecretKeys secretKey = AESHelper.generateKeyFromPassword(Settings.Secure.getString(getContentResolver(), Settings.Secure.ANDROID_ID), key);
    //  AESHelper.CipherTextIvMac cipherTextIvMac = AESHelper.encrypt(what??, secretKey);
    //  KLog.d("Encrypted text is: " + new String(cipherTextIvMac.getCipherText()));
    //
    //  //===================================
    //  byte[] decryptBytes = AESHelper.decrypt(cipherTextIvMac, secretKey);
    //  KLog.d("Decrypted text is: " + new String(decryptBytes));
    //} catch (GeneralSecurityException e) {
    //  e.printStackTrace();
    //} catch (UnsupportedEncodingException e) {
    //  e.printStackTrace();
    //}

  }
}


