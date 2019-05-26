package com.example.androidclassroom;

import android.graphics.Color;
import android.os.Bundle;
import android.support.constraint.ConstraintLayout;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.CardView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.util.Pair;
import android.view.MenuItem;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.database.sqllite.DbConnector;

import org.eclipse.paho.client.mqttv3.IMqttDeliveryToken;
import org.eclipse.paho.client.mqttv3.MqttCallbackExtended;
import org.eclipse.paho.client.mqttv3.MqttMessage;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.text.SimpleDateFormat;
import java.util.TimeZone;

import helpers.MqttHelper;
import model.Message;

import helpers.MqttHelper;
import model.User;

public class MessageView extends AppCompatActivity {
    MqttHelper mqttHelper;
    List<Message> listMsg = new ArrayList<>();
    DbConnector db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        db = new DbConnector(this);
        startMqtt();
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_message_view);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        String email = getIntent().getStringExtra("email");
        if (email == null) {
            email = "number1@email.com";
        }

        User user = db.getUserByEmail(email);
/*
        final DrawerLayout dLayout = (DrawerLayout) findViewById(R.id.drawer_layout); // initiate a DrawerLayout
        NavigationView navView = (NavigationView) findViewById(R.id.navigation);
        navView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(MenuItem menuItem) {
                Fragment frag = null; // create a Fragment Object
                int itemId = menuItem.getItemId(); // get selected menu item's id
                // check selected menu item's id and replace a Fragment Accordingly

                // display a toast message with menu item's title
                Toast.makeText(getApplicationContext(), menuItem.getTitle(), Toast.LENGTH_SHORT).show();
                if (frag != null) {
                    FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
                    //transaction.replace(R.id., frag); // replace a Fragment with Frame Layout
                    transaction.commit(); // commit the changes
                    dLayout.closeDrawers(); // close the all open Drawer Views
                    return true;
                }
                return false;
            }
        });*/
/*
        findViewById(R.id.imageButton1).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

        findViewById(R.id.imageButton2).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

        findViewById(R.id.imageButton3).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
*//*
        findViewById(R.id.floatingActionButton).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                DrawerLayout
                        dLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
// initiate a DrawerLayout
                Boolean
                        isOpen = dLayout.isDrawerOpen(dLayout);


                if (!isOpen) {
                    Toast.makeText(getApplicationContext(), "open", Toast.LENGTH_SHORT).show();
                    // initiate a DrawerLayout
                    dLayout.openDrawer(GravityCompat.START);
                    // open a Drawer
                } else {
                    Toast.makeText(getApplicationContext(), "close", Toast.LENGTH_SHORT).show();

                    dLayout.closeDrawers();
                }
                //findViewById(R.id.cardView).setLayoutParams(new ConstraintLayout.LayoutParams(300, 682));

                //((LinearLayout)findViewById(R.id.menuSide)).addView(new TextView(context).setText("hello"));
            }
        });*/
    }

    private void startMqtt() {
        mqttHelper = new MqttHelper(getApplicationContext());
        mqttHelper.setCallback(new MqttCallbackExtended() {
            @Override
            public void connectComplete(boolean b, String s) {

            }

            @Override
            public void connectionLost(Throwable throwable) {

            }

            @Override
            public void messageArrived(String topic, MqttMessage mqttMessage) throws Exception {
                Message message = new Message();
                message.topic = topic;
                message.body = mqttMessage.toString();
                message.date = new Date();
                SimpleDateFormat formatDate = new SimpleDateFormat("dd MMM YYYY  HH:mm:ss", Locale.FRANCE);
                formatDate.setTimeZone(TimeZone.getTimeZone("GMT-4"));
                db.addElement(message);
                listMsg.add(message);
                Log.w("Debug", "topic:" + message.topic + " message:" + message.body);

                CardView post = new CardView(getBaseContext());
                TextView text = new TextView(getBaseContext());
                text.setText(formatDate.format(message.date) + "\n\r" +"Sujet: " + message.topic + "\n\r" + "Message: " + message.body);
                post.addView(text);
                post.setCardBackgroundColor(Color.rgb(220,220,250));
                post.setPadding(20,200,20,200);
                post.setContentPadding(20,10,20,10);
                LinearLayout ll = findViewById(R.id.verticalFeed);
                ll.addView(post);
                List<Pair<String,String>[]> dbValues = db.getTable("messages");
//                String msg = "message";
//                for (int i = 0; i < 15; i++){
//                    mqttHelper.mqttAndroidClient.publish("/test",msg.getBytes(),0,false);
//                }
            }

            @Override
            public void deliveryComplete(IMqttDeliveryToken iMqttDeliveryToken) {

            }
        });
    }
}

