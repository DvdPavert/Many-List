package com.example.daniel.manylists;

/**
 * Created by Daniel on 02/10/2017.
 */

public class Task {

    private int ID;
    private String name;
    private String task;
    private String status;
    private String list;

    public Task(String name, String task, String list)
    {
        this.ID = -1;
        this.name = name;
        this.task = task;
        this.status = "incomplete";
        this.list = list;
    }

    public Task(int id, String name, String task, String status, String list)
    {
        this.ID = id;
        this.name = name;
        this.task = task;
        this.status = status;
        this.list = list;
    }

    public int getID()
    {
        return ID;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getTask() {
        return task;
    }

    public void setTask(String task) {
        this.task = task;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String  getList() {
        return list;
    }

    public void setList(TaskList list) {
        this.list = list.getName();
    }

    @Override
    public String toString()
    {
        return name;
    }
}



