package child.ryl.child.broadcast;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.content.LocalBroadcastManager;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import child.ryl.child.R;

/**
 *
 */
public class BroadcastActivity extends Activity implements View.OnClickListener {
    IntentFilter filter;
    NetWorkChangeReceiver receiver;
    Button broadcast_btn;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_broadcast);
//        init();
        localBroadcast();//本地广播，外部无法接收
    }

    private void init() {
        broadcast_btn = (Button) findViewById(R.id.broadcast_btn);
        broadcast_btn.setOnClickListener(this);
        receiver = new NetWorkChangeReceiver();
        /**
         * 系统内部action，是否有网络
         */
        filter = new IntentFilter("android.net.conn.CONNECTIVITY_CHANGE");
        registerReceiver(receiver, filter);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
//        unregisterReceiver(receiver);
        localManager.unregisterReceiver(receiver);
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.broadcast_btn:
//                Intent intent = new Intent("android.intent.action.MY_BROADCAST");
////                sendBroadcast(intent);//标准
//                sendOrderedBroadcast(intent, null);//有序

                Intent intent = new Intent("child.ryl.child.broadcast.LOCAL_BROADCAST");
                intent.putExtra("local", "local_broadcast");
                localManager.sendBroadcast(intent);
                break;
        }
    }

    class NetWorkChangeReceiver extends BroadcastReceiver {

        @Override
        public void onReceive(Context context, Intent intent) {
            if (null != intent.getStringExtra("local")) {
                Toast.makeText(context, "" + intent.getStringExtra("local"), Toast.LENGTH_LONG).show();
                return;
            }
            /**
             * 是否有网络
             */
            ConnectivityManager connectivityManager = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
            NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();
            if (networkInfo != null && networkInfo.isAvailable()) {
                Toast.makeText(context, "net is available", Toast.LENGTH_LONG).show();
            } else {
                Toast.makeText(context, "net is not available", Toast.LENGTH_LONG).show();
            }
//            Utils.isConnected(context);
        }
    }

    LocalBroadcastManager localManager;//本地广播，外部无法接收
    IntentFilter intentFilter;

    /**
     * //本地广播，外部无法接收
     */
    private void localBroadcast() {
        broadcast_btn = (Button) findViewById(R.id.broadcast_btn);
        broadcast_btn.setOnClickListener(this);
        localManager = LocalBroadcastManager.getInstance(this);
        intentFilter = new IntentFilter();
        intentFilter.addAction("child.ryl.child.broadcast.LOCAL_BROADCAST");
        receiver = new NetWorkChangeReceiver();
        localManager.registerReceiver(receiver, intentFilter);
//        sendBroadcast();
//        sendOrderedBroadcast();
    }
}
