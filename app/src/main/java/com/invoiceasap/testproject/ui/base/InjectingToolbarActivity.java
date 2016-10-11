package com.invoiceasap.testproject.ui.base;

import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import butterknife.ButterKnife;
import com.fizzbuzz.android.dagger.InjectingActivityModule;
import com.fizzbuzz.android.dagger.Injector;
import com.invoiceasap.testproject.dagger.ActivityModule;
import dagger.Module;
import dagger.ObjectGraph;
import java.util.ArrayList;
import java.util.List;

import static com.fizzbuzz.android.dagger.Preconditions.checkState;


public abstract class InjectingToolbarActivity extends AppCompatActivity implements Injector {

  private ObjectGraph mObjectGraph;

  @Override
  public final ObjectGraph getObjectGraph() {
    return mObjectGraph;
  }

  @Override
  public void inject(Object target) {
    checkState(mObjectGraph != null, "object graph must be assigned prior to calling inject");
    mObjectGraph.inject(target);
  }

  @Override
  protected void onCreate(android.os.Bundle savedInstanceState) {
    ObjectGraph appGraph = ((Injector) getApplication()).getObjectGraph();
    List<Object> activityModules = getModules();
    mObjectGraph = appGraph.plus(activityModules.toArray());

    inject(this);
    super.onCreate(savedInstanceState);
  }

  @Override
  public void setContentView(int layoutResID) {
    super.setContentView(layoutResID);
    ButterKnife.bind(this);
  }

  @Override
  protected void onDestroy() {
    mObjectGraph = null;
    super.onDestroy();
  }

  @Override
  public boolean onOptionsItemSelected(MenuItem item) {
    if (item.getItemId() == android.R.id.home) {
      onBackPressed();
    }
    return super.onOptionsItemSelected(item);
  }

  protected Object getModule() {
    return null;
  }

  /**
   * Returns the list of dagger modules to be included in this FragmentActivity's object graph.
   * Subclasses that
   * override this method should add to the list returned by super.getModules().
   *
   * @return the list of modules
   */
  protected List<Object> getModules() {
    List<Object> result = new ArrayList<Object>();
    result.add(new InjectingActivityModule(this, this));
    result.add(new InjectingToolbarActivityModule());
    if (getModule() != null) {
      result.add(getModule());
    }
    return result;
  }

  @Module(
      complete = true,
      library = false,
      addsTo = ActivityModule.class)
  public static class InjectingToolbarActivityModule {

  }
}