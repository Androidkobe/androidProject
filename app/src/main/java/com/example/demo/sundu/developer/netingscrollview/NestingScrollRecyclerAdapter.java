package com.example.demo.sundu.developer.netingscrollview;

import android.util.Log;
import android.view.Gravity;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.example.demo.R;

/**
 * @author cginechen
 * @date 2016-12-29
 */

public class NestingScrollRecyclerAdapter extends RecyclerView.Adapter<NestScrollViewHolder> {
    @Override
    public NestScrollViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        TextView textView = new TextView(parent.getContext());
        textView.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                NestingScrollUtil.dp2px(parent.getContext(), 50)));
        textView.setBackgroundResource(R.drawable.list_item_bg_with_border_bottom);
        int paddingHor = NestingScrollUtil.dp2px(parent.getContext(), 16);
        textView.setPadding(paddingHor, 0, paddingHor, 0);
        textView.setGravity(Gravity.CENTER_VERTICAL);
        textView.setTextSize(16);
        return new NestScrollViewHolder(textView);
    }

    @Override
    public void onBindViewHolder(NestScrollViewHolder holder, int position) {
        holder.setText("item " + position);
        Log.e("sundu", "NestingScrollRecyclerAdapter bind " + position);
    }


    @Override
    public int getItemCount() {
        return 50;
    }
}
