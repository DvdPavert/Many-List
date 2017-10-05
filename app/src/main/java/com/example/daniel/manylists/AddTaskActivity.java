package com.example.daniel.manylists;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ListView;

import com.google.gson.Gson;

public class AddTaskActivity extends AppCompatActivity {

    private EditText editTitle;
    private EditText editTask;
    private DBHelper db;
    private Context context = this;
    private TaskList taskList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_task);

        editTitle = (EditText) findViewById(R.id.editTitle);
        editTask = (EditText) findViewById(R.id.editTask);
        db = DBHelper.getInstance(this);
    }

    public void getTask(View view)
    {

        String JSONList = getIntent().getStringExtra("taskList");
        taskList = new Gson().fromJson(JSONList, TaskList.class);

        String title = editTitle.getText().toString();
        String task = editTask.getText().toString();

        Task newTask = new Task(title, task, taskList.getName());
        db.createContent(newTask);

        Intent intent =  new Intent(context, ListViewActivity.class);
        intent.putExtra("taskList", new Gson().toJson(taskList));
        startActivity(intent);
    }
}
