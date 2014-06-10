package com.swphone.remoter;

import android.accounts.AccountManager;
import android.app.Activity;
import android.app.Application;
import android.content.Intent;
import android.os.Bundle;
import com.swphone.remoter.auth.AuthManager;
import com.swphone.remoter.auth.AuthPreferences;
import com.swphone.remoter.auth.CachedResponseCallback;
import com.swphone.remoter.auth.google.GoogleAuthPreferences;

/**
 * Created by yuri on 09/06/14.
 */
public class Main extends Activity {

    AccountManager accountManager;
    AuthPreferences preferences;
    
    AuthManager authManager;
    
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.loading);
        RTApplication app  = (RTApplication) this.getApplication();
        this.accountManager = AccountManager.get(app);
        this.preferences = new GoogleAuthPreferences(app,"google");
        this.authManager = app.authManager;

        authManager.getCachedLogin(getResponseCallback());

    }

    public CachedResponseCallback getResponseCallback(){
        CachedResponseCallback callback =  new CachedResponseCallback() {
            @Override
            public void run(int resultCode) {
                Intent intent;
                RTApplication app = (RTApplication) getApplication();
                if (resultCode == AuthManager.RESULT_OK){
                    intent = new Intent(app,RemoterActivity.class);
                }else {
                    intent = new Intent(app,LoginActivity.class);
                }

                startActivity(intent);
            }
        };

        return callback;
    }



}
