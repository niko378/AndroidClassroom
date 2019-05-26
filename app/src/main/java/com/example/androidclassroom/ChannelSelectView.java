package com.example.androidclassroom;

import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Pair;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.database.sqllite.DbConnector;

import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Dictionary;
import java.util.Enumeration;
import java.util.List;

import model.User;

public class ChannelSelectView extends AppCompatActivity {

    private final String ALL_TAGS = "Informations générales;Examens;Cours;Travaux;Vie scolaire";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_channel_select_view);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        CheckBox cb;
        final LinearLayout ll = findViewById(R.id.checkbox_layout);
        final Context context = this;


        final User user = new User();
        user.loadDefaultTags();

        user.type = this.getIntent().getCharExtra("type", 'O');
        user.fullName = this.getIntent().getStringExtra("name");
        user.email = this.getIntent().getStringExtra("email");
        user.password = this.getIntent().getStringExtra("password");

        Button addTag = findViewById(R.id.add_tag);
        if (user.type != 'T') {
            addTag.setVisibility(View.INVISIBLE);
        } else {
            addTag.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    showPopUp(v);
                }
            });
        }

        findViewById(R.id.submit).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                user.tags = new ArrayList<>();
                for (int i = 0; i < ll.getChildCount(); i++) {
                    if (ll.getChildAt(i).getClass() == CheckBox.class && ((CheckBox) ll.getChildAt(i)).isChecked()) {

                        user.tags.add(((CheckBox) ll.getChildAt(i)).getText().toString());


                    }
                }
                Intent intent = new Intent(context, SelectClasses.class);
                Dictionary<String, Object> userDict = user.getDict();
                Enumeration<String> keys = userDict.keys();
                while (keys.hasMoreElements()) {
                    String key = keys.nextElement();
                    intent.putExtra(key, userDict.get(key).toString());
                }
                startActivity(intent);
                finish();
                return;
            }
        });


        String[] tags = ALL_TAGS.split(";");
        for (int i = 0; i < tags.length; i++) {
            cb = new CheckBox(this);
            cb.setText(tags[i]);
            if (user.tags.contains(tags[i])) {
                cb.setChecked(true);
            }
            ll.addView(cb);
        }
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


        return super.onOptionsItemSelected(item);
    }

    public void showPopUp(View view) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);

        final Context context = this;

        LinearLayout viewGroup = (LinearLayout) findViewById(R.id.popup_layout);

        LayoutInflater layoutInflater = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        //this is custom dialog
        //custom_popup_dialog contains textview only
        final View customView = layoutInflater.inflate(R.layout.add_tag_popup_dialog, viewGroup);


        final PopupWindow popup = new PopupWindow(this);

        popup.setContentView(customView);
        popup.setFocusable(true);
        popup.setBackgroundDrawable(new ColorDrawable(
                android.graphics.Color.TRANSPARENT));

        popup.showAtLocation(customView, Gravity.NO_GRAVITY, 150, 150);



        customView.findViewById(R.id.close_btn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                popup.dismiss();
            }
        });

        customView.findViewById(R.id.done_btn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CheckBox cb;
                LinearLayout ll = findViewById(R.id.checkbox_layout);

                cb = new CheckBox(context);
                cb.setText(((TextView) customView.findViewById(R.id.tag_name_field)).getText().toString());
                cb.setChecked(true);
                ll.addView(cb);

                popup.dismiss();
            }
        });
    }

}
