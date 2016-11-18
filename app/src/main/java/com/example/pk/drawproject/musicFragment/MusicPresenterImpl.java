package com.example.pk.drawproject.musicFragment;

import com.example.pk.drawproject.model.Model;
import com.example.pk.drawproject.model.ModelInterface;
import com.example.pk.drawproject.model.VkAudio;

import java.util.ArrayList;

/**
 * Created by pk on 05.11.2016.
 */
public class MusicPresenterImpl implements MusicPresenter {
    MusicFragmentView musicView;
    public static ArrayList<VkAudio> data;

    Model model;
    private int previousClick = -1;

    public MusicPresenterImpl(MusicFragmentView musicView) {
        this.musicView = musicView;
    }

    @Override
    public void loadMusicItems() {
       model = new Model();
        model.getVkSoundListWithListener(new ModelInterface.DataLoadedCallBack() {
            @Override
            public void onDataLoadSucces(ArrayList<VkAudio> vkAudios) {
                data = vkAudios;
                setAdapter(vkAudios);
            }
        });
    }

    @Override
    public void itemClick(int position) {
        if (previousClick == position) {
            musicView.clickOnTheSameItem();
        } else if (previousClick == -1) {
            previousClick = position;
            musicView.playSound(position);
        } else {
            musicView.playSound(position);
            previousClick = position;
        }
    }

    public void loadServiceSongList() {
        model.getSongWithListener(new ModelInterface.SongLoadedCallBack() {
            @Override
            public void onSongLoadSucces(ArrayList<String> url) {
                musicView.setServiceList(url);
            }
        });
    }

    @Override
    public void setAdapter(ArrayList<VkAudio> vkAudios) {
        musicView.setAdapter(vkAudios);
    }

}
