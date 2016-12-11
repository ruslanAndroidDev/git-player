package com.example.pk.drawproject.musicFragment;

import android.content.Context;

import com.example.pk.drawproject.DivideData;
import com.example.pk.drawproject.data.OnDataLoadInterface;
import com.example.pk.drawproject.data.MyVkHelper;
import com.example.pk.drawproject.data.VkAudioModel;

import java.util.ArrayList;

/**
 * Created by pk on 05.11.2016.
 */
public class MusicPresenterImpl implements MusicPresenter {
    MusicFragmentView musicView;
    public static ArrayList<VkAudioModel> data;

    MyVkHelper helper;
    Context context;

    public MusicPresenterImpl(MusicFragmentView musicView,Context context) {
        this.musicView = musicView;
        this.context = context;
    }

    @Override
    public void loadMusicItems() {
        helper = new MyVkHelper();
        helper.getVkSoundListWithListener(new OnDataLoadInterface.DataLoadedCallBack() {
            @Override
            public void onDataLoadSucces(ArrayList<VkAudioModel> vkAudios) {
                data = vkAudios;
                setAdapter(vkAudios);
                DivideData divideData = new DivideData(context);
                divideData.execute(vkAudios);
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
