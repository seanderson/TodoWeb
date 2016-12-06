package edu.bard.android.todoweb;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import java.util.List;

/**
 * Created by sven on 11/5/16.
 */

public class TodoFragment extends Fragment {
    private static final String TAG = "TodoFragment";
    private static final String DIALOG_LOGIN = "DialogLogin";
    private static final int REQUEST_LOGIN = 1;
    private RecyclerView mTodoView;
    private TodoAdapter mAdapter;

    public static TodoFragment newInstance(TodoWeb a) {
        return new TodoFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRetainInstance(true);
    }

    /**
     * Brings up the LoginFragment to allow authent.
     */
    public void showLogin() {
        FragmentManager mgr = this.getFragmentManager();
        LoginFragment loginfrag = new LoginFragment();
        loginfrag.setTargetFragment(TodoFragment.this, REQUEST_LOGIN); // to receive return data
        loginfrag.show(mgr,DIALOG_LOGIN);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_todoweb, container, false);

        LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity());
        Button loadbutton = (Button) v.findViewById(R.id.loadButton);
        // Try to load database from server
        // Failure will trigger showLogin via onPostResult in DataFetcher.
        loadbutton.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        new DataFetcher(TodoFragment.this,TodoFragment.this.getContext()).execute();
                    }
                }
        );

        mTodoView = (RecyclerView) v
                .findViewById(R.id.fragment_todo_recycler_view);

        mTodoView.setLayoutManager(layoutManager);
        updateUI();


        return v;
    }

    private void updateUI() {
        TodoItems todoitems = TodoItems.get(getActivity());
        List<Todo> todolist = todoitems.getItems();
        for (int i = 0; i < todolist.size(); i++) Log.d("LIST",todolist.get(i).toString());
        if (mAdapter == null) {  // if null, make a new adapter and bind to the view
            mAdapter = new TodoAdapter(todolist);
            mTodoView.setAdapter(mAdapter);
        } else {  // just notify about changes to underlying data
            mAdapter.notifyItemInserted(todolist.size());
        }
    }

    // Add one item to data, update UI
    public void addItem(String u, String t) {
        TodoItems todoitems = TodoItems.get(getActivity());
        todoitems.add(u,t);
        updateUI();
    }

    /**
     * Handle return of user/password and then
     * run DataFetcher to try again.
     * @param requestCode
     * @param resultCode
     * @param data
     */
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode != Activity.RESULT_OK) {
            return;
        }

        if (requestCode == REQUEST_LOGIN) { // user authentication data to retry fetch
            String[] authent = (String []) data.getSerializableExtra(LoginFragment.EXTRA_LOGINDATA);
            new DataFetcher(TodoFragment.this,TodoFragment.this.getContext()).execute(authent);
        }
    }

    // Holds todo item for recycle view
    private class TodoHolder extends RecyclerView.ViewHolder {

        public TextView mTitleTextView;
        public TodoHolder(View itemView) {
            super(itemView);

            mTitleTextView = (TextView) itemView;
        }
    }

    /*
    Adapter for recycleview.  Largely from BNRG.
     */
    private class TodoAdapter extends RecyclerView.Adapter<TodoHolder> {

        private List<Todo> mTodo;

        public TodoAdapter(List<Todo> todos) {
            mTodo = todos;
        }

        @Override
        public TodoHolder onCreateViewHolder(ViewGroup parent, int
                viewType) {
            LayoutInflater layoutInflater = LayoutInflater.from(getActivity());
            View view = layoutInflater
                    .inflate(android.R.layout.simple_list_item_1, parent, false);
            return new TodoHolder(view);
        }

        @Override
        public void onBindViewHolder(TodoHolder holder, int position) {
            Todo todo = mTodo.get(position);
            holder.mTitleTextView.setText(todo.getUser() + ":" + todo.getTask());
        }

        @Override
        public int getItemCount() {
            return mTodo.size();
        }
    }


}
