package com.example.pk.drawproject.musicFragment;

import android.media.AudioManager;
import android.media.MediaPlayer;
import android.util.Log;

import com.example.pk.drawproject.model.Model;
import com.example.pk.drawproject.model.ModelInterface;
import com.example.pk.drawproject.model.VkAudio;
import com.example.pk.drawproject.musicFragment.recyclerView.RecyclerItemClickListener;
import com.example.pk.drawproject.musicFragment.recyclerView.RecyclerViewAdapter;
import com.example.pk.drawproject.view.playerBar.PlayerBarInterface;
import com.example.pk.drawproject.view.playerBar.PlayerBarPresenter;
import com.example.pk.drawproject.view.playerBar.SoundFragment;

import java.io.IOException;
import java.util.ArrayList;

/**
 * Created by pk on 05.11.2016.
 */
public class MusicPresenter implements MusicPresenterInterface, RecyclerItemClickListener {
    MusicFragment fragment;
    public static MediaPlayer mediaPlayer;
    int prevPosition;
    ArrayList<VkAudio> data;
    static PlayerBarInterface playerBarInterface;

    public MusicPresenter(MusicFragment fragment) {
        this.fragment = fragment;
        mediaPlayer = new MediaPlayer();
        mediaPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);
    }

    public static void registerPlayerBar(SoundFragment fragment) {
        playerBarInterface = fragment;
    }

    @Override
    public void loadMusicItems() {
        Model model = new Model(this);
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
        showBar(position);
        Log.d("tag", "showBar");
        if (prevPosition == position) {
            if (mediaPlayer.isPlaying()) {
                mediaPlayer.pause();
                playerBarInterface.setMainBtnOnPlay();
            } else {
                mediaPlayer.start();
                playerBarInterface.setMainBtnOnPause();
            }
        } else {
            mediaPlayer.reset();
            Thread thread = new Thread(new Runnable() {
                @Override
                public void run() {
                    try {
                        mediaPlayer.setDataSource(data.get(position).getUrl());
                        mediaPlayer.prepare();
                        playerBarInterface.setMainBtnOnPause();
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
    public void showBar(int position) {
        fragment.showPlayerBar(position);
        playerBarInterface.filingField(data.get(position).getTitle(),data.get(position).getArtist());
    }

    @Override
    public void onItemClickListener(int position) {
        playSound(position);
    }
}
