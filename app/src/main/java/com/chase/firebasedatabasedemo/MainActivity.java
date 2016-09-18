package com.chase.firebasedatabasedemo;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class MainActivity extends AppCompatActivity {
    //Wire UP our views to the MainActivity
    TextView myMoodTextView;
    Button buttonHappy;
    Button buttonMeh;
    Button buttonSad;

    //To connect to our Firebase Realtime DB we need a connection
    //So lets create a Database Reference
    //This will get the root of the Firebase database JSON  tree
    DatabaseReference rootReference = FirebaseDatabase.getInstance().getReference();
    //By calling child we are creating a node called mood underneath the Root database location
    DatabaseReference moodReference = rootReference.child("mood");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //We instantiate our views here
        myMoodTextView = (TextView) findViewById(R.id.myMoodTextView);
        buttonHappy = (Button) findViewById(R.id.buttonHappy);
        buttonMeh = (Button) findViewById(R.id.buttonMeh);
        buttonSad = (Button) findViewById(R.id.buttonSad);
    }

    @Override
    protected void onStart() {
        super.onStart();

        //Now we have a reference to our mood node we can go ahead and create a listener
        //Notice we call mood reference here
        moodReference.addValueEventListener(new ValueEventListener() {
            //We get two call  back methods inside of this anonymous inner class
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                //This callback is fired up each time data changes in the realtime database
                // we get our data back as a datasnapshot which contains our data and other useful methods
                String text = dataSnapshot.getValue(String.class);
                //Using the snapshot we sync to the textview
                myMoodTextView.setText(text);

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                //This callback is fired up if we ever encounter any errors
            }
        });

        //Now lets use the buttons to push changes to the realtime db
        //notice we do not update the textview directly
        //we update the textview in real-time via the child reference
        buttonHappy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //use the reference to get the reference to the key node that you wish to exact change on
                moodReference.setValue("Happy! :)"); // mood:"Happy",
            }
        });

        buttonMeh.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                moodReference.setValue("Meh :/");
            }
        });

        buttonSad.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                moodReference.setValue("Sad :'(");
            }
        });
        //We don't modify anything from our local states
        //We only want to update from the real-time database.
        //--Happy Coding!!--//
    }
}