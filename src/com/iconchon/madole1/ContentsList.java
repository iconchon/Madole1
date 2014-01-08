package com.iconchon.madole1;

import helper.ContentsListAdapter;
import helper.JSONParser;
//import helper.ProfileListAdapter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.os.Bundle;
import android.app.Activity;
//import android.app.ProgressDialog;
import android.content.Intent;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.AdapterView.OnItemClickListener;

public class ContentsList extends Activity {
	
	//private ProgressDialog pDialog;

	JSONParser jsonParser = new JSONParser();

	ArrayList<HashMap<String, String>> contentsList;

	JSONArray content = null;
	
	public static final String TAG_ID = "id";
	public static final String TAG_NAME = "name";
	public static final String TAG_SUMMARY = "summary";
	public static final String TAG_MODULES = "modules";
	public static final String TAG_SUMMARIES = "summaries";
	
	String courseid;
	String coursename;
	String courseinit;
	String wstoken;
	String userid;
    String siteurl;
    String summaries = null;
    
	ListView list;
    ContentsListAdapter adapter;
	
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contents_list);
        
        TextView debugID = (TextView) findViewById(R.id.debugId);
		debugID.setText("Course - Contents");
		
        ArrayList<HashMap<String, String>> contentsList = new ArrayList<HashMap<String, String>>();
        
        Bundle bundle = this.getIntent().getExtras();
        courseid = bundle.getString("course_id");
        coursename = bundle.getString("course_name");
        courseinit = bundle.getString("course_init");
        wstoken = bundle.getString("token");
      	userid = bundle.getString("userid");
      	siteurl = bundle.getString("siteurl");
      
        
		Log.d(" Activity Name >", "======ContentsList ");
		
		siteurl = siteurl.concat("/webservice/rest/server.php");
		
		
		
		// Building Parameters
		List<NameValuePair> params = new ArrayList<NameValuePair>();
		params.add(new BasicNameValuePair("wstoken", wstoken));
		params.add(new BasicNameValuePair("moodlewsrestformat", "json"));
		params.add(new BasicNameValuePair("wsfunction", "core_course_get_contents"));
		params.add(new BasicNameValuePair("courseid", courseid));
		
		// getting JSON string from URL
		String json = jsonParser.makeHttpRequest(siteurl, "GET", params);

		// Check your log cat for JSON response
		Log.d("core_course_get_contents >", json.toString());
		
		try {
			//inbox = json.getJSONArray(TAG_MESSAGES);
			content = new JSONArray(json);
			// looping through All messages
			for (int i = 0; i < content.length(); i++) {
				JSONObject c = content.getJSONObject(i);

				String id = c.getString(TAG_ID);
				String name = c.getString(TAG_NAME);
				String summary = c.getString(TAG_SUMMARY);
				String modules = c.getString(TAG_MODULES);
				
				
				if (summary.equals("")){summaries="";} else {
						summaries=" With Summaries";
				}		
				
				HashMap<String, String> map = new HashMap<String, String>();

				// adding each child node to HashMap key => value
				map.put(TAG_ID, id);
				map.put(TAG_NAME, name);
				map.put(TAG_SUMMARY,summary);
				map.put(TAG_MODULES,modules);
				map.put(TAG_SUMMARIES, summaries);
				
				contentsList.add(map);
			}

		} catch (JSONException e) {
			e.printStackTrace();
		}


		list = (ListView)findViewById(R.id.list);
		
		// Getting adapter by passing xml data ArrayList
        adapter = new ContentsListAdapter(this, contentsList);        
        list.setAdapter(adapter);
        
        // Click event for single list row
        list.setOnItemClickListener(new OnItemClickListener() {
			public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
				/** Add something to do Here
				 * 
				 */
				
				Intent ContentListDetail = new Intent(getApplicationContext(), ContentListDetail.class);
//		        TextView module_id = (TextView) vi.findViewById(R.id.id);
//		        TextView module_name = (TextView) vi.findViewById(R.id.name);
//		        TextView module_string = (TextView) vi.findViewById(R.id.modules);
//		        TextView module_summary = (TextView) vi.findViewById(R.id.summary);
//		        TextView module_summaries = (TextView) vi.findViewById(R.id.summaries);
				
				String module_id = ((TextView) view.findViewById(R.id.participant_id)).getText().toString();
				String module_name = ((TextView) view.findViewById(R.id.name)).getText().toString();
				String module_string = ((TextView) view.findViewById(R.id.modules)).getText().toString();
				String module_summary = ((TextView) view.findViewById(R.id.summary)).getText().toString();
				
				ContentListDetail.putExtra("module_id", module_id);
				ContentListDetail.putExtra("module_name", module_name);
				ContentListDetail.putExtra("module_string", module_string);
				ContentListDetail.putExtra("module_summary", module_summary);
				
				ContentListDetail.putExtras(getIntent().getExtras());
                startActivity(ContentListDetail);
			}
		});	
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.activity_contents_list, menu);
        return true;
    }
}
