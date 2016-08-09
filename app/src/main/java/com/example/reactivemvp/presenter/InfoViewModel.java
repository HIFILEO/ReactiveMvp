package com.example.reactivemvp.presenter;

import com.example.reactivemvp.model.DisplayInfo;

import java.util.List;

/**
 * View interface to be implemented by the forward facing UI part of android. An activity or fragment.
 */
public interface InfoViewModel {
    void showInProgress(boolean show);
    void showError();
    void createAdapter(List<DisplayInfo> displayInfoList);
    void showDataListStyle();
    void showDataGridStyle();
    void setMenuTitle(int stringResourceId);
}
