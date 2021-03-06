package hu.ait.howlit.webservice;

import hu.ait.howlit.models.AccessToken;
import retrofit2.Call;
import retrofit2.http.POST;
import retrofit2.http.Query;


public interface YelpFusionAuthApi {
    @POST("/oauth2/token")
    Call<AccessToken> getToken(@Query("grant_type") String grantType,
                               @Query("client_id") String clientId,
                               @Query("client_secret") String clientSecret);
}
