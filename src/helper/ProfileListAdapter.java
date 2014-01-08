package helper;

import java.util.ArrayList;
import java.util.HashMap;

import com.iconchon.madole1.ProfileList;
import com.iconchon.madole1.R;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class ProfileListAdapter extends BaseAdapter {
    
    private Activity activity;
    private ArrayList<HashMap<String, String>> data;
    private static LayoutInflater inflater=null;
    public ImageLoader imageLoader; 
    
    public ProfileListAdapter(Activity a, ArrayList<HashMap<String, String>> d) {
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
            vi = inflater.inflate(R.layout.activity_profile_list_item, null);

        TextView fullname = (TextView)vi.findViewById(R.id.fullname); // title
        TextView id = (TextView)vi.findViewById(R.id.participant_id);
        TextView phone = (TextView)vi.findViewById(R.id.phone1);
        TextView email = (TextView)vi.findViewById(R.id.email); // artist name
        ImageView thumb_image=(ImageView)vi.findViewById(R.id.list_image); // thumb image
        
        HashMap<String, String> participant = new HashMap<String, String>();
        participant = data.get(position);
        
        int loader = android.R.drawable.ic_menu_gallery;
        
        // Setting all values in listview
        id.setText(participant.get(ProfileList.TAG_ID));
        fullname.setText(participant.get(ProfileList.TAG_FULLNAME));
        email.setText(participant.get(ProfileList.TAG_EMAIL));
        phone.setText(participant.get(ProfileList.TAG_PHONE));
        imageLoader.DisplayImage(participant.get(ProfileList.TAG_IMG_SMALL),loader , thumb_image);
        
        return vi;
    }
}