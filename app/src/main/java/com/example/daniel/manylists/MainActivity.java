package com.example.daniel.manylists;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Parcelable;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.InputType;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;

import com.google.gson.Gson;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private ArrayList<TaskList> list = new ArrayList();
    private ListView listList;
    private DBHelper db;
    private Context context = this;
    private  ArrayAdapter arrayAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        listList = (ListView) findViewById(R.id.listList);

        setListeners();

        db = DBHelper.getInstance(this);

//        Task testTask0 = new Task("testTask0", "testTaskContent0");
//        Task testTask1 = new Task("testTask1", "testTaskContent1");
//
//        TaskList testList0 = new TaskList("testName", "testStatus");
//
//        testList0.add(testTask0);
//        testList0.add(testTask1);
//
//        db.createList(testList0);
//        db.createContent(testTask0);
//        db.createContent(testTask1);

        list = db.readLists();

        makeTrackAdapter();
    }



    //Creates popup where a user can add a list
    public void addList(View view) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Title");

        final EditText input = new EditText(this);
        input.setInputType(InputType.TYPE_CLASS_TEXT);
        builder.setView(input);


        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                String listTitle = input.getText().toString();
                TaskList newList = new TaskList(listTitle, "empty");
                list.add(newList);
                db.createList(newList);
                makeTrackAdapter();
            }
        });

        builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        });

        builder.show();

    }


    // Displays the list in the ListView
    public void makeTrackAdapter() {
        arrayAdapter = new ArrayAdapter<>
                (this, android.R.layout.simple_list_item_1, android.R.id.text1, list);
        assert listList != null;
        listList.setAdapter(arrayAdapter);
    }

    private void setListeners()
    {
        listList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
                TaskList selectedTaskList = (TaskList) listList.getItemAtPosition(position);
                Intent intent = new Intent(context, ListViewActivity.class);
                intent.putExtra("taskList", new Gson().toJson(selectedTaskList));
                startActivity(intent);
            }
        });

        listList.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {

            @Override
            public boolean onItemLongClick(AdapterView<?> adapterView, View view, int position, long id) {
                TaskList selectedItem = (TaskList) arrayAdapter.getItem(position);
                list.remove(selectedItem);
                db.deleteListContent(selectedItem);
                db.deleteList(selectedItem);
                makeTrackAdapter();
                return true;
            }
        });
    }

}
