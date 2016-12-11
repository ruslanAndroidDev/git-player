package com.example.pk.drawproject.musicFragment;

import com.example.pk.drawproject.data.VkAudioModel;

import java.util.ArrayList;

/**
 * Created by pk on 05.11.2016.
 */
public interface MusicPresenter {
    void setAdapter(ArrayList<VkAudioModel> vkAudios);
    void loadMusicItems();
    void itemClick(int position);
}
