package com.invoiceasap.testproject.models;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

public class BeerItem extends RealmObject {
  @PrimaryKey private int id;
  private String name;
  private String type;

  public String getName() {
    return name;
  }

  public String getType() {
    return type;
  }
}
