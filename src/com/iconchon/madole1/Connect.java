package com.iconchon.madole1;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class Connect extends Activity implements OnClickListener {
	
	private int entries = 6;
	private String phoneNum[];
	private String buttonLabels[];
	
	private String contact_id;
	private String contact_name;
	private String contact_email;
	private String contact_phone;
		
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
    	
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_connect);
        
        Bundle bundle = this.getIntent().getExtras();
        contact_id = bundle.getString("contact_id");
        contact_name = bundle.getString("contact_name");
        contact_email = bundle.getString("contact_email");
        contact_phone = bundle.getString("contact_phone");
          
//        courseid = bundle.getString("course_id");
//        coursename = bundle.getString("course_name");
//        courseinit = bundle.getString("course_init");
//        wstoken = bundle.getString("token");
//        userid = bundle.getString("userid");
//        siteurl = bundle.getString("siteurl");
        
        Log.d(" Activity Name >", "======activity_connect ");      
        Log.d(" contact_id >", contact_id.toString());
        Log.d(" contact_name >", contact_name.toString());
        Log.d(" contact_email >", contact_email.toString());
        Log.d(" contact_phone >", contact_phone.toString());
        
        
//        setTitle("Connect");
        TextView tvC = (TextView)findViewById(R.id.tVconnect);
        tvC.setText(contact_name);
        
        phoneNum = new String[entries];
        buttonLabels = new String[entries];
        
        // Populate the data arrays
        populateArrays();
        
        // Set up buttons and attach click listeners
        
        Button button1 = (Button)findViewById(R.id.button1);
        button1.setText(buttonLabels[0]);
        button1.setOnClickListener(this);
        
        Button button2 = (Button)findViewById(R.id.send);
        button2.setText(buttonLabels[1]);
        button2.setOnClickListener(this);
        
        Button button3 = (Button)findViewById(R.id.button3);
        button3.setText(buttonLabels[2]);
        button3.setOnClickListener(this);
        
        Button button4 = (Button)findViewById(R.id.button4);
        button4.setText(buttonLabels[3]);
        button4.setOnClickListener(this);
        
        Button button5 = (Button)findViewById(R.id.button5);
        button5.setText(buttonLabels[4]);
        button5.setOnClickListener(this);
        
    }
    
    // Launch the phone dialer
    
    public void launchDialer(String number){
    	Log.i("open dialer", "");
    	String numberToDial = "tel:"+number;
		 startActivity(new Intent(Intent.ACTION_DIAL, Uri.parse(numberToDial)));
    }
      
	
    public void openProfile(String uId){
    	Log.i("open profile", "");
    	startActivity(new Intent(getApplicationContext(),  ProfileCourse.class).putExtra("userid", uId));
    }
    
    public void sendMessage(String uId){
    	Log.i("Send Message", "");
    	startActivity(new Intent(getApplicationContext(),  InstantMessage.class).putExtra("userid", uId));
    }
    
    public void sendMail(String uMail){
    	Log.i("Send Mail", "");
    	startActivity(new Intent(getApplicationContext(),  InstantMessage.class).putExtra("userid", uMail));
    }

    protected void sendSMSMessage(String number) {
        Log.i("Send SMS", "");
        
        try {
        	Intent smsIntent = new Intent(Intent.ACTION_VIEW);
        	smsIntent.setType("vnd.android-dir/mms-sms");
        	smsIntent.putExtra("address", number);
        	smsIntent.putExtra("sms_body","Hi");
        	startActivity(smsIntent);
			
		} catch (Exception e) {
			Toast.makeText(getApplicationContext(), "SMS faild!",
					Toast.LENGTH_LONG).show();
			e.printStackTrace();
		}
        
//        String phoneNo = txtphoneNo.getText().toString();
//        String message = txtMessage.getText().toString();
//
//        try {
//           SmsManager smsManager = SmsManager.getDefault();
//           smsManager.sendTextMessage(phoneNo, null, message, null, null);
//           Toast.makeText(getApplicationContext(), "SMS sent.",
//           Toast.LENGTH_LONG).show();
//        } catch (Exception e) {
//           Toast.makeText(getApplicationContext(),
//           "SMS faild, please try again.",
//           Toast.LENGTH_LONG).show();
//           e.printStackTrace();
        }
    
    
    /** Method to populate the data arrays */
    
    public void populateArrays(){
    	
    	/** In a practical application the arrays phoneNum and buttonLabels could be 
    	 * updated dynamically from the Web in this method.  For this project we just 
    	 * hard-wire in some values to illustrate how to use such data, once obtained,
    	 * to make phone calls.*/
    	
    	phoneNum[0] = "000-000-0001";
    	phoneNum[1] = "000-000-0002";
    	phoneNum[2] = "000-000-0003";
    	phoneNum[3] = "000-000-0004";
    	phoneNum[4] = "000-000-0005";
    	phoneNum[5] = "000-000-0006";
    	
    	buttonLabels[0] = "Profile";
    	buttonLabels[1] = "Call";
    	buttonLabels[2] = "SMS";
    	buttonLabels[3] = "e-Mail";
    	buttonLabels[4] = "Instant Message";
    }
    
    /** Process button events */
    
	public void onClick(View v) {
		switch (v.getId()) {
		
			case R.id.button1:
//				Intent i = new Intent(getApplicationContext(),  ProfileCourse.class);
//				i.putExtra("userid", contact_id);
//				startActivity(i);
////				launchDialer(phoneNum[0]);
				openProfile(contact_id);
				break;
				
			case R.id.send:
//				Intent call = new Intent(getApplicationContext(), ProfileCourse.class);
				launchDialer(contact_phone);
				break;
				
			case R.id.button3:
				sendSMSMessage(contact_phone);
				break;
				
			case R.id.button4:
				sendMail(contact_email);
				break;
				
			case R.id.button5:
				sendMessage(contact_id);
				break;
				
		}
	} 
}