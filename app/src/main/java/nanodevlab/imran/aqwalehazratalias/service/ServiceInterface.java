package nanodevlab.imran.aqwalehazratalias.service;

import nanodevlab.imran.aqwalehazratalias.Models.VideoResponse;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface ServiceInterface {

    @GET("search")
    Call<VideoResponse> getVideoList(@Query("part")String part,
                                     @Query("channelId")String channelId,
                                     @Query("maxResults")String maxResults,
                                     @Query("pageToken")String pageToken,
                                     @Query("type")String type,
                                     @Query("key")String key);



}



