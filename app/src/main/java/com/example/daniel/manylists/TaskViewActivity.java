package com.example.daniel.manylists;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.google.gson.Gson;

public class TaskViewActivity extends AppCompatActivity {

    private Task task;
    private TextView textTitle;
    private TextView textTask;
    private Context context = this;
    private DBHelper db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_task_view);

        textTitle = (TextView) findViewById(R.id.textTitle);
        textTask = (TextView) findViewById(R.id.textTask);
        db = DBHelper.getInstance(this);

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

    public void finishTask(View view)
    {
        TaskList taskList;

        String JSONList = getIntent().getStringExtra("taskList");
        taskList = new Gson().fromJson(JSONList, TaskList.class);

        String name = task.getName();
        task.setName(name + " (Done)");
        db.update(task);

        Intent intent =  new Intent(context, ListViewActivity.class);
        intent.putExtra("taskList", new Gson().toJson(taskList));
        startActivity(intent);
    }

}
