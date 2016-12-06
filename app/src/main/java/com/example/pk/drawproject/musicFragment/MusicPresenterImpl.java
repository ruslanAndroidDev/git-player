package com.example.pk.drawproject.musicFragment;

import com.example.pk.drawproject.model.OnDataLoadInterface;
import com.example.pk.drawproject.model.MyVkHelper;
import com.example.pk.drawproject.model.VkAudioModel;

import java.util.ArrayList;

/**
 * Created by pk on 05.11.2016.
 */
public class MusicPresenterImpl implements MusicPresenter {
    MusicFragmentView musicView;
    public static ArrayList<VkAudioModel> data;

    MyVkHelper helper;

    public MusicPresenterImpl(MusicFragmentView musicView) {
        this.musicView = musicView;
    }

    @Override
    public void loadMusicItems() {
        helper = new MyVkHelper();
        helper.getVkSoundListWithListener(new OnDataLoadInterface.DataLoadedCallBack() {
            @Override
            public void onDataLoadSucces(ArrayList<VkAudioModel> vkAudios) {
                data = vkAudios;
                setAdapter(vkAudios);
            }
        });
    }

    @Override
    public void itemClick(int position) {
        musicView.playSound(position);
    }

    @Override
    public void setAdapter(ArrayList<VkAudioModel> vkAudios) {
        musicView.setAdapter(vkAudios);
    }

}
