package com.iconchon.madole1;

import java.io.BufferedInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.apache.http.NameValuePair;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;



import helper.JSONParser;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Environment;
import android.app.Activity;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.webkit.WebView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

public class Download extends Activity {
	WebView browser;
	JSONArray down;
	String fname,fsize,furl;

	TextView tvfname,tvfsize,tvfurl;
	Button btnDownload;
	
	private ProgressDialog pDialog;
	
	ImageView my_image;

	public static final int progress_bar_type = 0; 
	
	String file_url;
//	private static String file_url = "http://10.0.2.2/download/download.jpg";
	

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_download);
        
        btnDownload = (Button) findViewById(R.id.dlButton);
        tvfname = (TextView)findViewById(R.id.fname);
        tvfsize = (TextView)findViewById(R.id.fsize);
        tvfurl = (TextView)findViewById(R.id.furl);
        
        Intent in = getIntent();
        String contentstring = in.getStringExtra("content_string");
        String siteurl = in.getStringExtra("siteurl");
        String wstoken = in.getStringExtra("token");
        
//        List<NameValuePair> params = new ArrayList<NameValuePair>();
//        String json = jsonParser.makeHttpRequest(siteurl, "GET", params);
        Log.d("token ", wstoken.toString());
        Log.d("content_strings ", contentstring.toString());
        
        try {
			down = new JSONArray(contentstring);
			// looping through All messages
			for (int i = 0; i < down.length(); i++) {
				JSONObject c = down.getJSONObject(i);

				// Storing each json item in variable
				fsize = c.getString("filesize");
				furl = c.getString("fileurl");
				fname = c.getString("filename");
//				String contents = c.getString(MODULE_CONTENT);
				
				
				
				Log.d("downloadurl ", furl.toString());
				file_url = furl.concat("&token="+wstoken);
				Log.d("newfurl ", file_url.toString());

			}

		} catch (JSONException e) {
			e.printStackTrace();
		}
        
        tvfname.setText(fname);
        tvfsize.setText(fsize);
        tvfurl.setText(furl);
        
        btnDownload.setOnClickListener(new View.OnClickListener() {

			public void onClick(View v) {
				// starting new Async Task
				new DownloadFileFromURL().execute(file_url);
			}
		});
        
//      setTitle("Judul nyah");
//    	browser = (WebView) findViewById(R.id.webkit);
//    	browser.loadUrl(url);
    	
    }
    
	@Override
	protected Dialog onCreateDialog(int id) {
		switch (id) {
		case progress_bar_type:
			pDialog = new ProgressDialog(this);
			pDialog.setMessage("Downloading file. Please wait...");
			pDialog.setIndeterminate(false);
			pDialog.setMax(100);
			pDialog.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
			pDialog.setCancelable(true);
			pDialog.show();
			return pDialog;
		default:
			return null;
		}
	}
	
	class DownloadFileFromURL extends AsyncTask<String, String, String> {

		/**
		 * Before starting background thread
		 * Show Progress Bar Dialog
		 * */
		@Override
		protected void onPreExecute() {
			super.onPreExecute();
			showDialog(progress_bar_type);
		}

		/**
		 * Downloading file in background thread
		 * */
		@Override
		protected String doInBackground(String... f_url) {
			int count;
	        try {
	            URL url = new URL(f_url[0]);
	            URLConnection conection = url.openConnection();
	            conection.connect();
	            // getting file length
	            int lenghtOfFile = conection.getContentLength();

	            // input stream to read file - with 8k buffer
	            InputStream input = new BufferedInputStream(url.openStream(), 1024);
	            
	            // Output stream to write file
	            OutputStream output = new FileOutputStream("/sdcard/"+fname);

	            byte data[] = new byte[1024];

	            long total = 0;

	            while ((count = input.read(data)) != -1) {
	                total += count;
	                // publishing the progress....
	                // After this onProgressUpdate will be called
	                publishProgress(""+(int)((total*100)/lenghtOfFile));
	                
	                // writing data to file
	                output.write(data, 0, count);
	            }

	            // flushing output
	            output.flush();
	            
	            // closing streams
	            output.close();
	            input.close();
	            
	        } catch (Exception e) {
	        	Log.e("Error: ", e.getMessage());
	        }
	        
	        return null;
		}
		
		/**
		 * Updating progress bar
		 * */
		protected void onProgressUpdate(String... progress) {
			// setting progress percentage
            pDialog.setProgress(Integer.parseInt(progress[0]));
       }

		/**
		 * After completing background task
		 * Dismiss the progress dialog
		 * **/
		@Override
		protected void onPostExecute(String file_url) {
			// dismiss the dialog after the file was downloaded
			dismissDialog(progress_bar_type);
			
			// Displaying downloaded image into image view
			// Reading image path from sdcard
			String imagePath = Environment.getExternalStorageDirectory().toString() + "/"+fname;
			
			Log.d("path",imagePath);
			// setting downloaded into image view
//			my_image.setImageDrawable(Drawable.createFromPath(imagePath));
		}

	}

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.activity_download, menu);
        return true;
    }
}
