package com.iconchon.madole1;


import helper.ContentsListAdapter;
import helper.ContentsListDetailAdapter;

import java.util.ArrayList;
import java.util.HashMap;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.app.ListActivity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.webkit.WebView;
import android.widget.AdapterView;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.AdapterView.OnItemClickListener;

public class ContentListDetail extends Activity {
	
	// Progress Dialog
	private ProgressDialog pDialog;
	
	ArrayList<HashMap<String, String>> contentListDetail;
	
	JSONArray detail = null;
//	String module_string;
	String contents = null;
	
	// JSON node keys
	//private static final String TAG_ID = "module_id";
//	public static final String TAG_NAME = "module_name";
//	private static final String TAG_STRING = "module_string";
	
	public static final String MODULE_ID = "id";
	public static final String MODULE_URL = "url";
	public static final String MODULE_NAME = "name";
	public static final String MODULE_MODICON = "modicon";
	public static final String MODULE_CONTENT = "contents";
	
//	private static final String TAG_init = "course_init";
//	public static final String TAG_SUMMARY = "module_summary";
	
	WebView summary;
	ListView list;
	ContentsListDetailAdapter adapter;
	

	
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_content_list_detail);
        
        TextView debugID = (TextView) findViewById(R.id.debugId);
		debugID.setText("content_list_detail");
        
        pDialog = new ProgressDialog(ContentListDetail.this);
		pDialog.setMessage("Loading Content ...");
		pDialog.setIndeterminate(false);
		pDialog.setCancelable(true);
		pDialog.show();
        
        Log.d(" Activity Name >", "======Content List Detail");
        
        
        // getting intent data
        Bundle bundle = this.getIntent().getExtras();
        // Get JSON values from previous intent       
        //String module_id = bundle.getString(TAG_ID);
//        String module_name = bundle.getString(TAG_NAME);
//        String module_string = bundle.getString(TAG_STRING);
//        String course_init = bundle.getString(TAG_init);
//        String module_summary = bundle.getString(TAG_SUMMARY);
        
        String courseid = bundle.getString("course_id");
        String coursename = bundle.getString("course_name");
        String courseinit = bundle.getString("course_init");
        String wstoken = bundle.getString("token");
        String userid = bundle.getString("userid");
        String siteurl = bundle.getString("siteurl");
        String moduleid = bundle.getString("module_id");
        String modulename = bundle.getString("module_name");
        String modulestring = bundle.getString("module_string");
        String modulesummary = bundle.getString("module_summary");
        
        setTitle(courseinit+"-"+modulename);
        
        
        //Toast.makeText(getApplicationContext(), "cant retreive recipient....", Toast.LENGTH_LONG).show();
        
        //summary web view
        summary=(WebView)findViewById(R.id.webViewSum);
        summary.loadData(modulesummary, "text/html", "UTF-8");
        
        // Hashmap for ListView
        contentListDetail = new ArrayList<HashMap<String, String>>();
 
        
		try {
			detail = new JSONArray(modulestring);
			// looping through All messages
			for (int i = 0; i < detail.length(); i++) {
				JSONObject c = detail.getJSONObject(i);

				// Storing each json item in variable
				String id = c.getString(MODULE_ID);
				String url = c.getString(MODULE_URL);
				String name = c.getString(MODULE_NAME);
				String icon = c.getString(MODULE_MODICON);
//				String contents = c.getString(MODULE_CONTENT);
				
				try {
					contents = c.getString(MODULE_CONTENT);
				} catch ( JSONException e ){
					e.printStackTrace();
					contents = "";
				}
				
				// creating new HashMap
				HashMap<String, String> map = new HashMap<String, String>();

				// adding each child node to HashMap key => value
				map.put(MODULE_ID, id);
				map.put(MODULE_NAME, name);
				map.put(MODULE_URL,url);
				map.put(MODULE_MODICON, icon);
				map.put(MODULE_CONTENT,contents);
				
				// adding HashList to ArrayList
				contentListDetail.add(map);
			}

		} catch (JSONException e) {
			e.printStackTrace();
		}
        
		list = (ListView)findViewById(R.id.list);
		
		// Getting adapter by passing xml data ArrayList
        adapter = new ContentsListDetailAdapter(this, contentListDetail);        
        list.setAdapter(adapter);
        
        pDialog.dismiss();
        
        // Click event for single list row
        list.setOnItemClickListener(new OnItemClickListener() {
			public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
				/** Add something to do Here
				 * 
				 */
				
				Intent ContentListDetail = new Intent(getApplicationContext(), Process.class);
				Bundle bundle_msg = new Bundle();

				// send course id to tracklist activity to get list of activities under that course
				String content_id = ((TextView) view.findViewById(R.id.participant_id)).getText().toString();
				String content_name = ((TextView) view.findViewById(R.id.name)).getText().toString();
				String content_url = ((TextView) view.findViewById(R.id.url)).getText().toString();
				String content_string = ((TextView) view.findViewById(R.id.content)).getText().toString();
				
				bundle_msg.putString("content_id", content_id);
                bundle_msg.putString("content_name", content_name);
                bundle_msg.putString("content_url", content_url);
                bundle_msg.putString("content_string", content_string);
                ContentListDetail.putExtras(getIntent().getExtras());
                ContentListDetail.putExtras(bundle_msg);
                startActivity(ContentListDetail);
			}
		});	
        
        
        // Loading INBOX in Background Thread
//        new LoadDetail().execute();
        
        // get listview
//     	ListView lv = getListView();        
        
        /**
		 * Listview item click listener
		 * TrackListActivity will be lauched by passing album id
		 * */
//		lv.setOnItemClickListener(new android.widget.AdapterView.OnItemClickListener() {
//			public void onItemClick(AdapterView<?> arg0, View view, int arg2, long arg3) {
//				// on selecting a single album
//				// TrackListActivity will be launched to show tracks inside the album
//				
//				
//				//i.putExtra("course_name", course_names);
//				//i.putExtra("path", path);	
//				
//			}
//		});        

    }

    /**
	 * Background Async Task to Load all INBOX messages by making HTTP Request
	 * */
//	class LoadDetail extends AsyncTask<String, String, String> {
//
//		/**
//		 * Before starting background thread Show Progress Dialog
//		 * */
//		@Override
//		protected void onPreExecute() {
//			super.onPreExecute();
//			
//		}

		/**
		 * getting Inbox JSON
		 * */
//		protected String doInBackground(String... args) {
//			
////			Log.d("module_string >",module_string.toString());
//
//
//			return null;
//		}

		/**
		 * After completing background task Dismiss the progress dialog
		 * **/
//		protected void onPostExecute(String file_url) {
//			// dismiss the dialog after getting all products
//			pDialog.dismiss();
//			// updating UI from Background Thread
//			runOnUiThread(new Runnable() {
//				public void run() {
//					/**
//					 * Updating parsed JSON data into ListView
//					 * */
//					ListAdapter adapter = new SimpleAdapter(
//							ContentListDetail.this, contentListDetail, R.layout.activity_content_list_detail_item, 
//							new String[] { MODULE_ID, MODULE_NAME, MODULE_URL, MODULE_CONTENT },
//							new int[] { R.id.module_id, R.id.name, R.id.url, R.id.content });
//					// updating listview
////					setListAdapter(adapter);
//				}
//			});
//
//		}

//	}
    
}
