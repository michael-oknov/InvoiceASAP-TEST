package com.invoiceasap.testproject;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Intent;
import com.invoiceasap.testproject.http.TestApi;
import com.invoiceasap.testproject.models.BeerList;
import com.invoiceasap.testproject.models.PlaceList;
import io.realm.Realm;
import java.io.IOException;
import java.util.concurrent.TimeUnit;
import javax.inject.Inject;
import retrofit2.Response;

public class UpdatingIntentService extends InjectingIntentService {
  @Inject TestApi testApi;
  @Inject AlarmManager alarmManager;
  private Realm realm;

  public UpdatingIntentService() {
    super("UpdatingIntentService");
  }

  @Override
  protected void onHandleIntent(Intent intent) {
    Intent nextIntent = new Intent(this, UpdatingIntentService.class);
    PendingIntent pendingIntent = PendingIntent.getService(this, 0, nextIntent, 0);
    alarmManager.cancel(pendingIntent);
    realm = Realm.getDefaultInstance();
    downloadPlaces();
    downloadBeers();

    alarmManager.set(AlarmManager.RTC_WAKEUP,
        System.currentTimeMillis() + TimeUnit.MINUTES.toMillis(3), pendingIntent);
  }

  private void downloadBeers() {
    try {
      Response<BeerList> response = testApi.getBeers().execute();
      realm.beginTransaction();
      realm.copyToRealmOrUpdate(response.body().getBeers());
      realm.commitTransaction();
    } catch (IOException ioe) {
      ioe.printStackTrace();
    }
  }

  private void downloadPlaces() {
    try {
      Response<PlaceList> response = testApi.getPlaces().execute();
      realm.beginTransaction();
      realm.copyToRealmOrUpdate(response.body().getLocations());
      realm.commitTransaction();
    } catch (IOException ioe) {
      ioe.printStackTrace();
    }
  }
}
