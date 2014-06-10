package com.swphone.remoter.auth;

import android.app.Activity;
import android.content.Intent;

/**
 * Created by yuri on 09/06/14.
 */
public interface AuthManagerService {

    public static final int RESULT_OK = 0;
    public static final int RESULT_NOT_OK =  1;
    public static final int RESULT_ERROR = 2;
    public static final int LOGIN_REQUEST = 1337;
    public static final int AUTH_TOKEN_REQUEST = 1338;

    public void getCachedLogin(CachedResponseCallback callback);
    public void performLogin(Activity activity);
    public void performLogout();
    public void invalidateCache();
    public void onActivityResult(Activity activity , int requestCode, int resultCode, Intent data);
    void loginCallback();
}
