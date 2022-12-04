package com.example.notepadpro;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.DialogFragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.View;

import java.util.ArrayList;
import java.util.List;

public class TasksList extends AppCompatActivity {

    private List<Task> tasks = new ArrayList<Task>();
    private RecyclerView recyclerView;
    private RecyclerView.Adapter recyclerViewAdapter;
    private RecyclerView.LayoutManager recyclerViewLayoutManager;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tasks_list);
        this.InitializeDataOfTasks();
        this.InitializeRecyclerView();
    }

    private void InitializeRecyclerView() {
        this.recyclerView = findViewById(R.id.tasksRecyclerView);
        this.recyclerViewLayoutManager = new LinearLayoutManager(this);
        this.recyclerViewAdapter = new TaskRecyclerViewAdapter(this.tasks, R.layout.task_item, new TaskRecyclerViewAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(String title, String content, String dueDate, int pos) {
            }
        });
        this.recyclerView.setLayoutManager(this.recyclerViewLayoutManager);
        this.recyclerView.setAdapter(this.recyclerViewAdapter);
    }

    // To test the tasks view
    private void InitializeDataOfTasks() {
        this.tasks = new ArrayList<Task>(){{
            add(new Task("Task 1", "Proyecto de mobiles", "05/12/2022"));
            add(new Task("Task 2", "Proyecto de paralelas", "06/12/2022"));
            add(new Task("Task 3", "Proyecto de Web", "07/12/2022"));
        }};
    }

    public void CreateTask(View v) {

    }
}