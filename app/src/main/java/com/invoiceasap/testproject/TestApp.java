package com.invoiceasap.testproject;

import com.fizzbuzz.android.dagger.InjectingApplication;
import com.invoiceasap.testproject.dagger.AppModule;
import com.squareup.leakcanary.LeakCanary;
import io.realm.Realm;
import java.util.List;

public class TestApp extends InjectingApplication {

  @Override
  public void onCreate() {
    super.onCreate();
    LeakCanary.install(this);
    Realm.init(this);
  }

  @Override
  protected List<Object> getModules() {
    List<Object> modules = super.getModules();
    modules.add(new AppModule(this));
    return modules;
  }
}
