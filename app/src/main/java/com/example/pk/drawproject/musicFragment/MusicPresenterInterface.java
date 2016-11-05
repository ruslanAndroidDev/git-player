package com.example.pk.drawproject.musicFragment;

import com.example.pk.drawproject.model.VkAudio;

import java.util.ArrayList;

/**
 * Created by pk on 05.11.2016.
 */
public interface MusicPresenterInterface {
    void vkGetAudio();
    void setAdapter(ArrayList<VkAudio> vkAudios);
    void playSound(int position);
    void setPlayerBar(int position);
}
