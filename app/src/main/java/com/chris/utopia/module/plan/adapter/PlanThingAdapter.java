package com.chris.utopia.module.plan.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.chris.utopia.R;
import com.chris.utopia.common.constant.Constant;
import com.chris.utopia.common.util.DateUtil;
import com.chris.utopia.common.view.DisplayLeakConnectorView;
import com.chris.utopia.entity.Thing;

import java.util.Calendar;
import java.util.List;

/**
 * Created by chris on 2015/11/18
 */
public class PlanThingAdapter extends RecyclerView.Adapter<PlanThingAdapter.ViewHolder> {
    // Store the context for later use
    private Context context;
    private List<Thing> dList;

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
    public PlanThingAdapter(Context context, List<Thing> dList) {
        this.context = context;
        this.dList = dList;
    }

    // Usually involves inflating a layout from XML and returning the holder
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        // Inflate the custom layout
        View itemView = LayoutInflater.from(context).inflate(R.layout.listview_plan_create, parent, false);
        // Return a new holder instance
        return new ViewHolder(context, itemView);
    }

    // Involves populating data into the item through holder
    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        // Get the data model based on position
        Thing thing = dList.get(position);
        holder.titleTv.setText(thing.getTitle());
        holder.descTv.setText(thing.getDescription());
        holder.percentTv.setText(thing.getProgress());
        String timeStr = thing.getBeginDate() + " " + thing.getBeginTime();
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(DateUtil.toDate(timeStr, Constant.DATETIME_FORMAT_6));

        int status;
        if(Constant.THING_STATUS_DONE.equals(thing.getStatus())) {
            status = 0;
            //holder.rootView.setBackgroundResource(R.drawable.today_task_done_background);
        }else if(Constant.THING_STATUS_IGNORE.equals(thing.getStatus())) {
            status = 1;
            //holder.rootView.setBackgroundResource(R.drawable.today_task_ignore_background);
        }else if(calendar.getTimeInMillis() < System.currentTimeMillis()) {
            status = 2;
            //holder.rootView.setBackgroundResource(R.drawable.today_task_delay_background);
        }else {
            status = 3;
            //holder.rootView.setBackgroundResource(R.drawable.selectable_item_background);
        }

        boolean isRoot = position == 0;
        boolean isLeakingInstance = position == getItemCount() - 1;
        DisplayLeakConnectorView connector = holder.stepView;
        if (isRoot) {
            connector.setType(DisplayLeakConnectorView.Type.START, status);
        } else {
            if (isLeakingInstance) {
                connector.setType(DisplayLeakConnectorView.Type.END, status);
            } else {
                connector.setType(DisplayLeakConnectorView.Type.NODE, status);
            }
        }
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
        public TextView percentTv;
        public TextView titleTv;
        public TextView descTv;
        public View rootView;
        public DisplayLeakConnectorView stepView;

        // We also create a constructor that accepts the entire item row
        // and does the view lookups to find each subview
        public ViewHolder(Context context,final View itemView) {
            super(itemView);

            rootView = itemView.findViewById(R.id.pcLv_layout);
            percentTv = (TextView) itemView.findViewById(R.id.pcLv_thing_percent);
            titleTv = (TextView) itemView.findViewById(R.id.pcLv_thing_title);
            descTv = (TextView) itemView.findViewById(R.id.pcLv_thing_desc);
            stepView = (DisplayLeakConnectorView) itemView.findViewById(R.id.stepView);

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