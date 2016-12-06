package edu.bard.android.todoweb;
/*
   This app is intended to demonstrate how you might authenticate with
   a server.  The particular server accesses a postgres database and
   is implemented via Flask.  The server code is called mytododb.
   The server provides access to a database containing pairs of users (emails) and tasks--that's it.
   For authentication pay attention to:
   * DataFetcher: demonstrates how to collect data on an AsynchronousTask.
   * Individual calls to obtain data are made by JSONParser, since web data is
     returned in this common format.  Authentication is held by the CookieManager.
   * LoginDialog/LoginFragment are used to query the user for login details.  Once
     obtained, the CookieManager takes care of repeated authentication with the server.
  @author S. Anderson
 */

import android.support.v4.app.Fragment;

public class TodoWeb extends SingleFragmentActivity {

    @Override
    public Fragment createFragment() {
        return TodoFragment.newInstance(this);
    }
}
