package com.example.androidclassroom;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Pair;
import android.view.View;
import android.widget.CheckBox;
import android.widget.LinearLayout;

import com.database.sqllite.DbConnector;

import java.util.ArrayList;
import java.util.List;

public class ChannelSelectView extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_channel_select_view);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        boolean isTeacher = this.getIntent().getBooleanExtra("isTeacher", false);
        List<String> choices = this.getIntent().getStringArrayListExtra("choices");

        if (!isTeacher) {
            findViewById(R.id.add_tag).setVisibility(View.INVISIBLE);
        }

        LinearLayout ll = findViewById(R.id.checkbox_layout);

        CheckBox cb;

        DbConnector dbConnector = new DbConnector(this);

        List<String> classes = new ArrayList<>();
        classes.add("305");
        try {
            dbConnector.deleteAllRows("users");
            dbConnector.addTeacher("bob.email.com", "bob", choices, classes);
            classes.add("306");
            dbConnector.addTeacher("carl.email.com","carl", choices, classes);
            dbConnector.addTags(choices, "bob.email.com");
            List<Pair<String, String>[]> value = dbConnector.getTable("users");
            System.out.println(value);
        } catch (Exception e) {
            System.out.println(e.toString());
        }
        for (int i = 0; i < choices.size(); i++) {
            cb = new CheckBox(this);
            cb.setText(choices.get(i));
            ll.addView(cb);
        }
    }

}
