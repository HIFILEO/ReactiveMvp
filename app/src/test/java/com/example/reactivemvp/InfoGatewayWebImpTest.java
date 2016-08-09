package com.example.reactivemvp;

import com.example.reactivemvp.gateway.InfoGateway;
import com.example.reactivemvp.gateway.InfoGatewayWebImpl;
import com.example.reactivemvp.model.Info;
import com.example.reactivemvp.model.SexType;
import com.example.reactivemvp.service.InfoResponseWeb;
import com.example.reactivemvp.service.InfoServiceApi;
import com.example.reactivemvp.service.InfoWeb;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;

import java.util.List;

import rx.Observable;
import rx.observers.TestSubscriber;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static org.mockito.MockitoAnnotations.initMocks;

/**
 * Test the InfoGatewayWebImpl
 */
public class InfoGatewayWebImpTest extends RxBaseTest {
    InfoGateway infoGateway;

    @Mock
    InfoServiceApi mockInfoServiceApi;

    @Before
    public void setUp() {
        super.setUp();
        initMocks(this);
        infoGateway = new InfoGatewayWebImpl(mockInfoServiceApi);
    }

    /**
     * This is an example of how we can test the observable and what results are returned.
     */
    @Test
    public void testLoadInfo() {
        //
        //Arrange
        //
        TestSubscriber<List<Info>> testSubscriber = new TestSubscriber<>();

        InfoWeb mockInfoWeb = mock(InfoWeb.class);
        when(mockInfoWeb.getFirstName()).thenReturn("Dan");
        when(mockInfoWeb.getLastName()).thenReturn("LEO");
        when(mockInfoWeb.getSex()).thenReturn("M");
        when(mockInfoWeb.getSalary()).thenReturn(1);

        InfoWeb[] infoWebs = new InfoWeb[1];
        infoWebs[0] = mockInfoWeb;

        InfoResponseWeb mockInfoResponseWeb = mock(InfoResponseWeb.class);
        when(mockInfoResponseWeb.getInfoArray()).thenReturn(infoWebs);
        when(mockInfoServiceApi.getInfo()).thenReturn(Observable.just(mockInfoResponseWeb));

        //
        //Act
        //
        infoGateway.loadInfo().subscribe(testSubscriber);
        testScheduler.triggerActions();

        //
        //Assert
        //
        testSubscriber.assertCompleted();
        testSubscriber.assertNoErrors();
        testSubscriber.assertValueCount(1);

        assertEquals("Dan", testSubscriber.getOnNextEvents().get(0).get(0).getFirstName());
        assertEquals("LEO", testSubscriber.getOnNextEvents().get(0).get(0).getLastName());
        assertEquals(1, testSubscriber.getOnNextEvents().get(0).get(0).getSalary());
        assertEquals(SexType.MALE, testSubscriber.getOnNextEvents().get(0).get(0).getSex());
    }

    /**
     * This is an example of how we can test the subscriber without need to mock Info Service.
     */
    @Test
    public void testLoadInfoSubscriptionFunc1() {
        //
        //Arrange
        //
        TestSubscriber<List<Info>> testSubscriber = new TestSubscriber<>();

        InfoWeb mockInfoWeb = mock(InfoWeb.class);
        when(mockInfoWeb.getFirstName()).thenReturn("Dan");
        when(mockInfoWeb.getLastName()).thenReturn("LEO");
        when(mockInfoWeb.getSex()).thenReturn("M");
        when(mockInfoWeb.getSalary()).thenReturn(1);

        InfoWeb[] infoWebs = new InfoWeb[1];
        infoWebs[0] = mockInfoWeb;

        InfoResponseWeb mockInfoResponseWeb = mock(InfoResponseWeb.class);
        when(mockInfoResponseWeb.getInfoArray()).thenReturn(infoWebs);

        InfoGatewayWebImpl.LoadInfoSubscriptionFunc1 loadInfoSubscriptionFunc1 = new
                InfoGatewayWebImpl.LoadInfoSubscriptionFunc1();
        //
        //Act
        //
        loadInfoSubscriptionFunc1.call(mockInfoResponseWeb).subscribe(testSubscriber);
        testScheduler.triggerActions();

        //
        //Assert
        //
        testSubscriber.assertCompleted();
        testSubscriber.assertNoErrors();
        testSubscriber.assertValueCount(1);

        assertEquals("Dan", testSubscriber.getOnNextEvents().get(0).get(0).getFirstName());
        assertEquals("LEO", testSubscriber.getOnNextEvents().get(0).get(0).getLastName());
        assertEquals(1, testSubscriber.getOnNextEvents().get(0).get(0).getSalary());
        assertEquals(SexType.MALE, testSubscriber.getOnNextEvents().get(0).get(0).getSex());
    }
}
