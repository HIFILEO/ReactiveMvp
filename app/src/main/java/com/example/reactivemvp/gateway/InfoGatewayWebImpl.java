package com.example.reactivemvp.gateway;

import android.support.annotation.VisibleForTesting;

import com.example.reactivemvp.model.Info;
import com.example.reactivemvp.model.InfoImpl;
import com.example.reactivemvp.model.SexType;
import com.example.reactivemvp.service.InfoResponseWeb;
import com.example.reactivemvp.service.InfoServiceApi;
import com.example.reactivemvp.service.InfoWeb;

import java.util.ArrayList;
import java.util.List;

import rx.Observable;
import rx.functions.Action1;
import rx.functions.Func1;
import rx.schedulers.Schedulers;
import timber.log.Timber;

/**
 * Implementation of GatewayInterface when loading data from web.
 */
public class InfoGatewayWebImpl implements InfoGateway {
    private InfoServiceApi infoServiceApi;

    public InfoGatewayWebImpl(InfoServiceApi infoServiceApi) {
        this.infoServiceApi = infoServiceApi;
    }

    @Override
    public Observable<List<Info>> loadInfo() {
        /*
        Notes - Load data from web on scheduler thread. Translate the web response to our internal business response
        on computation thread. Return observable.
         */
        return infoServiceApi.getInfo()
                .subscribeOn(Schedulers.io())
                .observeOn(Schedulers.computation())
                .flatMap(new LoadInfoSubscriptionFunc1())
                .doOnError(new Action1<Throwable>() {
                    @Override
                    public void call(Throwable throwable) {
                        Timber.e("Error: " + throwable.getLocalizedMessage());
                    }
                });
    }

    @VisibleForTesting
    public static class LoadInfoSubscriptionFunc1 implements Func1<InfoResponseWeb, Observable<List<Info>>> {

        @Override
        public Observable<List<Info>> call(InfoResponseWeb infoResponseWeb) {
            //
            //Sleep to simulate longer load times
            //
            try {
                Thread.sleep(3000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            //
            //Translate
            //
            List<Info> infoList = new ArrayList<>();

            for(InfoWeb infoWeb : infoResponseWeb.getInfoArray()) {
                @SexType  int sexType;
                if (infoWeb.getSex().equalsIgnoreCase("M")) {
                    sexType = SexType.MALE;
                } else if (infoWeb.getSex().equalsIgnoreCase("F")) {
                    sexType = SexType.FEMALE;
                } else {
                    sexType = SexType.UNKNOWN;
                }

                InfoImpl infoImpl = new InfoImpl(
                        infoWeb.getFirstName(),
                        infoWeb.getLastName(),
                        infoWeb.getSalary(),
                        sexType
                );

                infoList.add(infoImpl);
            }

            //
            //Return Observable
            //
            return Observable.just(infoList);
        }
    }
}
