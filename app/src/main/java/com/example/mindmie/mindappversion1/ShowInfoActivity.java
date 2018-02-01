package com.example.mindmie.mindappversion1;

import android.app.ProgressDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class ShowInfoActivity extends AppCompatActivity {

    //view
    private ListView listView;
    private ProgressDialog progressDialog;
    private ArrayList<Users> usersList;

    //auth
    private FirebaseAuth mAuth;

    //realtime db
    private DatabaseReference mDatabase;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_info);

        //view
        listView = findViewById(R.id.lv_userInfo);
        progressDialog = new ProgressDialog(this);
        usersList = new ArrayList<Users>();

        //Auth
        mAuth = FirebaseAuth.getInstance();

        //realtime db
        mDatabase = FirebaseDatabase.getInstance().getReference();

        initView();

    }

    //[start init view]
    private void initView() {

        //realtime
        final String userID = mAuth.getCurrentUser().getUid();
        DatabaseReference currentUser = FirebaseDatabase.getInstance().getReference().child("Users").child(userID);

        currentUser.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot snapshot : dataSnapshot.getChildren()){

                   Users users = dataSnapshot.getValue(Users.class);
                    users.getName();
                    users.getAge();
                    users.getGender();
                    users.getActivity();


                    usersList.add(users);
                    ArrayAdapter adapter = new ArrayAdapter(getApplicationContext(), android.R.layout.simple_list_item_1, usersList);
                    listView.setAdapter(adapter);
                }

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });


    }//[end init view]


}
