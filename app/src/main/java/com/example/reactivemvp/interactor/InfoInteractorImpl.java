package com.example.reactivemvp.interactor;

import android.support.annotation.VisibleForTesting;
import android.telecom.GatewayInfo;

import com.example.reactivemvp.gateway.InfoGateway;
import com.example.reactivemvp.model.Info;

import java.util.List;

import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;
import rx.schedulers.Schedulers;
import timber.log.Timber;

/**
 * Info Interactor Implementation.
 */
public class InfoInteractorImpl implements InfoInteractor {
    private InfoResponseModel infoResponseModel;
    private InfoGateway infoGateway;

    public InfoInteractorImpl(InfoGateway infoGateway) {
        this.infoGateway = infoGateway;
    }

    @Override
    public void setInfoResponseModel(InfoResponseModel infoResponseModel) {
        this.infoResponseModel = infoResponseModel;
    }

    @Override
    public void loadInfo() {
        infoGateway.loadInfo()
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(new LoadInfoSubscriptionAction1(this.infoResponseModel),
                        new LoadInfoSubscriptionErrorAction1(this.infoResponseModel));
    }

    /**
     * Visible for testing that a load info subscription does the right thing.
     */
    @VisibleForTesting
    public static class LoadInfoSubscriptionAction1 implements Action1<List<Info>> {
        private InfoResponseModel infoResponseModel;

        public LoadInfoSubscriptionAction1(InfoResponseModel infoResponseModel) {
            this.infoResponseModel = infoResponseModel;
        }

        @Override
        public void call(List<Info> infoList) {
            if (infoResponseModel != null) {
                infoResponseModel.infoLoaded(infoList);
            }

            /*
            Note - An interactor has interactions with many objects in order to complete a task. Any other interactions
            would be placed here.
             */
        }
    }

    /**
     * Visible for testing that a load info subscription does the right thing during an error.
     */
    @VisibleForTesting
    public static class LoadInfoSubscriptionErrorAction1 implements Action1<Throwable> {
        private InfoResponseModel infoResponseModel;

        public LoadInfoSubscriptionErrorAction1(InfoResponseModel infoResponseModel) {
            this.infoResponseModel = infoResponseModel;
        }

        @Override
        public void call(Throwable throwable) {
            Timber.e("Error: " + throwable.getLocalizedMessage());
            if (infoResponseModel != null) {
                infoResponseModel.errorLoadingInfoData();
            }
        }
    }
}
