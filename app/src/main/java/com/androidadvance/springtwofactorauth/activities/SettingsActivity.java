package com.androidadvance.springtwofactorauth.activities;

import android.content.Context;
import android.content.SharedPreferences;
import android.provider.Settings;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.telephony.TelephonyManager;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import com.androidadvance.springtwofactorauth.R;
import com.androidadvance.springtwofactorauth.utils.AESHelper;
import com.androidadvance.springtwofactorauth.utils.DialogFactory;
import com.socks.library.KLog;
import java.io.UnsupportedEncodingException;
import java.security.GeneralSecurityException;

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
        finish();
      }
    });

    button_reset.setOnClickListener(new View.OnClickListener() {
      @Override public void onClick(View view) {
        getSharedPreferences("PREFS", 0).edit().putString("auth_code", "").commit();
        getSharedPreferences("PREFS", 0).edit().putBoolean("has_auth_code", false).commit();
        DialogFactory.createSimpleOkDialog(mContext, getString(R.string.reset_ok), getString(R.string.application_reseted)).show();
      }
    });

    //try {
    //  AESHelper.SecretKeys secretKey = AESHelper.generateKeyFromPassword(Settings.Secure.getString(getContentResolver(), Settings.Secure.ANDROID_ID), "thisisa1337salt");
    //  AESHelper.CipherTextIvMac cipherTextIvMac = AESHelper.encrypt(editText_auth_code.getText().toString(), secretKey);
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
