package com.example.reactivemvp;

import com.example.reactivemvp.categories.IntegrationTest;
import com.example.reactivemvp.service.InfoResponseWeb;
import com.example.reactivemvp.service.InfoServiceApi;
import com.example.reactivemvp.service.InfoWeb;
import com.google.gson.Gson;

import org.junit.Before;
import org.junit.Test;
import org.junit.experimental.categories.Category;

import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
import rx.observers.TestSubscriber;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.MockitoAnnotations.initMocks;

/**
 * Test the InfoServiceApi
 *
 * NOTE - THIS IS AN INTEGRATION TEST. YOUR GRADLE TEST SUITE SHOULD BE ALTERED TO ONLY RUN THIS TEST IN THAT
 * ENVIRONMENT. THIS IS HERE ONLY AS AN EXAMPLE OF HOW YOU TEST AN ENDPOINT.
 */
@Category(IntegrationTest.class)
public class InfoServiceApiTest extends RxBaseTest {
    InfoServiceApi infoServiceApi;

    @Before
    public void setUp() {
        super.setUp();
        initMocks(this);

        Retrofit rest = new Retrofit.Builder()
                .baseUrl("https://api.myjson.com/")
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create(new Gson()))
                .build();


        infoServiceApi = rest.create(InfoServiceApi.class);
    }

    /**
     * This is an example of how we can test the observable and what results are returned.
     */
    @Test
    public void testLoadInfo() {
        //
        //Arrange
        //
        TestSubscriber<InfoResponseWeb> testSubscriber = new TestSubscriber<>();

        //
        //Act
        //
        infoServiceApi.getInfo().subscribe(testSubscriber);
        testScheduler.triggerActions();

        //
        //Assert
        //
        testSubscriber.assertCompleted();
        testSubscriber.assertNoErrors();
        testSubscriber.assertValueCount(1);

        InfoResponseWeb infoResponseWeb = testSubscriber.getOnNextEvents().get(0);
        assertNotNull(infoResponseWeb);
        assertThat(infoResponseWeb.getInfoArray().length).isGreaterThanOrEqualTo(10);

        InfoWeb infoWeb = infoResponseWeb.getInfoArray()[0];
        assertEquals("Tyrion", infoWeb.getFirstName());
        assertEquals("Lannister", infoWeb.getLastName());
        assertEquals("M", infoWeb.getSex());
        assertEquals(1000000, infoWeb.getSalary());

        assertFalse(true);
    }
}
