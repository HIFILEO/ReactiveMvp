package com.example.reactivemvp;

import com.example.reactivemvp.categories.UnitTest;
import com.example.reactivemvp.gateway.InfoGateway;
import com.example.reactivemvp.interactor.InfoInteractor;
import com.example.reactivemvp.interactor.InfoInteractorImpl;
import com.example.reactivemvp.interactor.InfoResponseModel;
import com.example.reactivemvp.model.Info;

import org.junit.Before;
import org.junit.Test;
import org.junit.experimental.categories.Category;
import org.mockito.Mock;

import java.util.ArrayList;
import java.util.List;

import rx.Observable;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.mockito.MockitoAnnotations.initMocks;

/**
 * Test the InfoInteractor
 */
@Category(UnitTest.class)
public class InfoInteractorTest extends RxBaseTest {
    InfoInteractor infoInteractor;
    InfoInteractorImpl infoInteractorImpl;

    @Mock
    InfoGateway mockInfoGateway;
    @Mock
    InfoResponseModel mockInfoResponseModel;

    @Before
    public void setUp() {
        super.setUp();
        initMocks(this);

        infoInteractorImpl = new InfoInteractorImpl(mockInfoGateway);
        infoInteractorImpl.setInfoResponseModel(mockInfoResponseModel);

        infoInteractor = infoInteractorImpl;
    }

    @Test
    public void testInfoInteractor_load_calls_gateway() {
        //
        //Arrange
        //
        List<Info> infoList = new ArrayList<>();
        when(mockInfoGateway.loadInfo()).thenReturn(Observable.just(infoList));

        //
        //Act
        //
        infoInteractor.loadInfo();

        //
        //Assert
        //
        verify(mockInfoGateway).loadInfo();
    }

    @Test
    public void testInfoInteractor_errorLoadingInfoData() {
        //
        //Arrange
        //
        InfoInteractorImpl.LoadInfoSubscriptionErrorAction1 loadInfoSubscriptionErrorAction1 =
                new InfoInteractorImpl.LoadInfoSubscriptionErrorAction1(mockInfoResponseModel);

        Throwable mockThrowable = mock(Throwable.class);
        when(mockThrowable.getLocalizedMessage()).thenReturn("");


        //
        //Act
        //
        loadInfoSubscriptionErrorAction1.call(mockThrowable);

        //
        //Assert
        //
        verify(mockInfoResponseModel).errorLoadingInfoData();
    }

    @Test
    public void testInfoInteractor_null_does_not_call_infoLoaded() {
        //
        //Arrange
        //
        InfoInteractorImpl.LoadInfoSubscriptionErrorAction1 loadInfoSubscriptionErrorAction1 =
                new InfoInteractorImpl.LoadInfoSubscriptionErrorAction1(null);

        Throwable mockThrowable = mock(Throwable.class);
        when(mockThrowable.getLocalizedMessage()).thenReturn("");


        //
        //Act
        //
        loadInfoSubscriptionErrorAction1.call(mockThrowable);

        //
        //Assert
        //
        verify(mockInfoResponseModel, never()).errorLoadingInfoData();
    }

    @SuppressWarnings("unchecked")
    @Test
    public void testInfoInteractor_call_to_infoLoaded() {
        //
        //Arrange
        //
        InfoInteractorImpl.LoadInfoSubscriptionAction1 loadInfoSubscriptionAction1 =
                new InfoInteractorImpl.LoadInfoSubscriptionAction1(mockInfoResponseModel);


        List<Info> mockInfoList = mock(List.class);

        //
        //Act
        //
        loadInfoSubscriptionAction1.call(mockInfoList);

        //
        //Assert
        //
        verify(mockInfoResponseModel).infoLoaded(mockInfoList);
    }
}
