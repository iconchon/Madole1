package com.iconchon.madole1;

import java.util.ArrayList;
import java.util.List;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import helper.AlertDialogManager;

import helper.DatabaseHelper;
import helper.Functions;
import helper.JSONParser;
import helper.User;
import helper.ConnectionDetector;

import android.os.AsyncTask;
import android.os.Bundle;
import android.os.SystemClock;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class Login extends Activity {
	
	// Database Helper
	DatabaseHelper db;
	
	// EditText uname,pword,server;
	Button next, off;
	private String username = null;
	private String password = null;
	private String url = null;
	private String urlLogin = null;
	private String token = null;
	private String error = null;
	private String userid = null;
	private String siteurl = null;
	private String function = null;


	static final int DIALOG_CLOSE = 0;
	
//	private ProgressDialog pDialog; ==== ini ga dipake
	
	private static final String TAG_TOKEN = "token";
	private static final String TAG_ERROR = "error";
	private static final String TAG_FUNCTION = "functions";
	
	
//	private JSONArray function = null;
	private JSONObject jObject=null;
	JSONParser jsonParser = new JSONParser();
//	JSONParser jsonparser = new JSONParser();
	
	ConnectionDetector cd;
	
	// Alert dialog manager
	AlertDialogManager alert = new AlertDialogManager();

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_login);
		
		//deleteDatabase("MadoleData"); //================== Need to be delete
		Log.d(" Activity Name >", "======Login"); //================== Need to be delete

		next = (Button) findViewById(R.id.button1);
		next.setOnClickListener(new View.OnClickListener() {

			public void onClick(View view) {

				EditText uname = (EditText) findViewById(R.id.editText1);
				EditText pword = (EditText) findViewById(R.id.editText2);

				username = uname.getText().toString();
				password = pword.getText().toString();

				if (username.equals("") || password.equals("")) {
					Log.e("alert","username atau password kosong");
					AlertDialog.Builder blank = new AlertDialog.Builder(Login.this);
					blank.setTitle("Alert");
					blank.setMessage("username or password is empty");
					blank.setPositiveButton("OK", null);
					blank.show();
					
				} else {
					
					db = new DatabaseHelper(getApplicationContext());
//					List<User> allUsers = db.getAllUsers();					
//					for (User users : allUsers) {
//						Log.d(
//								"User satu",
//								"Id :"+users.getId()+
//								"url :"+users.getUrl()+
//								"token :"+users.getToken());
//						url=users.getUrl();
//					}
					User usr=db.getUser(1);
					Log.d("user",usr.getId()+usr.getToken()+usr.getUrl());
					url=usr.getUrl();
					db.closeDB();
//					List<Tag> allTags = db.getAllTags();
//					for (Tag tag : allTags) {
//						Log.d("Tag Name", tag.getTagName()); 
//						
//					db.closeDB();
//					DatabaseHandler db = new DatabaseHandler(Login.this);
//				cu	List<User> userurl = db.getAllUsers();
					
//					for (User lu : userurl) {url = (lu.getUrl());}
					Log.d("nih",url);
					
					if ((url.equals(null))||(url.equals(""))){
						LoadServer();
						Log.d("If true", "URL is empty");
					} else {
						new LoadLogin().execute(); 
						Log.d("ini else","url ada isinya "+url);
						}
				}
								
			}

		});
	}

	

	void LoadServer() {
		Intent server = new Intent(Login.this, Server.class);
		startActivity(server);
		Log.d("activity","load server");
	} 
	
	class LoadLogin extends AsyncTask<String, String, String> {
		
		@Override
//		protected void onPreExecute() {
//			super.onPreExecute();
//			pDialog = new ProgressDialog(Login.this);
//			pDialog.setMessage("Please Wait..");
//			pDialog.setIndeterminate(false);
//			pDialog.setCancelable(true);
//			pDialog.show();
//		}
		
		protected String doInBackground(String... arg0) {

			
			urlLogin = url.concat("/login/token.php");
			
			
			List<NameValuePair> params = new ArrayList<NameValuePair>();
			
			// post album id, song id as GET parameters
			params.add(new BasicNameValuePair("username", username));
			params.add(new BasicNameValuePair("password", password));
			params.add(new BasicNameValuePair("service", "mobile_app"));
			
			Log.d("cek",username);
			Log.d("cek",password);
			Log.d("cek",urlLogin);
			
			
			// getting JSON string from URL
//			String json = jsonparser.makeHttpRequest(urlLogin, "GET", params);
			String json = jsonParser.makeHttpRequest(urlLogin, "GET", params);
			

			// Check your log cat for JSON response
			Log.d("/login/token.php >", json.toString());
			
			 			
	   			try {
					jObject = new JSONObject(json);
					token=jObject.getString(TAG_TOKEN);
					error=null;
//					error=jObject.getString(TAG_ERROR);
				} catch (JSONException e) {
					e.printStackTrace();
					Log.d("error 1 :",e.toString());
					try {
						jObject = new JSONObject(json);
						error=jObject.getString(TAG_ERROR);
						Log.d("error aja :",error.toString());
					} catch (JSONException e1) {
						e1.printStackTrace();
						Log.d("error 2 :",e1.toString());
					}
				}
	   			
	   			JSONParser jparser = new JSONParser();
	   			List<NameValuePair> param = new ArrayList<NameValuePair>();
				param.add(new BasicNameValuePair("wstoken", token));
				param.add(new BasicNameValuePair("moodlewsrestformat", "json"));
				param.add(new BasicNameValuePair("wsfunction", "core_webservice_get_site_info"));
				String theUrl = url.concat("/webservice/rest/server.php");
				
				String json1 = jparser.makeHttpRequest(theUrl, "GET", param);
				
				Log.d("core_webservice_get_site_info >", json1.toString());
				
				try {
					
//					function = new JSONArray(json);
					
					JSONObject info = new JSONObject(json1);
					userid =info.getString("userid");
					siteurl = info.getString("siteurl");
					function = info.getString(TAG_FUNCTION);
					
				} catch (JSONException e) {e.printStackTrace();}
				Log.e("function", function);
				
			return null;
			
		}
	
		protected void onPostExecute(String file_url) {
			

			if(error==null ) {
				
//				DatabaseHandler db = new DatabaseHandler(Login.this);
//				db.addUser(new User(token,url));
//				db.close();
				
				db = new DatabaseHelper(getApplicationContext());
				int jumlah = db.getUserCount();
				
				
				
				Log.d("sukses","login :"+token+" url "+url);
				Log.d("jumlah",""+jumlah);
				
				User user = new User();
//				User user = null;
				user.setToken(token);
				user.setUrl(url);
				user.setId(1);
				
				if (jumlah>0)
				{ long user1 = db.updateUser(user);} else 
				{ long user1 = db.createUser(user);}
				db.closeDB();
				
//				Intent MainMenu = new Intent(Login.this, MainMenu.class);=============== Harusnya INI
				Intent MainMenu = new Intent(Login.this, InstantMessage.class); //====== ini di apus
				
				MainMenu.putExtra("userid", userid);
				MainMenu.putExtra("token", token);
				MainMenu.putExtra("siteurl", siteurl);
				startActivity(MainMenu);
//				pDialog.dismiss();
//				List<User> user = db.getAllUsers();       
				
//                for (User cn : user) { String log = 
//                		"Id: "+cn.getID()+
//                		" ,TOKEN: " + cn.getToken() + 
//                		" ,URL: " + cn.getUrl();
//                Log.d("Name: post: "+cn.getID(), log);}
			} else {
					//System.out.println("error");
					AlertDialog.Builder aa = new AlertDialog.Builder(Login.this);
	     			aa.setTitle("Alert !");
					aa.setMessage(error.toString());
					aa.setPositiveButton("OK", null);
					aa.show();
//					pDialog.dismiss();
					//Intent i = new Intent(RevLog.this, Notif.class);
					//i.putExtra("img_src", R.drawable.ic_error);
					//i.putExtra("title", "Login Gagal");
					//i.putExtra("notif", "Username dan atau Password salah.");
					//startActivity(i);
				}
			}

	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.activity_login, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
		case R.id.about:
			Intent about = new Intent(Login.this, About.class);
			startActivity(about);
			return true;
		case R.id.feedback:
			Intent feedback = new Intent(Login.this, Feedback.class);
			startActivity(feedback);
			return true;
		case R.id.help:
			Intent help = new Intent(Login.this, Help.class);
			startActivity(help);
			return true;
		case R.id.settings:
			Intent server = new Intent(Login.this, Server.class);
			startActivity(server);
			return true;
		default:
			return super.onOptionsItemSelected(item);
		}
	}

	@Override
	public void onBackPressed() {
		// moveTaskToBack(true);
		showDialog(DIALOG_CLOSE);
	}

	protected Dialog onCreateDialog(int id) {
		// Alert dialog untuk konfirmasi keluar aplikasi
		switch (id) {
		case DIALOG_CLOSE:
			AlertDialog.Builder adbClose = new AlertDialog.Builder(Login.this);
			adbClose.setMessage("You are Leaving Madole?");
			final AlertDialog adClose = adbClose.create();
			adClose.setButton("Yes", new DialogInterface.OnClickListener() {
				public void onClick(DialogInterface dialog, int which) {
					moveTaskToBack(true);
				}
			});
			adClose.setButton2("No", new DialogInterface.OnClickListener() {
				public void onClick(DialogInterface dialog, int which) {
					adClose.cancel();
				}
			});
			return adClose;
		}
		return null;
	} // end DIALOG

}
