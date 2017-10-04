package com.example.daniel.manylists;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.InputType;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private ArrayList<TaskList> list = new ArrayList();
    private ListView listList;
    private DBHelper db;
    private Context context = this;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        listList = (ListView) findViewById(R.id.listList);

        listList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
                TaskList selectedTaskList = (TaskList) listList.getItemAtPosition(position);
                Intent intent = new Intent(context, ListViewActivity.class);
                intent.putExtra("taskList", (Serializable) selectedTaskList);
                startActivity(intent);
            }
        });

        db = DBHelper.getInstance(this);

        makeTrackAdapter();

        list = db.readLists(); //// TODO: 04/10/2017 FIX THIS ERROR
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
        ArrayAdapter arrayAdapter = new ArrayAdapter<>
                (this, android.R.layout.simple_list_item_1, android.R.id.text1, list);
        assert listList != null;
        listList.setAdapter(arrayAdapter);
    }

}
