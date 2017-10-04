package com.example.daniel.manylists;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Created by Daniel on 02/10/2017.
 */

public class TaskList {

    private int ID;
    private String name;
    private String status;
    private ArrayList<Task> contents;

    public TaskList(int ID, String name, String status)
    {
        this.ID = ID;
        this.name = name;
    }

    public TaskList(String name, String status)
    {
        this.name = name;
        this.status = "empty";
    }

    public void add(Task task)
    {
        contents.add(task);
    }

    public ArrayList<Task> getContents()
    {
        return contents;
    }



    public void delete(Task task)
    {
        contents.remove(task);
    }

    public int getID() {
        return ID;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
