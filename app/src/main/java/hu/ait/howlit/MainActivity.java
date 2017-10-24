package hu.ait.howlit;

import android.os.Bundle;
import android.os.StrictMode;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import hu.ait.howlit.adapter.RVAdapter;
import hu.ait.howlit.models.Business;
import hu.ait.howlit.models.Club;
import hu.ait.howlit.models.SearchResponse;
import hu.ait.howlit.webservice.YelpApi;
import hu.ait.howlit.webservice.YelpApiFactory;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class MainActivity extends AppCompatActivity {

    private static final String TAG = "MainActivity";

    // UI references.
    private LinearLayoutManager mLinearLayoutManager;
    private FirebaseDatabase mFirebaseDatabase;
    private DatabaseReference myRef;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.view_database_layout);
        mFirebaseDatabase = FirebaseDatabase.getInstance();
        myRef = mFirebaseDatabase.getReference();
        // Read from the database
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);
        try {
            fetchYelpData();
        } catch (IOException e) {
            e.printStackTrace();
        }

        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                // This method is called once with the initial value and again
                // whenever data at this location is updated.
                showData(dataSnapshot);
            }

            @Override
            public void onCancelled(DatabaseError error) {
                // Failed to read value
                Log.w(TAG, "Failed to read value.", error.toException());
            }
        });

    }

    private void showData(DataSnapshot dataSnapshot){
        ArrayList<Club> list = new ArrayList<Club>();
        mLinearLayoutManager = new LinearLayoutManager(this);
        RecyclerView rv = (RecyclerView)findViewById(R.id.rv);
        rv.setLayoutManager(mLinearLayoutManager);
        for(DataSnapshot ds : dataSnapshot.child("Clubs").getChildren()){
            Club currObj = new Club();
            currObj.setName(ds.child("name").getValue().toString());
            String currentString = ds.child("location").getValue().toString();
            currObj.setLocation(currentString.substring(1,currentString.length()-1));
            currObj.setDownvote_count(ds.child("downvote_count").getValue().toString());
            currObj.setUpvote_count(ds.child("upvote_count").getValue().toString());
            currObj.setPrice(ds.child("price").getValue().toString());
            list.add(currObj);
        }

        RVAdapter adapter = new RVAdapter(list);
        rv.setAdapter(adapter);
    }


    //add a toast to show when successfully signed in
    /**
     * customizable toast
     * @param message
     */
    private void toastMessage(String message){
        Toast.makeText(this,message,Toast.LENGTH_SHORT).show();
    }


    private void fetchYelpData() throws IOException {
        YelpApiFactory currentApi = new YelpApiFactory();
        YelpApi yelpFusionApi = currentApi.createAPI("VKFeiP6wniHxCYCkT-gjfQ","NwAZtsGhZqmATwPDvlvociPX2nBJsBvak1JScLMvJfffSa4oE7YgwPj0F9QY3u5k");
        Map<String, String> params = new HashMap<>();

        params.put("term", "night club");
        params.put("latitude", "37.788022");
        params.put("longitude", "-122.399797");

        Call<SearchResponse> call = yelpFusionApi.getBusinessSearch(params);
        call.enqueue(new Callback<SearchResponse>() {
            @Override
            public void onResponse(Call<SearchResponse> call, Response<SearchResponse> response) {
                SearchResponse searchResponse = response.body();
                ArrayList<Business> list = new ArrayList<Business>();

                list = searchResponse.getBusinesses();
                int index = 0;
                for (Business b: list) {
                    Club currentObject = new Club(b.getName().toString(), b.getLocation().getDisplayAddress().toString(), b.getPrice());
                    myRef.child("Clubs").child(String.valueOf(index)).setValue(currentObject);
                    index++;
                }
            }

            @Override
            public void onFailure(Call<SearchResponse> call, Throwable t) {
                toastMessage("Failed to add Yelp API data :(");
                System.out.println("+++++++ THERE IS A RPOBLEM");
            }
        });
    }

}