package com.gonza.inventariar.inventariar.UI;

import android.net.Uri;
import android.os.Environment;
import android.support.v4.view.MenuItemCompat;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.ShareActionProvider;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

import com.gonza.inventariar.inventariar.Elements.Item;
import com.gonza.inventariar.inventariar.Elements.Localization;
import com.gonza.inventariar.inventariar.R;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

public class main extends AppCompatActivity {

    private static final String TAG = "main";

    String folder;
    @Bind(R.id.location_TextView) TextView location_textView;
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
        // Action bar
        ActionBar ab = getSupportActionBar();
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

        folder = getResources().getString(R.string.folder);

        location_textView.setText("Locación: "+location_code);
    }

    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.activity_initial_actions, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle item selection
        switch (item.getItemId()) {
           case R.id.menu_item_share:
                setShareIntent();
                return true;
           default: return true;
        }
    }

    private void refreshTable(Localization loc){
        List<Item> items = Item.find(Item.class, "localization = ?",loc.getId()+"");
        table = new tableFragment();
        table.setItemList(items);
        FragmentTransaction ft = getFragmentManager().beginTransaction();
        ft.replace(R.id.fragment_container,table);
        ft.commit();
    }

    @OnClick(R.id.add_button) void scannItem(){
        Intent itemFormActivity = new Intent(main.this, ItemFormActivity.class);
        itemFormActivity.putExtra("Item", "N/A");
        itemFormActivity.putExtra("Localization",location_code);
        startActivity(itemFormActivity);
    }

    @OnClick(R.id.finish_button) void finishButton(){
        finish();
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        refreshTable(currentLocalization);
    }

    // Call to update the share intent
    private void setShareIntent() {
        table.setLocalization(location_code);
        // Create CSV
        table.exportToCSV();
        // Create the zip file
        String folderPath = Environment.getExternalStorageDirectory()+ File.separator +folder+File.separator+location_code;
        String zipFile = folderPath+".zip";
        Log.d(TAG,"share button press");
        zipFolder(folderPath,zipFile);
        Toast.makeText(this,"Zip file ready in pat "+folderPath,Toast.LENGTH_LONG).show();
        Intent shareIntent = new Intent(Intent.ACTION_SEND);
        shareIntent.setType("text/plain");
        shareIntent.putExtra(Intent.EXTRA_STREAM, Uri.fromFile(new File(zipFile)));

        startActivity(shareIntent);
    }

    private static void zipFolder(String inputFolderPath, String outZipPath) {
        try {
            FileOutputStream fos = new FileOutputStream(outZipPath);
            ZipOutputStream zos = new ZipOutputStream(fos);
            File srcFile = new File(inputFolderPath);
            File[] files = srcFile.listFiles();
            Log.d("", "Zip directory: " + srcFile.getName());
            for (int i = 0; i < files.length; i++) {
                Log.d("", "Adding file: " + files[i].getName());
                byte[] buffer = new byte[1024];
                FileInputStream fis = new FileInputStream(files[i]);
                zos.putNextEntry(new ZipEntry(files[i].getName()));
                int length;
                while ((length = fis.read(buffer)) > 0) {
                    zos.write(buffer, 0, length);
                }
                zos.closeEntry();
                fis.close();
            }
            zos.close();
        } catch (IOException ioe) {
            Log.d(TAG, ioe.getMessage());
        }
    }
}
