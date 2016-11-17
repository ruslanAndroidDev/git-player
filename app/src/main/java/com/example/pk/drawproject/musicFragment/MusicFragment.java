package com.example.pk.drawproject.musicFragment;

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

import com.example.pk.drawproject.PlayerService;
import com.example.pk.drawproject.R;
import com.example.pk.drawproject.model.VkAudio;
import com.example.pk.drawproject.musicFragment.recycler.RecyclerItemClickListener;
import com.example.pk.drawproject.musicFragment.recycler.RecyclerViewAdapter;

import java.util.ArrayList;

/**
 * Created by pk on 28.10.2016.
 */
public class MusicFragment extends Fragment implements MusicFragmentView {
    RecyclerView music_Recycler_View;
    MusicPresenterImpl musicPresenterImpl;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.music_fragment, container, false);
        Log.d("tag", "onCreateView,MusicFragment");
        music_Recycler_View = (RecyclerView) v.findViewById(R.id.musicRecyclerView);

        musicPresenterImpl = new MusicPresenterImpl(this);
        musicPresenterImpl.loadMusicItems();
        return v;
    }

    @Override
    public void setAdapter(ArrayList<VkAudio> vkAudios) {
        music_Recycler_View.setLayoutManager(new LinearLayoutManager(getContext()));
        RecyclerViewAdapter adapter = new RecyclerViewAdapter(vkAudios, getContext());
        music_Recycler_View.setAdapter(adapter);
        adapter.setRecyclerItemClickListener(new RecyclerItemClickListener() {
            @Override
            public void onItemClickListener(int position) {
                musicPresenterImpl.itemClick(position);
            }
        });
    }

    @Override
    public void playSound(String url) {
        Intent intent = new Intent(getContext(), PlayerService.class);
        intent.putExtra("url",url);
        getContext().startService(intent);
    }
}