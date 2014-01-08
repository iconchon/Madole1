
package com.iconchon.madole1;


import java.util.List;

import android.os.Bundle;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Intent;
import android.content.res.Resources.Theme;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.CompoundButton;
import helper.DatabaseHelper;
import helper.User;

public class Server extends Activity implements CompoundButton.OnCheckedChangeListener{

	String dbUrl;
	String iUrl;
	EditText editUrl;
	String newUrl;
	CheckBox check;
	DatabaseHelper db;
//	User user = new User();
	User user;
	
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_server);
        
        check = (CheckBox)findViewById(R.id.checkBox1);
        check.setOnCheckedChangeListener(this);
        
        db = new DatabaseHelper(getApplicationContext());
//		db.deleteUser(1);
//		db.deleteUser(2);
//		db.deleteUser(3);
        int jumlah = db.getUserCount();
       
		      
		if (jumlah>0) {
//			List<User> users = db.getAllUsers();       	        
//			for (User cn : users) { 
//	        	Log.d("ini getall : ", "url: "+cn.getUrl() +" token: "+ cn.getToken());
//	        }
			user = db.getUser(1);
			dbUrl=user.getUrl();
			
		} else { 
			user.setToken("");
			user.setUrl("");
			user.setId(1);
			
			long user1 = db.createUser(user);
			}
		
		db.closeDB();
		
        
        
        editUrl = (EditText)findViewById(R.id.ServURL);
        if (dbUrl.equals(null)){
        	Log.d("kosong", "true");
//        	editUrl.setText("");
        }else {
        	Log.d("isi", "true");
        	editUrl.setText(dbUrl.substring(7));        	
        }
        
       
        
        Button save = (Button)findViewById(R.id.ServButton);
        save.setOnClickListener(new OnClickListener() {
        	
        	public void onClick(View view) {
        		newUrl = editUrl.getText().toString();
        		if (newUrl.equals("")) {
					AlertDialog.Builder blank = new AlertDialog.Builder(Server.this);
					blank.setTitle("Alert");
					blank.setMessage("server is empty");
					blank.setPositiveButton("OK", null);
					blank.show();
				} else {
        		
        		String pre = "http://";
        		
        		newUrl = pre.concat(editUrl.getText().toString());
//        		long id1 = 1;
//        		long id2 = 2;
//        		db.createUser(new User("",newUrl));
//        		db.addUser(new User("",newUrl));
        		user.setUrl(newUrl);
        		db.updateUser(user);
        		List<User> user = db.getAllUsers();       
        		 
                for (User cn : user) { 
                	String log = 
                		"Id: "+cn.getId()+
                		" ,TOKEN: " + cn.getToken() + 
                		" ,URL: " + cn.getUrl();
                Log.d("ini server act: "+cn.getId(), log);
                
                }
                db.closeDB();
                finish();
                //Intent login = new Intent(Server.this, Login.class);
    			//startActivity(login);
				}
        	}
        });
    }
    
	public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
		if (isChecked){
			editUrl.setText("10.0.2.2/moodle");
		}
		else {
			editUrl.setText(dbUrl.substring(7));
		}
	}

	public void SaveUrl(String url){
		
	}
//    @Override
//    public boolean onCreateOptionsMenu(Menu menu) {
//        getMenuInflater().inflate(R.menu.activity_server, menu);
//        return true;
//    }
}
