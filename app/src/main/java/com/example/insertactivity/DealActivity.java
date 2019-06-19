package com.example.insertactivity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.List;

public class DealActivity extends AppCompatActivity {

    EditText mDealsTitleEt;
    EditText mpriceEt;
    EditText mDescriptionEt;
    private FirebaseDatabase mFirebaseDatabse;

    private DatabaseReference mDatabaseReference;
    TravelDeal deal;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_insert);


        FirebaseUtil.openFbReffernce("users", this);

        mFirebaseDatabse = FirebaseUtil.mFirebaseDatabse;
        mDatabaseReference = FirebaseUtil.mDatabaseReference;

        mDealsTitleEt = findViewById(R.id.et_deal_title);
        mpriceEt = findViewById(R.id.et_price);
        mDescriptionEt = findViewById(R.id.et_desc);
        Intent intent = getIntent();
        TravelDeal deal = (TravelDeal) intent.getSerializableExtra("Deal");
        if(deal == null)
        {
            deal = new TravelDeal();
        }

        this.deal  = deal;
        mDealsTitleEt.setText(deal.getTitle());
        mDescriptionEt.setText(deal.getDescription());
        mpriceEt.setText(deal.getPrice());

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
       MenuInflater menuInflater = getMenuInflater();
       menuInflater.inflate(R.menu.save_menu,menu);
       return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if(item.getItemId() == R.id.action_save)
        {
            saveDeal();
            Toast.makeText(this,"Saving...", Toast.LENGTH_LONG).show();
            backToList();

            clear();
        }
        else if (item.getItemId() == R.id.action_delete)
        {
            deleteDeal();
            Toast.makeText(this,"Deleted",Toast.LENGTH_LONG).show();
            backToList();
        }
            return super.onOptionsItemSelected(item);
    }

    private void clear() {
        mDealsTitleEt.setText("");
        mDescriptionEt.setText("");
        mpriceEt.setText("");
        mDescriptionEt.requestFocus();

    }

    private void saveDeal()
    {
        deal.setTitle( mDealsTitleEt.getText().toString());
        deal.setDescription( mDescriptionEt.getText().toString());
        deal.setPrice(mpriceEt.getText().toString());

        if(deal.getId() == null)
        {
            FirebaseDatabase.getInstance().getReference()
                    .child("users")
                    .child(FirebaseAuth.getInstance().getCurrentUser().getUid())
                    .push().setValue(deal);
           // mDatabaseReference.push().setValue(deal);
        }
        else
        {

           // mDatabaseReference.child(deal.getId()).setValue(deal);
            FirebaseDatabase.getInstance().getReference()
                    .child("users")
                    .child(FirebaseAuth.getInstance().getCurrentUser().getUid()).child(deal.getId())
                    .setValue(deal);

        }



    }


    private void  deleteDeal()
    {
        if(deal == null)
        {
            Toast.makeText(this,"You have to save inorder to delete",Toast.LENGTH_SHORT).show();
            return;
        }
        else {
            mDatabaseReference.child(FirebaseAuth.getInstance().getCurrentUser().getUid()).child(deal.getId()).removeValue();
        }

    }


    private void backToList()
    {
        Intent intent = new Intent(this, ListActivity.class);
//        Toast.makeText(this,"Saving...", Toast.LENGTH_LONG).show();
        startActivity(intent);

    }
}
