package app;

import android.app.Application;
import android.content.Intent;

import go.deyu.util.AppContextSingleton;
import go.deyu.util.LOG;
import service.ScreenLockService;

/**
 * Created by huangeyu on 15/5/21.
 */
public class App extends Application {

    private final String TAG =getClass().getSimpleName();

//    in app create init some util tool and register MessageReceiver
    @Override
    public void onCreate() {
        super.onCreate();
        LOG.LOGTAG = getString(getApplicationInfo().labelRes);
        LOG.d(TAG , "onCreate");
        AppContextSingleton.initialize(this);
        startService(new Intent(this, ScreenLockService.class));
    }

}
