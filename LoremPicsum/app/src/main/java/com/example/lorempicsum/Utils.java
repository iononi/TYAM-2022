package com.example.lorempicsum;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import com.example.lorempicsum.SWApiService;

public class Utils {
    public static final String API = "/v2/list";
    public static final String TITLE = "Lorem Picsum";

    private static final String BASE_URL = "https://picsum.photos/";

    private static SWApiService service;

    public static SWApiService createService () {
        if (service == null) {
            Retrofit retrofit = new Retrofit.Builder()
                    .addConverterFactory(GsonConverterFactory.create())
                    .baseUrl (BASE_URL)
                    .build();

            service = retrofit.create (SWApiService.class);
        }

        return service;
    }
}
