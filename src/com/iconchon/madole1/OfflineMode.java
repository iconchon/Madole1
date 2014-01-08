package com.iconchon.madole1;

import helper.Course;
import helper.DatabaseHelper;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import android.os.AsyncTask;
import android.os.Bundle;
import android.app.ListActivity;
import android.content.Intent;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;

public class OfflineMode extends ListActivity {
	
	private static final String TAG_ID = "id";
	private static final String TAG_FULLNAME = "fullname";
	private static final String TAG_SHORTNAME = "shortname";
	
	DatabaseHelper db;
//	int id;
	
	ArrayList<HashMap<String, String>> courseOff;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_offline_mode);
        Log.d("ini Offline oi2: ", "--------------ow");
        
        courseOff = new ArrayList<HashMap<String, String>>();
        new offlinemode().execute();
        
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
				Intent i = new Intent(getApplicationContext(), FileList.class);
				
				// send course id to tracklist activity to get list of activities under that course
				String course_id = ((TextView) view.findViewById(R.id.course_id)).getText().toString();
				String course_names = ((TextView) view.findViewById(R.id.fullname)).getText().toString();
				String course_init = ((TextView) view.findViewById(R.id.subject)).getText().toString();
				
				i.putExtra("course_id", course_id);	
				i.putExtra("course_name", course_names);
				i.putExtra("course_init", course_init);
				//i.putExtra("path", path);	
				
				startActivity(i);
			}
		});

    }

    class offlinemode extends AsyncTask<String, String, String> {

		@Override
		protected String doInBackground(String... arg0) {
			// TODO Auto-generated method stub
//			DatabaseHandler db = new DatabaseHandler(OfflineMode.this);
			db = new DatabaseHelper(getApplicationContext());
	
			/** PERLU PERHATIAN KHUSUS..!!!!<<<<<<<<<<<<<<<<<<<<<<<<
//	        List<Course> myCourse = db.getAllCourse();       
//	        db.close();
//	        for (Course cn : myCourse) {
//	        	
//	        	String id = String.valueOf(cn.getId());
//	        	String fullname = cn.getFullName();
//	        	String shortname= cn.getShortName();
//	        	
//	        	Log.d("ini Offline oi: "+cn.getId(), shortname + fullname);
//	        
//		        HashMap<String, String> map = new HashMap<String, String>();
//	
//				// adding each child node to HashMap key => value
//		        map.put(TAG_ID, String.valueOf(id));
//				map.put(TAG_FULLNAME, fullname);
//				map.put(TAG_SHORTNAME, shortname);
//	
//				// adding HashList to ArrayList
//				courseOff.add(map);
//	        
//	        }
 * <<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<
 */
			return null;
			
		}
		
		protected void onPostExecute(String file_url) {
			// dismiss the dialog after getting all products
			Log.d("ini Offline oi2: ", "--------------ow");
			// updating UI from Background Thread
			runOnUiThread(new Runnable() {
				public void run() {
					/**
					 * Updating parsed JSON data into ListView
					 * */
					ListAdapter adapter = new SimpleAdapter(
							OfflineMode.this, courseOff,
							R.layout.activity_course_list_item, 
							new String[] { TAG_ID ,TAG_FULLNAME, TAG_SHORTNAME },
							new int[] { R.id.course_id ,R.id.fullname, R.id.subject });
					// updating listview
					setListAdapter(adapter);
				}
			});

		}
    	
    }
    
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.activity_offline_mode, menu);
        return true;
    }
}
