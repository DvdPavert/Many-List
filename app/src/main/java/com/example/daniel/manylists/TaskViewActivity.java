package com.example.daniel.manylists;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import com.google.gson.Gson;

public class TaskViewActivity extends AppCompatActivity {

    private Task task;
    private TextView textTitle;
    private TextView textTask;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_task_view);

        textTitle = (TextView) findViewById(R.id.textTitle);
        textTask = (TextView) findViewById(R.id.textTask);


        loadTask();
    }

    private void loadTask()
    {
        String JSONList = getIntent().getStringExtra("task");
        task = new Gson().fromJson(JSONList, Task.class);
        Log.d("log", JSONList);
        textTitle.setText(task.getName());
        textTask.setText(task.getTask());
    }

}
