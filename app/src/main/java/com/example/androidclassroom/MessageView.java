package com.example.androidclassroom;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.CardView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.widget.LinearLayout;
import android.widget.TextView;

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

public class MessageView extends AppCompatActivity {
    MqttHelper mqttHelper;
    List<String> listFiltre = new ArrayList<>();
    List<Message> listMsg = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        startMqtt();
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_message_view);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        listFiltre.add("Informations générales");
        listFiltre.add("Cours");
        listFiltre.add("Travaux");
        listFiltre.add("Examens");
        listFiltre.add("Vie scolaire");

        /*
        findViewById(R.id.imageButton1).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        }); */

        /*
        findViewById(R.id.imageButton2).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
        */

        /*
        findViewById(R.id.imageButton3).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
        */
        /*
        findViewById(R.id.floatingActionButton).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                findViewById(R.id.menuSide).setLayoutParams(new FrameLayout.LayoutParams(120, 682));
                //findViewById(R.id.cardView).setLayoutParams(new FrameLayout.LayoutParams(300, 682));

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
                Log.w("Debug", "topic:" + message.topic + " message:" + message.body);

                for (String abonnement : listFiltre) {
                    if (abonnement.equals(message.topic)) {
                        listMsg.add(message);
                        CardView post = new CardView(getBaseContext());
                        TextView text = new TextView(getBaseContext());
                        text.setText(formatDate.format(message.date) + "\n\r" + "Sujet: " + message.topic + "\n\r" + "Message: " + message.body);
                        post.addView(text);
                        post.setCardBackgroundColor(Color.rgb(220, 220, 250));
                        post.setPadding(20, 200, 20, 200);
                        post.setContentPadding(20, 10, 20, 10);
                        LinearLayout ll = findViewById(R.id.verticalFeed);
                        ll.addView(post);

                        break;
                    }
                }

            }

            @Override
            public void deliveryComplete(IMqttDeliveryToken iMqttDeliveryToken) {

            }
        });
    }
}


//                String msg = "message";
//                for (int i = 0; i < 15; i++){
//                    mqttHelper.mqttAndroidClient.publish("/test",msg.getBytes(),0,false);
//