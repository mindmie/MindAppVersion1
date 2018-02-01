package com.example.mindmie.mindappversion1;

import android.app.ListActivity;
import android.app.ProgressDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class ShowInfoActivity extends AppCompatActivity {

    //view
    private ListView listView;
    private ProgressDialog progressDialog;
    private List<Users> usersList;

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
        usersList = new ArrayList<>();

        //Auth
        mAuth = FirebaseAuth.getInstance();

        //realtime db
        mDatabase = FirebaseDatabase.getInstance().getReference();

        initView();

    }



    //realtime
    String userID = mAuth.getCurrentUser().getUid();
    DatabaseReference currentUser = FirebaseDatabase.getInstance().getReference().child("Users").child(userID);



    //[on start]

    @Override
    protected void onStart() {
        super.onStart();

        currentUser.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                usersList.clear();

                for (DataSnapshot userSnapshot : dataSnapshot.getChildren()){
                    Users users = userSnapshot.getValue(Users.class);
                    usersList.add(users);

                }

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

    //[on start]
    //[start init view]
    private void initView() {


        ArrayList<String> nameExample = new ArrayList<>();

        nameExample.add("adapter1");
        nameExample.add("adapter2");
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(getApplicationContext(),android.R.layout.simple_list_item_1, nameExample);
        listView.setAdapter(adapter);







     //  currentUser.addValueEventListener(new ValueEventListener() {
     //       @Override
      //      public void onDataChange(DataSnapshot dataSnapshot) {
                //showData(dataSnapshot);


            //    Users users = dataSnapshot.getValue(Users.class);




                //Map<String,String> map = dataSnapshot.getValue(Map.class);

//                String name = map.get("name");
//                String age = map.get("age");
//                String gender = map.get("gender");
//                String height = map.get("height");
//                String weight = map.get("weight");
                //String value = dataSnapshot.getValue(String.class);
                //tvName.setText(name);
        //    }



       //    @Override
      //      public void onCancelled(DatabaseError databaseError) {

      //      }
     //   });













    }//[end init view]


    //[start show data]
//    private void showData(DataSnapshot dataSnapshot) {
//
//        for (DataSnapshot ds: dataSnapshot.getChildren()){
//            Users userInfo = new Users();
//            userInfo.setUserName( ds.child(userID).getValue(Users.class).getUserName());
//            userInfo.setUserAge(ds.child(userID).getValue(Users.class).getUserAge());
//            userInfo.setUserGender(ds.child(userID).getValue(Users.class).getUserGender());
//            userInfo.setUserWeight(ds.child(userID).getValue(Users.class).getUserWeight());
//            userInfo.setUserHeight(ds.child(userID).getValue(Users.class).getUserHeight());
//            userInfo.setUserActivity(ds.child(userID).getValue(Users.class).getUserActivity());
//
//            ArrayList<String> array = new ArrayList<>();
//            array.add(userInfo.getUserName());
//            array.add(userInfo.getUserAge());
//            array.add(userInfo.getUserGender());
//            array.add(userInfo.getUserWeight());
//            array.add(userInfo.getUserHeight());
//            array.add(userInfo.getUserActivity());
//
//            listViewUserInfo.setAdapter(adapter);
//
//
//            //tvName.setText(userInfo.getUserName());
//
//        }
//
//    }//[end show data]
}
