package com.example.pk.drawproject.musicFragment;

import android.content.Context;

import com.example.pk.drawproject.data.MyVkHelper;
import com.example.pk.drawproject.data.PlayerInterfaces;
import com.example.pk.drawproject.data.VkAudioModel;

import java.util.ArrayList;

/**
 * Created by pk on 05.11.2016.
 */
public class MusicPresenterImpl implements MusicPresenter {
    MusicFragmentView musicView;

    MyVkHelper helper;
    Context context;

    public MusicPresenterImpl(MusicFragmentView musicView, Context context) {
        this.musicView = musicView;
        this.context = context;
    }

    @Override
    public void loadMyMusicItems() {
        helper = new MyVkHelper();
        helper.getMySoundListWithListener(new PlayerInterfaces.DataLoadedCallBack() {
            @Override
            public void onDataLoadSucces(ArrayList<VkAudioModel> vkAudios) {
                musicView.setAdapter(vkAudios);
                helper.divideData(vkAudios, context);
            }
        });
    }

    @Override
    public void itemClick(int position) {
        musicView.playSound(position);
    }

    @Override
    public void loadSearchableAudioList(String text) {
        helper.searchAudio(text, new PlayerInterfaces.DataLoadedCallBack() {
            @Override
            public void onDataLoadSucces(ArrayList<VkAudioModel> vkAudios) {
                musicView.setAdapter(vkAudios);
                helper.divideData(vkAudios, context);
            }
        });
    }


}
