package com.gonza.inventariar.inventariar.UI;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

import com.gonza.inventariar.inventariar.R;

public class main extends Activity {
    @Bind(R.id.location_TextView) TextView location_textView;
    @Bind(R.id.add_button) Button addButton;
    @Bind(R.id.finish_button) Button finishButton;

    private static final int SCANN_LOCATION = 0;
    private static final int SCANN_ITEM = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        // Get the location code
        Intent mainActivity = getIntent();
        String location_code = mainActivity.getStringExtra("Location");

        location_textView.setText("Locación: "+location_code);
    }

    @OnClick(R.id.add_button) void scannItem(){
        Intent waitScannActivity = new Intent(main.this,WaitScannActivity.class);
        waitScannActivity.putExtra("scannType",SCANN_ITEM);
        startActivity(waitScannActivity);
    }

}
