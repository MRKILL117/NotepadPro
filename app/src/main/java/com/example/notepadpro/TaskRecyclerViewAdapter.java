package com.example.notepadpro;

import android.graphics.Paint;
import android.graphics.fonts.Font;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class TaskRecyclerViewAdapter extends RecyclerView.Adapter<TaskRecyclerViewAdapter.ViewHolder> {

    private final List<Task> tasks;
    private final int layout;
    private final OnDeleteClickListener onDelete;
    private final OnEditClickListener onEdit;

    public interface OnDeleteClickListener {
        void onDeleteClick(int position);
    }
    public interface OnEditClickListener {
        void onEditClick(int position);
    }

    public TaskRecyclerViewAdapter(List<Task> tasks, int layout, OnDeleteClickListener onDelete, OnEditClickListener onEdit) {
        this.tasks = tasks;
        this.layout = layout;
        this.onDelete = onDelete;
        this.onEdit = onEdit;
    }

    @NonNull
    @Override
    public TaskRecyclerViewAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(layout, parent, false);
        TaskRecyclerViewAdapter.ViewHolder vh = new TaskRecyclerViewAdapter.ViewHolder(view);
        return vh;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.bind(tasks.get(position).title, tasks.get(position).content, tasks.get(position).dueDate, tasks.get(position).dueTime, this.onDelete, this.onEdit);
    }

    @Override
    public int getItemCount() {
        return this.tasks.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {

        TextView taskTitle, taskContent, taskDueDate, taskDueTime;
        Button taskDeleteButton, taskEditButton;
        CheckBox taskChecbox;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            this.taskTitle = itemView.findViewById(R.id.taskTitle);
            this.taskContent = itemView.findViewById(R.id.taskContent);
            this.taskDueDate = itemView.findViewById(R.id.taskDueDate);
            this.taskDueTime = itemView.findViewById(R.id.taskDueTime);
            this.taskChecbox = itemView.findViewById(R.id.taskCheckbox);
            this.taskDeleteButton = itemView.findViewById(R.id.deleteTaskButton);
            this.taskEditButton = itemView.findViewById(R.id.editTaskButton);
        }

        public void bind(String title, String content, String dueDate, String dueTime, final TaskRecyclerViewAdapter.OnDeleteClickListener onDelete, final TaskRecyclerViewAdapter.OnEditClickListener onEdit) {
            this.taskTitle.setText(title);
            this.taskContent.setText(content);
            this.taskDueDate.setText(dueDate);
            this.taskDueTime.setText(dueTime);
            this.taskChecbox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton compoundButton, boolean checked) {
                    taskEditButton.setEnabled(!checked);
                    if(checked) {
                        taskTitle.setPaintFlags(taskTitle.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);
                        taskContent.setPaintFlags(taskContent.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);
                        taskDueDate.setPaintFlags(taskDueDate.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);
                        taskDueTime.setPaintFlags(taskDueTime.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);
                    }
                    else {
                        taskTitle.setPaintFlags(taskTitle.getPaintFlags() & (~ Paint.STRIKE_THRU_TEXT_FLAG));
                        taskContent.setPaintFlags(taskContent.getPaintFlags() & (~ Paint.STRIKE_THRU_TEXT_FLAG));
                        taskDueDate.setPaintFlags(taskDueDate.getPaintFlags() & (~ Paint.STRIKE_THRU_TEXT_FLAG));
                        taskDueTime.setPaintFlags(taskDueTime.getPaintFlags() & (~ Paint.STRIKE_THRU_TEXT_FLAG));
                    }
                }
            });

            this.taskDeleteButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    onDelete.onDeleteClick(getAdapterPosition());
                }
            });
            this.taskEditButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    onEdit.onEditClick(getAdapterPosition());
                }
            });
        }
    }
}
