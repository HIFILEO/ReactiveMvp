package com.example.reactivemvp.viewcontroller;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.example.reactivemvp.R;
import com.example.reactivemvp.adapter.InfoListAdapter;
import com.example.reactivemvp.application.ReactiveMvpApplication;
import com.example.reactivemvp.gateway.InfoGateway;
import com.example.reactivemvp.gateway.InfoGatewayWebImpl;
import com.example.reactivemvp.interactor.InfoInteractor;
import com.example.reactivemvp.interactor.InfoInteractorImpl;
import com.example.reactivemvp.model.DisplayInfo;
import com.example.reactivemvp.presenter.InfoPresenter;
import com.example.reactivemvp.presenter.InfoPresenterImpl;
import com.example.reactivemvp.presenter.InfoViewModel;
import com.example.reactivemvp.service.InfoServiceApi;
import com.example.reactivemvp.view.DividerItemDecoration;

import java.util.List;

import javax.inject.Inject;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * This is the only activity for the application that links into the MVP architecture.
 */
public class InfoActivity extends AppCompatActivity implements InfoViewModel {
    private InfoPresenter infoPresenter;
    private InfoListAdapter infoListAdapter;
    private Menu menu;

    //Injected components
    @Inject
    InfoServiceApi infoServiceApi;

    //Bind Views
    @Bind(R.id.progressBar)
    ProgressBar progressBar;
    @Bind(R.id.toolbar)
    Toolbar toolbar;
    @Bind(R.id.recyclerView)
    RecyclerView recyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_info);
        ButterKnife.bind(this);

        //Inject Dagger Service
        ReactiveMvpApplication.getInstance().getComponent().inject(this);

        // Sets the Toolbar to act as the ActionBar for this Activity window.
        // Make sure the toolbar exists in the activity and is not null
        setSupportActionBar(toolbar);

        //Create Interactor
        InfoGateway infoGateway = new InfoGatewayWebImpl(infoServiceApi);
        InfoInteractor infoInteractor = new InfoInteractorImpl(infoGateway);

        //Create Presenter
        infoPresenter = new InfoPresenterImpl(infoInteractor, this);

        //Start Presenter so Interactor response model is setup correctly.
        infoPresenter.start();

        //Load Info
        infoPresenter.loadInfo();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        /*
        Create a menu option to toggle the screen between List and Grid view.
         */
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.info_layout_menu, menu);
        this.menu = menu;
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.toggleListLayout:
                infoPresenter.toggleList();
            break;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void showInProgress(boolean show) {
        if (show) {
            progressBar.setVisibility(View.VISIBLE);
        } else {
            progressBar.setVisibility(View.GONE);
        }
    }

    @Override
    public void showError() {
        Toast.makeText(this, R.string.error_msg, Toast.LENGTH_LONG).show();
        infoPresenter.loadInfo();
    }

    @Override
    public void createAdapter(List<DisplayInfo> displayInfoList) {
        infoListAdapter = new InfoListAdapter(displayInfoList);
        recyclerView.setAdapter(infoListAdapter);
    }

    @Override
    public void showDataListStyle() {
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.addItemDecoration(new DividerItemDecoration(
                this,
                DividerItemDecoration.VERTICAL_LIST,
                getResources().getColor(android.R.color.black, null)));
        infoListAdapter.setShowListView(true);
        recyclerView.setAdapter(infoListAdapter);
    }

    @Override
    public void showDataGridStyle() {
        recyclerView.setLayoutManager(new GridLayoutManager(this, 2));
        recyclerView.addItemDecoration(new DividerItemDecoration(
                this,
                DividerItemDecoration.HORIZONTAL_LIST,
                getResources().getColor(android.R.color.black, null)));
        infoListAdapter.setShowListView(false);
        recyclerView.setAdapter(infoListAdapter);
    }

    @Override
    public void setMenuTitle(int stringResourceId) {
        menu.findItem(R.id.toggleListLayout).setTitle(stringResourceId);
    }

}
