package com.example.daniel.manylists;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;

/**
 * Created by Daniel on 02/10/2017.
 */

public class DBHelper extends SQLiteOpenHelper {

        private static DBHelper sInstance;

        private static final String DATABASE_NAME = "todoDB.db";
        private static final int DATABASE_VERSION = 1;
        private static final String KEY_ID = "_id";
        private static final String KEY_NAME = "name";
        private static final String KEY_TASK = "task";
        private static final String KEY_STATUS = "status";
        private static final String KEY_LIST = "list";
        private static final String TABLE_CONTENT = "content";
        private static final String TABLE_LIST = "lists";

        public static synchronized DBHelper getInstance(Context context)
        {
            if(sInstance == null)
            {
                sInstance = new DBHelper(context.getApplicationContext());
            }
            return sInstance;
        }

       private DBHelper(Context context) {
            super(context, DATABASE_NAME, null, DATABASE_VERSION);
        }

        @Override
        public void onCreate(SQLiteDatabase db) {
                String CREATE_TABLE_CONTENT = "CREATE TABLE " + TABLE_CONTENT + "(" + KEY_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                        + KEY_NAME + " TEXT NOT NULL," + KEY_TASK + " TEXT NOT NULL," + KEY_STATUS + " TEXT NOT NULL)";

                String CREATE_TABLE_LISTS = "CREATE TABLE " + TABLE_LIST + "(" + KEY_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                        + KEY_NAME + " TEXT NOT NULL," + KEY_STATUS + " TEXT NOT NULL)";

                db.execSQL(CREATE_TABLE_CONTENT);
                db.execSQL(CREATE_TABLE_LISTS);
        }

        @Override
        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
            db.execSQL("DROP TABLE IF EXISTS " +  TABLE_CONTENT);
            db.execSQL("DROP TABLE IF EXISTS " + TABLE_LIST);
            onCreate(db);
        }

        public void createContent(Task task)
        {
            SQLiteDatabase db = getWritableDatabase();

            ContentValues values = new ContentValues();
            values.put(KEY_NAME, task.getName());
            values.put(KEY_TASK, task.getTask());
            values.put(KEY_STATUS, task.getStatus());
            values.put(KEY_LIST, task.getList());

            db.insert(TABLE_CONTENT, null, values);
            db.close();
        }

        public void createList(TaskList list)
        {
            SQLiteDatabase db = getWritableDatabase();

            ContentValues values = new ContentValues();
            values.put(KEY_NAME, list.getName());
            values.put(KEY_STATUS, list.getStatus());

            db.insert(TABLE_LIST, null, values);
            db.close();
        }

        public ArrayList<Task> readContent(String taskList)
        {
            SQLiteDatabase db = getReadableDatabase();

            ArrayList<Task> list = new ArrayList<>();

            String query =  "SELECT " + KEY_ID + ", " +  KEY_NAME + ", " +  KEY_TASK + ", " + KEY_STATUS
                    + ", " + KEY_LIST  +" FROM " + TABLE_CONTENT + "WHERE" + KEY_LIST  + "=" + taskList;

            Cursor cursor = db.rawQuery(query, null);

            if(cursor.moveToFirst())
            {
                do
                {
                    int id = cursor.getInt(cursor.getColumnIndex(KEY_ID));
                    String name = cursor.getString(cursor.getColumnIndex(KEY_NAME));
                    String task = cursor.getString(cursor.getColumnIndex(KEY_TASK));
                    String status = cursor.getString(cursor.getColumnIndex(KEY_STATUS));
                    String currentList = cursor.getString(cursor.getColumnIndex(KEY_LIST));

                    Task newTask = new Task(id, name, task, status, currentList);
                    list.add(newTask);
                }
                while(cursor.moveToNext());
            }

            cursor.close();
            db.close();

            return list;
        }

        public ArrayList<TaskList> readLists()
        {
            SQLiteDatabase db = getReadableDatabase();

            ArrayList<TaskList> list = new ArrayList<>();

            String query =  "SELECT " + KEY_ID + ", " +  KEY_NAME + ", " + KEY_STATUS +  " FROM " + TABLE_LIST; //// TODO: 04/10/2017 WHY ERROR HERE QUERY FUCKING HELL 

            Cursor cursor = db.rawQuery(query, null);

            if(cursor.moveToFirst())
            {
                do
                {
                    int id = cursor.getInt(cursor.getColumnIndex(KEY_ID));
                    String name = cursor.getString(cursor.getColumnIndex(KEY_NAME));
                    String status = cursor.getString(cursor.getColumnIndex(KEY_STATUS));

                    TaskList taskList = new TaskList(id, name, status);
                    list.add(taskList);
                }
                while(cursor.moveToNext());
            }

            cursor.close();
            db.close();

            return list;
        }

        public int update(Task task)
        {
            SQLiteDatabase db = getWritableDatabase();

            ContentValues values = new ContentValues();
            values.put(KEY_NAME, task.getName());
            values.put(KEY_TASK, task.getTask());
            values.put(KEY_STATUS, task.getStatus());
            values.put(KEY_LIST, task.getList());

            return db.update(TABLE_CONTENT, values, KEY_ID + " = ? ", new String[] {String.valueOf(task.getID())});

        }

        public void deleteContent(Task task)
        {
            SQLiteDatabase db = getWritableDatabase();
            db.delete(TABLE_CONTENT, " " + KEY_ID + " = ? ", new String[] {String.valueOf(task.getID())} );
            db.close();
        }

        public void deleteList(TaskList list)
        {
            SQLiteDatabase db = getWritableDatabase();
            db.delete(TABLE_LIST, " " + KEY_ID + " = ? ", new String[] {String.valueOf(list.getID())} );
            db.close();
        }
    }