package com.androidadvance.springtwofactorauth;

import android.app.Application;
import android.content.Context;
import android.content.pm.ApplicationInfo;
import com.socks.library.KLog;

public class BaseApplication extends Application {

  private static boolean isDebuggable;

  @Override public void onCreate() {
    super.onCreate();

    isDebuggable = (0 != (getApplicationInfo().flags & ApplicationInfo.FLAG_DEBUGGABLE));

    if (isDebuggable) {
      KLog.init(true);
    } else {
      KLog.init(false);
    }
  }

  public static boolean isDebuggable() {
    return isDebuggable;
  }

  @Override public void onLowMemory() {
    super.onLowMemory();
    KLog.e("##### onLowMemory #####");
  }

  public static BaseApplication get(Context context) {
    return (BaseApplication) context.getApplicationContext();
  }
}