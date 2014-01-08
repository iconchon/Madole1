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

import android.app.ListActivity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.Toast;

public class CourseList extends ListActivity {
	
	// Database Helper
	DatabaseHelper db;
		
    private String token;
    private String url;
    private String userid;
    
	// Progress Dialog
	private ProgressDialog pDialog;

	// Creating JSON Parser object
	JSONParser jsonParser = new JSONParser();

	ArrayList<HashMap<String, String>> infoList;
	ArrayList<HashMap<String, String>> courseList;

	// products JSONArray
	JSONArray course = null;
	JSONObject info = null;

	// Inbox JSON url
	//private static final String INBOX_URL = "http://192.168.43.8/moodle/webservice/rest/server.php";
	//private static final String INBOX_URL = "http://10.0.2.2/moodle/webservice/rest/server.php";
//	String wstoken=null;
//	String moodlewsrestformat=null;
//	String wsfunction=null;
//	String userid=null;
//	String cId;
	//TextView debugID;
	
	// ALL JSON node names
	//private static final String TAG_MESSAGES = "messages";
	private static final String TAG_ID = "id";
	private static final String TAG_FULLNAME = "fullname";
	//private static final String TAG_EMAIL = "shortname";
	private static final String TAG_SHORTNAME = "shortname";
	private static final String TAG_COUNT = "enrolledusercount";
	

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_course_list);
		
		Log.d(" Activity Name >", "======CourseList ");
		
//		DatabaseHandler db = new DatabaseHandler(this);
		db = new DatabaseHelper(getApplicationContext());
		
		User loginuser = db.getUser(1);
		url = loginuser.getUrl();
		token = loginuser.getToken();
		db.closeDB();
		
//		List<User> loginuser = db.getAllUsers();
//		db.close();
//		for (User cn: loginuser ) {
//			url = cn.getUrl();
//			token = cn.getToken();
//			String log = 
//	        		"Id: "+cn.getId()+
//	        		" ,TOKEN: " + cn.getToken() + 
//	        		" ,URL: " + cn.getUrl();
//	        Log.d(" Course List nih : "+cn.getId(), log);
//		}
		
		TextView debugID = (TextView) findViewById(R.id.debugId);
		debugID.setText("Main Menu - Course List");
		
		url = url.concat("/webservice/rest/server.php");
//		Log.d("url baru :",url);
		
		//String urlId = pathURL.concat("?wsfunction=core_webservice_get_site_info&moodlewsrestformat=json&wstoken="+token);
			
		Intent in = getIntent();
	    userid = in.getStringExtra("userid");
	    
	    //Toast.makeText(CourseList.this, "userid :"+ userid, Toast.LENGTH_LONG).show();
	    //String URL = in.getStringExtra("URL");
		
	    //System.out.println(userid);
	    //System.out.println(URL);
	    
		// Hashmap for ListView
        courseList = new ArrayList<HashMap<String, String>>();
 
        // Loading INBOX in Background Thread
        new LoadInbox().execute();
        
        // get listview
     	ListView lv = getListView();        
        
        /**
		 * Listview item click listener
		 * TrackListActivity will be lauched by passing album id
		 * */
		lv.setOnItemClickListener(new android.widget.AdapterView.OnItemClickListener() {
			public void onItemClick(AdapterView<?> arg0, View view, int arg2, long arg3) {
				// on selecting a single album
				// TrackListActivity will be launched to show tracks inside the album
				Intent i = new Intent(getApplicationContext(), MainCourse.class);
				
				// send course id to tracklist activity to get list of activities under that course
				String course_id = ((TextView) view.findViewById(R.id.course_id)).getText().toString();
				String course_names = ((TextView) view.findViewById(R.id.fullname)).getText().toString();
				String course_init = ((TextView) view.findViewById(R.id.subject)).getText().toString();
				
				i.putExtra("course_id", course_id);	
				i.putExtra("course_name", course_names);
				i.putExtra("course_init", course_init);
				i.putExtras(getIntent().getExtras());//=======send extras
				//i.putExtra("path", path);	
				
				startActivity(i);
			}
		});
        
	}

	/**
	 * Background Async Task to Load all INBOX messages by making HTTP Request
	 * */
	class LoadInbox extends AsyncTask<String, String, String> {

		/**
		 * Before starting background thread Show Progress Dialog
		 * */
		@Override
		protected void onPreExecute() {
			
			super.onPreExecute();
			pDialog = new ProgressDialog(CourseList.this);
			pDialog.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
//			pDialog.setMessage("Loading Inbox ...");
			pDialog.setIndeterminate(false);
			pDialog.setCancelable(true);
			pDialog.setMax(100);
    		pDialog.setProgress(0);
			pDialog.show();
			
			
		}

		/**
		 * getting  JSON
		 * */
		protected String doInBackground(String... args) {
			
			System.out.println(pDialog.getProgress());

//			List<NameValuePair> param = new ArrayList<NameValuePair>();
//			param.add(new BasicNameValuePair("wstoken", token));
//			param.add(new BasicNameValuePair("moodlewsrestformat", "json"));
//			param.add(new BasicNameValuePair("wsfunction", "core_webservice_get_site_info"));
//			String json1 = jsonParser.makeHttpRequest(url, "GET", param);
//			Log.d("Inbox JSON: satu ", json1.toString());
//			try {
//				info = new JSONObject(json1);
//				userid =info.getString("userid");
//			} catch (JSONException e) {e.printStackTrace();}
			
			//Intent sendid = new Intent();
			
			// Building Parameters
			List<NameValuePair> params = new ArrayList<NameValuePair>();
			
			// post album id, song id as GET parameters
			params.add(new BasicNameValuePair("wstoken", token));
			params.add(new BasicNameValuePair("moodlewsrestformat", "json"));
			params.add(new BasicNameValuePair("wsfunction", "core_enrol_get_users_courses"));
			params.add(new BasicNameValuePair("userid", userid));
			
			
			// getting JSON string from URL
//			while(pDialog.getProgress() < 100) {pDialog.incrementProgressBy(1);}
			String json = jsonParser.makeHttpRequest(url, "GET", params);

			// Check your log cat for JSON reponse
			Log.d("core_enrol_get_users_courses >", json.toString());
//			db = new DatabaseHelper(getApplicationContext());
//			Course dbcourse = new Course(); /** ============================== */

			try {
				course = new JSONArray(json);

				// looping through All messages
				int j = 100 / course.length();
				for (int i = 0; i < course.length(); i++) {
					
					pDialog.incrementProgressBy(j);
					System.out.println(pDialog.getProgress());
					
					JSONObject c = course.getJSONObject(i);

					// Storing each json item in variable
					String id = c.getString(TAG_ID);
					String from = c.getString(TAG_FULLNAME);
					String subject = c.getString(TAG_SHORTNAME);
					String count = c.getString(TAG_COUNT);
					
					
//					dbcourse.setCourseName(TAG_FULLNAME);
//					db.createCourse(dbcourse);
					
					
					// creating new HashMap
					HashMap<String, String> map = new HashMap<String, String>();

					// adding each child node to HashMap key => value
					map.put(TAG_ID, id);
					map.put(TAG_FULLNAME, from);
					map.put(TAG_SHORTNAME, subject);
					map.put(TAG_COUNT, count);

					// adding HashList to ArrayList
					courseList.add(map);
				}
				
//		        db.close();

			} catch (JSONException e) {
				e.printStackTrace();
				Toast.makeText(getApplicationContext(), e.toString(), Toast.LENGTH_LONG).show();
			
			}
			
			while(pDialog.getProgress() < 100) {pDialog.incrementProgressBy(1);}
			return null;
		}

		/**
		 * After completing background task Dismiss the progress dialog
		 * **/
		protected void onPostExecute(String file_url) {
			// dismiss the dialog after getting all products
			pDialog.dismiss();
			
			// updating UI from Background Thread
			runOnUiThread(new Runnable() {
				public void run() {
					/**
					 * Updating parsed JSON data into ListView
					 * */
					ListAdapter adapter = new SimpleAdapter(
							CourseList.this, courseList,
							R.layout.activity_course_list_item, 
							new String[] { TAG_ID ,TAG_FULLNAME, TAG_SHORTNAME, TAG_COUNT },
							new int[] { R.id.course_id ,R.id.fullname, R.id.subject, R.id.count });
					// updating listview
					setListAdapter(adapter);
				}
			});

		}

	}
	
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
		case R.id.about:
			Intent about = new Intent(CourseList.this, About.class);
			startActivity(about);
			return true;
		case R.id.feedback:
			Intent feedback = new Intent(CourseList.this, Feedback.class);
			startActivity(feedback);
			return true;
		case R.id.help:
			Intent help = new Intent(CourseList.this, Help.class);
			startActivity(help);
			return true;
		case R.id.settings:
			Intent server = new Intent(CourseList.this, Server.class);
			startActivity(server);
			return true;
		default:
			return super.onOptionsItemSelected(item);
		}
	}
}

