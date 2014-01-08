package com.iconchon.madole1;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import javax.security.auth.PrivateCredentialPermission;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.os.AsyncTask;
import android.os.Bundle;
import android.os.SystemClock;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.text.StaticLayout;
import android.util.Log;
import android.view.Menu;
import android.widget.Toast;
import helper.DatabaseHelper;
import helper.Functions;
import helper.JSONParser;
import helper.User;

public class Help extends Activity {
	
	private long start;
	private long end;
	private String functions;
	
	private ProgressDialog pDialog;

	private static final String TAG_FUNCTION = "functions";
	private static final String TAG_NAME = "name";
	
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_help);
        
        
        
        long now = SystemClock.uptimeMillis();
		//=================================
		start = SystemClock.uptimeMillis();
		//=================================
		/*---------------------------------*/
		end = SystemClock.uptimeMillis();
		

		DatabaseHelper db = new DatabaseHelper(getApplicationContext());
		User loginuser = db.getUser(1);
		String url = loginuser.getUrl();
		String token = loginuser.getToken();
		db.closeDB();
		
		JSONParser jparser = new JSONParser();
		List<NameValuePair> param = new ArrayList<NameValuePair>();
		param.add(new BasicNameValuePair("wstoken", token));
		param.add(new BasicNameValuePair("moodlewsrestformat", "json"));
		param.add(new BasicNameValuePair("wsfunction", "core_webservice_get_site_info"));
		
		String theUrl = url.concat("/webservice/rest/server.php");
		
		String json1 = jparser.makeHttpRequest(theUrl, "GET", param);
		
//		Log.d("core_webservice_get_site_info >", json1.toString());
		
		try {
		
			JSONObject info = new JSONObject(json1);
			functions = info.getString(TAG_FUNCTION);
			
			Log.e("function", functions);
			
		} catch (JSONException e) {e.printStackTrace();}
		
		
		try {
			JSONArray func = new JSONArray(functions);

			// looping through All messages

			for (int i = 0; i < func.length(); i++) {
				
				System.out.println(pDialog.getProgress());
				
				JSONObject item = func.getJSONObject(i);

				// Storing each json item in variable
				String name = item.getString(TAG_NAME);
				
				Log.d("name",name);
				
//				HashMap<String, String> map = new HashMap<String, String>();

			}
			
//	        db.close();

		
		} catch (JSONException e) {e.printStackTrace();}
		
    }
    
    class testing extends AsyncTask<String, String, String> {

    	@Override
		protected void onPreExecute() {
			super.onPreExecute();
			pDialog = new ProgressDialog(Help.this);
			pDialog.setMessage("Please Wait..");
			pDialog.setIndeterminate(false);
			pDialog.setCancelable(true);
			pDialog.show();
		}
    	
    	@Override
		protected String doInBackground(String... params) {
    		
    		

			
    		return null;
		}
    	
    	@Override
    	protected void onPostExecute(String params){
    		
    	}
			
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.activity_help, menu);
        return true;
    }
    
}

