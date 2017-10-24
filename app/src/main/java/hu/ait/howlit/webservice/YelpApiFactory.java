package hu.ait.howlit.webservice;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;

import hu.ait.howlit.exception.ErrorHandIntercep;
import hu.ait.howlit.intercept.AccessTokenInterceptor;
import hu.ait.howlit.models.AccessToken;
import okhttp3.OkHttpClient;
import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.jackson.JacksonConverterFactory;



public class YelpApiFactory {
    private static final String YELP_API_BASE_URL = "https://api.yelp.com";

    private OkHttpClient httpClient;
    private OkHttpClient authClient;
    private AccessToken accessToken;

    public YelpApiFactory() {}

    public YelpApi createAPI(String clientId, String clientSecret) throws IOException {
        getAccessToken(clientId, clientSecret);

        return getYelpFusionApi();
    }

    public YelpApi createAPI(String accessToken) throws IOException {
        this.accessToken = new AccessToken();
        this.accessToken.setAccessToken(accessToken);
        this.accessToken.setTokenType("Bearer");
        return getYelpFusionApi();
    }

    private YelpApi getYelpFusionApi() {
        httpClient = new OkHttpClient.Builder()
                .addInterceptor(new AccessTokenInterceptor(accessToken))
                .addInterceptor(new ErrorHandIntercep())
                .build();
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(getAPIBaseUrl())
                .addConverterFactory(getJacksonFactory())
                .client(this.httpClient)
                .build();
        return retrofit.create(YelpApi.class);
    }

    public AccessToken getAccessToken(String clientId, String clientSecret) throws IOException {
        authClient = new OkHttpClient.Builder()
                .addInterceptor(new ErrorHandIntercep())
                .build();
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(getAPIBaseUrl())
                .addConverterFactory(getJacksonFactory())
                .client(authClient)
                .build();

        YelpFusionAuthApi client = retrofit.create(YelpFusionAuthApi.class);
        Call<AccessToken> call = client.getToken("client_credentials", clientId, clientSecret);
        //Call<AccessToken> call = client.getToken("client_credentials", "5qCTTm8-e0ZZezY4v0suOA",
        //        "w4MhIE0p7Q0gt1gnVqhiQc2NfACDUIpNwjUgUmwokc1eFMxSOIEJzXJiwcSmH0am");
        accessToken = call.execute().body();
        return accessToken;
    }

    private static JacksonConverterFactory getJacksonFactory(){
        ObjectMapper mapper = new ObjectMapper();
        mapper.disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES);
        return JacksonConverterFactory.create(mapper);
    }

    public String getAPIBaseUrl() {
        return YELP_API_BASE_URL;
    }

}
