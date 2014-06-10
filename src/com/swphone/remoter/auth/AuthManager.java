package com.swphone.remoter.auth;

import android.content.Context;

/**
 * Created by yuri on 09/06/14.
 */
public abstract class AuthManager implements AuthManagerService{
    public Context context;

    protected AuthManager(Context context) {
        this.context = context;
    }
}
