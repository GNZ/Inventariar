package com.gonza.inventariar.inventariar.UI;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.widget.Toast;

import com.gonza.inventariar.inventariar.R;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

import butterknife.ButterKnife;
import butterknife.OnClick;


public class SharedActivity extends Activity {

    private static final String TAG = "SharedActivity";
    private static final int SHARE_RESULT = 1;
    private String location;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shared);
        ButterKnife.bind(this);
        location = getIntent().getStringExtra("Localization");

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
            Toast.makeText(this, getResources().getString(R.string.shared_success), Toast.LENGTH_LONG).show();
            finish();
    }

    @OnClick(R.id.share_button) void share() {
        String folder = getResources().getString(R.string.folder);
        String folderPath = Environment.getExternalStorageDirectory()+File.separator +folder+File.separator+location;
        String zipFile = folderPath+".zip";
        Log.d(TAG,"share button press");
        zipFolder(folderPath,zipFile);
        Toast.makeText(this,"Zip file ready in pat "+folderPath,Toast.LENGTH_LONG).show();
        Intent shareIntent = new Intent(Intent.ACTION_SEND);
        shareIntent.setType("text/plain");
        shareIntent.putExtra(Intent.EXTRA_STREAM, Uri.fromFile(new File(zipFile)));
        startActivity(shareIntent);
    }

    @OnClick(R.id.exit_button) void finishAction() {
        finish();
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
