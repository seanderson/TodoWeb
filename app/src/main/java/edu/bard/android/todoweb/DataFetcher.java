package edu.bard.android.todoweb;


import android.content.Context;
import android.content.res.Resources;
import android.os.AsyncTask;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;

/**
 * Fetch data from server on separate thread.  Data returned as JSON array.
 * @author S.Anderson 11/25/16.
 *
 */
class DataFetcher extends AsyncTask<String, String, JSONObject> {
    private TodoFragment mTodoFragment = null;
    private static String SERVER_URL;
    private static String LOGIN_URL;
    private static String DB_URL;
    private static final String TAG_SUCCESS = "success";
    private static final String TAG_MESSAGE = "message";
    private Context context;
    private JSONObject dataJSN = null;  // JSON data recovered from fetch
    private boolean retryLogin;

    public DataFetcher(TodoFragment frag,Context context) {
        this.mTodoFragment = frag;
        this.context = context;
        retryLogin = true;
        dataJSN = null;
        Resources res = context.getResources();
        SERVER_URL = res.getString(R.string.server_url);
        LOGIN_URL = SERVER_URL + res.getString(R.string.login_url);
        DB_URL = SERVER_URL + res.getString(R.string.db_url);
    }

    @Override
    protected void onPreExecute() {

    }

    @Override
    protected JSONObject doInBackground(String... args) {
        JSONParser jsonParser = new JSONParser();
        JSONObject json = null;
        HashMap<String, String> params = new HashMap<>();
        if (args != null && args.length == 2) { // login first
            if ( !login(jsonParser,args[0],args[1]) ) {
                retryLogin = false; // had password but failed to login
                return json; // signals failure to login (bad password)
            }
        }
        try { // try get the database
            params.clear();
            json = jsonParser.makeHttpRequest(DB_URL, "GET", params);
            if (json != null) {
                Log.d("JSON db result", json.toString());
                dataJSN = json;
            } else { // failed to get db, signal that we need login in onPostExecute
                json = null;
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return json;
    }



    /**
     * Note that this is run on the main UI thread, so
     * it can update data and views.
     * @param json
     */
    protected void onPostExecute(JSONObject json) {
        if (json == null) {
            if (retryLogin) this.mTodoFragment.showLogin();
        }
        else {
            //Toast.makeText(context, json.toString(),Toast.LENGTH_SHORT).show();
            try {
                Resources res = context.getResources();
                JSONArray jsonArray = json.getJSONArray(res.getString(R.string.json_array));
                for (int i=0; i<jsonArray.length(); i++) {
                    JSONObject jobj = jsonArray.getJSONObject(i);

                    String p = jobj.getString(res.getString(R.string.json_user));
                    String t = jobj.getString(res.getString(R.string.json_task));
                    mTodoFragment.addItem(p,t); // add to arraylist
                }
            } catch (JSONException jex) {
                Log.d("ERROR",jex.toString());
            }
        }

    }

    /*
      User has entered authent info, so try to login.
     */

    public boolean login(JSONParser jsonParser, String user,String password) {
        HashMap<String, String> params = new HashMap<>();

        Resources res = context.getResources();
        params.put(res.getString(R.string.authent_user), user);  // authentication params
        params.put(res.getString(R.string.authent_password), password);

        JSONObject json = jsonParser.makeHttpRequest(LOGIN_URL, "POST", params);
        try {
            if (json != null && json.getBoolean("login")) {
                Log.d("JSON login", json.toString());
                return true;
            } else { // failed to login, give up
                Log.d("JSON login", "failed");
                return false;
            }
        } catch (JSONException jex) {
            return false; // failure for any anomoly
        }
    }

    /** Returns data after execute. Will return null if
     * no data was obtained.
     * @return jsn data from database.
     */
    public JSONObject getDataJSN() {
        return this.dataJSN;
    }
}