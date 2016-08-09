package com.example.reactivemvp.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.example.reactivemvp.model.DisplayInfo;

import butterknife.ButterKnife;

/**
 * Base class for View Holder so adapter didn't care about types.
 */
public abstract class InfoViewHolder extends RecyclerView.ViewHolder {

    public InfoViewHolder(View itemView) {
        super(itemView);
        ButterKnife.bind(this, itemView);
    }

    public abstract void bind(DisplayInfo displayInfo);
}
