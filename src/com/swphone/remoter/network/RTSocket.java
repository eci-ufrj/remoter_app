package com.swphone.remoter.network;

import android.app.Activity;
import android.util.Log;
import android.widget.Toast;
import com.koushikdutta.async.http.AsyncHttpClient;
import com.koushikdutta.async.http.socketio.ConnectCallback;
import com.koushikdutta.async.http.socketio.SocketIOClient;
import com.koushikdutta.async.http.socketio.SocketIORequest;
import com.swphone.remoter.R;
import org.json.JSONArray;
import org.json.JSONException;

/**
 * Created by yuri on 04/06/14.
 */
public class RTSocket {
    public SocketIOClient socket;
    public Activity activity;

    public RTSocket(Activity pActivity){
        this.activity = pActivity;
    }

    public void start(){
        String socketAddress = this.activity.getString(R.string.socket_address);
        SocketIORequest request = new SocketIORequest(socketAddress,"/mobile");
        SocketIOClient.connect(AsyncHttpClient.getDefaultInstance(), request, new ConnectCallback() {
            @Override
            public void onConnectCompleted(Exception ex, SocketIOClient client) {
                if (ex != null){
                    ex.printStackTrace();
                    Log.d("Connect", "Failed");
                    activity.runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            Toast.makeText(activity,"Peida", Toast.LENGTH_LONG);

                        }
                    });


                    return;
                }
                socket = client;
                Log.d("Connect", "Connected");
            }

        });
    }

    public void emit(String event, String args) {
        RunEmiter emiter = new RunEmiter(this.activity,this.socket,event,args);
        Thread emitThread = new Thread(emiter);
        emitThread.start();
        //emiter.run();



    }
}
