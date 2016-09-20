package com.chris.utopia.module.role.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.chris.utopia.R;
import com.chris.utopia.entity.Role;

import java.util.List;

/**
 * Created by chris on 2015/11/18
 */
public class RoleAdapter extends RecyclerView.Adapter<RoleAdapter.ViewHolder> {
    // Store the context for later use
    private Context context;
    private List<Role> dList;

    private static OnItemClickListener listener;
    // Define the listener interface
    public interface OnItemClickListener {
        void onItemClick(View itemView, int position);
    }
    // Define the method that allows the parent activity or fragment to define the listener
    public void setOnItemClickListener(OnItemClickListener listener) {
        this.listener = listener;
    }

    // Pass in the context and users array into the constructor
    public RoleAdapter(Context context, List<Role> dList) {
        this.context = context;
        this.dList = dList;
    }

    // Usually involves inflating a layout from XML and returning the holder
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        // Inflate the custom layout
        View itemView = LayoutInflater.from(context).inflate(R.layout.listview_role_item, parent, false);
        // Return a new holder instance
        return new ViewHolder(context, itemView);
    }

    // Involves populating data into the item through holder
    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        // Get the data model based on position
        Role role = dList.get(position);
        holder.titleTv.setText(role.getName());
        holder.descTv.setText(role.getDescription().replace("\n", "").replace(" ", ""));
    }

    // Return the total count of items
    @Override
    public int getItemCount() {
        return dList.size();
    }

    // Provide a direct reference to each of the views within a data item
    // Used to cache the views within the item layout for fast access
    public static class ViewHolder extends RecyclerView.ViewHolder {
        // Your holder should contain a member variable
        // for any view that will be set as you render a row
        public TextView titleTv;
        public TextView descTv;


        // We also create a constructor that accepts the entire item row
        // and does the view lookups to find each subview
        public ViewHolder(Context context,final View itemView) {
            super(itemView);

            titleTv = (TextView) itemView.findViewById(R.id.roleLv_name_tv);
            descTv = (TextView) itemView.findViewById(R.id.roleLv_desc_tv);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    // Triggers click upwards to the adapter on click
                    if (listener != null)
                        listener.onItemClick(itemView, getLayoutPosition());
                }
            });
        }
    }
}