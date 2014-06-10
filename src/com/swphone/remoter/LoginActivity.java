package com.swphone.remoter;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import com.swphone.remoter.auth.AuthManager;
import com.swphone.remoter.auth.google.GoogleAuthManager;

/**
 * Created by yuri on 07/06/14.
 */
public class LoginActivity extends Activity {

    public AuthManager authManager;

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.login);
        RTApplication app = (RTApplication) getApplication();
        this.authManager = app.authManager;


    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        authManager.onActivityResult(this, requestCode,  resultCode,  data);
    }

    public void loginWithGoogle(View view){
        this.authManager.performLogin(this);
    }
}
