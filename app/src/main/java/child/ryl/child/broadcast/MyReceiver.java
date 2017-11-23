package child.ryl.child.broadcast;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.widget.Toast;

public class MyReceiver extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {
        Toast.makeText(context, "MyReceiver", Toast.LENGTH_LONG).show();
//        abortBroadcast();//停止
//        throw new UnsupportedOperationException("Not yet implemented");
    }
}
