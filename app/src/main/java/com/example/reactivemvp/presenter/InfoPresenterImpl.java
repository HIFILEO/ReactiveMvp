package com.example.reactivemvp.presenter;

import android.support.annotation.VisibleForTesting;

import com.example.reactivemvp.R;
import com.example.reactivemvp.interactor.InfoInteractor;
import com.example.reactivemvp.interactor.InfoResponseModel;
import com.example.reactivemvp.model.DisplayInfo;
import com.example.reactivemvp.model.Info;
import com.example.reactivemvp.model.ListType;
import com.example.reactivemvp.model.SexType;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

/**
 * Implements the Presenter interface.
 */
public class InfoPresenterImpl implements InfoPresenter, InfoResponseModel {
    private InfoViewModel infoViewModel;
    private InfoInteractor infoInteractor;
    private @ListType int listType = ListType.LIST;

    public InfoPresenterImpl(InfoInteractor infoInteractor, InfoViewModel infoViewModel) {
        this.infoInteractor = infoInteractor;
        this.infoViewModel = infoViewModel;
    }

    @Override
    public void loadInfo() {
        infoInteractor.loadInfo();
        infoViewModel.showInProgress(true);
    }

    @Override
    public void start() {
        this.infoInteractor.setInfoResponseModel(this);
    }

    @Override
    public void toggleList() {
        if (listType == ListType.LIST) {
            listType = ListType.GRID;
            infoViewModel.showDataGridStyle();
            infoViewModel.setMenuTitle(R.string.grid_view);
        } else {
            listType = ListType.LIST;
            infoViewModel.showDataListStyle();
            infoViewModel.setMenuTitle(R.string.list_view);
        }
    }

    @Override
    public void infoLoaded(List<Info> infoList) {
        //
        //Translate internal business logic to external view logic. In other words, what are the requirements
        //for how we show data?
        //
        List<DisplayInfo> displayInfoList = new ArrayList<>();

        for (Info info : infoList) {
            displayInfoList.add(new DisplayInfoImp(info.getFirstName(), info.getLastName(), info.getSalary(),
                    info.getSex()));
        }

        infoViewModel.showInProgress(false);
        infoViewModel.createAdapter(displayInfoList);

        if (listType == ListType.LIST) {
            infoViewModel.showDataListStyle();
        } else {
            infoViewModel.showDataGridStyle();
        }
    }

    @Override
    public void errorLoadingInfoData() {
        infoViewModel.showError();
    }

    /**
     * Inner class for how this presenter decides to show it's data for display. Notice the constructor translates
     * the internal business class into a display class based on the requirements for how data is shown to the screen.
     */
    @VisibleForTesting
    public static class DisplayInfoImp implements DisplayInfo {
        private String name;
        private String abbreviation = "";
        private String salary;
        private int resColor;

        public DisplayInfoImp(String firstName, String lastName, int salary, @SexType int sexType) {
            //
            //Guard
            //
            if (firstName == null) {
                firstName = "";
            }
            if (lastName == null) {
                lastName = "";
            }

            //
            //translate for name
            //
            name = firstName + " " + lastName;

            //
            //Translate for abbreviation
            //
            String firstNameAbr = "";
            String lastNameAbr = "";
            if (!firstName.isEmpty()) {
                firstNameAbr = firstName.substring(0,1);
            }
            if (!lastName.isEmpty()) {
                lastNameAbr = lastName.substring(0,1);
            }
            abbreviation = firstNameAbr + lastNameAbr;

            //
            //Translate for salary
            //
            DecimalFormat formatter = new DecimalFormat("#,###.00");
            this.salary = "$" + formatter.format(salary);

            //
            //Translate for Sex
            //
            switch (sexType) {
                case SexType.FEMALE:
                    resColor = R.color.red;
                    break;
                case SexType.MALE:
                    resColor = R.color.blue;
                    break;
                case SexType.UNKNOWN:
                    resColor = R.color.green;
            }
        }

        @Override
        public String getName() {
            return name;
        }

        @Override
        public String getAbbreviation() {
            return abbreviation;
        }

        @Override
        public String getSalary() {
            return salary;
        }

        @Override
        public int getColor() {
            return resColor;
        }
    }
}
