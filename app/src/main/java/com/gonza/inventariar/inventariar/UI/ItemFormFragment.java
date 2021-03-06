package com.gonza.inventariar.inventariar.UI;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.app.Fragment;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.gonza.inventariar.inventariar.Elements.Item;
import com.gonza.inventariar.inventariar.R;
import com.gonza.inventariar.inventariar.core.ScalingUtilities;
import com.weiwangcn.betterspinner.library.BetterSpinner;

import java.io.BufferedInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link ItemFormFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link ItemFormFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ItemFormFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    private static final String TAG = "ItemFromFragment";
    private static final int RESULT_LOAD_IMAGE = 1;
    private static final int IMAGE_SCALED = 80;
    private static final int IMAGE_WIDTH = 320;
    private static final int IMAGE_HEIGHT = 640;

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    @Bind(R.id.inventory_editText) EditText inventoryEditText;
    @Bind(R.id.name_editText) EditText nameEditText;
    @Bind(R.id.barcode_editText) EditText barcodeEditText;
    @Bind(R.id.brand_spinner)  BetterSpinner brandSpinner;
    @Bind(R.id.category_spinner) BetterSpinner categorySpinner;
    @Bind(R.id.pic1_imageView) ImageView pic1;
    @Bind(R.id.pic2_imageView) ImageView pic2;
    @Bind(R.id.pic3_imageView) ImageView pic3;
    @Bind(R.id.pic4_imageView) ImageView pic4;
    @Bind(R.id.description_editText) EditText descriptionEditText;
    @Bind(R.id.value_editText) EditText valueEditText;
    private boolean thereIsBarcode = false;
    private String barcode;
    private View.OnClickListener addPicListener;
    private int picViewImagePress;
    private int picNumber = 0;
    private String folder;
    private String folderPath;
    private String location;
    private String temNewImage;
    private ImageView[] picArray;

    private OnFragmentInteractionListener mListener;

    public ItemFormFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment ItemFormFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static ItemFormFragment newInstance(String param1, String param2) {
        ItemFormFragment fragment = new ItemFormFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
        folder = getResources().getString(R.string.folder);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_item_form, container, false);
        ButterKnife.bind(this,rootView);

        picArray = new ImageView[]{pic1,pic2,pic3,pic4};

        for (int i = 1; i<picArray.length; i++){
            picArray[i].setClickable(false);
        }

        ArrayAdapter<CharSequence> spinnerCategoryArrayAdapter = ArrayAdapter.createFromResource(
                getActivity(), R.array.categorys, android.R.layout.simple_dropdown_item_1line);
        categorySpinner.setAdapter(spinnerCategoryArrayAdapter);

        ArrayAdapter<CharSequence> spinnerBrandArrayAdapter = ArrayAdapter.createFromResource(
                getActivity(), R.array.brands, android.R.layout.simple_dropdown_item_1line);
        brandSpinner.setAdapter(spinnerBrandArrayAdapter);

        if (thereIsBarcode){
            barcodeEditText.setText(barcode);
            barcodeEditText.setKeyListener(null);
        }

        //Folder name
        folderPath = Environment.getExternalStorageDirectory()+File.separator +folder + File.separator + location + File.separator;
        temNewImage = Environment.getExternalStorageDirectory()+File.separator +folder + File.separator+"temp.jpg";

        //Check if your application folder exists in the external storage, if not create it:
        File imageStorageFolder = new File(folderPath);
        if (!imageStorageFolder.exists()) {
            imageStorageFolder.mkdirs();
            Log.d(TAG , "Folder created at: "+imageStorageFolder.toString());
        }

        addPicListener = new AddPicListener();

        pic1.setOnClickListener(addPicListener);

        return rootView;
    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p/>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }

    public void setItemBarcode(String barcode){
        thereIsBarcode = true;
        this.barcode = barcode;
    }

    public void setLocation(String loc){
        location = loc;
    }

    public boolean checkAndGetItem(Item item){
        String inventoryCode = inventoryEditText.getText().toString();
        String name = nameEditText.getText().toString();
        String brand = brandSpinner.getText().toString();
        String category = categorySpinner.getText().toString();
        boolean check = !inventoryCode.equals("") &&
                !name.equals("") && !brand.equals("") && !category.equals("");
        if (!check){
            String msg = getResources().getString(R.string.msg_noObligatoryFields);
            Toast.makeText(getActivity(),msg,Toast.LENGTH_SHORT).show();
            return false;
        }
        item.setInventoryCode(inventoryCode);
        item.setName(name);
        item.setBrand(brand);
        item.setCategory(category);
        item.setPictures(picNumber);
        if (!barcodeEditText.getText().toString().equals("")) item.setBarCode(barcodeEditText.getText().toString());
        if (!descriptionEditText.getText().toString().equals("")) item.setDescription(descriptionEditText.getText().toString());
        if (!valueEditText.getText().toString().equals("")) item.setValue(valueEditText.getText().toString());
        return true;
    }

    public void onActivityResult(int requestCode, int resultCode, Intent data){
        if (requestCode == RESULT_LOAD_IMAGE) {
            FileInputStream in;
            BufferedInputStream buf;
            try {
                File imageFile = imageFile();
                if (imageFile.exists()) imageFile.delete();
                in = new FileInputStream(temNewImage);
                buf = new BufferedInputStream(in);
                Bitmap preImage = BitmapFactory.decodeStream(buf);
                Bitmap image = ScalingUtilities.createScaledBitmap(preImage,IMAGE_WIDTH,
                        IMAGE_HEIGHT, ScalingUtilities.ScalingLogic.FIT);
                ByteArrayOutputStream bytes = new ByteArrayOutputStream();
                image.compress(Bitmap.CompressFormat.JPEG,IMAGE_SCALED,bytes);
                imageFile.createNewFile();
                FileOutputStream fo = new FileOutputStream(imageFile);
                fo.write(bytes.toByteArray());
                fo.close();
                refreshImageViews(imageFile.getAbsolutePath());
                //TODO remove took picture
            } catch (Exception e) {
                Log.e(TAG, "ERROR:" + e.toString());
            }

        }

    }

    private File imageFile(){
        String filename = inventoryEditText.getText().toString()+picViewImagePress+".jpg";
        File sdCard = Environment.getExternalStorageDirectory();
        String imageStorageFolderPath = File.separator + folder + File.separator;
        File destinationFile = new File(folderPath + filename);
        return destinationFile;
    }

    private void setPic(String pathToImage) {
        // Get the dimensions of the View
        int targetW = picArray[picViewImagePress].getWidth();
        int targetH = picArray[picViewImagePress].getHeight();

        // Get the dimensions of the bitmap
        BitmapFactory.Options bmOptions = new BitmapFactory.Options();
        bmOptions.inJustDecodeBounds = true;
        BitmapFactory.decodeFile(pathToImage, bmOptions);
        int photoW = bmOptions.outWidth;
        int photoH = bmOptions.outHeight;

        // Determine how much to scale down the image
        int scaleFactor = Math.min(photoW/targetW, photoH/targetH);

        // Decode the image file into a Bitmap sized to fill the View
        bmOptions.inJustDecodeBounds = false;
        bmOptions.inSampleSize = scaleFactor;
        bmOptions.inPurgeable = true;

        Bitmap bitmap = BitmapFactory.decodeFile(pathToImage, bmOptions);
        picArray[picViewImagePress].setImageBitmap(bitmap);
    }


    private void refreshImageViews(String image ){
        //picArray[picViewImagePress].setImageBitmap(image);
        setPic(image);
        picNumber = picViewImagePress+1;
        if (picViewImagePress < 3){
            picArray[picViewImagePress+1].setImageResource(R.drawable.add_image);
            picArray[picViewImagePress+1].setClickable(true);
            picArray[picViewImagePress+1].setOnClickListener(addPicListener);
        }
    }


    class AddPicListener implements View.OnClickListener {


        @Override
        public void onClick(View v) {
            if (inventoryEditText.getText().toString().equals("")){
                String msg = getResources().getString(R.string.msg_noInventoryCode);
                Toast.makeText(getActivity(),msg,Toast.LENGTH_SHORT).show();
                return;
            }
            switch (v.getId()){
                case R.id.pic1_imageView:
                    picViewImagePress = 0;
                    //TODO open camera and take the picture
                    break;
                case R.id.pic2_imageView:
                    picViewImagePress = 1;
                    break;
                case R.id.pic3_imageView:
                    picViewImagePress = 2;
                    break;
                case R.id.pic4_imageView:
                    picViewImagePress = 3;
                    break;
            }
            openCamera();
        }

    }

    private void openCamera() {
        Intent photoPickerIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        File destinationFile = new File(temNewImage);
        photoPickerIntent.putExtra(MediaStore.EXTRA_OUTPUT,Uri.fromFile(destinationFile));
        startActivityForResult(photoPickerIntent, RESULT_LOAD_IMAGE);
    }


}
