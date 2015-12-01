package com.gonza.inventariar.inventariar.UI;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;

import com.gonza.inventariar.inventariar.R;

import java.util.ArrayList;
import java.util.List;

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

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    @Bind(R.id.inventory_editText) EditText inventoryEditText;
    @Bind(R.id.name_editText) EditText nameEditText;
    @Bind(R.id.number_editText) EditText numberEditText;
    @Bind(R.id.brand_spinner) Spinner brandSpinner;
    @Bind(R.id.category_spinner) Spinner categorySpinner;
    @Bind(R.id.pic1_imageView) ImageView pic1;
    @Bind(R.id.pic2_imageView) ImageView pic2;
    @Bind(R.id.pic3_imageView) ImageView pic3;
    @Bind(R.id.pic4_imageView) ImageView pic4;
    @Bind(R.id.description_editText) EditText descriptionEditText;
    @Bind(R.id.value_editText) EditText valueEditText;

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
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_item_form, container, false);
        ButterKnife.bind(this,rootView);

        ArrayAdapter<CharSequence> spinnerCategoryArrayAdapter = ArrayAdapter.createFromResource(
                getActivity(), R.array.categorys, android.R.layout.simple_spinner_item);
        categorySpinner.setAdapter(spinnerCategoryArrayAdapter);

        ArrayAdapter<CharSequence> spinnerBrandArrayAdapter = ArrayAdapter.createFromResource(
                getActivity(), R.array.brands, android.R.layout.simple_spinner_item);
        brandSpinner.setAdapter(spinnerBrandArrayAdapter);

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
}
