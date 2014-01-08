package com.iconchon.madole1;

import helper.DatabaseHelper;
import helper.JSONParser;
import helper.User;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.os.AsyncTask;
import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class InstantMessage extends Activity {

	private String userid;
	private String toid;
	private String url;
	private String token;
	private String message;
	private String msgid;
	private String errormessage;
	private String error;
	
	DatabaseHelper db;
	
	JSONParser jsonParser = new JSONParser();
	 
//	JSONObject imsg = null;
	JSONArray imsg = null;
	
	/* (non-Javadoc)
	 * @see android.app.Activity#onCreate(android.os.Bundle)
	 */
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_instant_message);
		
		Intent in = getIntent();
	    userid = in.getStringExtra("userid");
		
		Log.d(" Activity Name >", " InstantMessage ");
		Log.d("Send Message to :", userid);
		
//		DatabaseHandler db = new DatabaseHandler(this);
		db = new DatabaseHelper(getApplicationContext());

		
		User pengguna = db.getUser(1);
		url = pengguna.getUrl();
		token = pengguna.getToken();
		db.closeDB();
	
//		List<User> pengguna = db.getAllUsers();
//		for (User cn: pengguna ) {
//			url = cn.getUrl();
//			token = cn.getToken();
//			String log = 
//					"Id: "+cn.getId()+
//					" ,TOKEN: " + cn.getToken() + 
//					" ,URL: " + cn.getUrl();
//			Log.d(" Instan Message nih : "+cn.getId(), log);
//		}
		

//		Bundle bundle = this.getIntent().getExtras();
//        userid = bundle.getString("userid");
        toid = "7";
        
        url = url.concat("/webservice/rest/server.php");
        
        Button send = (Button) findViewById(R.id.send);
        send.setOnClickListener(new OnClickListener() {
			
			public void onClick(View v) {
				// TODO Auto-generated method stub
				EditText msg = (EditText) findViewById(R.id.im_editText);
				message = msg.getText().toString();
				
				new LoadSend().execute();
			}
		});
        
    	
        
	}

	class LoadSend extends AsyncTask<String, String, String>{

		@Override
		protected String doInBackground(String... args) {
			// TODO Auto-generated method stub
		
			List<NameValuePair> params = new ArrayList<NameValuePair>();
			params.add(new BasicNameValuePair("wstoken", token));
			params.add(new BasicNameValuePair("moodlewsrestformat", "xml"));
			params.add(new BasicNameValuePair("wsfunction", "core_message_send_instant_messages"));
			params.add(new BasicNameValuePair("messages[0][touserid]", toid));
			params.add(new BasicNameValuePair("messages[0][text]", message));
			params.add(new BasicNameValuePair("messages[0][textformat]", "2" ));
			
			String json = jsonParser.makeHttpRequest(url, "GET", params);
			
			Log.d("core_enrol_get_users_courses >", json.toString());
			
			try {
				imsg = new JSONArray(json);
//				
//				msgid=imsg.getString("msgid");
//				errormessage=imsg.getString("errormessage");
//				error=null;
				
				for (int i = 0; i < imsg.length(); i++) {
					JSONObject c = imsg.getJSONObject(i);
					msgid=c.getString("msgid");
					errormessage=c.getString("errormessage");
					Log.d("msgid", msgid);
					Log.d("errormessage", errormessage);
				}
			} catch (JSONException e) {
				e.printStackTrace();
				Log.d("error 1 :",e.toString());
//				try {
////					error=c.getString("errormessage");
//					Log.d("error aja :",error.toString());
//				} catch (JSONException e1) {
//					e1.printStackTrace();
//					Log.d("error 2 :",e1.toString());
//				}
			}
			
			return null;
		}
		
	
	}; 
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.instant_message, menu);
		return true;
	}

}
