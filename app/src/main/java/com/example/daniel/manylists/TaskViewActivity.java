package com.example.daniel.manylists;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class TaskViewActivity extends AppCompatActivity {

    private Task task;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_task_view);

        loadTask();
    }

    private void loadTask()
    {
        task = (Task) getIntent().getSerializableExtra("taskList");
        //// TODO: 04/10/2017  Set task to textboxes
    }
}
