package com.example.reactivemvp.interactor;

import com.example.reactivemvp.model.Info;

import java.util.List;

/**
 * Response Model - how the Interactor communicates with presenter.
 */
public interface InfoResponseModel {
    void infoLoaded(List<Info> infoList);
    void errorLoadingInfoData();
}
