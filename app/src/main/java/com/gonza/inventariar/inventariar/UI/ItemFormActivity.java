package com.gonza.inventariar.inventariar.UI;


import android.app.FragmentTransaction;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.Toast;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

import com.gonza.inventariar.inventariar.Elements.Item;
import com.gonza.inventariar.inventariar.Elements.Localization;
import com.gonza.inventariar.inventariar.R;

import java.util.List;


public class ItemFormActivity extends AppCompatActivity {

    @Bind(R.id.add_button) Button addButton;
    @Bind(R.id.fragment_container) FrameLayout fragmentContainer;
    private ItemFormFragment itemFormFragment;
    private String itemBarcode;
    private Localization localization;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_item_form);
        ButterKnife.bind(this);

        //Action bar
        ActionBar ab = getSupportActionBar();

        //Get item's barcode
        itemBarcode = getIntent().getStringExtra("Item");
        //Get location
        String loc = getIntent().getStringExtra("Localization");
        // Create ItemFormFragment and pass item's barcode if the user pass it
        itemFormFragment = new ItemFormFragment();
        itemFormFragment.setLocalization(loc);

        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);

        if (!itemBarcode.equals("N/A")){
            itemFormFragment.setItemBarcode(itemBarcode);
        }

        //Search in the db for the localization
        List<Localization> searchLocation = Localization.findWithQuery(Localization.class,
                "Select * from localization where inventory_code = ?", loc);
        localization = searchLocation.get(0);

        FragmentTransaction ft = getFragmentManager().beginTransaction();


        ft.add(R.id.fragment_container, itemFormFragment);
        ft.commit();

    }

    @OnClick(R.id.add_button) void addItem() {
        Item newItem = new Item(localization);
        if (itemFormFragment.checkAndGetItem(newItem)) {
            //TODO check if not exist and save it
            newItem.save();
            Toast.makeText(this,"Se creo el item "+newItem.getName(),Toast.LENGTH_SHORT).show();
            finish();
            //TODO somehow refresh the table that show items and show a toast with the added item
        }
    }
}
