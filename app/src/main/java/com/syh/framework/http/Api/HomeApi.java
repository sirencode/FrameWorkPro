package com.syh.framework.http.Api;


import com.syh.framework.http.model.DataDemo;
import com.syh.framework.http.model.HttpBaseResult;

import java.util.List;

import io.reactivex.Observable;
import retrofit2.http.GET;
import retrofit2.http.Query;

/**
 * Created bg shenyonghe on 2018/5/22.
 */
public interface HomeApi {

    @GET("query")
    Observable<HttpBaseResult<List<DataDemo>>> getTest(@Query("type") String type, @Query("postid") String postid);
}
