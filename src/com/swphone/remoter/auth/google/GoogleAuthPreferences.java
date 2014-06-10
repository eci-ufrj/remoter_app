package com.swphone.remoter.auth.google;

import android.content.Context;
import android.content.SharedPreferences;
import android.text.Editable;
import android.util.Log;
import com.swphone.remoter.auth.AuthPreferences;

/**
 * Created by yuri on 09/06/14.
 */
public class GoogleAuthPreferences extends AuthPreferences{

    public GoogleAuthPreferences(Context pContext,String preferences_tag){
        super(pContext, preferences_tag);
        this.preferences = context.getSharedPreferences(preferences_tag,Context.MODE_PRIVATE);
    }

    public String getToken(){
        return this.preferences.getString("token",null);

    }
    public void setToken(String token){
        SharedPreferences.Editor editor = this.preferences.edit();
        editor.putString("token",token);
        editor.commit();
    }

    public String getUsername(){
        return  this.preferences.getString("name",null);
    }

    public void setUsername(String username){
        SharedPreferences.Editor editor = this.preferences.edit();
        editor.putString("name",username);
        editor.commit();
    }

}
