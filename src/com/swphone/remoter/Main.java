package com.swphone.remoter;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import com.koushikdutta.async.future.Future;
import com.koushikdutta.async.future.FutureCallback;
import com.koushikdutta.async.http.AsyncHttpClient;
import com.koushikdutta.async.http.socketio.ConnectCallback;
import com.koushikdutta.async.http.socketio.SocketIOClient;
import com.koushikdutta.async.http.socketio.SocketIORequest;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class Main extends Activity {
    /**
     * Called when the activity is first created.
     */

    public SocketIOClient app_socket;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        String socketAddress = getString(R.string.socket_address);
        Log.d("SOCKET","Address: "+socketAddress );
        SocketIORequest request = new SocketIORequest(socketAddress,"/mobile");
        SocketIOClient.connect(AsyncHttpClient.getDefaultInstance(), request, new ConnectCallback() {
                    @Override
                    public void onConnectCompleted(Exception ex, SocketIOClient client) {
                        if (ex != null){
                            ex.printStackTrace();
                            return;
                        }
                        app_socket = client;
                        Log.d("Connect", "Connected");
                    }

                });
    }

    // Method that plays the video on the browser when the button 'Play' is touched.
    public void startAction(View view) throws JSONException {
        app_socket.emit("interaction",new JSONArray("[play_button]"));
        Log.d("Play", "Playing");
    }

    public void stopAction(View view) throws JSONException {
        app_socket.emit("interaction",new JSONArray("[stop_button]"));
        Log.d("Stop", "Stopped");
    }
}
