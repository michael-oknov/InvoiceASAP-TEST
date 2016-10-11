package com.invoiceasap.testproject.ui;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.Toolbar;
import android.view.View;
import butterknife.Bind;
import butterknife.ButterKnife;
import com.invoiceasap.testproject.UploadingIntentService;
import com.invoiceasap.testproject.R;
import com.invoiceasap.testproject.dagger.ActivityModule;
import com.invoiceasap.testproject.ui.base.InjectingToolbarActivity;
import com.invoiceasap.testproject.ui.base.ViewPagerAdapter;
import dagger.Module;

public class MainActivity extends InjectingToolbarActivity {

  private SectionsPagerAdapter mSectionsPagerAdapter;

  @Bind(R.id.container) ViewPager mViewPager;

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
    startService(new Intent(this, UploadingIntentService.class));

  }


  public class SectionsPagerAdapter extends ViewPagerAdapter {

    @Override
    public View getView(int position, ViewPager pager) {
      View v;
      switch (position){
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
      String title;
      switch (position){
        case 0:
          title = "List";
          break;
        case 1:
          title = "Map";
          break;
        default:
          title = "No name";
      }
      return title;
    }
  }

  @Override
  protected Object getModule() {
    return new DaggerModule();
  }

  @Module(
      injects = { BeerListView.class, LocationsMapView.class, MainActivity.class},
      addsTo = ActivityModule.class)
  public static class DaggerModule {

  }

}
