package com.explore.archana.nanoproject.ui.activity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.explore.archana.nanoproject.R;


public class MainActivity extends Activity implements View.OnClickListener {

    //Buttons for apps to launch
    private Button spotifySteamerButton, scoresAppButton, libraryAppButton, buildItBiggerButton, baconReaderButton, capstoneButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        spotifySteamerButton = (Button) findViewById(R.id.button);
        scoresAppButton = (Button) findViewById(R.id.button2);
        libraryAppButton = (Button) findViewById(R.id.button3);
        buildItBiggerButton = (Button) findViewById(R.id.button4);
        baconReaderButton = (Button) findViewById(R.id.button5);
        capstoneButton = (Button) findViewById(R.id.button6);

        spotifySteamerButton.setOnClickListener(this);
        scoresAppButton.setOnClickListener(this);
        libraryAppButton.setOnClickListener(this);
        buildItBiggerButton.setOnClickListener(this);
        baconReaderButton.setOnClickListener(this);
        capstoneButton.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.button:
                displayToast(this.getString(R.string.open_sportify));
                ConnectivityManager connMgr = (ConnectivityManager)
                        getSystemService(Context.CONNECTIVITY_SERVICE);
                NetworkInfo networkInfo = connMgr.getActiveNetworkInfo();
                if (networkInfo != null && networkInfo.isConnected()) {
                    // fetch data
                    Intent movieListActivity = new Intent(this, MovieListActivity.class);
                    startActivity(movieListActivity);
                } else {
                    // display error
                    displayToast("Check Internet Connection");
                }

                break;
            case R.id.button2:
                displayToast(this.getString(R.string.open_scores));
                break;
            case R.id.button3:
                displayToast(this.getString(R.string.open_library));
                break;
            case R.id.button4:
                displayToast(this.getString(R.string.open_bigger));
                break;
            case R.id.button5:
                displayToast(this.getString(R.string.open_bacon));
                break;
            case R.id.button6:
                displayToast(this.getString(R.string.open_capstone));
                break;
        }

    }

    Toast toast;

    private void displayToast(String text) {
        if (toast != null) {
            toast.cancel();
        }
        toast.makeText(this, text, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Log.d("OnBackPressed","pressed");
    }
}
