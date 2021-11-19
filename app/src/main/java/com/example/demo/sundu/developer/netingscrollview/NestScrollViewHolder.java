package com.example.demo.sundu.developer.netingscrollview;

import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

/**
 * @author cginechen
 * @date 2016-12-29
 */

public class NestScrollViewHolder extends RecyclerView.ViewHolder {

    private TextView mItemView;

    public NestScrollViewHolder(TextView itemView) {
        super(itemView);
        mItemView = itemView;
    }

    public void setText(String text) {
        mItemView.setText(text);
    }
}
