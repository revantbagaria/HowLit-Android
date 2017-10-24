package hu.ait.howlit.exception;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;

import hu.ait.howlit.exception.exceptions.YelpError;
import hu.ait.howlit.exception.exceptions.unexpectedApiError;
import okhttp3.Interceptor;
import okhttp3.Response;

public class ErrorHandIntercep implements Interceptor {
    private static final ObjectMapper objectMapper = new ObjectMapper();
    @Override
    public Response intercept(Chain chain) throws IOException {
        Response response = chain.proceed(chain.request());
        if (!response.isSuccessful()) {
            throw parseError(
                    response.code(),
                    response.message(),
                    response.body() != null ? response.body().string() : null
            );
        }
        return response;
    }

    private YelpError parseError(int code, String message, String responseBody) throws IOException {
        if (responseBody == null) {
            return new unexpectedApiError(code, message);
        }

        JsonNode errorJsonNode = objectMapper.readTree(responseBody).path("error");
        String errorCode = errorJsonNode.path("code").asText();
        String errorText = errorJsonNode.path("description").asText();
        return new unexpectedApiError(code, message, errorCode, errorText);
    }
}
