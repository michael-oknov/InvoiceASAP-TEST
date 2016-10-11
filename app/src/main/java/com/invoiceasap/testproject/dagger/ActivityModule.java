package com.invoiceasap.testproject.dagger;

import android.app.Activity;
import com.fizzbuzz.android.dagger.InjectingActivityModule;
import com.invoiceasap.testproject.ui.MainActivity;
import com.invoiceasap.testproject.utils.PlatformUtils;
import dagger.Module;
import dagger.Provides;
import javax.inject.Singleton;

@Module(injects = { MainActivity.class
}, library = true,
        addsTo = AppModule.class,
        includes = InjectingActivityModule.class)
public class ActivityModule {

  @Singleton
  @Provides
  PlatformUtils providePlatformUtils(Activity activity){
    return new PlatformUtils(activity);
  }
}
