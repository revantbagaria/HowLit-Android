package hu.ait.howlit;

import android.os.Bundle;
import android.support.annotation.Nullable;
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

import java.util.ArrayList;

import hu.ait.howlit.adapter.RVAdapter;
import hu.ait.howlit.models.Club;


public class ViewDatabase extends AppCompatActivity {

    private static final String TAG = "ViewDatabase";

    //add Firebase Database stuff
    private FirebaseDatabase mFirebaseDatabase;
    private DatabaseReference myRef;
    private LinearLayoutManager mLinearLayoutManager;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.view_database_layout);


        mFirebaseDatabase = FirebaseDatabase.getInstance();
        myRef = mFirebaseDatabase.getReference();

        // Read from the database
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
        for(DataSnapshot ds : dataSnapshot.getChildren()){

            mLinearLayoutManager = new LinearLayoutManager(this);
            RecyclerView rv = (RecyclerView)findViewById(R.id.rv);
            rv.setLayoutManager(mLinearLayoutManager);
            ArrayList<Club> list = new ArrayList<Club>();
            list.add(new Club("hi", "hlello", "awea"));
            RVAdapter adapter = new RVAdapter(list);
            Log.i("TA", ds.child("Clubs").getChildren().toString());
            rv.setAdapter(adapter);
            //ArrayList<Club> list  = (ArrayList<Club>) ds.child("Clubs").getValue();
            Log.i("ATTA", ds.child("Clubs").toString());


        }
    }


    /**
     * customizable toast
     * @param message
     */
    private void toastMessage(String message){
        Toast.makeText(this,message, Toast.LENGTH_SHORT).show();
    }
}