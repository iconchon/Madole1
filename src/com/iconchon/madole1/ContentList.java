package com.iconchon.madole1;


import helper.JSONParser;

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
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;

public class ContentList extends ListActivity {
	
//	private String token =null;
	private String userid=null;
    private String siteurl=null;
	
	// Progress Dialog
	private ProgressDialog pDialog;

	// Creating JSON Parser object
	JSONParser jsonParser = new JSONParser();

	ArrayList<HashMap<String, String>> contentList;

	// products JSONArray
	JSONArray content = null;
	JSONArray modules =null;
	JSONArray detail = null;

	// Inbox JSON url
	//private static final String INBOX_URL = "http://192.168.1.10/moodle/webservice/rest/server.php";
//	private static final String INBOX_URL = "http://10.0.2.2/moodle/webservice/rest/server.php";
	String wstoken=null;
//	String moodlewsrestformat= null;
	String wsfunction= "core_course_get_contents";
	
	
	
	// ALL JSON node names
	private static final String TAG_ID = "id";
	private static final String TAG_NAME = "name";
	private static final String TAG_SUMMARY = "summary";
	private static final String TAG_MODULES = "modules";
	//private static final String TAG_MODULES_ID = "id";
	//private static final String TAG_MODULES_NAME = "name";
	//private static final String TAG_MODULES_URL = "url";
	//private static final String TAG_MODULES_CONTENT = "contents";
	//private static final String TAG_MODULES_CONTENT_TYPE = "type";
	
	String courseid,coursename,courseinit;
	
	//public
//	String module_id, module_name, module_url, content_type;
	//String aa;
	
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_content_list);
        
        Log.d(" Activity Name >", "======ContentList ");
//        Log.d("coba ah",Functions);
        
//        Intent in = getIntent();
//	    String userid = in.getStringExtra("userid");
//	    wstoken = in.getStringExtra("token");
	    
	    
	    
        
        Intent i = getIntent();
        courseid = i.getStringExtra("course_id");
        coursename = i.getStringExtra("course_name");
        courseinit = i.getStringExtra("course_init");
        wstoken = i.getStringExtra("token");
        userid = i.getStringExtra("userid");
        siteurl = i.getStringExtra("siteurl");
        

        setTitle(coursename);
        
//        Toast.makeText(ContentList.this, "userid :"+ userid, Toast.LENGTH_LONG).show();
        Log.d("contentlist extre",wstoken+"|"+userid+"|"+siteurl+"|"+courseid+"|"+coursename+"|"+courseinit);
        
        siteurl = siteurl.concat("/webservice/rest/server.php");
        // Hashmap for ListView
        contentList = new ArrayList<HashMap<String, String>>();
 
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
				Intent ContentListDetail = new Intent(getApplicationContext(), ContentListDetail.class);
				
//				Bundle bundle_msg = new Bundle();
				// send course id to tracklist activity to get list of activities under that course
				
				String send_module_id = ((TextView) view.findViewById(R.id.module_id)).getText().toString();
				String send_module_name = ((TextView) view.findViewById(R.id.name)).getText().toString();
				String send_module_string = ((TextView) view.findViewById(R.id.modules)).getText().toString();
				String send_module_summary = ((TextView) view.findViewById(R.id.summary)).getText().toString();
				
//				bundle_msg.putString("module_id", send_module_id);
				ContentListDetail.putExtra("module_id", send_module_id);
				ContentListDetail.putExtra("module_name", send_module_name);
				ContentListDetail.putExtra("module_string", send_module_string);
				ContentListDetail.putExtra("module_summary", send_module_summary);
//				ContentListDetail.putExtra("course_init", courseinit);
				
				ContentListDetail.putExtras(getIntent().getExtras());
                startActivity(ContentListDetail);
                
                
//                ContentListDetail.putExtras(bundle_msg);
//                ContentListDetail.putExtras(getIntent().getBundleExtra("token"));
                
				
				//i.putExtra("course_name", course_names);
				//i.putExtra("path", path);	
				
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
			pDialog = new ProgressDialog(ContentList.this);
			pDialog.setMessage("Loading Content ...");
			pDialog.setIndeterminate(false);
			pDialog.setCancelable(true);
			pDialog.show();
		}

		/**
		 * getting Inbox JSON
		 * */
		protected String doInBackground(String... args) {
			// Building Parameters
			List<NameValuePair> params = new ArrayList<NameValuePair>();
			
			// post album id, song id as GET parameters
			params.add(new BasicNameValuePair("wstoken", wstoken));
			params.add(new BasicNameValuePair("moodlewsrestformat", "json"));
			params.add(new BasicNameValuePair("wsfunction", "core_course_get_contents"));
			params.add(new BasicNameValuePair("courseid", courseid));
			
			// getting JSON string from URL
			String json = jsonParser.makeHttpRequest(siteurl, "GET", params);

			// Check your log cat for JSON reponse
			Log.d("core_course_get_contents ", json.toString());
//			String html = "<p>Summary in this <br /> Now what? </p>";
			

			
			try {
				//inbox = json.getJSONArray(TAG_MESSAGES);
				content = new JSONArray(json);
				// looping through All messages
				for (int i = 0; i < content.length(); i++) {
					JSONObject c = content.getJSONObject(i);

					// Storing each json item in variable
					String id = c.getString(TAG_ID);
					String name = c.getString(TAG_NAME);
					String summary = c.getString(TAG_SUMMARY);
//					String summary;
					String modules = c.getString(TAG_MODULES);
					
					if (summary.equals("")){summary=" No Summary";} else {
						summary = c.getString(TAG_SUMMARY);
					}					
					/**
					try {
						modules = c.getJSONArray(TAG_MODULES);
						for (int j = 0; j < modules.length(); j++) {
							JSONObject cd = modules.getJSONObject(j);
							cd.getString(TAG_MODULES_ID);
							cd.getString(TAG_MODULES_NAME);
							cd.getString(TAG_MODULES_URL);
							
							try {
								detail = cd.getJSONArray(TAG_MODULES_CONTENT);
								for (int k = 0; k < detail.length(); k++) {
									JSONObject cde = detail.getJSONObject(j);
									cde.getString(TAG_MODULES_CONTENT_TYPE);
								} 
							} catch (Exception e ){}
						}
					} catch (Exception e) {	// TODO: handle exception
						}
					**/
					
					//System.out.println(modules);
					
					/*// Module each content
					JSONObject modules = c.getJSONObject(TAG_MODULES);
					module_id = modules.getString(TAG_MODULES_ID);
					module_name = modules.getString(TAG_MODULES_NAME);
					module_url = modules.getString(TAG_MODULES_URL);
					
					// Content of each module
					JSONObject content = modules.getJSONObject(TAG_MODULES_CONTENT);
					content_type = content.getString(TAG_MODULES_CONTENT_TYPE);*/
					

					// creating new HashMap
					HashMap<String, String> map = new HashMap<String, String>();

					// adding each child node to HashMap key => value
					map.put(TAG_ID, id);
					map.put(TAG_NAME, name);
					map.put(TAG_SUMMARY,summary);
					map.put(TAG_MODULES,modules);
					
					//map.put(TAG_MODULES_ID,modules.toString(0));
					//map.put(TAG_MODULES_NAME,modules.toString(1));
					//map.put(TAG_MODULES_URL, summary);
					//map.put(TAG_MODULES_CONTENT_TYPE, summary);

					// adding HashList to ArrayList
					contentList.add(map);
				}

			} catch (JSONException e) {
				e.printStackTrace();
			}

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
							ContentList.this, contentList, R.layout.activity_content_list_item, 
//							new String[] { TAG_ID },
//							new int[] { R.id.module_id});
							new String[] { TAG_ID, TAG_NAME, TAG_SUMMARY, TAG_MODULES },
							new int[] { R.id.module_id, R.id.name, R.id.summary, R.id.modules });
					// updating listview
					setListAdapter(adapter);
				}
			});

		}

	}
	

}
