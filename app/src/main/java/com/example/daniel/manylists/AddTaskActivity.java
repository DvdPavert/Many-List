package com.example.daniel.manylists;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

public class AddTaskActivity extends AppCompatActivity {

    private EditText editTitle;
    private EditText editTask;
    private DBHelper db;

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
        String list = getIntent().getStringExtra("list");
        String title = editTitle.getText().toString();
        String task = editTask.getText().toString();

        Task newTask = new Task(title, task, list);
        db.createContent(newTask);

        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }
}
