package com.explore.archana.nanoproject;

import android.app.Activity;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;


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
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.button:
                displayToast("Button to open Sportify Steamer App");
                break;
            case R.id.button2:
                displayToast("Button to open Scores App");
                break;
            case R.id.button3:
                displayToast("Button to open Library App");
                break;
            case R.id.button4:
                displayToast("Button to open Build It Bigger App");
                break;
            case R.id.button5:
               displayToast("Button to open Bacon Reader App");
                break;
            case R.id.button6:
                displayToast("Button to open Capstone App");
                break;
        }

    }

    private void displayToast(String text) {
        Toast toast = null;
        if (toast != null)
            toast.cancel();
        else
            toast.makeText(this, text, Toast.LENGTH_SHORT).show();
    }

    private void cancleToast(String cancle) {
        Toast.makeText(this, cancle, Toast.LENGTH_SHORT).cancel();
    }

}
