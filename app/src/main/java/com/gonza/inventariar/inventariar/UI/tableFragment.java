package com.gonza.inventariar.inventariar.UI;

import android.app.Fragment;
import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import butterknife.ButterKnife;
import it.gmariotti.cardslib.library.internal.Card;
import it.gmariotti.cardslib.library.internal.CardArrayAdapter;
import it.gmariotti.cardslib.library.internal.CardHeader;
import it.gmariotti.cardslib.library.internal.CardThumbnail;
import it.gmariotti.cardslib.library.view.CardListView;

import com.gonza.inventariar.inventariar.Elements.Item;
import com.gonza.inventariar.inventariar.Elements.Value;
import com.gonza.inventariar.inventariar.R;
import com.opencsv.CSVWriter;

import java.io.File;
import java.io.FileWriter;
import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link tableFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link tableFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class tableFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    private static final String TAG = "tableFragment";
    //table
   // @Bind(R.id.table) TableLayout table;
    private int col = 2;
    private String[] vals;
    private ArrayList<Value> values;
    private int elements;
    private List<Item> items;
    private String location;
    private String element;

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private OnFragmentInteractionListener mListener;

    public tableFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment tableFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static tableFragment newInstance(String param1, String param2) {
        tableFragment fragment = new tableFragment();
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
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView =  inflater.inflate(R.layout.fragment_table, container, false);
        ButterKnife.bind(this,rootView);
        //create table
        vals = getResources().getStringArray(R.array.columnValues);
        values = new ArrayList<Value>();
        ArrayList<Card> cards = new ArrayList<Card>();
        if (items != null || !items.isEmpty()) {
            for (Item e: items) {
                //cards

                Card card = new Card(rootView.getContext());

                // Create a CardHeader
                CardHeader header = new CardHeader(rootView.getContext());
                // Add Header to card
                header.setTitle(e.getInventoryCode());
                card.setTitle(e.getName());
                card.addCardHeader(header);

                CardThumbnail thumb = new CardThumbnail(rootView.getContext());
                // TODO put the correct image
                thumb.setDrawableResource(R.drawable.no_image);
                card.addCardThumbnail(thumb);

                cards.add(card);
                Log.d(TAG,e.getInventoryCode());

            }
            CardArrayAdapter mCardArrayAdapter = new CardArrayAdapter(rootView.getContext(), cards);

            CardListView listView = (CardListView) rootView.findViewById(R.id.myList);
            if (listView != null) {
                listView.setAdapter(mCardArrayAdapter);
            }
        }
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

    public void setItemList(List<Item> items){
        this.items = items;
    }

    public void setLocation(String location){ this.location = location; }

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

    public void exportToCSV(){

       String folderPath = Environment.getExternalStorageDirectory()+
                File.separator+getResources().getString(R.string.folder)+File.separator
                + location;
        File exportDir = new File(folderPath);
        if (!exportDir.exists())
        {
            exportDir.mkdirs();
        }
        File file = new File(exportDir, location +".csv");
        try{
            file.createNewFile();
            CSVWriter csvWriter = new CSVWriter(new FileWriter(file),';');
            String[] columnsCSV = getResources().getStringArray(R.array.columnCSV);
            csvWriter.writeNext(columnsCSV);

            for (Item e: items){
                String[] newRow = {e.getInventoryCode(),e.getName(),e.getBarCode(),
                        e.getBrand(),e.getCategory(),e.getPictures()+"",e.getDescription(),e.getValue()};
                csvWriter.writeNext(newRow);
            }

            csvWriter.close();

        }catch (Exception e){
            Log.e("MainActivity", e.getMessage(), e);
        }
    }
}
