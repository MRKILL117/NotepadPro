package com.example.notepadpro;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.DialogFragment;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Toast;

import java.util.Calendar;

public class TaskForm extends AppCompatActivity {

    private EditText taskTitleInput, taskContentInput;
    private static Button taskDueDateButton, taskDueTimeButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_task_form);
        taskTitleInput = findViewById(R.id.taskTitleInput);
        taskContentInput = findViewById(R.id.taskContentInput);
        taskDueDateButton = findViewById(R.id.dueDateButton);
        taskDueTimeButton = findViewById(R.id.dueTimeButton);
    }

    public void CloseActivity(View v) {
        Intent i = new Intent();
        switch (v.getId()) {
            case R.id.acceptButton:
                Task newTask = this.ValidateAndGetTaskCreated();
                if(newTask != null) {
                    i.putExtra("taskTitle", newTask.title);
                    i.putExtra("taskContent", newTask.content);
                    i.putExtra("taskDueDate", newTask.dueDate);
                    i.putExtra("taskDueTime", newTask.dueTime);
                    setResult(200, i);
                    finish();
                }
                else Toast.makeText(this, "Campos incompletos", Toast.LENGTH_LONG).show();
                break;
            default:
                setResult(404, i);
                finish();
                break;
        }
    }

    @Nullable
    private Task ValidateAndGetTaskCreated() {
        String taskTitle, taskContent, taskDueDate, taskDueTime;

        if(taskTitleInput.getText().toString().length() == 0) {
            taskTitleInput.setError("Ingrese un titulo");
        }
        if(taskContentInput.getText().toString().length() == 0) {
            taskContentInput.setError("Ingrese una tarea por hacer");
        }

        taskTitle = taskTitleInput.getText().toString();
        taskContent = taskContentInput.getText().toString();
        taskDueDate = taskDueDateButton.getText().toString().contains("limite") ? "Fecha indefinida" : taskDueDateButton.getText().toString();
        taskDueTime = taskDueTimeButton.getText().toString().contains("limite") ? "" : taskDueTimeButton.getText().toString();

        if(taskTitle.length() < 1 || taskContent.length() < 1 || taskDueDate.length() < 1) return null;
        if(taskDueTime.length() != 0) return new Task(taskTitle, taskContent, taskDueDate, taskDueTime);
        else return new Task(taskTitle, taskContent, taskDueDate);
    }

    // Date picker
    public void OpenDatePicker(View v) {
        DialogFragment datePicker = new TaskForm.DatePickerModal();
        datePicker.show(getSupportFragmentManager(), "Calendario");
    }
    public static class DatePickerModal extends DialogFragment implements DatePickerDialog.OnDateSetListener {
        @NonNull
        @Override
        public Dialog onCreateDialog(@Nullable Bundle savedInstanceState){
            final Calendar c = Calendar.getInstance();
            int year = c.get(Calendar.YEAR);
            int month = c.get(Calendar.MONTH);
            int day = c.get(Calendar.DAY_OF_MONTH);
            return new DatePickerDialog(getActivity(), this, year, month, day);
        }
        @Override
        public void onDateSet(DatePicker datePicker, int day, int month, int year) {
            String date =  day + "/" + (month+1) + "/" + year;
            taskDueDateButton.setText(date);
        }
    }
}