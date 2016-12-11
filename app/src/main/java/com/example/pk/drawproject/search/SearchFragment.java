package com.example.pk.drawproject.search;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.pk.drawproject.DivideData;
import com.example.pk.drawproject.PlayerService;
import com.example.pk.drawproject.R;
import com.example.pk.drawproject.data.MyVkHelper;
import com.example.pk.drawproject.data.OnDataLoadInterface;
import com.example.pk.drawproject.data.VkAudioModel;
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
        vkHelper.searchAudio(text, new OnDataLoadInterface.DataLoadedCallBack() {
            @Override
            public void onDataLoadSucces(ArrayList<VkAudioModel> vkAudios) {
                setItems(vkAudios);
            }
        });
    }

    public void setItems(final ArrayList<VkAudioModel> data) {
        searchAdapter = new RecyclerViewAdapter(data, getContext());
        searchAdapter.setRecyclerItemClickListener(new RecyclerItemClickListener() {
            @Override
            public void onItemClickListener(int position) {
                Intent intent = new Intent(getContext(), PlayerService.class);
                intent.setAction(PlayerService.PLAY_SOUND);
                intent.putExtra("position", position);
                getContext().startService(intent);
            }
        });
        searchRecyclerView.setAdapter(searchAdapter);
        DivideData divideData = new DivideData(getContext());
        divideData.execute(data);
    }
}
