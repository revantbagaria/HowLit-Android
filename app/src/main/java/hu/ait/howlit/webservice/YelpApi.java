package hu.ait.howlit.webservice;

import java.util.Map;

import hu.ait.howlit.models.AutoComplete;
import hu.ait.howlit.models.Business;
import hu.ait.howlit.models.Reviews;
import hu.ait.howlit.models.SearchResponse;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;
import retrofit2.http.QueryMap;



public interface YelpApi {
    @GET("/v3/businesses/search")
    Call<SearchResponse> getBusinessSearch(@QueryMap Map<String, String> params);

    @GET("/v3/businesses/search/phone")
    Call<SearchResponse> getPhoneSearch(@Query("phone") String phone);

    @GET("/v3/transactions/{transaction_type}/search")
    Call<SearchResponse> getTransactionSearch(@Path("transaction_type") String transactionType, @QueryMap Map<String, String> params);

    @GET("/v3/businesses/{id}")
    Call<Business> getBusiness(@Path("id") String id);

    @GET("/v3/businesses/{id}/reviews")
    Call<Reviews> getBusinessReviews(@Path("id") String id, @Query("locale") String locale);

    @GET("/v3/autocomplete")
    Call<AutoComplete> getAutocomplete(@QueryMap Map<String, String> params);
}
