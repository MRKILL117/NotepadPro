package com.example.notepadpro;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.DialogFragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class TasksList extends AppCompatActivity {

    private static int OK = 200;
    private List<Task> tasks = new ArrayList<Task>();
    private RecyclerView recyclerView;
    private RecyclerView.Adapter recyclerViewAdapter;
    private RecyclerView.LayoutManager recyclerViewLayoutManager;

    // To start an activity and get back data
    ActivityResultLauncher<Intent> modalLauncher = registerForActivityResult(
            new ActivityResultContracts.StartActivityForResult(),
            new ActivityResultCallback<ActivityResult>() {
                @Override
                public void onActivityResult(ActivityResult result) {
                    Log.i("customLog", "On activity result: " + result.getResultCode());
                    if(result.getResultCode() == OK) {
                        Intent i = result.getData();
                        AddTask(new Task(
                                i.getStringExtra("taskTitle"),
                                i.getStringExtra("taskContent"),
                                i.getStringExtra("taskDueDate"),
                                i.getStringExtra("taskDueTime")
                                )
                        );
                    }
                }
            }
    );


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
            public void onItemClick(String title, String content, String dueDate, String dueTime, int pos) {
            }
        });
        this.recyclerView.setLayoutManager(this.recyclerViewLayoutManager);
        this.recyclerView.setAdapter(this.recyclerViewAdapter);
    }

    // To test the tasks view
    private void InitializeDataOfTasks() {
        this.tasks = new ArrayList<Task>(){{
            add(new Task("Task 1", "Proyecto de mobiles", "05/12/2022", "15:00"));
            add(new Task("Task 2", "Proyecto de paralelas", "06/12/2022", "12:00"));
            add(new Task("Task 3", "Proyecto de Web", "07/12/2022"));
        }};
    }

    public void CreateTask(View v) {
        Intent i = new Intent(this, TaskForm.class);
        this.modalLauncher.launch(i);
    }

    private void AddTask(Task newTask) {
        this.tasks.add(0, newTask);
        this.recyclerViewAdapter.notifyItemInserted(0);
    }
    private void RemoveTask(int pos) {
        this.tasks.remove(pos);
        this.recyclerViewAdapter.notifyItemRemoved(pos);
    }
    private void UpdateTask(int pos) {
        this.recyclerViewAdapter.notifyItemChanged(pos);
    }

}