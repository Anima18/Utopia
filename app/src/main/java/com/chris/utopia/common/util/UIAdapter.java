package com.chris.utopia.common.util;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/**
 * Created by Admin on 2015/8/19.
 */
public class UIAdapter extends RecyclerView.Adapter<UIAdapter.ViewHolder> {

    private Context context;
    private int layoutId;
    private int itemSize;

    // Pass in the context and users array into the constructor
    public UIAdapter(Context context, int layoutId, int itemSize) {
        this.context = context;
        this.layoutId = layoutId;
        this.itemSize = itemSize;
    }

    // Usually involves inflating a layout from XML and returning the holder
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        // Inflate the custom layout
        View itemView = LayoutInflater.from(context).inflate(layoutId, parent, false);
        // Return a new holder instance
        return new ViewHolder(context, itemView);
    }

    // Involves populating data into the item through holder
    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {

    }

    // Return the total count of items
    @Override
    public int getItemCount() {
        return itemSize;
    }

    // Provide a direct reference to each of the views within a data item
    // Used to cache the views within the item layout for fast access
    public static class ViewHolder extends RecyclerView.ViewHolder {

        public ViewHolder(Context context,final View itemView) {
            super(itemView);
        }
    }

}