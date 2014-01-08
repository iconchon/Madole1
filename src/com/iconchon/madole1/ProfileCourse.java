package com.iconchon.madole1;

import helper.JSONParser;

import java.util.ArrayList;
import java.util.List;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import helper.DatabaseHelper;
import helper.ImageLoader;
import helper.Functions;
import helper.User;
import android.os.AsyncTask;
import android.os.Bundle;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.util.Log;
import android.view.Menu;
import android.widget.ImageView;
import android.widget.TextView;

public class ProfileCourse extends Activity {
	
	DatabaseHelper db;
	
    private String token;
    private String url;
    private String userid;
    TextView tvphone1;
    String phone1;
	
	// Progress Dialog
	private ProgressDialog pDialog;

	// Creating JSON Parser object
	JSONParser jsonParser = new JSONParser();

	// products JSONArray
	JSONArray profile = null;
	JSONObject info = null;

	// Inbox JSON url
	//private static final String PROFILE_URL = "http://192.168.1.10/madol/core_user_get_users_by_id.html";
	//private static final String PROFILE_URL = "http://10.0.2.2/madol/userprofile.html";
	//private static final String PROFILE_URL = "http://10.0.2.2/moodle/webservice/rest/server.php";
//	String wstoken="2b74d3ed04a029857bcf33a46d8e322c",
//			moodlewsrestformat="json",
//			wsfunction="core_user_get_users_by_id",
//			userid="2";
	
	// ALL JSON node names
	private static final String TAG_ID = "id";
	private static final String TAG_USERNAME = "username";
	private static final String TAG_FULLNAME = "fullname";
	private static final String TAG_EMAIL = "email";
//	private static final String TAG_ADDRESS = "address";
	private static final String TAG_PHONE1 = "phone1";
//	private static final String TAG_PHONE2 = "phone2";
	private static final String TAG_ID_NUMBER = "idnumber";
	private static final String TAG_CITY = "city";
//	private static final String TAG_URL = "url";
	private static final String TAG_IMAGE = "profileimageurl";
	private static final String TAG_LASTACC ="lastaccess";
	
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile_course);
        
        Log.d(" Activity Name >", " ProfileCourse ");
        
        Bundle bundle = this.getIntent().getExtras();
        userid = bundle.getString("userid");
        
		pDialog = new ProgressDialog(ProfileCourse.this);
//		pDialog.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
//		pDialog.setMessage("Loading Profile");
		pDialog.setIndeterminate(false);
		pDialog.setCancelable(true);
//		pDialog.setMax(100);
//		pDialog.setProgress(0);
		pDialog.show();
		
//        Intent inte = getIntent();
//	    String xx = inte.getStringExtra("userid");
//	    String yy = inte.getStringExtra("siteurl");
//	    //String URL = in.getStringExtra("URL");
//	    
//	    Log.d("satu", xx);
//	    Log.d("dua", yy);
		
	    
	    
//        Intent profiler = getIntent();
//        String satu  = profiler.getStringExtra("Value1");
//        String dua  = profiler.getStringExtra("Value2");
        
        
        //Bundle bundle = this.getIntent().getExtras();
		//String satu = bundle.getString("satu");
        //String dua = bundle.getString("dua");
        
        //Log.d("satu",satu.toString());
        //Log.d("dua",dua.toString());
        
//        DatabaseHandler db = new DatabaseHandler(this);
		db = new DatabaseHelper(getApplicationContext());
		
//        List<User> loginuser = db.getAllUsers();
        User loginuser = db.getUser(1);
		url = loginuser.getUrl();
		token = loginuser.getToken();
        
//		for (User cn: loginuser ) {
//			url = cn.getUrl();
//			token = cn.getToken();
//			String log = 
//	        		"Id: "+cn.getID()+
//	        		" ,TOKEN: " + cn.getToken() + 
//	        		" ,URL: " + cn.getUrl();
//	        Log.d(" Course List nih : "+cn.getID(), log);
//		}
        
		url = url.concat("/webservice/rest/server.php");
//		Log.d("url profile :",url.toString());

		new LoadProfile().execute();
        // Hashmap for ListView
        //contentList = new ArrayList<HashMap<String, String>>();
 
        // Loading INBOX in Background Thread
        
        
		db.close();
        
    }
    
    /**
	 * Background A sync Task to Load all INBOX messages by making HTTP Request
	 * */
	class LoadProfile extends AsyncTask<String, String, String> {

		/**
		 * Before starting background thread Show Progress Dialog
		 * */
		@Override
		protected void onPreExecute() {
			super.onPreExecute();

		}

		protected String doInBackground(String... args) {
			
//			Intent in = getIntent();
//		    userid = in.getStringExtra("userid");
		    
		    
		    
//		    Toast.makeText(ProfileCourse.this, "userid :"+ userid, Toast.LENGTH_LONG).show();
		    
			
//			List<NameValuePair> param = new ArrayList<NameValuePair>();
//			param.add(new BasicNameValuePair("wstoken", token));
//			param.add(new BasicNameValuePair("moodlewsrestformat", "json"));
//			param.add(new BasicNameValuePair("wsfunction", "core_webservice_get_site_info"));
//			Log.d("url doin bg :",url.toString());
//			String json1 = jsonParser.makeHttpRequest(url, "GET", param);
//			
//			Log.d("Inbox JSON: satu ", json1.toString());
//			
//			try {
//				info = new JSONObject(json1);
//				userid =info.getString("userid");
//				Log.d("ini user id nya",userid);
//			} catch (JSONException e) {e.printStackTrace();}
			
//			while(pDialog.getProgress() < 50) {pDialog.incrementProgressBy(1);}
			
			return null;
		}

		/**
		 * After completing background task Dismiss the progress dialog
		 * **/
		
		protected void onPostExecute(String file_url) {		
			
			List<NameValuePair> params = new ArrayList<NameValuePair>();
//			while(pDialog.getProgress() < 100) {pDialog.incrementProgressBy(1);}
			// post album id, song id as GET parameters
			params.add(new BasicNameValuePair("wstoken", token));
			params.add(new BasicNameValuePair("moodlewsrestformat", "json"));
			params.add(new BasicNameValuePair("wsfunction", "core_user_get_users_by_id"));
			params.add(new BasicNameValuePair("userids[0]", userid));
			
			// getting JSON string from URL
//			Log.d("url on post bg :",url);
			String json = jsonParser.makeHttpRequest(url, "GET", params);

			// Check your log cat for JSON reponse
			Log.d("core_user_get_users_by_id >", json.toString());

			try {
				//inbox = json.getJSONArray(TAG_MESSAGES);
				profile = new JSONArray(json);
				// looping through All messages
				for (int i = 0; i < profile.length(); i++) {
					JSONObject c = profile.getJSONObject(i);

					// Storing each json item in variable
					String id = c.getString(TAG_ID);
					String username = c.getString(TAG_USERNAME);
					String fullname = c.getString(TAG_FULLNAME);
					String email = c.getString(TAG_EMAIL);
					
					
//					String phone2 = c.getString(TAG_PHONE2);
//					String address = c.getString(TAG_ADDRESS);
					String city = c.getString(TAG_CITY);
					String idnumber = c.getString(TAG_ID_NUMBER);
					String imageurl = c.getString(TAG_IMAGE);
//					String url = c.getString(TAG_URL);
					String lastaccess = c.getString(TAG_LASTACC);
					
					//Time Convert From Unix time stamp
					String at = Functions.Convert(lastaccess);
					
					TextView tvid = (TextView) findViewById(R.id.pid);
					TextView tvusername = (TextView) findViewById(R.id.pUsername);
					TextView tvfullname = (TextView) findViewById(R.id.pFullname);
					TextView tvemail = (TextView) findViewById(R.id.pEmail);
					
//					TextView tvphone2 = (TextView) findViewById(R.id.pPhone2);
//					TextView tvaddress = (TextView) findViewById(R.id.pAddress);
					TextView tvcity = (TextView) findViewById(R.id.pCity);
					TextView tvidnumber = (TextView) findViewById(R.id.pIdnumber);
//					TextView tvurl = (TextView) findViewById(R.id.pUrl);
					TextView tvlastacc = (TextView) findViewById(R.id.pLastaccess);
					
					tvid.setText("Course Profile User Id :"+id);
					tvfullname.setText(fullname);
					tvusername.setText(username);
					tvemail.setText(email);
					
//					tvphone2.setText(phone2);
//					tvaddress.setText(address);
					tvcity.setText(city);
					tvidnumber.setText(idnumber);
//					tvurl.setText(url);
					tvlastacc.setText(at);
					
					// Loader image - will be shown before loading image
			        int loader = R.drawable.loader;
			        
			        // Imageview to show
			        ImageView image = (ImageView) findViewById(R.id.image);
			        
					// Image url
			        //String image_url = "http://api.androidhive.info/images/sample.jpg";
			        String image_url = imageurl.toString();
			        
			        // ImageLoader class instance
			        ImageLoader imgLoader = new ImageLoader(getApplicationContext());
			        
			        // whenever you want to load an image from url
			        // call DisplayImage function
			        // Url - image Url to load
			        // loader - loader image, will be displayed before getting image
			        // image - ImageView 
			        imgLoader.DisplayImage(image_url, loader, image);
				}

			} catch (JSONException e) {
				e.printStackTrace();
			}
			
			try {
				profile = new JSONArray(json);
				// looping through All messages
				for (int i = 0; i < profile.length(); i++) {
					JSONObject c = profile.getJSONObject(i);
					phone1 = c.getString(TAG_PHONE1);
					tvphone1 = (TextView) findViewById(R.id.pPhone1);
					tvphone1.setText(phone1);
				}
			}
			catch (JSONException e) {e.printStackTrace();}
			pDialog.dismiss();
		}

	}


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.activity_profile_course, menu);
        return true;
    }
}
