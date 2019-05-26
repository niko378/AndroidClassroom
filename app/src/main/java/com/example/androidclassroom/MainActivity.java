package com.example.androidclassroom;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.widget.TextView;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.support.v7.widget.Toolbar;

import org.eclipse.paho.client.mqttv3.IMqttDeliveryToken;
import org.eclipse.paho.client.mqttv3.MqttCallbackExtended;
import org.eclipse.paho.client.mqttv3.MqttMessage;

import helpers.MqttHelper;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    MqttHelper mqttHelper;

    TextView dataReceived;

    @Override

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        Button confirm = findViewById(R.id.create_account_btn);

        final MainActivity activity = this;
        confirm.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                System.out.println("changing activity");

                Intent intent = new Intent(activity, ChannelSelectView.class);
                intent.putExtra("name", ((EditText) findViewById(R.id.name_field)).getText().toString());
                intent.putExtra("email", ((EditText) findViewById(R.id.email_field)).getText().toString());
                intent.putExtra("password", ((EditText) findViewById(R.id.psw_field)).getText().toString());

                RadioGroup group = (RadioGroup) findViewById(R.id.type_group);
                char type;
                switch (group.indexOfChild(findViewById(group.getCheckedRadioButtonId())))  {
                    case 0:
                        type = 'T';
                        break;
                    case 1:
                        type = 'P';
                        break;
                    default:
                        type = 'S';
                        break;
                }
                intent.putExtra("type", type);

                startActivity(intent);
                finish();
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
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
                Log.w("Debug", mqttMessage.toString());
                dataReceived.setText(mqttMessage.toString());
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

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            System.out.println("changing activity");
            ArrayList<String> choices = new ArrayList<>();
            choices.add("hello");
            choices.add("world");

            Intent intent = new Intent(this, ChannelSelectView.class);
            intent.putExtra("isTeacher", true);

            intent.putExtra("choices", choices);
            startActivity(intent);
            finish();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}