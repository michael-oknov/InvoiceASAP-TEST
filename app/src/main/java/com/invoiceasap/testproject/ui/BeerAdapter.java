package com.invoiceasap.testproject.ui;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import butterknife.Bind;
import butterknife.ButterKnife;
import com.invoiceasap.testproject.R;
import com.invoiceasap.testproject.models.BeerItem;
import java.util.ArrayList;
import java.util.List;

public class BeerAdapter extends RecyclerView.Adapter<BeerAdapter.ViewHolder> {
  private List<BeerItem> mDataset = new ArrayList<>();
  @Override
  public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
    View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.adapter_beer_row, parent, false);
    return new ViewHolder(v);
  }

  @Override
  public void onBindViewHolder(ViewHolder holder, int position) {
    BeerItem item = mDataset.get(position);
    holder.nameView.setText(item.getName());
    holder.typeView.setText(item.getType());
  }

  @Override
  public int getItemCount() {
    return mDataset.size();
  }

  public void setDataset(List<BeerItem> beerItems){
    mDataset.clear();
    mDataset.addAll(beerItems);
    notifyDataSetChanged();
  }

  class ViewHolder extends RecyclerView.ViewHolder {
    @Bind(R.id.name) TextView nameView;
    @Bind(R.id.type) TextView typeView;

    public ViewHolder(View itemView) {
      super(itemView);
      ButterKnife.bind(this, itemView);
    }
  }
}
