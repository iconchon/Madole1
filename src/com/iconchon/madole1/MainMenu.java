package com.iconchon.madole1;

import android.os.Bundle;
import android.app.TabActivity;
import android.content.Intent;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TabHost;
import android.widget.TabHost.TabSpec;

public class MainMenu extends TabActivity {
	// TabSpec Names
	private static final String COURSE_SPEC = "Courses";
	private static final String PROFILE_SPEC = "Profile";
	
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_menu);
        
        TabHost tabHost = getTabHost();
        
        // Inbox Tab
        TabSpec inboxSpec = tabHost.newTabSpec(COURSE_SPEC);
        // Tab Icon
        inboxSpec.setIndicator(COURSE_SPEC, getResources().getDrawable(R.drawable.icon_inbox));
        Intent inboxIntent = new Intent(this, CourseList.class);
        inboxIntent.putExtras(getIntent().getExtras());//==send extras
        // Tab Content
        inboxSpec.setContent(inboxIntent);
        
        
        // Profile Tab
        TabSpec profileSpec = tabHost.newTabSpec(PROFILE_SPEC);
        profileSpec.setIndicator(PROFILE_SPEC, getResources().getDrawable(R.drawable.icon_profile));
        Intent profileIntent = new Intent(this, ProfileCourse.class);
        profileIntent.putExtras(getIntent().getExtras());//==send extras
        profileSpec.setContent(profileIntent);
        
        // Adding all TabSpec to TabHost
        tabHost.addTab(inboxSpec); // Adding Inbox tab
        tabHost.addTab(profileSpec); // Adding Profile tab
    }
    
    @Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
		case R.id.about:
			Intent about = new Intent(MainMenu.this, About.class);
			startActivity(about);
			return true;
		case R.id.feedback:
			Intent feedback = new Intent(MainMenu.this, Feedback.class);
			startActivity(feedback);
			return true;
		case R.id.help:
			Intent help = new Intent(MainMenu.this, Help.class);
			startActivity(help);
			return true;
		case R.id.settings:
			Intent server = new Intent(MainMenu.this, Server.class);
			startActivity(server);
			return true;
		default:
			return super.onOptionsItemSelected(item);
		}
	}

}
