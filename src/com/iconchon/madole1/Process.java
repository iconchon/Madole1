package com.iconchon.madole1;

import com.iconchon.madole1.ContentList.LoadInbox;

import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.util.Log;
import android.view.Menu;
import android.webkit.WebView;
import android.widget.TextView;

public class Process extends Activity {
	WebView browser;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_process);
        
        Bundle bundle = this.getIntent().getExtras();
        
//        Intent i = getIntent();
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
        String contentid = bundle.getString("content_id");
        String contentname = bundle.getString("content_name");
        String contenturl = bundle.getString("content_url");
        String contentstring = bundle.getString("content_string");
        
        
        Log.d("login", wstoken+"="+userid+"="+siteurl);
        Log.d("process",courseid+"="+coursename+"="+courseinit);
        Log.d("process2",moduleid+"="+modulename+"="+modulestring+"="+modulesummary);
        Log.d("process3",contentid+"="+contentname+"="+contenturl+"="+contentstring);
        
        if (contentstring.equals("")){
        	LoadWeb(contenturl);
        	} 
        else {
        	Intent download = new Intent(getApplicationContext(), Download.class);
			download.putExtras(getIntent().getExtras());//=======send extras
			startActivity(download);
		}
        
    }
    
    void LoadWeb(String url) {
    	setTitle("Judul nyah");
    	browser = (WebView) findViewById(R.id.webkit);
    	browser.loadUrl(url);
	} 
    
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.activity_process, menu);
        return true;
    }
}
