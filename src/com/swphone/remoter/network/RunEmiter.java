package com.swphone.remoter.network;

import android.app.Activity;
import android.widget.Toast;
import com.koushikdutta.async.http.socketio.SocketIOClient;
import org.json.JSONArray;
import org.json.JSONException;

/**
 * Created by yuri on 04/06/14.
 */
public class RunEmiter implements Runnable {

    private Activity activity;
    private String event;
    private String args;
    private SocketIOClient socket;

    public RunEmiter(Activity pActivity,SocketIOClient pSocket,String pEvent, String pArgs){
        this.activity =pActivity;
        this.socket=pSocket;
        this.event=pEvent;
        this.args=pArgs;
    }

    @Override
    public void run() {
        String formattedArgs = "["+args+"]";
        JSONArray jsonArgs = null;
        try {
            jsonArgs = new JSONArray(formattedArgs);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        activity.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                Toast.makeText(activity, "Peida", Toast.LENGTH_LONG);
            }
        });
        this.socket.emit(event,jsonArgs);
    }
}
