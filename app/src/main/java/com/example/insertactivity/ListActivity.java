package com.example.insertactivity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.Window;
import android.view.WindowManager;

import com.firebase.ui.auth.AuthUI;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;

public class ListActivity extends AppCompatActivity {

    ArrayList<TravelDeal> deals;
    private FirebaseDatabase mFirebaseDatabse;
    private DatabaseReference mDatabaseReference;
    private ChildEventListener mChildEventListener;

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.list_activity_menu, menu);
        return  true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId())
        {
            case  R.id.action_insert:
                startActivity(new Intent(this, DealActivity.class));
                return true;
            case R.id.action_logout:
                AuthUI.getInstance()
                        .signOut(this)
                        .addOnCompleteListener(new OnCompleteListener<Void>() {
                            public void onComplete(@NonNull Task<Void> task) {
                              Log.d("logout","true");
                              FirebaseUtil.attachListener();
                            }
                        });
                FirebaseUtil.detach();
                return true;


        }


        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_list);




//        FirebaseUtil.openFbReffernce("traveldeals");
//        mFirebaseDatabse = FirebaseUtil.mFirebaseDatabse;
//        mDatabaseReference = FirebaseUtil.mDatabaseReference;
//        mChildEventListener = new ChildEventListener() {
//            @Override
//            public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
//                TextView tvDeals = findViewById(R.id.list_view);
//                TravelDeal td = dataSnapshot.getValue(TravelDeal.class); // we're serilizing it and putting it on the class Travel Deal
//                deals.add(td);
//                tvDeals.setText(tvDeals.getText() + "\n" + td.getTitle());
//                for(TravelDeal d : deals)
//                {
//                    Log.d("check", d.toString());
//                }
//            }
//
//            @Override
//            public void onChildChanged(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
//
//            }
//
//            @Override
//            public void onChildRemoved(@NonNull DataSnapshot dataSnapshot) {
//
//            }
//
//            @Override
//            public void onChildMoved(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
//
//            }
//
//            @Override
//            public void onCancelled(@NonNull DatabaseError databaseError) {
//
//            }
//        };
//        mDatabaseReference.addChildEventListener(mChildEventListener);

    }

    @Override
    protected void onPause() {
        super.onPause();

        FirebaseUtil.detach();
    }

    @Override
    protected void onResume() {
        super.onResume();
        FirebaseUtil.openFbReffernce("users",this);
        RecyclerView rvDeals = findViewById(R.id.rvDeals);
        final DealsAdapter adapter = new DealsAdapter();
        rvDeals.setAdapter(adapter);
        LinearLayoutManager dealsLayoutManager = new LinearLayoutManager(this,LinearLayoutManager.VERTICAL,false);
        rvDeals.setLayoutManager(dealsLayoutManager);
        FirebaseUtil.attachListener();
    }

}
