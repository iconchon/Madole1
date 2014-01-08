package com.iconchon.madole1;

import helper.JSONParser;
import helper.ProfileListAdapter;

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
import android.content.Intent;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.AdapterView.OnItemClickListener;

public class ProfileList extends Activity {
	
	// Progress Dialog

	// Creating JSON Parser object
	JSONParser jsonParser = new JSONParser();

	// products JSONArray
	JSONArray course = null;
	JSONObject info = null;

	String courseid;
    String coursename;
    String courseinit;
    String wstoken;
  	String userid;
  	String siteurl;
  	String address="";
  	String phone1="";
	
	// ALL JSON node names
	public static final String TAG_ID = "id";
	public static final String TAG_FULLNAME = "fullname";
	public static final String TAG_EMAIL = "email";
	public static final String TAG_PHONE = "phone1";
	public static final String TAG_LASTACCESS = "lastaccess";
	public static final String TAG_IMG_SMALL = "profileimageurlsmall";
	public static final String TAG_IMG = "profileimageurl";

	ListView list;
    ProfileListAdapter adapter;
    
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile_list);
        
        ArrayList<HashMap<String, String>> profileList = new ArrayList<HashMap<String, String>>();
        
        TextView debugID = (TextView) findViewById(R.id.debugId);
		debugID.setText("Course - Participants");
        
        Bundle bundle = this.getIntent().getExtras();
        courseid = bundle.getString("course_id");
        coursename = bundle.getString("course_name");
        courseinit = bundle.getString("course_init");
        wstoken = bundle.getString("token");
      	userid = bundle.getString("userid");
      	siteurl = bundle.getString("siteurl");
      
        
		Log.d(" Activity Name >", "======ProfileList ");
		
		siteurl = siteurl.concat("/webservice/rest/server.php");
		
		// Building Parameters
		List<NameValuePair> params = new ArrayList<NameValuePair>();
		params.add(new BasicNameValuePair("wstoken", wstoken));
		params.add(new BasicNameValuePair("moodlewsrestformat", "json"));
		params.add(new BasicNameValuePair("wsfunction", "core_enrol_get_enrolled_users"));
		params.add(new BasicNameValuePair("courseid", courseid));
		
		// getting JSON string from URL
		String json = jsonParser.makeHttpRequest(siteurl, "GET", params);

		// Check your log cat for JSON response
		Log.d("core_enrol_get_enrolled_users >", json.toString());

		try {
			course = new JSONArray(json);
			
			// looping through All messages
			for (int i = 0; i < course.length(); i++) {
				
				JSONObject c = course.getJSONObject(i);

				// Storing each json item in variable
				String id = c.getString(TAG_ID);
				String fullname = c.getString(TAG_FULLNAME);
				String email = c.getString(TAG_EMAIL);
				String lastaccess = c.getString(TAG_LASTACCESS);
				String profileimageurlsmall = c.getString(TAG_IMG_SMALL);
				String profileimageurl = c.getString(TAG_IMG);
				
				// creating new HashMap
				HashMap<String, String> map = new HashMap<String, String>();

				try { 
					address = c.getString("address");
					map.put("address", address);}
				catch (JSONException e){Log.d("address","empty");}
				try { 
					phone1 = c.getString("phone1"); 
					map.put(TAG_PHONE, phone1);}
				catch (JSONException e){Log.d("phone1","empty");}
				
				// adding each child node to HashMap key => value
				map.put(TAG_ID, id);
				map.put(TAG_FULLNAME, fullname);
				map.put(TAG_EMAIL, email);
				map.put(TAG_LASTACCESS, lastaccess);
				map.put(TAG_IMG_SMALL, profileimageurlsmall);
				map.put(TAG_IMG, profileimageurl);
				
				// adding HashList to ArrayList
				profileList.add(map);
			}
		} catch (JSONException e) {	e.printStackTrace();}
		
		
		list=(ListView)findViewById(R.id.list);
		
		// Getting adapter by passing xml data ArrayList
        adapter=new ProfileListAdapter(this, profileList);        
        list.setAdapter(adapter);
        
        // Click event for single list row
        list.setOnItemClickListener(new OnItemClickListener() {
			public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
				/** Add something to do Here
				 *  nanti di isi opsi : view profile, call, sms, email, send instant message, 
				 */
				Intent ConnectList = new Intent(getApplicationContext(), Connect.class);
//		        TextView module_id = (TextView) vi.findViewById(R.id.id);
//		        TextView module_name = (TextView) vi.findViewById(R.id.name);
//		        TextView module_string = (TextView) vi.findViewById(R.id.modules);
//		        TextView module_summary = (TextView) vi.findViewById(R.id.summary);
//		        TextView module_summaries = (TextView) vi.findViewById(R.id.summaries);
				
				String contact_id = ((TextView) view.findViewById(R.id.participant_id)).getText().toString();
				String contact_name = ((TextView) view.findViewById(R.id.fullname)).getText().toString();
				String contact_email = ((TextView) view.findViewById(R.id.email)).getText().toString();
				String contact_phone = ((TextView) view.findViewById(R.id.phone1)).getText().toString();
				
				
				ConnectList.putExtra("contact_id", contact_id);
				ConnectList.putExtra("contact_name", contact_name);
				ConnectList.putExtra("contact_email", contact_email);
				ConnectList.putExtra("contact_phone", contact_phone);
				ConnectList.putExtras(getIntent().getExtras());
                startActivity(ConnectList);
				
			}
		});	
	}
    

    
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.activity_profile_list, menu);
        return true;
    }
}
