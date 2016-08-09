package com.example.reactivemvp.service;

import retrofit2.http.GET;
import rx.Observable;

/**
 * Retrofit Interface.
 */
public interface InfoServiceApi {

    /*
    Note - the two get's are here to show an example of expanding data where sex type went from M/F to Unknown
     */
    @GET("https://api.myjson.com/bins/18vxt")
    //@GET("https://api.myjson.com/bins/2lsth")
    Observable<InfoResponseWeb> getInfo();

}
