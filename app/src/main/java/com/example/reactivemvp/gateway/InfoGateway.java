package com.example.reactivemvp.gateway;

import com.example.reactivemvp.model.Info;

import java.util.List;

import rx.Observable;

/**
 * The Gateway Interface
 */
public interface InfoGateway {
    Observable<List<Info>> loadInfo();
}
