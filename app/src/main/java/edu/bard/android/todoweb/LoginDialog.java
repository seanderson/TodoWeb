package edu.bard.android.todoweb;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

/**
 * Created by sven on 12/4/16.
 */

public class LoginDialog extends Dialog {
    String user = null;
    String password = null;
    LoginFragment parentFrag  = null;

    // simple dialog for login
    public LoginDialog(Context c,LoginFragment frag) {
        super(c);
        parentFrag = frag;
        setTitle(R.string.login_title);
        setContentView(R.layout.login_dialog);

        Button loginbutton = (Button) findViewById(R.id.login_button);

        loginbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                user = ((EditText) findViewById(R.id.username)).getText().toString();
                password = ((EditText) findViewById(R.id.password)).getText().toString();
                String[] pair = {user,password};
                parentFrag.sendResult(Activity.RESULT_OK,pair);
                parentFrag.dismiss();
            }
        });
    }

}
