package com.example.insertactivity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import com.firebase.ui.auth.data.model.User;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class DealsAdapter extends RecyclerView.Adapter<DealsAdapter.DealsViewHolder>
{
    ArrayList<TravelDeal> deals;
    private FirebaseDatabase mFirebaseDatabse;
    private DatabaseReference mDatabaseReference;
    private ChildEventListener mChildEventListener;
    private FirebaseAuth mFirebaseAuth;
    private Context mContext;

    public DealsAdapter()

    {
        //FirebaseUtil.openFbReffernce("traveldeals",// STOPSHIP: 6/18/19  );
        mFirebaseDatabse = FirebaseUtil.mFirebaseDatabse;
        mDatabaseReference = FirebaseUtil.mDatabaseReference;
        mFirebaseAuth = FirebaseUtil.mFirebaseAuth;
        deals = FirebaseUtil.mDeals;
      //  TravelDeal mTravelDeal = new TravelDeal();
//        Query query = mDatabaseReference.child("users").orderByKey()
//                .equalTo(mFirebaseAuth.getInstance().getCurrentUser().getUid());
//
//        Query query2 = FirebaseDatabase.getInstance().getReference().child("users").
//                orderByKey().limitToFirst(6);
//        query2.addListenerForSingleValueEvent(new ValueEventListener() {
//            @Override
//            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
//                for(DataSnapshot snapshot : dataSnapshot.getChildren())
//                {
//                    Log.d("chekme","" + snapshot);
//                    Log.d("chekme","" + snapshot.getValue());
//
//                    String name = (String)snapshot.child("title").getValue();
//                    String description = (String)snapshot.child("des").getValue();
//                    //TravelDeal travelDeal = snapshot.getValue(TravelDeal.class);
//                    Log.d("checkme",name);
//
//
//                }
//            }
//
//            @Override
//            public void onCancelled(@NonNull DatabaseError databaseError) {
//
//            }
//        });
//            Log.d("test",query.toString());
//            query.addListenerForSingleValueEvent(new ValueEventListener() {
//                @Override
//                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
//                    for(DataSnapshot singleSnapshot : dataSnapshot.getChildren())
//                {
//                    Log.d("chhhhh",singleSnapshot.getValue().toString());
//                    TravelDeal Tdeal = singleSnapshot.getValue(TravelDeal.class);
//                    deals.add(Tdeal);
//
//                    notifyItemInserted(deals.size() - 1);
//
//                }
//
//                }
//
//                @Override
//                public void onCancelled(@NonNull DatabaseError databaseError) {
//                Log.d("noooooooooooooo","noooo");
//                }
//            });
//        query.addValueEventListener(new ValueEventListener() {
//            @Override
//            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
//                for(DataSnapshot singleSnapshot : dataSnapshot.getChildren())
//                {
//                    TravelDeal Tdeal = singleSnapshot.getValue(TravelDeal.class);
//                    deals.add(Tdeal);
//                    Log.d("test",Tdeal.toString());
//                    notifyItemInserted(deals.size() - 1);
//
//                }
//            }
//
//            @Override
//            public void onCancelled(@NonNull DatabaseError databaseError) {
//
//            }
//        });

        mChildEventListener = new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {



                TravelDeal td = dataSnapshot.getValue(TravelDeal.class); // we're serilizing it and putting it on the class Travel Deal

                Log.d("deal", td.getTitle());
                td.setId(dataSnapshot.getKey());
                deals.add(td);
                // i think it notifying at the same time adding it at the back
                notifyItemInserted(deals.size()-1);
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
        TravelDeal deal = deals.get(position);

        //binding it to the holder
        holder.mConstraintLayout.setAnimation(AnimationUtils.loadAnimation(mContext,R.anim.anim_2));

        holder.bind(deal);

    }

    @Override
    public int getItemCount() {
        return deals.size();
    }

    public class DealsViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener
    {
        // we will use this class to describe how to bind the data
        // to a single row(item)
        TextView tvTitle, tvprice, tvDesc;
        ImageView img;
        ConstraintLayout mConstraintLayout;
        public DealsViewHolder(@NonNull View itemView) {
            super(itemView);
            mConstraintLayout = itemView.findViewById(R.id.container);
            tvTitle = itemView.findViewById(R.id.item_title);
            tvprice = itemView.findViewById(R.id.item_price);
            tvDesc = itemView.findViewById(R.id.item_description);
            img = itemView.findViewById(R.id.item_image);
            itemView.setOnClickListener(this);



        }

        public void bind(TravelDeal deal)
        {
            tvTitle.setText(deal.getTitle());
            tvDesc.setText(deal.getDescription());
            tvprice.setText(deal.getPrice());
        }

        @Override
        public void onClick(View view) {
          int position =getAdapterPosition();
          //Toast.makeText(view.getContext(),"working", Toast.LENGTH_SHORT).show();
          TravelDeal selectedDeal = deals.get(position);
            Intent intent = new Intent(view.getContext(),DealActivity.class);
            intent.putExtra("Deal",selectedDeal);
            view.getContext().startActivity(intent);
        }
    }

}
