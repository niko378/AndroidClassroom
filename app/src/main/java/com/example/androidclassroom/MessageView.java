package com.example.androidclassroom;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.util.Pair;
import android.widget.LinearLayout;
import android.widget.TextView;

import org.eclipse.paho.client.mqttv3.IMqttDeliveryToken;
import org.eclipse.paho.client.mqttv3.MqttCallbackExtended;
import org.eclipse.paho.client.mqttv3.MqttMessage;

import java.util.ArrayList;
import java.util.List;
import helpers.MqttHelper;
import model.Message;

import helpers.MqttHelper;

public class MessageView extends AppCompatActivity {
    MqttHelper mqttHelper;
    List<Message> listMsg = new ArrayList<>();
    List<TextView> feed = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        startMqtt();
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_message_view);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
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

                listMsg.add(message);
                Log.w("Debug", "topic:" + message.topic + " message:" + message.body);

                TextView post = new TextView(getBaseContext());
                post.setText("Sujet: " + message.topic + "\n\r" + "Message: " + message.body);
                feed.add(post);
                LinearLayout ll = findViewById(R.id.verticalFeed);
                ll.addView(post);


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

