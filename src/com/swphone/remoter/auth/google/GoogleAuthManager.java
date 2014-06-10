package com.swphone.remoter.auth.google;

import android.accounts.*;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.StrictMode;
import android.util.Log;
import com.swphone.remoter.RTApplication;
import com.swphone.remoter.RemoterActivity;
import com.swphone.remoter.auth.AuthManager;
import com.swphone.remoter.auth.AuthManagerService;
import com.swphone.remoter.auth.AuthPreferences;
import com.swphone.remoter.auth.CachedResponseCallback;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

/**
 * Created by yuri on 09/06/14.
 */
public class GoogleAuthManager extends AuthManager {


    public final String OAUTH_SCOPE = "oauth2: openid email";

    private GoogleAuthPreferences authPreferences;
    private AccountManager accountManager;

    public GoogleAuthManager(Context context) {

        super(context);
        this.authPreferences = new GoogleAuthPreferences(this.context,"google");
        this.accountManager = AccountManager.get(this.context);
    }

    @Override
    public void getCachedLogin(CachedResponseCallback callback) {

        String token = this.authPreferences.getToken();
        String username = this.authPreferences.getUsername();

        if (username != null && token!= null) {

            int resultCode = validateToken(token);
            callback.run(resultCode);

        }else {
            int resultCode = RESULT_NOT_OK;
            callback.run(resultCode);
        }

    }

    @Override
    public void performLogin(Activity activity) {
        Intent intent = AccountManager.newChooseAccountIntent(null, null,
                new String[] { "com.google" }, false, null, null, null, null);
        activity.startActivityForResult(intent,LOGIN_REQUEST);

    }

    @Override
    public void performLogout() {

    }

    @Override
    public void invalidateCache() {
        accountManager.invalidateAuthToken("com.google",authPreferences.getToken());
        authPreferences.setToken(null);
    }

    @Override
    public void onActivityResult(Activity activity,int requestCode, int resultCode, Intent data) {
        if (requestCode == LOGIN_REQUEST && resultCode == Activity.RESULT_OK){
            String accountName = data.getStringExtra(AccountManager.KEY_ACCOUNT_NAME);
            authPreferences.setUsername(accountName);
            invalidateCache();
            getAuthToken(activity);


        }else if (requestCode == AUTH_TOKEN_REQUEST && resultCode == Activity.RESULT_OK){
            getAuthToken(activity);
        }
    }

    @Override
    public void loginCallback() {

    }


    private int validateToken(String token){
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);

        int codeForToken = getTokenInfoFromGoogleApi(token);
        switch (codeForToken){
            case 200:
                return RESULT_OK;
            case 401:
                return RESULT_NOT_OK;
            default:
                return RESULT_ERROR;

        }


    }

    private int getTokenInfoFromGoogleApi(String token){
        URL url = null;
        try {
            url = new URL("https://www.googleapis.com/oauth2/v1/userinfo?access_token="
                    + token);
        } catch (MalformedURLException e) {
            return 0;
        }
        HttpURLConnection conn = null;
        try {
            conn = (HttpURLConnection) url.openConnection();
        } catch (IOException e) {
            return 0;
        }
        int resultCode = 0;
        try {
           resultCode = conn.getResponseCode();
        } catch (IOException e) {
            return 0;
        }
        return resultCode;
    }
    public static InputStream get(String url) {
        InputStream content = null;
        try {
            HttpClient httpclient = new DefaultHttpClient();
            HttpResponse response = httpclient.execute(new HttpGet(url));
            content = response.getEntity().getContent();
        } catch (Exception e) {
            Log.d("[GET REQUEST]", "Network exception");
        }
        return content;
    }

    private void getAuthToken(Activity activity){
        Account userAccount = null;
        String user = authPreferences.getUsername();
        for (Account account : accountManager.getAccountsByType("com.google")) {
            if (account.name.equals(user)) {
                userAccount = account;

                break;
            }
        }
        if (userAccount != null) {
            accountManager.getAuthToken(userAccount, OAUTH_SCOPE, null, activity, new OnTokenAcquired(activity), null);
        }

    }

    private class OnTokenAcquired implements AccountManagerCallback<Bundle>{
        Activity activity;

        OnTokenAcquired(Activity pActivity){
            this.activity=pActivity;
        }

        @Override
        public void run(AccountManagerFuture<Bundle> result) {
            Bundle bundle = null;
            try{
                bundle =result.getResult();
            } catch (AuthenticatorException e) {
                e.printStackTrace();
            } catch (OperationCanceledException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
            Intent intent = (Intent) bundle.get(AccountManager.KEY_INTENT);

            if (intent != null) {
                activity.startActivityForResult(intent,AUTH_TOKEN_REQUEST);
            }else{
                String token = bundle.getString(AccountManager.KEY_AUTHTOKEN);
                String name = bundle.getString(AccountManager.KEY_ACCOUNT_NAME);
                authPreferences.setToken(token);
                authPreferences.setUsername(name);
                intent = new Intent(activity, RemoterActivity.class);
                activity.startActivity(intent);

            }


        }
    }
}
