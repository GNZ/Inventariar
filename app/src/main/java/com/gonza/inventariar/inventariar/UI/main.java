package com.gonza.inventariar.inventariar.UI;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.gonza.inventariar.inventariar.R;

public class main extends Activity {
    private TextView location_textView;
    private Button addButton;
    private Button finishButton;

    private static final int SCANN_LOCATION = 0;
    private static final int SCANN_ITEM = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        // Get the location code
        Intent mainActivity = getIntent();
        String location_code = mainActivity.getStringExtra("Location");
        // Init elements
        location_textView = (TextView) findViewById(R.id.location_TextView);
        addButton = (Button) findViewById(R.id.add_button);
        finishButton = (Button) findViewById(R.id.finish_button);

        addButton.setOnClickListener(new addButtonListener());

        location_textView.setText("Locación: "+location_code);
    }

    private class addButtonListener implements View.OnClickListener {

        @Override
        public void onClick(View v) {
            Intent waitScannActivity = new Intent(main.this,WaitScannActivity.class);
            waitScannActivity.putExtra("scannType",SCANN_ITEM);
            startActivity(waitScannActivity);
        }
    }
}
