package com.gonza.inventariar.inventariar.UI;

import android.app.Activity;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

import com.gonza.inventariar.inventariar.Elements.Item;
import com.gonza.inventariar.inventariar.Elements.Localization;
import com.gonza.inventariar.inventariar.R;

import java.util.List;

public class main extends Activity {
    private static final String TAG = "main";
    @Bind(R.id.location_TextView) TextView location_textView;
    @Bind(R.id.finish_button) Button finishButton;
    private tableFragment table;
    private String location_code;
    private Localization currentLocalization;

    private static final int SCANN_LOCATION = 0;
    private static final int SCANN_ITEM = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        // Create tableFragment
        table = new tableFragment();
        // Get location's barcode
        location_code = getIntent().getStringExtra("Location");
        // Send the location code to the fragment

        List<Localization> searchLocation = Localization.findWithQuery(Localization.class,
                "Select * from localization where inventory_code = ?", location_code);
        if (searchLocation.isEmpty()) {
            currentLocalization = new Localization(location_code);
            currentLocalization.save();
            Toast.makeText(this,getResources().getString(R.string.new_location_add)
                    +": "+location_code,Toast.LENGTH_LONG).show();
        }else {
            currentLocalization = searchLocation.get(0);
            refreshTable(currentLocalization);
        }

        location_textView.setText("Locación: "+location_code);
    }

    private void refreshTable(Localization loc){
        List<Item> items = Item.find(Item.class, "localization = ?",loc.getId()+"");
        table = new tableFragment();
        table.setItemList(items);
        for (Item e: items){
            Log.d(TAG,e.getName());
        }
        FragmentTransaction ft = getFragmentManager().beginTransaction();
        ft.replace(R.id.fragment_container,table);
        ft.commit();
    }

    @OnClick(R.id.add_button) void scannItem(){
        Intent waitScannActivity = new Intent(main.this,WaitScannActivity.class);
        waitScannActivity.putExtra("scannType",SCANN_ITEM);
        waitScannActivity.putExtra("Localization",location_code);
        startActivity(waitScannActivity);
    }

    @OnClick(R.id.finish_button) void finishButton(){

    }

    @OnClick(R.id.to_share_button) void shareButtonAction() {
        table.setLocalization(location_code);
        table.exportToCSV();
        //Toast.makeText(this,"Exportado a CSV",Toast.LENGTH_LONG).show();
        Intent sharedActivity = new Intent(main.this,SharedActivity.class);
        sharedActivity.putExtra("Localization",location_code);
        startActivity(sharedActivity);
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        refreshTable(currentLocalization);
    }
}
