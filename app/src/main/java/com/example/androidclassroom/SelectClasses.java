package com.example.androidclassroom;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Pair;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.database.sqllite.DbConnector;

import java.util.Dictionary;
import java.util.Hashtable;
import java.util.List;

import model.User;

public class SelectClasses extends AppCompatActivity implements ClassListFragment.OnFragmentInteractionListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_classes);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        final SelectClasses context = this;


        findViewById(R.id.add_class_btn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int fragments = ((LinearLayout) findViewById(R.id.class_list)).getChildCount();
                FragmentTransaction ft = getSupportFragmentManager().beginTransaction();

                Fragment fragment1 = ClassListFragment.newInstance();
                ft.add(R.id.class_list, fragment1, "fragment_" + fragments);
                ft.addToBackStack("fragment_" + fragments);
                ft.commit();

            }
        });

        findViewById(R.id.done_class_btn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String classes = context.getIntent().getStringExtra("classes");
                for (Fragment fragment : getSupportFragmentManager().getFragments()) {
                    classes = classes.concat(((ClassListFragment)fragment).textView.getText().toString() + ";");
                }
                User user = new User();
                Dictionary<String, Object> dict = new Hashtable<>();
                dict.put("type", context.getIntent().getStringExtra("type"));
                dict.put("name", context.getIntent().getStringExtra("name"));
                dict.put("email", context.getIntent().getStringExtra("email"));
                dict.put("password", context.getIntent().getStringExtra("password"));
                dict.put("tags", context.getIntent().getStringExtra("tags"));
                dict.put("classes", classes);

                user.loadFromDict(dict);

                DbConnector dbConnector = new DbConnector(getApplicationContext());
                dbConnector.addElement(user);

                List<Pair<String,String>[]> db = dbConnector.getTable("users");


                Intent intent = new Intent(context, MessageView.class);
                intent.putExtra("email", user.email);
                startActivity(intent);
                finish();

            }
        });


    }

    @Override
    public void onAttachFragment(Fragment fragment) {
        if (fragment instanceof ClassListFragment) {
            ClassListFragment classListFragment = (ClassListFragment) fragment;
            classListFragment.setOnAddNameSelectedListener(this);
        }
    }

    public void onAddNameSelected() {


    }


}
