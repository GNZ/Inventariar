package com.gonza.inventariar.inventariar.UI;

import android.support.v7.app.ActionBar;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.ShareActionProvider;

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

    private static final String TAG = "InitialActivity";

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
        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(false);


    }

    @OnClick(R.id.iniciarRetomar_button) void iniciarRetomar(){
        Intent waitScannActivity = new Intent(InitialActivity.this, WaitScannActivity.class);
        waitScannActivity.putExtra("scanType", SCAN_LOCATION);
        startActivity(waitScannActivity);
    }
}
