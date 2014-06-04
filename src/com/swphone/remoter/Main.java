package com.swphone.remoter;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;
import com.koushikdutta.async.future.Future;
import com.koushikdutta.async.future.FutureCallback;
import com.koushikdutta.async.http.AsyncHttpClient;
import com.koushikdutta.async.http.socketio.ConnectCallback;
import com.koushikdutta.async.http.socketio.SocketIOClient;
import com.koushikdutta.async.http.socketio.SocketIORequest;
import com.swphone.remoter.network.RTSocket;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class Main extends Activity {
    /**
     * Called when the activity is first created.
     */

    public RTSocket socketManager;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);

        this.socketManager = new RTSocket(this);
        this.socketManager.start();

    }

    // Method that plays the video on the browser when the button 'Play' is touched.
    public void startAction(View view) throws JSONException {
        socketManager.emit("interaction","play_button");
        Log.d("Play", "Playing");

    }

    public void stopAction(View view) throws JSONException {
        socketManager.emit("interaction","stop_button");
        Log.d("Stop", "Stopped");
    }
}
