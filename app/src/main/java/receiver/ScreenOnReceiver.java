package receiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;

import go.deyu.util.LOG;
import service.ScreenLockService;

public class ScreenOnReceiver extends BroadcastReceiver {
    public ScreenOnReceiver() {
    }

    private final String TAG = getClass().getSimpleName();


    public IntentFilter getIntentFilter(){
        final IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction(Intent.ACTION_SCREEN_ON);
        return intentFilter;
    }

    @Override
    public void onReceive(Context context, Intent intent) {
        LOG.d(TAG, "onReceive : " + intent.getAction());
        if(intent.getAction().equals(Intent.ACTION_SCREEN_ON)){
            Intent i = new Intent(context , ScreenLockService.class);
            i.setAction(ScreenLockService.ACTION_ON_SCREEN_ON);
            context.startService(i);
        }
    }
}
