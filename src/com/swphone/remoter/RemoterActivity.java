package com.swphone.remoter;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import com.swphone.remoter.network.RTSocket;
import org.json.JSONException;

public class RemoterActivity extends Activity {
    /**
     * Called when the activity is first created.
     */

    public RTSocket socketManager;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.remote);

        this.socketManager = new RTSocket(this);
        this.socketManager.start();

    }



    public void onTouchEmitterButton(View view){

        String buttonTag = view.getTag().toString();
        socketManager.emit("interaction",buttonTag);
        Log.d("Interaction", buttonTag);

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
