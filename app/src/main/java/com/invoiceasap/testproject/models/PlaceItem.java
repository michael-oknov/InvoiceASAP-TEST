package com.invoiceasap.testproject.models;

import com.google.android.gms.maps.model.LatLng;
import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

public class PlaceItem extends RealmObject {
  @PrimaryKey private int id;
  private String name;
  private double latitude;
  private double longitude;

  public double getLatitude() {
    return latitude;
  }

  public double getLongitude() {
    return longitude;
  }

  public LatLng getLocation() {
    return new LatLng(latitude, longitude);
  }

  public String getName() {
    return name;
  }
}
