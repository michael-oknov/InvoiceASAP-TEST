package com.invoiceasap.testproject.dagger;

import com.invoiceasap.testproject.UploadingIntentService;
import dagger.Module;

@Module(injects = { UploadingIntentService.class}, library = false,
        addsTo = AppModule.class)
public class ServiceModule {
}
