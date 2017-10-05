package com.example.daniel.manylists;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.google.gson.Gson;

import java.io.Serializable;
import java.util.ArrayList;

public class ListViewActivity extends AppCompatActivity {

    private ArrayList<Task> list = new ArrayList();
    private ListView listTasks;
    private TaskList taskList;
    private DBHelper db;
    private Context context = this;
    private ArrayAdapter arrayAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_view);

        listTasks = (ListView) findViewById(R.id.listTasks);

        setListeners();

        db = DBHelper.getInstance(this);

        loadList();

        makeTrackAdapter();
    }

    private void loadList()
    {
        String JSONList = getIntent().getStringExtra("taskList");
        taskList = new Gson().fromJson(JSONList, TaskList.class);
        list = db.readContent(taskList.getName());

    }

    public void makeTrackAdapter()
    {
        arrayAdapter = new ArrayAdapter<>
                (this, android.R.layout.simple_list_item_1, android.R.id.text1, list);
        assert listTasks != null;
        listTasks.setAdapter(arrayAdapter);
    }

    public void addTask(View view)
    {
        Intent intent =  new Intent(context, AddTaskActivity.class);
        intent.putExtra("taskList", new Gson().toJson(taskList));
        startActivity(intent);
    }

    private void setListeners()
    {
        listTasks.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
                Task selectedTask = (Task) listTasks.getItemAtPosition(position);
                Intent intent =  new Intent(context, TaskViewActivity.class);
                intent.putExtra("task", new Gson().toJson(selectedTask));
                intent.putExtra("taskList", new Gson().toJson(taskList));
                startActivity(intent);
            }
        });

        listTasks.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {

            @Override
            public boolean onItemLongClick(AdapterView<?> adapterView, View view, int position, long id) {
                Task selectedItem = (Task) arrayAdapter.getItem(position);
                list.remove(selectedItem);
                makeTrackAdapter();
                db.deleteContent(selectedItem);
                return true;
            }
        });
    }

    @Override
    public void onBackPressed() {
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);

    }
}
