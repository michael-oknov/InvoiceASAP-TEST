package com.invoiceasap.testproject.utils;

import android.app.Activity;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GoogleApiAvailability;
import javax.inject.Inject;

public class PlatformUtils {
  private Activity activity;
  private static final int PLAY_SERVICES_RESOLUTION_REQUEST = 0xf7;

  @Inject
  public PlatformUtils(Activity activity) {
    this.activity = activity;
  }

  public boolean checkPlayServices() {
    GoogleApiAvailability googleAPI = GoogleApiAvailability.getInstance();
    int result = googleAPI.isGooglePlayServicesAvailable(activity);
    if(result != ConnectionResult.SUCCESS) {
      if(googleAPI.isUserResolvableError(result)) {
        googleAPI.getErrorDialog(activity, result,
            PLAY_SERVICES_RESOLUTION_REQUEST).show();
      }

      return false;
    }

    return true;
  }
}
