package com.example.reactivemvp.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.reactivemvp.R;
import com.example.reactivemvp.model.DisplayInfo;

import java.util.List;

/**
 * Simple recycle view adapter.
 */
public class InfoListAdapter extends RecyclerArrayAdapter<DisplayInfo, InfoViewHolder> {
    private static final int LIST_VIEW = 0;
    private static final int GRID_VIEW = 1;
    private boolean showListView = true;

    /**
     * Constructor
     * @param objects
     */
    public InfoListAdapter(List<DisplayInfo> objects) {
        super(objects);
    }

    /**
     * Set showListView - true for list, false for gridview
     * @param showListView -
     */
    public void setShowListView(boolean showListView) {
        this.showListView = showListView;
    }

    @Override
    public InfoViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        //Inflate
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.info_item, parent, false);

        //Return correct type
        if (viewType == LIST_VIEW) {
            return new ListInfoViewHolder(view);
        } else {
            return new GridInfoViewHolder(view);
        }
    }

    @Override
    public void onBindViewHolder(InfoViewHolder holder, int position) {
        holder.bind(getItem(position));
    }

    @Override
    public int getItemViewType(int position) {
        return showListView ? LIST_VIEW : GRID_VIEW;
    }
}
