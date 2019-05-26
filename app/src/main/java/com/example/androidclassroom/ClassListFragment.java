package com.example.androidclassroom;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link ClassListFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link ClassListFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ClassListFragment extends Fragment implements View.OnClickListener {

    OnFragmentInteractionListener  callback;

    public TextView textView;

    private OnFragmentInteractionListener mListener;

    public ClassListFragment() {
        // Required empty public constructor
    }

    public void setOnAddNameSelectedListener(OnFragmentInteractionListener  callback) {
        this.callback = callback;
    }

    // This interface can be implemented by the Activity, parent Fragment,
    // or a separate test implementation.
    public interface OnFragmentInteractionListener  {
        public void onAddNameSelected();
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @return A new instance of fragment ClassListFragment.
     */

    public static ClassListFragment newInstance() {
        ClassListFragment fragment = new ClassListFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);

        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_class_list, container, false);
        textView = (TextView) v.findViewById(R.id.class1);
        ImageButton deleteButton = (ImageButton) v.findViewById(R.id.delete1);
        deleteButton.setOnClickListener(this);

        return v;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.delete1:
                getFragmentManager().popBackStack(this.getTag(), FragmentManager.POP_BACK_STACK_INCLUSIVE);
                break;
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
}
