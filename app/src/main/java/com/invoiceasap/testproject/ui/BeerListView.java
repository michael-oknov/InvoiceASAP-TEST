package com.invoiceasap.testproject.ui;

import android.content.Context;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import com.invoiceasap.testproject.models.BeerItem;
import io.realm.Realm;
import io.realm.RealmResults;

public class BeerListView extends RecyclerView {
  private BeerAdapter adapter;
  private Realm realm;
  private RealmResults<BeerItem> result;

  public BeerListView(Context context) {
    super(context, null);
    setLayoutManager(new LinearLayoutManager(context));
    adapter = new BeerAdapter();
    setAdapter(adapter);
  }

  @Override
  protected void onAttachedToWindow() {
    super.onAttachedToWindow();
    realm = Realm.getDefaultInstance();
    result = realm.where(BeerItem.class)
        .findAllAsync();
    result.addChangeListener(adapter::setDataset);
  }


  @Override
  protected void onDetachedFromWindow() {
    super.onDetachedFromWindow();
    realm.close();
  }
}



