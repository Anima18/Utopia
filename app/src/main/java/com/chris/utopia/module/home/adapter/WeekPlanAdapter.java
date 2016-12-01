package com.chris.utopia.module.home.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.chris.utopia.R;
import com.chris.utopia.common.constant.Constant;
import com.chris.utopia.common.util.CollectionUtil;
import com.chris.utopia.common.util.DateUtil;
import com.chris.utopia.entity.Plan;
import com.chris.utopia.entity.Thing;
import com.gc.materialdesign.views.ButtonFlat;

import java.util.Calendar;
import java.util.List;

/**
 * Created by chris on 2015/11/18
 */
public class WeekPlanAdapter extends RecyclerView.Adapter<WeekPlanAdapter.ViewHolder> {
    // Store the context for later use
    private Context context;
    private List<Plan> dList;

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
    public WeekPlanAdapter(Context context, List<Plan> dList) {
        this.context = context;
        this.dList = dList;
    }

    // Usually involves inflating a layout from XML and returning the holder
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        // Inflate the custom layout
        View itemView = LayoutInflater.from(context).inflate(R.layout.listview_week_plan_item, parent, false);
        // Return a new holder instance
        return new ViewHolder(context, itemView);
    }

    // Involves populating data into the item through holder
    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        // Get the data model based on position
        Plan plan = dList.get(position);
        holder.titleTv.setText(plan.getName());
        holder.subTitleTv.setText(plan.getBeginDate() + ", " +plan.getThingQuadrant());
        holder.descTv.setText(plan.getDescription());
        holder.moreBt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (holder.moreView.getVisibility() == View.GONE) {
                    holder.moreView.setVisibility(View.VISIBLE);
                    holder.moreBt.setText("收起详情");
                } else {
                    holder.moreView.setVisibility(View.GONE);
                    holder.moreBt.setText("更多详情");
                }
            }
        });
        List<Thing> thingList = plan.getThingList();
        if(CollectionUtil.isNotEmpty(thingList)) {
            for(Thing thing : thingList) {
                String timeStr = thing.getBeginDate() + " " + thing.getBeginTime();
                Calendar calendar = Calendar.getInstance();
                calendar.setTime(DateUtil.toDate(timeStr, Constant.DATETIME_FORMAT_6));

                if("星期一".equals(thing.getWhatDay())) {
                    holder.monDayView.setVisibility(View.VISIBLE);
                    initThingBackground(holder.monDayView, thing, calendar);
                    holder.mondayTimeTv.setText(thing.getWhatDay());
                    holder.mondayTitleTv.setText(thing.getTitle());
                    holder.mondayProgressTv.setText(thing.getProgress());

                }else if("星期二".equals(thing.getWhatDay())) {
                    holder.tuesDayView.setVisibility(View.VISIBLE);
                    initThingBackground(holder.tuesDayView, thing, calendar);
                    holder.tuesdayTimeTv.setText(thing.getWhatDay());
                    holder.tuesdayTitleTv.setText(thing.getTitle());
                    holder.tuesdayProgressTv.setText(thing.getProgress());
                }else if("星期三".equals(thing.getWhatDay())) {
                    holder.wednesDayView.setVisibility(View.VISIBLE);
                    initThingBackground(holder.wednesDayView, thing, calendar);
                    holder.wednesdayTimeTv.setText(thing.getWhatDay());
                    holder.wednesdayTitleTv.setText(thing.getTitle());
                    holder.wednesdayProgressTv.setText(thing.getProgress());
                }else if("星期四".equals(thing.getWhatDay())) {
                    holder.thursDayView.setVisibility(View.VISIBLE);
                    initThingBackground(holder.thursDayView, thing, calendar);
                    holder.thursdayTimeTv.setText(thing.getWhatDay());
                    holder.thursdayTitleTv.setText(thing.getTitle());
                    holder.thursdayProgressTv.setText(thing.getProgress());
                }else if("星期五".equals(thing.getWhatDay())) {
                    holder.friDayView.setVisibility(View.VISIBLE);
                    initThingBackground(holder.friDayView, thing, calendar);
                    holder.fridayTimeTv.setText(thing.getWhatDay());
                    holder.fridayTitleTv.setText(thing.getTitle());
                    holder.fridayProgressTv.setText(thing.getProgress());
                }else if("星期六".equals(thing.getWhatDay())) {
                    holder.saturDayView.setVisibility(View.VISIBLE);
                    initThingBackground(holder.saturDayView, thing, calendar);
                    holder.staturdayTimeTv.setText(thing.getWhatDay());
                    holder.staturdayTitleTv.setText(thing.getTitle());
                    holder.staturdayProgressTv.setText(thing.getProgress());
                }else if("星期日".equals(thing.getWhatDay())) {
                    holder.sunDayView.setVisibility(View.VISIBLE);
                    initThingBackground(holder.sunDayView, thing, calendar);
                    holder.sundayTimeTv.setText(thing.getWhatDay());
                    holder.sundayTitleTv.setText(thing.getTitle());
                    holder.sundayProgressTv.setText(thing.getProgress());
                }
            }
        }
    }

    public void initThingBackground(View view, Thing thing, Calendar calendar) {
        if(Constant.THING_STATUS_DONE.equals(thing.getStatus())) {
            //view.setBackgroundResource(R.drawable.today_task_done_background);
            view.setBackgroundColor(context.getResources().getColor(R.color.today_task_done));
        }else if(Constant.THING_STATUS_IGNORE.equals(thing.getStatus())) {
            //view.setBackgroundResource(R.drawable.today_task_ignore_background);
            view.setBackgroundColor(context.getResources().getColor(R.color.today_task_ignore));
        }else if(calendar.getTimeInMillis() < System.currentTimeMillis()) {
            //view.setBackgroundResource(R.drawable.today_task_delay_background);
            view.setBackgroundColor(context.getResources().getColor(R.color.today_task_delay));
        }else {
            //view.setBackgroundResource(R.drawable.selectable_item_background);
            //view.setBackgroundColor(context.getResources().getColor(R.color.today_task_done));
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
        public TextView titleTv;
        public TextView subTitleTv;
        public TextView descTv;
        public ButtonFlat moreBt;
        public View moreView;
        public View monDayView;
        public View tuesDayView;
        public View wednesDayView;
        public View thursDayView;
        public View friDayView;
        public View saturDayView;
        public View sunDayView;
        public TextView mondayTimeTv;
        public TextView mondayTitleTv;
        public TextView mondayProgressTv;
        public TextView tuesdayTimeTv;
        public TextView tuesdayTitleTv;
        public TextView tuesdayProgressTv;
        public TextView wednesdayTimeTv;
        public TextView wednesdayTitleTv;
        public TextView wednesdayProgressTv;
        public TextView thursdayTimeTv;
        public TextView thursdayTitleTv;
        public TextView thursdayProgressTv;
        public TextView fridayTimeTv;
        public TextView fridayTitleTv;
        public TextView fridayProgressTv;
        public TextView staturdayTimeTv;
        public TextView staturdayTitleTv;
        public TextView staturdayProgressTv;
        public TextView sundayTimeTv;
        public TextView sundayTitleTv;
        public TextView sundayProgressTv;

        // We also create a constructor that accepts the entire item row
        // and does the view lookups to find each subview
        public ViewHolder(Context context,final View itemView) {
            super(itemView);

            titleTv = (TextView) itemView.findViewById(R.id.planWeekList_title_tv);
            subTitleTv = (TextView) itemView.findViewById(R.id.planWeekList_subTitle_tv);
            descTv = (TextView) itemView.findViewById(R.id.planWeekList_summary_tv);
            moreBt = (ButtonFlat) itemView.findViewById(R.id.planWeekList_more_bt);
            moreView = itemView.findViewById(R.id.planWeekList_more_layout);
            monDayView = itemView.findViewById(R.id.planWeekList_monday_layout);
            tuesDayView = itemView.findViewById(R.id.planWeekList_tuesday_layout);
            wednesDayView = itemView.findViewById(R.id.planWeekList_wednesday_layout);
            thursDayView = itemView.findViewById(R.id.planWeekList_thursday_layout);
            friDayView = itemView.findViewById(R.id.planWeekList_friday_layout);
            saturDayView = itemView.findViewById(R.id.planWeekList_saturday_layout);
            sunDayView = itemView.findViewById(R.id.planWeekList_sunday_layout);
            mondayTimeTv = (TextView)itemView.findViewById(R.id.monday_time_tv);
            mondayTitleTv = (TextView)itemView.findViewById(R.id.monday_title_tv);
            mondayProgressTv = (TextView)itemView.findViewById(R.id.monday_progress_tv);
            tuesdayTimeTv = (TextView)itemView.findViewById(R.id.tuesday_time_tv);
            tuesdayTitleTv = (TextView)itemView.findViewById(R.id.tuesday_title_tv);
            tuesdayProgressTv = (TextView)itemView.findViewById(R.id.tuesday_progress_tv);
            wednesdayTimeTv = (TextView)itemView.findViewById(R.id.wednesday_time_tv);
            wednesdayTitleTv = (TextView)itemView.findViewById(R.id.wednesday_title_tv);
            wednesdayProgressTv = (TextView)itemView.findViewById(R.id.wednesday_progree_tv);
            thursdayTimeTv = (TextView)itemView.findViewById(R.id.thursday_time_tv);
            thursdayTitleTv = (TextView)itemView.findViewById(R.id.thursday_title_tv);
            thursdayProgressTv = (TextView)itemView.findViewById(R.id.thursday_progress_tv);
            fridayTimeTv = (TextView)itemView.findViewById(R.id.friday_time_tv);
            fridayTitleTv = (TextView)itemView.findViewById(R.id.friday_title_tv);
            fridayProgressTv = (TextView)itemView.findViewById(R.id.friday_progress_tv);
            staturdayTimeTv = (TextView)itemView.findViewById(R.id.saturday_time_tv);
            staturdayTitleTv = (TextView)itemView.findViewById(R.id.saturday_title_tv);
            staturdayProgressTv = (TextView)itemView.findViewById(R.id.saturday_progress_tv);
            sundayTimeTv = (TextView)itemView.findViewById(R.id.sunday_time_tv);
            sundayTitleTv = (TextView)itemView.findViewById(R.id.sunday_title_tv);
            sundayProgressTv = (TextView)itemView.findViewById(R.id.sunday_progress_tv);

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