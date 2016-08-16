package com.example.reactivemvp;

import com.example.reactivemvp.categories.UnitTest;
import com.example.reactivemvp.interactor.InfoInteractor;
import com.example.reactivemvp.model.DisplayInfo;
import com.example.reactivemvp.model.Info;
import com.example.reactivemvp.model.SexType;
import com.example.reactivemvp.presenter.InfoPresenter;
import com.example.reactivemvp.presenter.InfoPresenterImpl;
import com.example.reactivemvp.presenter.InfoViewModel;

import org.junit.Before;
import org.junit.Test;
import org.junit.experimental.categories.Category;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;

import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.verify;
import static org.mockito.MockitoAnnotations.initMocks;

/**
 * Test the InfoPresenter
 */
@Category(UnitTest.class)
public class InfoPresenterTest extends RxBaseTest {
    InfoPresenter infoPresenter;
    InfoPresenterImpl infoPresenterImpl;

    @Mock
    InfoInteractor mockInfoInteractor;
    @Mock
    InfoViewModel mockInfoViewModel;

    @Before
    public void setUp() {
        super.setUp();
        initMocks(this);

        infoPresenterImpl = new InfoPresenterImpl(mockInfoInteractor, mockInfoViewModel);
        infoPresenter = infoPresenterImpl;
    }

    @Test
    public void testStart() {
        //
        //Arrange
        //

        //
        //Act
        //
        infoPresenter.loadInfo();

        //
        //Assert
        //
        verify(mockInfoInteractor).loadInfo();
        verify(mockInfoViewModel).showInProgress(true);
    }

    @Test
    public void testToggleList() {
        //
        //Arrange
        //

        //
        //Act
        //
        infoPresenter.toggleList();

        //
        //Assert
        //
        verify(mockInfoViewModel).showDataGridStyle();
        verify(mockInfoViewModel).setMenuTitle(R.string.grid_view);
    }

    @Test
    public void testToggleList_toList() {
        //
        //Arrange
        //

        //
        //Act
        //
        infoPresenter.toggleList();
        infoPresenter.toggleList();

        //
        //Assert
        //
        verify(mockInfoViewModel).showDataListStyle();
        verify(mockInfoViewModel).setMenuTitle(R.string.list_view);
    }

    @SuppressWarnings("unchecked")
    @Test
    public void testInfoLoaded() {
        //
        //Arrange
        //

        //
        //Act
        //
        infoPresenterImpl.infoLoaded(new ArrayList<Info>());

        //
        //Assert
        //
        verify(mockInfoViewModel).showInProgress(false);

        ArgumentCaptor<List<DisplayInfo>> argumentCaptor = ArgumentCaptor.forClass(List.class);
        verify(mockInfoViewModel).createAdapter(argumentCaptor.capture());
        assertThat(argumentCaptor.getValue().size()).isEqualTo(0);

        verify(mockInfoViewModel).showDataListStyle();
    }

    @Test
    public void testErrorLoadingInfoData() {
        //
        //Arrange
        //

        //
        //Act
        //
        infoPresenterImpl.errorLoadingInfoData();

        //
        //Assert
        //
        verify(mockInfoViewModel).showError();
    }

    @Test
    public void testDisplayInfoImp() {
        //
        //Arrange
        //
        InfoPresenterImpl.DisplayInfoImp displayInfoImp =
                new InfoPresenterImpl.DisplayInfoImp(
                        "Dan",
                        "Leonardis",
                        1000000,
                        SexType.MALE
                );
        //
        //Act
        //

        //
        //Assert
        //
        assertEquals("Dan Leonardis", displayInfoImp.getName());
        assertEquals("DL", displayInfoImp.getAbbreviation());
        assertEquals("$1,000,000.00", displayInfoImp.getSalary());
        assertEquals( R.color.blue, displayInfoImp.getColor());
    }

}
