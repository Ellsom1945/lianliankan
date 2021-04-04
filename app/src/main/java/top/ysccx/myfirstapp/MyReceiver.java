package top.ysccx.myfirstapp;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.widget.Toast;

public class MyReceiver extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {
        String msg = intent.getStringExtra("name")+"，恭喜你！你只用了"+intent.getStringExtra("time")+"s";
        Toast.makeText(context,msg,Toast.LENGTH_LONG).show();
    }
}
