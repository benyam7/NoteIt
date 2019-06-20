package com.example.insertactivity;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AnimationUtils;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.List;

public class NoteAdapter extends RecyclerView.Adapter<NoteAdapter.DealsViewHolder> implements Filterable
{
    List<Note> mNotes;
    private FirebaseDatabase mFirebaseDatabse;
    private DatabaseReference mDatabaseReference;
    private ChildEventListener mChildEventListener;
    private FirebaseAuth mFirebaseAuth;
    private Context mContext;
    boolean isDark = false;
    List<Note> mFilteredNotes;




    public NoteAdapter()

    {
        //FirebaseUtil.openFbReffernce("traveldeals",// STOPSHIP: 6/18/19  );
        mFirebaseDatabse = FirebaseUtil.mFirebaseDatabse;
        mDatabaseReference = FirebaseUtil.mDatabaseReference;
        mFirebaseAuth = FirebaseUtil.mFirebaseAuth;
        mNotes = FirebaseUtil.sNotes;
        this.mFilteredNotes = mNotes;
        mChildEventListener = new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {



                Note td = dataSnapshot.getValue(Note.class); // we're serilizing it and putting it on the class Travel Deal

                Log.d("deal", td.getTitle());
                td.setId(dataSnapshot.getKey());
                mNotes.add(td);
                // i think it notifying at the same time adding it at the back
                notifyItemInserted(mNotes.size()-1);
            }

            @Override
            public void onChildChanged(@NonNull DataSnapshot dataSnapshot, @Nullable String s)
            {

            }

            @Override
            public void onChildRemoved(@NonNull DataSnapshot dataSnapshot) {

            }

            @Override
            public void onChildMoved(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        };

         mDatabaseReference.child(mFirebaseAuth.getCurrentUser().getUid()).addChildEventListener(mChildEventListener);

    }

    @NonNull
    @Override
    public DealsViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        mContext = parent.getContext();
      View itemView = LayoutInflater.from(mContext).
              inflate(R.layout.item, parent, false);
      return new DealsViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull DealsViewHolder holder, int position) {
        Note deal = mFilteredNotes.get(position);

        //binding it to the holder
        holder.mConstraintLayout.setAnimation(AnimationUtils.loadAnimation(mContext,R.anim.anim_2));

        holder.bind(deal);

    }

    @Override
    public int getItemCount() {
        return mFilteredNotes.size();
    }

    @Override
    public Filter getFilter() {
        return new Filter() {
            @Override
            protected FilterResults performFiltering(CharSequence charSequence) {
                String key = charSequence.toString();
                if(key.isEmpty())
                {
                    mFilteredNotes = mNotes;
                }
                else
                {
                    List<Note> listFiltered = new ArrayList<>();
                    for (Note note : mNotes)
                    {
                        if(note.getTitle().toLowerCase().contains(key.toLowerCase()))
                        {
                            listFiltered.add(note);
                        }
                    }
                    mFilteredNotes = listFiltered;
                }
                FilterResults filterResults = new FilterResults();
                filterResults.values = mFilteredNotes;
                return filterResults;
            }

            @Override
            protected void publishResults(CharSequence charSequence, FilterResults filterResults) {
                mFilteredNotes = (List<Note>) filterResults.values;
                notifyDataSetChanged();
            }
        };
    }


    public class DealsViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener
    {
        // we will use this class to describe how to bind the data
        // to a single row(item)
        TextView tvTitle, tvDate, tvDesc;
        ImageView img;
        ConstraintLayout mConstraintLayout;

        public DealsViewHolder(@NonNull View itemView) {
            super(itemView);
            mConstraintLayout = itemView.findViewById(R.id.container);
            tvTitle = itemView.findViewById(R.id.tv_title);
            tvDesc = itemView.findViewById(R.id.tv_date);
            tvDesc = itemView.findViewById(R.id.tv_description);

            itemView.setOnClickListener(this);

            if(isDark)
            {
                setDarkTheme();
            }

        }

        public void bind(Note note)
        {

            tvTitle.setText(note.getTitle());
          //  tvDesc.setText(deal.getDescription());
//            tvDate.setText(deal.getDate());
        }

        @Override
        public void onClick(View view) {
          int position =getAdapterPosition();
          //Toast.makeText(view.getContext(),"working", Toast.LENGTH_SHORT).show();
          Note selectedDeal = mNotes.get(position);
            Intent intent = new Intent(view.getContext(), NoteActivity.class);
            intent.putExtra("Deal",selectedDeal);
            view.getContext().startActivity(intent);
        }

        private void setDarkTheme()
        {
            mConstraintLayout.setBackgroundResource(R.drawable.car_bg_dark);
        }
    }

}
