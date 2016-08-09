package com.example.reactivemvp.adapter;

import android.view.View;
import android.widget.TextView;

import com.example.reactivemvp.R;
import com.example.reactivemvp.model.DisplayInfo;

import butterknife.Bind;

/**
 * View Holder for list related items.
 */
public class ListInfoViewHolder extends InfoViewHolder {
    @Bind(R.id.nameTextView)
    TextView nameTextView;
    @Bind(R.id.salaryTextView)
    TextView salaryTextview;

    public ListInfoViewHolder(View itemView) {
        super(itemView);
    }

    @Override
    public void bind(DisplayInfo displayInfo) {
        nameTextView.setText(displayInfo.getName());
        salaryTextview.setText(displayInfo.getSalary());

        if (displayInfo.getColor() != 0) {
            nameTextView.setTextColor(nameTextView.getContext().getColor(displayInfo.getColor()));
        }
    }
}
