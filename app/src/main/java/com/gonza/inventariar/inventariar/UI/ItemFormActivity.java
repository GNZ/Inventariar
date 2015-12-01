package com.gonza.inventariar.inventariar.UI;

import android.app.Activity;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.os.Bundle;
import android.widget.Button;
import android.widget.FrameLayout;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

import com.gonza.inventariar.inventariar.R;


public class ItemFormActivity extends Activity {

    @Bind(R.id.add_button) Button addButton;
    @Bind(R.id.fragment_container) FrameLayout fragmentContainer;
    private ItemFormFragment itemFormFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_item_form);
        ButterKnife.bind(this);



        FragmentTransaction ft = getFragmentManager().beginTransaction();

        itemFormFragment = new ItemFormFragment();

        ft.add(R.id.fragment_container, itemFormFragment);
        ft.commit();

    }

    @OnClick(R.id.add_button) void addItem() {
        //TODO add item to the database
        //TODO somehow refresh the table that show items and show a toast with the added item
        finish();
    }
}
