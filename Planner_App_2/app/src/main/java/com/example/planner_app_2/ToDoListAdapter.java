package com.example.planner_app_2;

import android.content.Context;
import android.graphics.drawable.GradientDrawable;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Locale;

public class ToDoListAdapter extends RecyclerView.Adapter<ToDoListAdapter.TaskViewHolder> {

    // Constant for date format<
    private static final String DATE_FORMAT = "dd/MM/yyy";

    // Class variables for the List that holds task data and the Context
    private List<Task> mTaskEntries;

    private Context mContext;

    // Date formatter
    private SimpleDateFormat dateFormat = new SimpleDateFormat(DATE_FORMAT, Locale.getDefault());

    // the adapter constructor
    public ToDoListAdapter(Context context) {

        mContext = context;

    }

    @Override
    public TaskViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        // Inflate the task_layout to a view

        View view = LayoutInflater.from(mContext)
                .inflate(R.layout.layout_rowitem, parent, false);

        return new TaskViewHolder(view);

    }

    @Override
    public void onBindViewHolder(TaskViewHolder holder, int position) {

        // Determine the values of the wanted data

        Task taskEntry = mTaskEntries.get(position);
        String description = taskEntry.getDescription();
        int priority = taskEntry.getPriority();
        String updatedAt = dateFormat.format(taskEntry.getUpdatedAt());
//        Toast.makeText(mContext,description,Toast.LENGTH_LONG).show();
        //Set values
        holder.taskDescriptionView.setText(description);
        holder.updatedAtView.setText(updatedAt);
        // Programmatically set the text and color for the priority //TextView
        String priorityString = "" + priority; // converts int to String
        holder.priorityView.setText(priorityString);
        GradientDrawable priorityCircle = (GradientDrawable) holder.priorityView.getBackground();
        // Get the appropriate background color based on the priority
        int priorityColor = getPriorityColor(priority);
        priorityCircle.setColor(priorityColor);

    }
    private int getPriorityColor(int priority) {

        int priorityColor = 0;
        switch (priority) {
            case 1:
                priorityColor = ContextCompat.getColor(mContext, R.color.materialRed);
                break;
            case 2:               priorityColor = ContextCompat.getColor(mContext, R.color.materialOrange);
                break;
            case 3:
                priorityColor = ContextCompat.getColor(mContext, R.color.materialYellow);
                break;
            default:
                break;
        }
        return priorityColor;
    }
    @Override
    public int getItemCount() {
        if (mTaskEntries == null) {
            return 0;
        }

        return mTaskEntries.size();
    }
    /**

     * When data changes, this method updates the list of taskEntries
     * and notifies the adapter to use the new values on it
     */
    public void setTasks(List<Task> taskEntries) {
        mTaskEntries = taskEntries;
        notifyDataSetChanged();
    }
    // Inner class for creating ViewHolders
    class TaskViewHolder extends RecyclerView.ViewHolder {
        // Class variables for the task description and priority TextViews
        TextView taskDescriptionView;
        TextView updatedAtView;
        TextView priorityView;
        public TaskViewHolder(View itemView) {
            super(itemView);
            taskDescriptionView = itemView.findViewById(R.id.taskDescription);
            updatedAtView = itemView.findViewById(R.id.taskUpdatedAt);
            priorityView = itemView.findViewById(R.id.priorityTextView);
        }
    }
}