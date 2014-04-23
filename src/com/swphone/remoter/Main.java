package com.swphone.remoter;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import com.koushikdutta.async.http.AsyncHttpClient;
import com.koushikdutta.async.http.socketio.ConnectCallback;
import com.koushikdutta.async.http.socketio.SocketIOClient;

public class Main extends Activity {
    /**
     * Called when the activity is first created.
     */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
    }

    // Method that plays the video on the browser when the button 'Play' is touched.
    public void playVideo(View view) {
        SocketIOClient.connect(AsyncHttpClient.getDefaultInstance(), "http://www.google.com", new ConnectCallback() {

            @Override
            public void onConnectCompleted(Exception ex, SocketIOClient client) {
                if (ex != null){
                    ex.printStackTrace();
                    return;
                }

                System.out.println("Connected");
            }

        });
    }
}
