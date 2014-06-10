package com.swphone.remoter;

import android.app.Application;
import com.swphone.remoter.auth.AuthManager;
import com.swphone.remoter.auth.google.GoogleAuthManager;

/**
 * Created by yuri on 09/06/14.
 */
public class RTApplication extends Application {

    public AuthManager authManager;
    @Override
    public void onCreate() {
        super.onCreate();

        this.authManager = new GoogleAuthManager(this);

    }
}
