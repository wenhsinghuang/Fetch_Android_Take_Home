package com.example.fetch_android_take_home.viewModels;

import android.app.Application;
import android.view.View;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModel;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.app.Application;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import com.example.fetch_android_take_home.MainActivity;
import com.example.fetch_android_take_home.R;
import com.example.fetch_android_take_home.models.FetchJSON;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

public class MainViewModel extends ViewModel {
    private MainActivity mainActivity;
    private MainRecyclerViewAdaptor adaptor;
    private LiveData<ArrayList<HashMap<String, String>>> mLiveNameList;

    public void initActivity(Application application,
                             MainActivity mainActivity, View rootView) {
        this.mainActivity = mainActivity;
        final RecyclerView NameListRecyclerView = rootView.findViewById(R.id.main_view_name_list_recycle_view);
        this.adaptor = new MainRecyclerViewAdaptor(this.mainActivity, this);

        FetchJSON fetchJSON = new FetchJSON();
        fetchJSON.execute();

        fetchJSON.onPostExecute(null);
        ArrayList<HashMap<String, String>> NameList = fetchJSON.list;
        System.out.println("VM"+NameList.isEmpty());

        // Filter out items with blank or null name
        ArrayList<HashMap<String, String>> NewNameList = new ArrayList<>();
        for (HashMap<String, String> item : NameList) {
            if (item.get("name") != null && !item.get("name").equals("") && !item.get("name").equals("null")) {
                NewNameList.add(item);
            }
        }


        // Sort the results first by listId, then by name
        Collections.sort(NewNameList, new Comparator<HashMap<String, String>>() {
            @Override
            public int compare(HashMap<String, String> item1, HashMap<String, String> item2) {
                int compare = Integer.valueOf(item1.get("listId")).compareTo(Integer.valueOf(item2.get("listId")));
                if (compare == 0) {
                    compare = item1.get("name").compareTo(item2.get("name"));
                }
                return compare;
            }
        });

        for (HashMap<String, String> item : NewNameList) {
            System.out.println("VM"+item.get("id"));
        }
        mLiveNameList = new LiveData<ArrayList<HashMap<String, String>>>(NewNameList) {};
        for (HashMap<String, String> item : mLiveNameList.getValue()) {
            System.out.println("live"+item.get("id"));
        }

        NameListRecyclerView.setAdapter(this.adaptor);
        NameListRecyclerView.setLayoutManager(new LinearLayoutManager(application));
        this.bindNameList(mLiveNameList);
    }

    public void bindNameList(LiveData<ArrayList<HashMap<String, String>>> NameListLive) {
        if(NameListLive.getValue() != null) {
            for (HashMap<String, String> item : NameListLive.getValue()) {
                System.out.println("bind"+item.get("id"));
            }
            this.adaptor.updateNameList(NameListLive.getValue());
        }
        NameListLive.observe(this.mainActivity,
                new Observer<ArrayList<HashMap<String, String>>>() {
                    @Override
                    public void onChanged(ArrayList<HashMap<String, String>> newNameList) {
                        if(newNameList != null) {
                            adaptor.updateNameList(NameListLive.getValue());
                        }
                    }
                });
    }


}