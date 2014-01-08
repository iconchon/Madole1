package com.iconchon.madole1;

import android.os.Bundle;
import android.app.Activity;
import android.view.Menu;

public class FileList extends Activity {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_file_list);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.activity_file_list, menu);
        return true;
    }
}
