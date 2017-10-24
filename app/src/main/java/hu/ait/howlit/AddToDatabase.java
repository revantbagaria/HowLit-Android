package hu.ait.howlit;

import android.os.Bundle;
import android.os.StrictMode;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
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

import hu.ait.howlit.models.Business;
import hu.ait.howlit.models.Club;
import hu.ait.howlit.models.SearchResponse;
import hu.ait.howlit.webservice.YelpApi;
import hu.ait.howlit.webservice.YelpApiFactory;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class AddToDatabase extends AppCompatActivity {

    private static final String TAG = "AddToDatabase";

    private Button btnSubmit;

    private EditText mNewName, mNewLocation;

    private LinearLayoutManager mLinearLayoutManager;

    private ArrayList<Club> clubList;
    //add Firebase Database stuff
    private FirebaseDatabase mFirebaseDatabase;
    private DatabaseReference myRef;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.add_to_database_layout);
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);
        try {
            fetchYelpData();
        } catch (IOException e) {
            e.printStackTrace();
        }

        mFirebaseDatabase = FirebaseDatabase.getInstance();
        myRef = mFirebaseDatabase.getReference();


        // Read from the database
        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                // This method is called once with the initial value and again
                // whenever data at this location is updated.
                Object value = dataSnapshot.getValue();
                Log.d(TAG, "Value is: " + value);
            }

            @Override
            public void onCancelled(DatabaseError error) {
                // Failed to read value
                Log.w(TAG, "Failed to read value.", error.toException());
            }
        });
    }

    private void fetchYelpData() throws IOException {
        YelpApiFactory currentApi = new YelpApiFactory();
        YelpApi yelpFusionApi = currentApi.createAPI("5qCTTm8-e0ZZezY4v0suOA","w4MhIE0p7Q0gt1gnVqhiQc2NfACDUIpNwjUgUmwokc1eFMxSOIEJzXJiwcSmH0am");
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
                for (Business b: list) {
                    Club currentObject = new Club(b.getName().toString(), b.getLocation().getDisplayAddress().toString(), b.getPrice());
                    clubList.add(currentObject);
                    myRef.child("Clubs").child(b.getName().toString()).setValue(currentObject);
                }
            }

            @Override
            public void onFailure(Call<SearchResponse> call, Throwable t) {
                toastMessage("Failed to add Yelp API data :(");
                System.out.println("+++++++ THERE IS A RPOBLEM");
            }
        });
    }

    //add a toast to show when successfully signed in
    /**
     * customizable toast
     * @param message
     */

    private void toastMessage(String message){
        Toast.makeText(this,message,Toast.LENGTH_SHORT).show();
    }
}