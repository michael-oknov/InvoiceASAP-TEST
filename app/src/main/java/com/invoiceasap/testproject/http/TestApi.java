package com.invoiceasap.testproject.http;

import com.invoiceasap.testproject.models.BeerList;
import com.invoiceasap.testproject.models.PlaceList;
import retrofit2.Call;
import retrofit2.http.GET;

/*I know that we have one api method, but it seems that it should be separated calls*/
public interface TestApi {
  @GET("ontap-taps-search.json")
  Call<PlaceList> getPlaces();

  @GET("ontap-taps-search.json")
  Call<BeerList> getBeers();
}
