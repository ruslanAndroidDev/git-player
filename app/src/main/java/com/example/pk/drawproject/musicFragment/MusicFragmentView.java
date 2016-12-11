package com.example.pk.drawproject.musicFragment;

import com.example.pk.drawproject.data.VkAudioModel;
import java.util.ArrayList;

/**
 * Created by pk on 05.11.2016.
 */
public interface MusicFragmentView {
    void setAdapter(ArrayList<VkAudioModel> vkAudios);
    void playSound(int songPosition);
}
