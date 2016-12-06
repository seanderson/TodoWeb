package edu.bard.android.todoweb;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;

/**
 * Created by sven on 12/4/16.
 */

public class LoginFragment extends DialogFragment {
    public static final String EXTRA_LOGINDATA =
            "edu.bard.android.todoweb.logindata";

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        return new LoginDialog(this.getContext(),this);
    }

    public static LoginFragment newInstance(String msg) {
        LoginFragment frag = new LoginFragment();
        Bundle args = new Bundle();
        return frag;
    }

    /**
     * This returns the email/passwd pair to the TodoFragmetn.
     * @param resultCode
     * @param data an array of two strings, email,password
     */
    public void sendResult(int resultCode, String[] data) {
        if (getTargetFragment() == null) {
            return;
        }
        Intent intent = new Intent();
        intent.putExtra(EXTRA_LOGINDATA, data);
        getTargetFragment()
                .onActivityResult(getTargetRequestCode(), resultCode, intent);
    }

}


