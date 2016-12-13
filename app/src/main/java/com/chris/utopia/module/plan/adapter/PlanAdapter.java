package com.chris.utopia.module.plan.adapter;

import android.content.Context;
import android.support.v7.widget.ListPopupWindow;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.chris.utopia.R;
import com.chris.utopia.entity.Plan;
import com.chris.utopia.module.home.adapter.MenuAdapter;

import java.util.List;

import static com.chris.utopia.common.util.CommonUtil.getApplicationContext;

/**
 * Created by chris on 2015/11/18
 */
public class PlanAdapter extends RecyclerView.Adapter<PlanAdapter.ViewHolder> {
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

    private static OnLongItemClickListener longClickListener;
    // Define the listener interface
    public interface OnLongItemClickListener {
        void onLongClick(View itemView, int position);
    }
    // Define the method that allows the parent activity or fragment to define the listener
    public void setOnLongItemClickListener(OnLongItemClickListener longClickListener) {
        this.longClickListener = longClickListener;
    }

    /*private static OverflowMenuClickListener overflowMenuClickListener;
    // Define the listener interface
    public interface OverflowMenuClickListener {
        void onClick(View itemView, int position);
    }
    // Define the method that allows the parent activity or fragment to define the listener
    public void setOnOverflowMenuClickListener(OverflowMenuClickListener overflowMenuClickListener) {
        this.overflowMenuClickListener = overflowMenuClickListener;
    }*/

    // Pass in the context and users array into the constructor
    public PlanAdapter(Context context, List<Plan> dList) {
        this.context = context;
        this.dList = dList;
    }

    // Usually involves inflating a layout from XML and returning the holder
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        // Inflate the custom layout
        View itemView = LayoutInflater.from(context).inflate(R.layout.listview_plan_item, parent, false);
        // Return a new holder instance
        return new ViewHolder(context, itemView);
    }

    // Involves populating data into the item through holder
    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        // Get the data model based on position
        Plan plan = dList.get(position);
        holder.titleTv.setText(plan.getName());
        holder.descTv.setText(plan.getDescription());
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
        public TextView secondTitleTv;
        public ImageView overflowMenu;


        // We also create a constructor that accepts the entire item row
        // and does the view lookups to find each subview
        public ViewHolder(final Context context, final View itemView) {
            super(itemView);
            Log.i("Chris", "planAdapter");
            titleTv = (TextView) itemView.findViewById(R.id.planFrg_title_tv);
            descTv = (TextView) itemView.findViewById(R.id.planFrg_summary_tv);
            secondTitleTv = (TextView) itemView.findViewById(R.id.planFrg_second_title);
            overflowMenu = (ImageView) itemView.findViewById(R.id.planFrg_overflow_menu);

            String items[] = { "0", "1", "2", "3", "4", "5", "6", "7", "8", "9" };
            final ListPopupWindow listPopupWindow = new ListPopupWindow(context);
            MenuAdapter adapter = new MenuAdapter(context, R.layout.listview_menu_item, 10);
            listPopupWindow.setAdapter(adapter);
            // 对话框的宽高
            listPopupWindow.setWidth(450);
            listPopupWindow.setHeight(300);

            // ListPopupWindow的锚,弹出框的位置是相对当前View的位置
            listPopupWindow.setAnchorView(overflowMenu);
            // ListPopupWindow 距锚view的距离
            listPopupWindow.setHorizontalOffset(-350);

            // 选择item的监听事件
            listPopupWindow.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int pos, long id) {
                    Toast.makeText(getApplicationContext(), "选择:" + pos, Toast.LENGTH_SHORT).show();
                    listPopupWindow.dismiss();
                }
            });

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    // Triggers click upwards to the adapter on click
                    if (listener != null)
                        listener.onItemClick(itemView, getLayoutPosition());
                }
            });

            itemView.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View view) {
                    if(longClickListener != null) {
                        longClickListener.onLongClick(itemView, getLayoutPosition());
                    }
                    return true;
                }
            });

            overflowMenu.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    listPopupWindow.setVerticalOffset(-overflowMenu.getHeight()-60);

                    listPopupWindow.setModal(true);
                    listPopupWindow.show();
                }
            });
        }
    }
}