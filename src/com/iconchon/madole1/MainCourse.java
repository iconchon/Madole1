package com.iconchon.madole1;

import android.os.Bundle;
import android.app.TabActivity;
import android.content.Intent;
import android.view.Menu;
import android.widget.TabHost;
import android.widget.TabHost.TabSpec;

public class MainCourse extends TabActivity {
	
	private static final String CONTENT_LIST_SPEC = "Contents";
	private static final String PROFILE_LIST_SPEC = "Participants";

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_course);
        
        TabHost tabHost = getTabHost();
        
        // Inbox Tab
        TabSpec CourseSpec = tabHost.newTabSpec(CONTENT_LIST_SPEC);
        // Tab Icon
        CourseSpec.setIndicator(CONTENT_LIST_SPEC, getResources().getDrawable(R.drawable.icon_inbox));
        Intent inboxIntent = new Intent(this, ContentsList.class);
        inboxIntent.putExtras(getIntent().getExtras());//==send extras
        // Tab Content
        CourseSpec.setContent(inboxIntent);
        
        
        // Profile Tab
        TabSpec profileSpec = tabHost.newTabSpec(PROFILE_LIST_SPEC);
        profileSpec.setIndicator(PROFILE_LIST_SPEC, getResources().getDrawable(R.drawable.icon_profile));
        Intent profileIntent = new Intent(this, ProfileList.class);
        profileIntent.putExtras(getIntent().getExtras());//==send extras
        // Tab Content
        profileSpec.setContent(profileIntent);
        
        // Adding all TabSpec to TabHost
        tabHost.addTab(CourseSpec); // Adding Inbox tab
        tabHost.addTab(profileSpec); // Adding Profile tab
        
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.activity_course, menu);
        return true;
    }
}
