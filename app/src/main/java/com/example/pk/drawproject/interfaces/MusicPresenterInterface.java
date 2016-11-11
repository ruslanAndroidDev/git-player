package com.example.pk.drawproject.interfaces;

import com.example.pk.drawproject.model.VkAudio;

import java.util.ArrayList;

/**
 * Created by pk on 05.11.2016.
 */
public interface MusicPresenterInterface {
    void setAdapter(ArrayList<VkAudio> vkAudios);
    void loadMusicItems();
    void playSound(int position);
}
