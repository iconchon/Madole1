package helper;

import java.util.ArrayList;
import java.util.HashMap;

import com.iconchon.madole1.ContentListDetail;
import com.iconchon.madole1.ContentsList;
import com.iconchon.madole1.R;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class ContentsListDetailAdapter extends BaseAdapter {
    
    private Activity activity;
    private ArrayList<HashMap<String, String>> data;
    private static LayoutInflater inflater=null;
    public ImageLoader imageLoader; 
    
    public ContentsListDetailAdapter(Activity a, ArrayList<HashMap<String, String>> d) {
        activity = a;
        data=d;
        inflater = (LayoutInflater)activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        imageLoader=new ImageLoader(activity.getApplicationContext());
    }

    public int getCount() {
        return data.size();
    }

    public Object getItem(int position) {
        return position;
    }

    public long getItemId(int position) {
        return position;
    }
    
    public View getView(int position, View convertView, ViewGroup parent) {
        View vi=convertView;
        if(convertView==null)
            vi = inflater.inflate(R.layout.activity_content_list_detail_item, null);

        TextView module_id = (TextView) vi.findViewById(R.id.participant_id);
        TextView module_name = (TextView) vi.findViewById(R.id.name);
        TextView module_string = (TextView) vi.findViewById(R.id.url);
        TextView module_summary = (TextView) vi.findViewById(R.id.content);
//        TextView module_summaries = (TextView) vi.findViewById(R.id.summaries);
		
//        TextView title = (TextView)vi.findViewById(R.id.title); // title
//        TextView artist = (TextView)vi.findViewById(R.id.artist); // artist name
//        TextView duration = (TextView)vi.findViewById(R.id.duration); // duration
        ImageView thumb_image=(ImageView)vi.findViewById(R.id.list_image); // thumb image
        
        HashMap<String, String> song = new HashMap<String, String>();
        song = data.get(position);
        

        int loader = android.R.drawable.ic_menu_gallery;
//        String temp=song.get(ContentListDetail.TAG_MODULES);
        
//        Log.d("nyoba TEmp",temp.toString());
        
        
        // Setting all values in listview
        module_id.setText(song.get(ContentListDetail.MODULE_ID));
        module_name.setText(song.get(ContentListDetail.MODULE_NAME));
        module_string.setText(song.get(ContentListDetail.MODULE_URL));
        module_summary.setText(song.get(ContentListDetail.MODULE_CONTENT));
//        module_summaries.setText(song.get(ContentListDetail.MODULE_CONTENT));
        
//        duration.setText(song.get(ProfileList.TAG_ID));
//        if (temp.equals("[]")){
//        	thumb_image.setImageResource(R.drawable.fail);
//        }else{
//        	thumb_image.setImageResource(R.drawable.success);
//        }
        imageLoader.DisplayImage(song.get(ContentListDetail.MODULE_MODICON),loader , thumb_image);
        return vi;
    }
}