package hu.ait.howlit.intercept;


import java.io.IOException;

import hu.ait.howlit.models.AccessToken;
import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;



public class AccessTokenInterceptor implements Interceptor {

    private AccessToken accessToken;

    public AccessTokenInterceptor(AccessToken accessToken){
        if(accessToken == null) {
            throw new IllegalArgumentException("accessToken");
        }
        this.accessToken = accessToken;
    }

    @Override
    public Response intercept(Chain chain) throws IOException {
        Request originalRequest = chain.request();
        originalRequest = originalRequest.newBuilder()
                .header("Accept", "application/json")
                .header("Authorization", accessToken.getTokenType() + " " + accessToken.getAccessToken())
                .build();
        return chain.proceed(originalRequest);
    }
}
