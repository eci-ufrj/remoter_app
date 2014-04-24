package com.swphone.remoter;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import com.koushikdutta.async.future.Future;
import com.koushikdutta.async.http.AsyncHttpClient;
import com.koushikdutta.async.http.socketio.ConnectCallback;
import com.koushikdutta.async.http.socketio.SocketIOClient;

public class Main extends Activity {
    /**
     * Called when the activity is first created.
     */

    public Future<SocketIOClient> app_socket;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);

        app_socket =
                SocketIOClient.connect(AsyncHttpClient.getDefaultInstance(), "http://localhost:5000/mobile", new ConnectCallback() {
                    @Override
                    public void onConnectCompleted(Exception ex, SocketIOClient client) {
                        if (ex != null){
                            ex.printStackTrace();
                            return;
                        }

                        Log.d("Connect", "Connected");
                    }

                });
    }

    // Method that plays the video on the browser when the button 'Play' is touched.
    public void startAction(View view) {
        Log.d("Play", "Playing");
    }

    public void stopAction(View view){
        Log.d("Stop", "Stopped");
    }
}
