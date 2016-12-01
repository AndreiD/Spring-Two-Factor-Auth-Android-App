package com.androidadvance.springtwofactorauth.activities;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import com.androidadvance.springtwofactorauth.BaseActivity;
import com.androidadvance.springtwofactorauth.R;
import com.google.firebase.iid.FirebaseInstanceId;
import com.socks.library.KLog;

public class MainActivity extends BaseActivity {

  private MainActivity mContext;
  private ImageButton imageButton_settings;
  private Button button_yes;
  private Button button_no;
  private String auth_code;

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
      KLog.d("AUTH CODE: " + auth_code);
    }

    imageButton_settings.setOnClickListener(new View.OnClickListener() {
      @Override public void onClick(View view) {
        startActivity(new Intent(mContext, SettingsActivity.class));
      }
    });

    KLog.d("TOKEN IS: " + FirebaseInstanceId.getInstance().getToken());

  }
}


