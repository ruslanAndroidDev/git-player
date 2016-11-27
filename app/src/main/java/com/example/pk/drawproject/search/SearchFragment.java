package com.example.pk.drawproject.search;

import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;

import com.example.pk.drawproject.R;
import com.example.pk.drawproject.model.MyVkHelper;
import com.example.pk.drawproject.model.VkAudio;
import com.example.pk.drawproject.musicFragment.recycler.RecyclerItemClickListener;
import com.example.pk.drawproject.musicFragment.recycler.RecyclerViewAdapter;

import java.util.ArrayList;

/**
 * Created by pk on 27.11.2016.
 */
public class SearchFragment extends Fragment implements SwipeRefreshLayout.OnRefreshListener {
    SwipeRefreshLayout refreshLayout;
    RecyclerView searchRecyclerView;
    ProgressBar progressBar;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.search_layout, container, false);
        searchRecyclerView = (RecyclerView) v.findViewById(R.id.searchRecyclerView);
        searchRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        progressBar = (ProgressBar)v.findViewById(R.id.mprogress);
        Log.d("tag", "onCreateView");
        return v;
    }

    public void loadItem(String text) {
        MyVkHelper vkHelper = new MyVkHelper();
        Log.d("tag", "text +" + text);
        setItems(vkHelper.searchAudio(text));
    }

    public void setItems(ArrayList<VkAudio> data) {
        RecyclerViewAdapter searchAdapter = new RecyclerViewAdapter(data, getContext());
        searchAdapter.setRecyclerItemClickListener(new RecyclerItemClickListener() {
            @Override
            public void onItemClickListener(int position) {
                Log.d("tag", "Click in searchFragment");
            }
        });
        searchRecyclerView.setAdapter(searchAdapter);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        refreshLayout = (SwipeRefreshLayout) view.findViewById(R.id.refreshLayout);
        refreshLayout.setOnRefreshListener(this);
        refreshLayout.setColorSchemeColors(Color.BLUE);
        Log.d("tag", "onViewCreated");
    }

    @Override
    public void onRefresh() {
        refreshLayout.setRefreshing(true);
        refreshLayout.postDelayed(new Runnable() {
            @Override
            public void run() {
                refreshLayout.setRefreshing(false);
            }
        }, 3000);

    }

    public void showProgress() {
        searchRecyclerView.setVisibility(View.GONE);
        progressBar.setVisibility(View.VISIBLE);
    }

    public void hideProgress() {
        progressBar.setVisibility(View.GONE);
        searchRecyclerView.setVisibility(View.VISIBLE);
    }
}
