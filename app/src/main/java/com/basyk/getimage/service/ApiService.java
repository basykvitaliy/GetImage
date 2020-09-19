package com.basyk.getimage.service;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.GET;

public interface ApiService {

    @GET("task-m-001/list.php")
    Call<ResponseBody> getImages2();
}
