package com.phenamwema.topup;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.widget.Toast;

import java.util.HashMap;

public class Receiver extends BroadcastReceiver {

    public Receiver(){}

    @Override
    public void onReceive(Context context, Intent intent) {
        String ussd_message = intent.getStringExtra("session_messages");
        String response = null;
        if(intent.hasExtra("transaction_extras")){
            HashMap<String, String> t_extras = (HashMap<String, String>)intent.getSerializableExtra("transaction_extras");
            if(t_extras.containsKey("bundle"))
                response = t_extras.get("session_messages");
            Toast.makeText(context,""+response,Toast.LENGTH_LONG).show();
        }
        /*Toast.makeText(context, "Message: "+ response,Toast.LENGTH_LONG).show();
        intent = new Intent(intent);
        intent.setClass(context, MainActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        context.startActivity(intent);*/

    }
}
