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


import java.util.ArrayList;

import helpers.MqttHelper;

public class MainActivity extends AppCompatActivity {
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
        getMenuInflater().inflate(R.menu.menu_full, menu);
        return true;
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

            Intent intent = new Intent(this, MessageView.class);
            intent.putExtra("isTeacher", true);

            intent.putExtra("choices", choices);
            startActivity(intent);
            finish();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}