package com.example.notepadpro;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class TaskRecyclerViewAdapter extends RecyclerView.Adapter<TaskRecyclerViewAdapter.ViewHolder> {

    private final List<Task> tasks;
    private final int layout;
    private final OnItemClickListener onItemClick;

    public interface OnItemClickListener {
        void onItemClick(String title, String content, String dueDate, int position);
    }

    public TaskRecyclerViewAdapter(List<Task> tasks, int layout, OnItemClickListener onItemClick) {
        this.tasks = tasks;
        this.layout = layout;
        this.onItemClick = onItemClick;
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
        holder.bind(tasks.get(position).title, tasks.get(position).content, tasks.get(position).dueDate, this.onItemClick);
    }

    @Override
    public int getItemCount() {
        return this.tasks.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {

        TextView taskTitle, taskContent, taskDueDate;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            this.taskTitle = itemView.findViewById(R.id.taskTitle);
            this.taskContent = itemView.findViewById(R.id.taskContent);
            this.taskDueDate = itemView.findViewById(R.id.taskDueDate);
        }

        public void bind(String title, String content, String dueDate, final TaskRecyclerViewAdapter.OnItemClickListener itemListener) {
            this.taskTitle.setText(title);
            this.taskContent.setText(content);
            this.taskDueDate.setText(dueDate);
            itemView.setOnClickListener(new View.OnClickListener(){
                @Override
                public void onClick(View view){
                    itemListener.onItemClick(title, content, dueDate, getAdapterPosition());
                }
            });
        }
    }
}
