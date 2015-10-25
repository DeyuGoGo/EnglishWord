package service;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.view.WindowManager;

import go.deyu.englishword.ScreenLockView;
import go.deyu.englishword.SettingConfig;
import go.deyu.util.LOG;
import receiver.ScreenOnReceiver;

public class ScreenLockService extends Service {

    public static final String ACTION_ON_SCREEN_ON = "go.deyu.englishword.screen.on";

    private final int NOTIFICATION_FOREGROUND_ID = 0x87;

    private ScreenOnReceiver receiver ;

    private final String TAG = getClass().getSimpleName();


    public ScreenLockService() {
    }

    @Override
    public void onCreate() {
        super.onCreate();
        LOG.d(TAG, "onCreate");
        receiver = new ScreenOnReceiver();
        registerReceiver(receiver, receiver.getIntentFilter());

    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {

        if(intent == null || intent.getAction() == null) return START_STICKY;

        if(intent.getAction().equals(ACTION_ON_SCREEN_ON)){
            LOG.d(TAG,"ACTION_ON_SCREEN_ON");

            if(SettingConfig.getScreenLockStatus(this))
                showScreenLockView();

        }
        return START_STICKY;
    }




    /**
     * Called by the system to notify a Service that it is no longer used and is being removed.  The
     * service should clean up any resources it holds (threads, registered
     * receivers, etc) at this point.  Upon return, there will be no more calls
     * in to this Service object and it is effectively dead.  Do not call this method directly.
     */
    @Override
    public void onDestroy() {
        LOG.d(TAG, "onDestroy");
        super.onDestroy();
        if(receiver!=null)unregisterReceiver(receiver);
    }

    @Override
    public IBinder onBind(Intent intent) {
        // TODO: Return the communication channel to the service.
        throw new UnsupportedOperationException("Not yet implemented");
    }

    private void showScreenLockView(){
        ScreenLockView view = new ScreenLockView(this);
        WindowManager.LayoutParams params = new WindowManager.LayoutParams();
        params.width = WindowManager.LayoutParams.MATCH_PARENT;
        params.height = WindowManager.LayoutParams.MATCH_PARENT;
        params.type = WindowManager.LayoutParams.TYPE_PRIORITY_PHONE;
        params.flags |= WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE;
        params.flags |= WindowManager.LayoutParams.FLAG_FULLSCREEN;
        WindowManager wm = (WindowManager) getSystemService(WINDOW_SERVICE);
        wm.addView(view, params);
    }

}
