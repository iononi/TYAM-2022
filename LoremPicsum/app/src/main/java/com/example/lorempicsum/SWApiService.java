package com.example.lorempicsum;

import android.icu.util.UniversalTimeScale;
import com.example.lorempicsum.LoremHeader;
import retrofit2.Call;
import retrofit2.http.GET;

public interface SWApiService {
    @GET(Utils.API)
    Call<LoremHeader> getLorem();
}
