package com.invoiceasap.testproject.dagger;

import com.invoiceasap.testproject.UpdatingIntentService;
import dagger.Module;

@Module(injects = { UpdatingIntentService.class}, library = false,
        addsTo = AppModule.class)
public class ServiceModule {
}
