package edu.bard.android.todoweb;

/**
 * Created by sven on 12/4/16.
 */

public class Todo {
    private String mUser;
    private String mTask;

    public Todo(String u, String t) {
        mUser = u;
        mTask = t;
    }
    public String getUser() {
        return mUser;
    }

    public void setUser(String user) {
        mUser = user;
    }

    public String getTask() {
        return mTask;
    }

    public void setTask(String task) {
        mTask = task;
    }

    @Override
    public String toString() {
        return mUser + ":" + mTask;
    }
}
