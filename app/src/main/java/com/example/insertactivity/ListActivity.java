package com.example.insertactivity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Toast;

import com.firebase.ui.auth.AuthUI;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;

public class ListActivity extends AppCompatActivity {

    ArrayList<Note> deals;
    private FirebaseDatabase mFirebaseDatabse;
    private DatabaseReference mDatabaseReference;
    private ChildEventListener mChildEventListener;
    boolean misDark = false;
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
                startActivity(new Intent(this, NoteActivity.class));
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
        getSupportActionBar().hide();
        setContentView(R.layout.activity_list);

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
        final RecyclerView rvDeals = findViewById(R.id.rvDeals);
        final NoteAdapter adapter = new NoteAdapter();
        rvDeals.setAdapter(adapter);
        LinearLayoutManager dealsLayoutManager = new LinearLayoutManager(this,LinearLayoutManager.VERTICAL,false);
        rvDeals.setLayoutManager(dealsLayoutManager);
        FirebaseUtil.attachListener();

        //final RecyclerView rvDeals = findViewById(R.id.rvDeals);


//        FloatingActionButton fabSwitcher = findViewById(R.id.fab_switcher);
//        final boolean isDark = false;
//        final ConstraintLayout  rootLayout =findViewById(R.id.roo_layout);
//        fabSwitcher.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Toast.makeText(ListActivity.this,"work",Toast.LENGTH_SHORT).show();
//
//                if(isDark) {
//                    rootLayout.setBackgroundColor(getResources().getColor(R.color.balck));
//
//                }
//
//                else
//                {
//                    rootLayout.setBackgroundColor(getResources().getColor(R.color.white));
//                }
//                final NoteAdapter adapter = new NoteAdapter();
//                rvDeals.setAdapter(adapter);
//
//                LinearLayoutManager dealsLayoutManager = new LinearLayoutManager(getApplicationContext(),LinearLayoutManager.VERTICAL,false);
//                rvDeals.setLayoutManager(dealsLayoutManager);
//                //FirebaseUtil.attachListener();
//            }
//        });
    }

}
