package com.example.daniel.manylists;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.io.Serializable;
import java.util.ArrayList;

public class ListViewActivity extends AppCompatActivity {

    ArrayList<Task> list = new ArrayList();
    ListView listTasks;
    private TaskList taskList;
    private DBHelper db;
    private Context context = this;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_view);

        listTasks = (ListView) findViewById(R.id.listTasks);

        listTasks.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
                Task selectedTask = (Task) listTasks.getItemAtPosition(position);
                Intent intent = new Intent(context, TaskViewActivity.class);
                intent.putExtra("task", (Serializable) selectedTask);
                startActivity(intent);
            }
        });

        db = DBHelper.getInstance(this);

        loadList();

    }

    private void loadList()
    {
        taskList = (TaskList) getIntent().getSerializableExtra("taskList");
        list = db.readContent(taskList.getName());
        makeTrackAdapter();
    }

    public void makeTrackAdapter()
    {
        ArrayAdapter arrayAdapter = new ArrayAdapter<>
                (this, android.R.layout.simple_list_item_1, android.R.id.text1, list);
        assert listTasks != null;
        listTasks.setAdapter(arrayAdapter);
    }

    public void addTask(View view)
    {
        Intent intent =  new Intent(context, AddTaskActivity.class);
        intent.putExtra("list", taskList.getName());
        startActivity(intent);
    }
}
