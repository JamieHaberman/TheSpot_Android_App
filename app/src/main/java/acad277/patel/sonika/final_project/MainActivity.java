package acad277.patel.sonika.test;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import acad277.patel.sonika.final_project.R;
import acad277.patel.sonika.test.model.Spot;

import com.google.firebase.database.Query;
import com.squareup.picasso.Picasso;
import com.bumptech.glide.Glide;
import com.firebase.ui.database.FirebaseListAdapter;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.w3c.dom.Text;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Objects;

import static acad277.patel.sonika.test.R.id.imageView;
import static acad277.patel.sonika.test.R.id.listNoteTitle;
import static acad277.patel.sonika.test.R.id.outlets;
import static acad277.patel.sonika.test.R.id.querythis;
import static acad277.patel.sonika.test.R.id.sonika;
import static acad277.patel.sonika.test.R.id.text;
import static acad277.patel.sonika.test.R.id.view;
import static acad277.patel.sonika.test.R.id.wifi;
import static android.R.string.yes;
import static android.icu.lang.UCharacter.GraphemeClusterBreak.L;
import static android.icu.lang.UCharacter.GraphemeClusterBreak.T;
import static java.lang.System.in;


public class MainActivity extends AppCompatActivity {
    private ListView list;
    private FirebaseDatabase database;
    private DatabaseReference dbRefNotes;
    private DatabaseReference thing;
    FirebaseListAdapter mAdapter;
    private ArrayList<Spot> spots;
    private SpotAdapter spotAdapter;
    //todo database references
    private ImageView wifi1;
    private ImageView outlets1;
    private String spotName;
    private String spotType;
    private String wifi;
    private String outlets;
    private String address;
    private String imageURL;
    int i = 1;
    int b =2;
    public static final String SPOTS = "spots";
    String imgURl;

    public static final String EXTRA_URL = "https://finalproject-1c092.firebaseio.com/";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ListView mylist = (ListView) findViewById(R.id.mylist);
        database = FirebaseDatabase.getInstance();

        spots = new ArrayList<>();
        spotAdapter = new SpotAdapter(spots);
        mylist.setAdapter(spotAdapter);
        //todo get database reference paths
        dbRefNotes = database.getReference();
        thing = dbRefNotes.child("spots");

        Query query = thing.orderByChild("Wifi").equalTo(1);
        if(i ==1){
            query = thing.orderByChild("spot_type").equalTo("Library");
        }
        if(b ==2){
            query = thing.orderByChild("spot_type").equalTo("Hotel");
        }
        query.addValueEventListener(new ValueEventListener() {
            private int i=0;

            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for(DataSnapshot messageSnapshot: dataSnapshot.getChildren()){
                    spotName = (String) messageSnapshot.child("spotName").getValue();
                    spotType = (String) messageSnapshot.child("spot_type").getValue();
                    wifi = (String) messageSnapshot.child("wifi").getValue();
                    outlets = (String) messageSnapshot.child("outlets").getValue();
                    address = (String) messageSnapshot.child("Address").getValue();
                    imageURL = (String) messageSnapshot.child("imageURL").getValue();
                    Log.d(MainActivity.this.toString(), spotName);
                    Spot s = new Spot();
                    s.setSpotName(spotName);
                    s.setSpot_type(spotType);
                    s.setWifi(wifi);
                    s.setOutlets(outlets);
                    s.setAddress(address);
                    s.setImageURL(imageURL);
//                    if(s.getOutlets().equals("1")){
//                        outlets1.setImageResource(R.drawable.outlet);
//                    }
//                    else{
//                        outlets1.setImageResource(R.drawable.noutlet);
//                    }
//                    if(s.getWifi().equals("1")){
//                        wifi1.setImageResource(R.drawable.wifi);
//                    }
//                    else{
//                        wifi1.setImageResource(R.drawable.nowifi);
//                    }
                    spots.add(s);
                }






                //to redraw the screen
                spotAdapter.notifyDataSetChanged();
//                   Log.d(Integer.toString(i), sampleString[i]);
//                    i++;
//                }
            }


            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        list = (ListView) findViewById(R.id.notefragment);
        //todo get database reference paths //



        //todo instantiate adapter
        //todo set adapter for listview
        mAdapter = new NoteFirebaseAdapter(this, Spot.class, R.layout.list_note_item, thing);
//    list.setAdapter(mAdapter);
    }
    //todo create custom FirebaseListAdapter

    public class NoteFirebaseAdapter extends FirebaseListAdapter<Spot>{
        public NoteFirebaseAdapter(Activity activity, Class<Spot> modelClass, int modelLayout, DatabaseReference ref) {
            super(activity, modelClass, modelLayout, ref);
        }
        @Override
        protected  void populateView(View v, Spot model, int position){
            TextView textSpotName = (TextView) v.findViewById(R.id.listSpotName);
            TextView textAddress = (TextView) v.findViewById(R.id.listAddress);
            TextView textSpotType = (TextView) v.findViewById(R.id.listSpotType);
            ImageView imageSpot = (ImageView)v.findViewById(R.id.imageView);

            textSpotName.setText(model.getSpotName());
            textAddress.setText(model.getAddress());
            textSpotType.setText(model.getSpot_type());
            if(model.getOutlets().equals("1")){
                outlets1.setImageResource(R.drawable.outlet);
            }
            else{
                outlets1.setImageResource(R.drawable.noutlet);
            }
            if(model.getWifi().equals("1")){
                wifi1.setImageResource(R.drawable.wifi);
            }
            else{
                wifi1.setImageResource(R.drawable.nowifi);
            }

//            textSpotType.setText(model.getSpot_type_id());
        }
    }


    private class SpotAdapter extends ArrayAdapter<Spot>{

        //build an arraylist of food
        ArrayList<Spot> spots = new ArrayList<>();

        //always need a constructor, typicallly needs a context and a list
        public SpotAdapter(ArrayList<Spot> spots ){
            super(getApplicationContext(), 0, spots);
            this.spots = spots;
        }

        //the job of getView is to create  ONE row of our listView
        @NonNull
        @Override
        public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
            if (convertView == null){
                convertView = getLayoutInflater().inflate(R.layout.list_note_item,null);
            }
            final ImageView imageSpot;
            Spot f = spots.get(position);

            //references to each view within the list row
            TextView textName = (TextView) convertView.findViewById(R.id.listSpotType);
            TextView textLocation = (TextView) convertView.findViewById(R.id.listSpotName);
            TextView textAddress = (TextView)convertView.findViewById(R.id.listAddress);
            imageSpot = (ImageView)convertView.findViewById(R.id.imageView);
            wifi1 = (ImageView)findViewById(R.id.wifi);
            outlets1 = (ImageView)findViewById(R.id.outlets);

            // loads the data from the object into the view
            textName.setText(f.getSpot_type());
            textLocation.setText(f.getSpotName());
            textAddress.setText(f.getAddress());
            imgURl = f.getImageURL();

//            try {
//                Bitmap spotURL = null;
//                InputStream in = new java.net.URL(imgURl).openStream();
//                in = new java.net.URL(imgURl).openStream();
//                spotURL = BitmapFactory.decodeStream(in);
//                imageSpot.setImageBitmap(spotURL);
//            } catch (IOException e) {
//                e.printStackTrace();
//            }


            new AsyncTask<Void,Void,Bitmap>()
            {
                @Override
                protected Bitmap doInBackground(Void... params) {
                    Bitmap spotURL = null;

                    try {
                        spotURL = null;
                        InputStream in = null;
                        in = new java.net.URL(imgURl).openStream();
                        spotURL = BitmapFactory.decodeStream(in);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    return spotURL;
                }

                @Override
                protected void onPostExecute(Bitmap bitmap) {
                    super.onPostExecute(bitmap);
                    imageSpot.setImageBitmap(bitmap);
                }
            }.execute();





            return convertView;
        }
    }

}