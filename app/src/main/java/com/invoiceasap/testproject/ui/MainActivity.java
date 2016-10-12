package com.invoiceasap.testproject.ui;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.Toolbar;
import android.view.View;
import butterknife.Bind;
import butterknife.ButterKnife;
import com.invoiceasap.testproject.R;
import com.invoiceasap.testproject.UpdatingIntentService;
import com.invoiceasap.testproject.dagger.ActivityModule;
import com.invoiceasap.testproject.ui.base.InjectingToolbarActivity;
import com.invoiceasap.testproject.ui.base.ViewPagerAdapter;
import dagger.Module;

public class MainActivity extends InjectingToolbarActivity {

  @Bind(R.id.container) ViewPager mViewPager;
  private SectionsPagerAdapter mSectionsPagerAdapter;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_main);

    Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
    setSupportActionBar(toolbar);
    mSectionsPagerAdapter = new SectionsPagerAdapter();

    mViewPager.setAdapter(mSectionsPagerAdapter);
    mViewPager.setOffscreenPageLimit(0);

    TabLayout tabLayout = ButterKnife.findById(this, R.id.tabs);
    tabLayout.setupWithViewPager(mViewPager);
    startService(new Intent(this, UpdatingIntentService.class));
  }

  @Override
  protected Object getModule() {
    return new DaggerModule();
  }

  @Module(
      injects = { BeerListView.class, LocationsMapView.class, MainActivity.class },
      addsTo = ActivityModule.class)
  public static class DaggerModule {

  }

  public class SectionsPagerAdapter extends ViewPagerAdapter {

    @Override
    public View getView(int position, ViewPager pager) {
      View v;
      switch (position) {
        case 0:
          v = new BeerListView(MainActivity.this);
          getObjectGraph().inject(v);
          break;
        case 1:
          v = new LocationsMapView(MainActivity.this);
          getObjectGraph().inject(v);
          break;
        default:
          v = new View(MainActivity.this);
      }
      return v;
    }

    @Override
    public int getCount() {
      return 2;
    }

    @Override
    public CharSequence getPageTitle(int position) {
      int stringId;
      switch (position) {
        case 0:
          stringId = R.string.list;
          break;
        case 1:
          stringId = R.string.map;
          break;
        default:
          stringId = R.string.no_name;
      }
      return getString(stringId);
    }
  }
}
