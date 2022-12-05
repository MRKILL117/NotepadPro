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
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class TasksList extends AppCompatActivity {

    private static final int CREATED_OK = 200;
    private static final int EDITED_OK = 202;
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
                    if(result.getResultCode() == 404) return;
                    Intent i = result.getData();
                    String title = i.getStringExtra("title"),
                            content = i.getStringExtra("content"),
                            dueDate = i.getStringExtra("dueDate"),
                            dueTime = i.getStringExtra("dueTime");
                    switch (result.getResultCode()) {
                        case CREATED_OK:
                            AddTask(new Task(title, content, dueDate, dueTime));
                            break;
                        case EDITED_OK:
                            int pos = i.getIntExtra("position", -1);
                            if(pos == -1) break;
                            Task taskToEdit = tasks.get(pos);
                            taskToEdit.title = title;
                            taskToEdit.content = content;
                            taskToEdit.dueDate = dueDate;
                            taskToEdit.dueTime = dueTime;
                            Log.i("custom", "Updating task");
                            Log.i("custom", title);
                            UpdateTask(pos);
                            break;
                    }
                }
            }
    );


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tasks_list);
        //this.InitializeDataOfTasks();
        this.InitializeRecyclerView();
    }

    // Menu methods
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.task_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.addTaskMenuButton:
                this.CreateTask();
                return true;
            case R.id.goToNotesMenuButton:
                Intent i = new Intent(this, NotasActivity.class);
                startActivity(i);
                return true;
            default: return super.onOptionsItemSelected(item);
        }
    }

    private void InitializeRecyclerView() {
        this.recyclerView = findViewById(R.id.tasksRecyclerView);
        this.recyclerViewLayoutManager = new LinearLayoutManager(this);
        this.recyclerViewAdapter = new TaskRecyclerViewAdapter(this.tasks, R.layout.task_item, new TaskRecyclerViewAdapter.OnDeleteClickListener() {
            @Override
            public void onDeleteClick(int pos) {
                RemoveTask(pos);
            }
        }, new TaskRecyclerViewAdapter.OnEditClickListener() {
            @Override
            public void onEditClick(int pos) {
                GoToEditTask(pos);
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
        this.CreateTask();
    }

    public void CreateTask() {
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
    private void GoToEditTask(int pos) {
        Task selectedTask = this.tasks.get(pos);
        Intent i = new Intent(this, TaskForm.class);
        i.putExtra("title", selectedTask.title);
        i.putExtra("content", selectedTask.content);
        i.putExtra("dueDate", selectedTask.dueDate);
        i.putExtra("dueTime", selectedTask.dueTime);
        i.putExtra("position", pos);
        this.modalLauncher.launch(i);
    }
    private void UpdateTask(int pos) {
        this.recyclerViewAdapter.notifyItemChanged(pos);
    }

}