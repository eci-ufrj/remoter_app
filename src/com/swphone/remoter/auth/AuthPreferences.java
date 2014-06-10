package com.swphone.remoter.auth;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;

/**
 * Created by yuri on 09/06/14.
 */
public abstract class AuthPreferences implements AuthPreferencesService{
    public Context context;
    public SharedPreferences preferences;
    public AuthPreferences(Context pContext,String tag){
        this.context = pContext;

    }
}
