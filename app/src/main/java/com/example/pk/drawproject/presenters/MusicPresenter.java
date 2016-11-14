package com.example.pk.drawproject.presenters;

import android.content.Intent;

import com.example.pk.drawproject.PlayerService;
import com.example.pk.drawproject.interfaces.MusicPresenterInterface;
import com.example.pk.drawproject.model.Model;
import com.example.pk.drawproject.model.ModelInterface;
import com.example.pk.drawproject.model.VkAudio;
import com.example.pk.drawproject.ui.adapter.RecyclerItemClickListener;
import com.example.pk.drawproject.ui.adapter.RecyclerViewAdapter;
import com.example.pk.drawproject.ui.fragments.MusicFragment;

import java.util.ArrayList;

/**
 * Created by pk on 05.11.2016.
 */
public class MusicPresenter implements MusicPresenterInterface, RecyclerItemClickListener {
    MusicFragment fragment;
    public static ArrayList<VkAudio> data;

    public MusicPresenter(MusicFragment fragment) {
        this.fragment = fragment;
    }

    @Override
    public void loadMusicItems() {
        Model model = new Model();
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
    public void onItemClickListener(int position) {
        //playSound(position);
        Intent start = new Intent(fragment.getContext(), PlayerService.class);
        start.putExtra("url", data.get(position).getUrl());
        fragment.getContext().startService(start);
    }
}
