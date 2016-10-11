package com.invoiceasap.testproject;

import android.app.IntentService;
import android.content.Context;
import com.fizzbuzz.android.dagger.InjectingApplication;
import com.fizzbuzz.android.dagger.Injector;
import com.invoiceasap.testproject.dagger.ServiceModule;
import dagger.Module;
import dagger.ObjectGraph;
import dagger.Provides;
import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;
import java.util.ArrayList;
import java.util.List;
import javax.inject.Qualifier;
import javax.inject.Singleton;

import static java.lang.annotation.ElementType.FIELD;
import static java.lang.annotation.ElementType.METHOD;
import static java.lang.annotation.ElementType.PARAMETER;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

/**
 * Manages an ObjectGraph on behalf of an Service.  This graph is created by extending the
 * application-scope graph with
 * Service-specific module(s).
 */
public abstract class InjectingIntentService extends IntentService implements Injector {

  private ObjectGraph mObjectGraph;

  public InjectingIntentService() {
    super(InjectingIntentService.class.getName());
  }

  public InjectingIntentService(String name) {
    super(name);
  }

  /**
   * Creates an object graph for this Service by extending the application-scope object graph with
   * the modules
   * returned by {@link #getModules()}.
   * <p/>
   * Injects this Service using the created graph.
   */
  @Override
  public void onCreate() {
    super.onCreate();

    mObjectGraph = ((Injector) getApplication()).getObjectGraph().plus(getModules().toArray());

    // then inject ourselves
    inject(this);
  }

  /**
   * Gets this Service's object graph.
   *
   * @return the object graph
   */
  @Override
  public ObjectGraph getObjectGraph() {
    return mObjectGraph;
  }

  /**
   * Injects a target object using this Service's object graph.
   *
   * @param target the target object
   */
  public void inject(Object target) {
   if(mObjectGraph == null)
     throw new IllegalStateException("object graph must be initialized prior to calling inject");
    mObjectGraph.inject(target);
  }

  /**
   * Returns the list of dagger modules to be included in this Service's object graph.  Subclasses
   * that override
   * this method should add to the list returned by super.getModules().
   *
   * @return the list of modules
   */
  protected List<Object> getModules() {
    List<Object> result = new ArrayList<Object>();
    result.add(new InjectingIntentServiceModule(this, this));
    result.add(new ServiceModule());
    return result;
  }

  /**
   * The dagger module associated with {@link com.fizzbuzz.android.dagger.InjectingService}.
   */
  @Module(
      library = true) public static class InjectingIntentServiceModule {
    private android.app.Service mService;
    private Injector mInjector;

    /**
     * Class constructor.
     *
     * @param service the Service with which this module is associated.
     */
    public InjectingIntentServiceModule(android.app.Service service, Injector injector) {
      mService = service;
      mInjector = injector;
    }

    /**
     * Provides the Application Context
     *
     * @return the Application Context
     */
    @Provides
    @Singleton
    @InjectingApplication.InjectingApplicationModule.Application
    public Context provideApplicationContext() {
      return mService.getApplicationContext();
    }

    @Provides
    @Singleton
    public android.app.Service provideService() {
      return mService;
    }

    @Provides
    @Singleton
    @Service
    public Injector provideServiceInjector() {
      return mInjector;
    }

    @Qualifier @Target({ FIELD, PARAMETER, METHOD }) @Documented @Retention(RUNTIME)
    public @interface Service {
    }
  }
}
