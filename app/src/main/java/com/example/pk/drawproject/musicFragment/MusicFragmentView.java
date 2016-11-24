package com.example.pk.drawproject.musicFragment;

import com.example.pk.drawproject.model.VkAudio;
import java.util.ArrayList;

/**
 * Created by pk on 05.11.2016.
 */
public interface MusicFragmentView {
    void setAdapter(ArrayList<VkAudio> vkAudios);
    void playSound(int songPosition);
    void setServiceList(ArrayList<String> url);
}
