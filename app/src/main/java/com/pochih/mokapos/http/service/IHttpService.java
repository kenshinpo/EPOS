package com.pochih.mokapos.http.service;

import com.pochih.mokapos.entity.Item;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;

/**
 * Created by A-Po on 2018/01/02.
 */

public interface IHttpService {
    @GET("photos")
    Call<List<Item>> getItems();
}
