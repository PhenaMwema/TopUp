package com.phenamwema.topup;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.hover.sdk.api.Hover;
import com.hover.sdk.api.HoverParameters;

public class MainActivity extends AppCompatActivity {

    Button btn_1,btn_2,btn_3,btn_4,btn_5;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        //initialise Hover SDK
        Hover.initialize(this);

        //initialise buttons
        btn_1 = (Button) findViewById(R.id.btn_action1);
        btn_2 = (Button) findViewById(R.id.btn_action2);
        btn_3 = (Button) findViewById(R.id.btn_action3);
        btn_4 = (Button) findViewById(R.id.btn_action4);
        btn_5 = (Button) findViewById(R.id.btn_action5);

        //start ussd session by any button click
        btn_1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dataRequest(1);//bundle option 1
            }
        });

        btn_2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dataRequest(2);//bundle option 2
            }
        });

        btn_3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dataRequest(3);//bundle option 3
            }
        });

        btn_4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dataRequest(4);//bundle option 4
            }
        });

        btn_5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dataRequest(5);//bundle option 5
            }
        });

    }

    //select bundle option and parse to Hover variable
    public void dataRequest(int bundle_option){
        Intent intent = new HoverParameters.Builder(this)
                .request("eedfb940").style(R.style.AppTheme_NoActionBar)
                .extra("bundle", String.valueOf(bundle_option))
                .buildIntent();

        startActivityForResult(intent,0);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        //super.onActivityResult(requestCode, resultCode, data);
        if(requestCode==0 && resultCode==Activity.RESULT_OK){
            String[] sessionText = data.getStringArrayExtra("response_message");
            Toast.makeText(this,""+sessionText,Toast.LENGTH_LONG).show();
        } else if(requestCode==0 && requestCode==Activity.RESULT_CANCELED){
            Toast.makeText(this,"Error: "+data.getStringExtra("error"),Toast.LENGTH_LONG).show();
        }
    }
}
