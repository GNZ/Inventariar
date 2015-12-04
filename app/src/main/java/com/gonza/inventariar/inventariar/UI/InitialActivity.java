package com.gonza.inventariar.inventariar.UI;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuInflater;
import android.widget.Button;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

import com.gonza.inventariar.inventariar.R;
import com.google.android.gms.common.api.GoogleApiClient;

/**
 * An example full-screen activity that shows and hides the system UI (i.e.
 * status bar and navigation/system bar) with user interaction.
 */
public class InitialActivity extends AppCompatActivity {


    /**
     * Some older devices needs a small delay between UI widget updates
     * and a change of the status and navigation bar.
     */
    private final Handler mHideHandler = new Handler();
    @Bind(R.id.iniciarRetomar_button) Button iniciarRetomarButton;
    private static final int SCAN_LOCATION = 0;
    private static final int SCAN_ITEM = 1;
    /**
     * ATTENTION: This was auto-generated to implement the App Indexing API.
     * See https://g.co/AppIndexing/AndroidStudio for more information.
     */
    private GoogleApiClient client;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_initial);
        ButterKnife.bind(this);

    }

    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.activity_initial_actions, menu);

        return super.onCreateOptionsMenu(menu);
    }

    @OnClick(R.id.iniciarRetomar_button) void iniciarRetomar(){
        Intent waitScannActivity = new Intent(InitialActivity.this, WaitScannActivity.class);
        waitScannActivity.putExtra("scannType", SCAN_LOCATION);
        startActivity(waitScannActivity);
    }


}
