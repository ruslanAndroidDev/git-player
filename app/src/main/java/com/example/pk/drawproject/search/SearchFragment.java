package com.example.pk.drawproject.search;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.pk.drawproject.R;
import com.example.pk.drawproject.model.ModelInterface;
import com.example.pk.drawproject.model.MyVkHelper;
import com.example.pk.drawproject.model.VkAudio;
import com.example.pk.drawproject.musicFragment.recycler.RecyclerItemClickListener;
import com.example.pk.drawproject.musicFragment.recycler.RecyclerViewAdapter;

import java.util.ArrayList;

/**
 * Created by pk on 27.11.2016.
 */
public class SearchFragment extends Fragment {
    RecyclerView searchRecyclerView;
    RecyclerViewAdapter searchAdapter;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.search_layout, container, false);
        searchRecyclerView = (RecyclerView) v.findViewById(R.id.searchRecyclerView);
        searchRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        Log.d("tag", "onCreateView");
        return v;
    }

    @Override
    public void onResume() {
        super.onResume();

    }

    public void loadItem(String text) {
        Log.d("tag", "text +" + text);
        MyVkHelper vkHelper = new MyVkHelper();
        vkHelper.load(text, new ModelInterface.DataLoadedCallBack() {
            @Override
            public void onDataLoadSucces(ArrayList<VkAudio> vkAudios) {
                setItems(vkAudios);
            }
        });
    }

    public void setItems(ArrayList<VkAudio> data) {
        Log.d("tag","setItems,data.size +"  + data.size());
        searchAdapter = new RecyclerViewAdapter(data, getContext());
        searchAdapter.setRecyclerItemClickListener(new RecyclerItemClickListener() {
            @Override
            public void onItemClickListener(int position) {
                Log.d("tag", "Click in searchFragment");
            }
        });
        searchRecyclerView.setAdapter(searchAdapter);
    }
}
