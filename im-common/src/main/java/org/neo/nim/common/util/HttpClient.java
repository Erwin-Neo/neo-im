package org.neo.nim.common.util;

import okhttp3.*;

import java.io.IOException;

/**
 * @version : 1.0
 * @description :
 */
public class HttpClient {

    private static final MediaType mediaType = MediaType.parse("application/json");

    public static Response call(OkHttpClient okHttpClient, String params, String url) throws IOException {
        RequestBody requestBody = RequestBody.create(mediaType, params);

        Request request = new Request.Builder()
                .url(url)
                .post(requestBody)
                .build();

        Response response = okHttpClient.newCall(request).execute();
        if (!response.isSuccessful()) {
            throw new IOException("Unexpected code " + response);
        }

        return response;
    }

}
