package com.invoiceasap.testproject.ui;

import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.GoogleMapOptions;
import com.google.android.gms.maps.MapsInitializer;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.invoiceasap.testproject.models.PlaceItem;
import com.invoiceasap.testproject.utils.PlatformUtils;
import io.realm.Realm;
import io.realm.RealmResults;
import java.util.List;
import javax.inject.Inject;

public class LocationsMapView extends com.google.android.gms.maps.MapView {
  private final static float ZOOM_LEVEL = 12;
  private static GoogleMap mMap;
  @Inject PlatformUtils platformUtils;
  private Realm realm;
  private Handler handler = new Handler();

  public LocationsMapView(Context context) {
    super(context, new GoogleMapOptions());
    Log.e("Michael", "LocationsMapView");
    realm = Realm.getDefaultInstance();
  }

  @Override
  protected void onAttachedToWindow() {
    super.onAttachedToWindow();
    Log.e("Michael", "onAttachedToWindow");
    if (platformUtils.checkPlayServices()) setupMap();
  }

  @Override
  protected void onDetachedFromWindow() {
    super.onDetachedFromWindow();
    realm.close();
    onStop();
    onDestroy();
  }

  private void setupMap() {
    MapsInitializer.initialize(getContext());
    onCreate(new Bundle());
    onResume();
    getMapAsync(this::initMap);
  }

  private void initMap(GoogleMap map) {
    Log.e("Michael", "initMap");
    mMap = map;
    fetchData();
  }

  private void fetchData() {
    RealmResults<PlaceItem> result = realm.where(PlaceItem.class).findAllAsync();
    result.addChangeListener(this::showPlaces);
  }

  public void showPlaces(List<PlaceItem> places) {
    if (places == null || places.isEmpty()) {

      return;
    }
    if (places.get(0) != null) {
      CameraUpdate cameraUpdate = CameraUpdateFactory.newLatLngZoom(
          new LatLng(places.get(0).getLatitude(), places.get(0).getLongitude()), ZOOM_LEVEL);
      mMap.animateCamera(cameraUpdate);
    }
    mMap.clear();
    for (PlaceItem place : places) {
      MarkerOptions markerOption = new MarkerOptions().position(place.getLocation());
      markerOption.title(place.getName());
      mMap.addMarker(markerOption);
    }
  }
}
