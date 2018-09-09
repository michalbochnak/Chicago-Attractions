//
// Michal Bochnak
// CS 478, Project #3
// UIC, April 2, 2018
//


package com.webvideoplayer.cs478project03chicagoattractionsinfo;

import android.app.ListFragment;
import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;


//
// Fragment to hold the list of items
//
public class MyListFragment extends ListFragment {

    private static final String TAG = "MyListFragment";
    private CommunicationInterface parentAct = null;

    @Override
    public void onAttach(Context activity) {
        super.onAttach(activity);
        try {
            parentAct = (CommunicationInterface) activity;
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }

    // allow easy communication between classes
    public interface CommunicationInterface {
        void onItemSelection(int index);
        int getArrayId();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRetainInstance(true);
    }

    // Called to create the content view for this Fragment
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        // Inflate the layout
        return super.onCreateView(inflater, container, savedInstanceState);
    }

    // Set up some information about the mQuoteView TextView
    @Override
    public void onActivityCreated(Bundle savedInstanceState) {

        super.onActivityCreated(savedInstanceState);
        setListAdapter(new ArrayAdapter<String>(getActivity(),
               R.layout.list_item, getResources().getStringArray
                (parentAct.getArrayId())));

        getListView().setChoiceMode(ListView.CHOICE_MODE_SINGLE);
    }

    // Called when the user selects an item from the List
    @Override
    public void onListItemClick(ListView l, View v, int pos, long id) {
        l.setItemChecked(pos, true);
        parentAct.onItemSelection(pos);
    }

}
