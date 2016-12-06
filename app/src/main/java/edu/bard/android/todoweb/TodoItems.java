package edu.bard.android.todoweb;

import android.content.Context;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by sven on 12/4/16.
 * This is a poor, temporary substitute for a full database version.
 */

public class TodoItems {
    private static TodoItems sTodoItems;
    private List<Todo> mItems;

    private TodoItems(Context c) {
        mItems = new ArrayList<Todo>();
        for (int i = 0; i < 10; i++) {
            mItems.add(new Todo("psuedo-user" + i, "psuedo-task" + i));
        }
    }

    public static TodoItems get(Context context) {
        if (sTodoItems == null) {
            sTodoItems = new TodoItems(context);
        }
        return sTodoItems;
    }

    /** Add this user/task pair */
    public void add(String user, String task) {
        mItems.add(new Todo(user,task));
    }
    /** Get the underlying data */
    public List<Todo> getItems() {
        return mItems;
    }



}
