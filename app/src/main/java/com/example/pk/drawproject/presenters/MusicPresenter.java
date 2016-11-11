package com.example.pk.drawproject.presenters;

import android.media.AudioManager;
import android.media.MediaPlayer;
import android.util.Log;

import com.example.pk.drawproject.musicFragment.MusicPresenterInterface;
import com.example.pk.drawproject.presenters.MainPresenter;
import com.example.pk.drawproject.model.Model;
import com.example.pk.drawproject.model.ModelInterface;
import com.example.pk.drawproject.model.VkAudio;
import com.example.pk.drawproject.musicFragment.recyclerView.RecyclerItemClickListener;
import com.example.pk.drawproject.musicFragment.recyclerView.RecyclerViewAdapter;
import com.example.pk.drawproject.ui.fragments.MusicFragment;

import java.io.IOException;
import java.util.ArrayList;

/**
 * Created by pk on 05.11.2016.
 */
public class MusicPresenter implements MusicPresenterInterface, RecyclerItemClickListener {
    MusicFragment fragment;
    public static MediaPlayer mediaPlayer;
    int prevPosition;
    public static ArrayList<VkAudio> data;

    public MusicPresenter(MusicFragment fragment) {
        this.fragment = fragment;
        mediaPlayer = new MediaPlayer();
        mediaPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);
    }

    @Override
    public void loadMusicItems() {
        Model model = new Model();
        model.getVkSoundListWithListener(new ModelInterface.DataLoadedCallBack() {
            @Override
            public void onDataLoadSucces(ArrayList<VkAudio> vkAudios) {
                data = vkAudios;
                setAdapter(vkAudios);
            }
        });
    }

    @Override
    public void setAdapter(ArrayList<VkAudio> vkAudios) {
        RecyclerViewAdapter adapter = new RecyclerViewAdapter(vkAudios, fragment.getContext());
        adapter.setRecyclerItemClickListener(this);
        fragment.setAdapter(adapter);
    }

    @Override
    public void playSound(final int position) {
        MainPresenter presenter = MainPresenter.getInstance();
        presenter.showBar(position);
        Log.d("tag", "showBar");
        if (prevPosition == position) {
            if (mediaPlayer.isPlaying()) {
                mediaPlayer.pause();
            } else {
                mediaPlayer.start();
            }
        } else {
            mediaPlayer.reset();
            Thread thread = new Thread(new Runnable() {
                @Override
                public void run() {
                    try {
                        Log.d("tag", "thread.run");
                        mediaPlayer.setDataSource(data.get(position).getUrl());
                        mediaPlayer.prepare();
                        mediaPlayer.start();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            });
            thread.start();
            prevPosition = position;
        }
    }

    @Override
    public void onItemClickListener(int position) {
        playSound(position);
    }
}
