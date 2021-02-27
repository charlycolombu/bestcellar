package com.example.caveavinmmm.api;

import androidx.annotation.NonNull;

import com.example.caveavinmmm.response.ImaggaResponse;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

import java.util.Map;

import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Header;
import retrofit2.http.HeaderMap;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;

public interface UploadApis {
    @Multipart
    @POST("text")
    Call<ImaggaResponse> uploadImage(@Header("Authorization") String value, @Part MultipartBody.Part image);

    @POST("text")
    Call<ResponseBody> uploadImage(@Header("Authorization") String value);
}
