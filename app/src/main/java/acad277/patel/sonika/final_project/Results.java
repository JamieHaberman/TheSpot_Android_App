package acad277.patel.sonika.final_project;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class Results extends AppCompatActivity {

    private final static String TAG = Results.class.getSimpleName();

    private final String URL = "https://restcountries.eu/rest/v2/name/";

    private TextView textJSON;
    private ListView listStudySpots;
    private TextView textLanguage;
    private RelativeLayout layout;
    String position1;

    ArrayAdapter<String> adapter;
    ArrayList<String> countries;

    //TODO Volley
    public RequestQueue queue;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_results);
        //this is the list of study spots

        listStudySpots = (ListView) findViewById(R.id.list_studyspots);
//        layout.setBackgroundColor(Color.parseColor("#d6fffc"));

        Intent data= getIntent();
        //Any time we are retrieiving an object from an intent, we use seriable exra
        position1= data.getStringExtra("EXTRA_LANGUAGE");

        countries = new ArrayList<>();
        adapter = new ArrayAdapter<>(
                this,
                android.R.layout.simple_list_item_1,
                countries);
        listStudySpots.setAdapter(adapter);
        queue = Volley.newRequestQueue(this);
        requestJSONParse(URL + position1);

    }

    public void requestJSONParse(String reqURL) {JsonArrayRequest req = new JsonArrayRequest(reqURL,
            new Response.Listener<JSONArray>() {

                @Override
                public void onResponse(JSONArray response) {
                    try {
                        JSONObject object = (JSONObject) response.get(0);



                        String population = object.getString("name");
                        JSONArray jsonLanguages = object.getJSONArray("languages");

                        //on each request, you need to empty the languages


                        countries.clear();
                        for(int i = 0; i<response.length(); i++){
                            JSONObject objLanguage = (JSONObject) response.get(i);
                            String language = objLanguage.getString("name");
                            countries.add(language);

                            listStudySpots.getSelectedItem();


                        }

                        adapter.notifyDataSetChanged();


                    } catch (JSONException e) {
                        e.printStackTrace();

                    }
                }
            },
            new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {

                }
            }
    );
        queue.add(req);


    }
}

