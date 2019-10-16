package com.phenamwema.topup;

import android.app.Activity;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.NotificationCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.TextureView;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.hover.sdk.api.Hover;
import com.hover.sdk.api.HoverParameters;

public class MainActivity extends AppCompatActivity {

    Button btn_1,btn_2,btn_3,btn_4,btn_5;
    TextView tvCancel, dialogMessage;

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
    protected void dataRequest(int bundle_option){
        Intent intent = new HoverParameters.Builder(this)
                .request("eedfb940").style(R.style.AppTheme_NoActionBar)
                .extra("bundle", String.valueOf(bundle_option))
                .buildIntent();

        startActivityForResult(intent,0);
    }

    public void popUpNotification(){
        NotificationManager manager = (NotificationManager) this.getSystemService(Context.NOTIFICATION_SERVICE);
        String channel_id = "ID1";
        CharSequence name = "PopUp channel";
        String descriptionText = "pop-up channel description";
        if(android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O){//api >=8.0 need to create a notification channel

            // Create the NotificationChannel
            int importance = NotificationManager.IMPORTANCE_HIGH;
            NotificationChannel channel = new NotificationChannel(channel_id, name, importance);
            channel.setDescription(descriptionText);
            channel.setLightColor(Color.RED);
            channel.enableVibration(true);
            channel.setShowBadge(false);
            manager.createNotificationChannel(channel);//create notification channel
        }

        Intent intent = new Intent(this,MainActivity.class);
        //PendingIntent pendingIntent = PendingIntent.getActivity(this,123,intent,
          //      PendingIntent.FLAG_UPDATE_CURRENT);


        //build notification
        NotificationCompat.Builder builder = new NotificationCompat.Builder(MainActivity.this,channel_id)
                .setSmallIcon(R.mipmap.ic_launcher)
                .setContentTitle("New notification from PopUp")
                .setContentText("It worked!")
                .setPriority(NotificationManager.IMPORTANCE_HIGH)
                .setAutoCancel(true);

        Notification notification = builder.build();

        Intent notificationIntent = new Intent(MainActivity.this,MainActivity.class);
        PendingIntent contentIntent = PendingIntent.getActivity(MainActivity.this,123,notificationIntent,
                PendingIntent.FLAG_UPDATE_CURRENT);

        builder.setContentIntent(contentIntent);
        manager.notify(0,notification);//show pop-up notification
    }

    protected void popUpWindow(){

        LayoutInflater layoutInflater = LayoutInflater.from(this);
        final View dialog_view = layoutInflater.inflate(R.layout.custom_dialog,null);

        tvCancel = (TextView) dialog_view.findViewById(R.id.tvCancel);
        dialogMessage = (TextView) dialog_view.findViewById(R.id.tvDialogMessage);
        dialogMessage.setText(R.string.buy_airtime);

        //display promo dialog
        final AlertDialog alertDialog = new AlertDialog.Builder(this)
                .setView(dialog_view)
                .setCancelable(false)
                .show();

        //cancel promo pop-up
        tvCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                alertDialog.dismiss();
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        //super.onActivityResult(requestCode, resultCode, data);
        if(requestCode==0 && resultCode==Activity.RESULT_OK){
            String[] sessionText = data.getStringArrayExtra("ussd_messages");
            Toast.makeText(this,""+sessionText,Toast.LENGTH_LONG).show();
            popUpWindow();
        } else if(requestCode==0 && requestCode==Activity.RESULT_CANCELED){
            Toast.makeText(this,"Error: "+data.getStringExtra("error"),Toast.LENGTH_LONG).show();
        }
    }
}
