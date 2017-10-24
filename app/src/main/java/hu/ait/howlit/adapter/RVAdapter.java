package hu.ait.howlit.adapter;


import android.content.Context;
import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.List;

import hu.ait.howlit.R;
import hu.ait.howlit.models.Club;


public class RVAdapter extends RecyclerView.Adapter<RVAdapter.ViewHolder>{
    private List<Club> clubList;
    private Context context;
    private FirebaseDatabase mFirebaseDatabase;
    private DatabaseReference myRef;

    public RVAdapter(List<Club> clubList) {
        this.clubList = clubList;
    }


    @Override
    public RVAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View rowView = LayoutInflater.from(parent.getContext()).inflate(
                R.layout.club_row, parent, false);
        ViewHolder ch = new ViewHolder(rowView);
        context = parent.getContext();
        return ch;
    }

    @Override
    public void onBindViewHolder(final RVAdapter.ViewHolder holder, int position) {
        mFirebaseDatabase = FirebaseDatabase.getInstance();
        myRef = mFirebaseDatabase.getReference();

        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                // This method is called once with the initial value and again
                // whenever data at this location is updated.
                Object value = dataSnapshot.getValue();
                Log.d("HI ", "Value is: " + value);
            }

            @Override
            public void onCancelled(DatabaseError error) {
                // Failed to read value
                Log.w("HELLO ", "Failed to read value.", error.toException());
            }
        });
        Club currentClub = clubList.get(position);

        holder.name_club.setText(currentClub.getName());
        holder.address_club.setText(currentClub.getLocation());
        holder.upvote_count.setText(currentClub.getUpvote_count());
        holder.downvote_count.setText(currentClub.getDownvote_count());
        holder.btnVote.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDialog(holder.getAdapterPosition());
            }
        });
    }

    public void showDialog(final int position){
        final AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setTitle("Vote Here");

        builder.setPositiveButton("Lit", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                int currentScore = Integer.parseInt(clubList.get(position).getUpvote_count());
                currentScore++;
                Log.i("score", String.valueOf(currentScore));
                clubList.get(position).setUpvote_count(String.valueOf(currentScore));
                myRef.child("Clubs").child(String.valueOf(position)).child("upvote_count").setValue(currentScore);
            }
        });

        builder.setNegativeButton("Not Lit", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                int currentScore = Integer.parseInt(clubList.get(position).getDownvote_count());
                currentScore++;
                Log.i("score", String.valueOf(currentScore));
                myRef.child("Clubs").child(String.valueOf(position)).child("downvote_count").setValue(currentScore);

            }
        });

        AlertDialog alertDialog = builder.create();
        alertDialog.show();
    }



    @Override
    public int getItemCount() {
        return clubList.size();
    }


    public static class ViewHolder extends RecyclerView.ViewHolder {
        private ImageView vote_icon;
        private TextView name_club;
        private TextView address_club;
        private TextView upvote_count;
        private TextView downvote_count;
        private ImageView upvote_icon;
        private ImageView downvote_icon;
        private Button btnVote;

        public ViewHolder(View itemView) {
            super(itemView);
            name_club = (TextView) itemView.findViewById(R.id.name_club);
            address_club = (TextView) itemView.findViewById(R.id.address_club);
            downvote_count = (TextView) itemView.findViewById(R.id.downvote_count);
            upvote_count = (TextView) itemView.findViewById(R.id.upvote_count);
            upvote_icon = (ImageView) itemView.findViewById(R.id.upvote_icon);
            downvote_icon = (ImageView) itemView.findViewById(R.id.downvote_icon);
            btnVote = (Button) itemView.findViewById(R.id.btnVote);
        }
    }

}