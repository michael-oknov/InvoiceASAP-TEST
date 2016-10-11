package com.invoiceasap.testproject.dagger;

import android.app.AlarmManager;
import android.content.Context;
import com.invoiceasap.testproject.TestApp;
import dagger.Module;
import dagger.Provides;
import javax.inject.Singleton;

@Module(injects = TestApp.class, library = true, includes = NetworkModule.class)
public class AppModule {
  private TestApp app;

  public AppModule(TestApp app) {
    this.app = app;
  }

  @Provides
  @Singleton
  TestApp providTestApplication() {
    return app;
  }

  @Provides
  @Singleton
  AlarmManager providAlarmManager() {
    return (AlarmManager) app.getSystemService(Context.ALARM_SERVICE);
  }
}
