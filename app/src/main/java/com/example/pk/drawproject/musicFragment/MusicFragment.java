package com.example.pk.drawproject.musicFragment;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.IBinder;
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

import java.io.IOException;
import java.util.ArrayList;

/**
 * Created by pk on 28.10.2016.
 */
public class MusicFragment extends Fragment implements MusicFragmentView {
    RecyclerView music_Recycler_View;
    MusicPresenterImpl musicPresenterImpl;

    PlayerService playerService;

    private boolean bound = false;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        Intent intent = new Intent(getContext(), PlayerService.class);
        getContext().bindService(intent, connection, Context.BIND_AUTO_CREATE);
        Log.d("tag", "OnAttach");
    }

    @Override
    public void onDetach() {
        super.onDetach();
        getContext().unbindService(connection);
        Log.d("tag", "onDetach");
    }

    private ServiceConnection connection = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            PlayerService.PlayerBinder playerBinder = (PlayerService.PlayerBinder) service;
            playerService = playerBinder.getPlayer();
            bound = true;
            Log.d("tag", "serviceConnected");
        }

        @Override
        public void onServiceDisconnected(ComponentName name) {
            bound = false;
            Log.d("tag", "onServiceDisconnected");
        }
    };

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
        try {
            playerService.playSounds(url);
        } catch (IOException e) {
            Log.d("tag", "AAAAAAAAAAAA  ERROR syka");
        }
    }

    @Override
    public void clickOnTheSameItem() {
        if (playerService.isPlaying()) {
            playerService.pause();
        } else {
            playerService.resumePlaying();
        }
    }
}