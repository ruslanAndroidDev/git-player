package com.example.pk.drawproject.musicFragment;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.pk.drawproject.R;
import com.example.pk.drawproject.bottomTabFragments.MusicPagerAdapter;
import com.example.pk.drawproject.model.VkAudio;
import com.example.pk.drawproject.musicFragment.recyclerView.RecyclerViewAdapter;

import java.util.ArrayList;

/**
 * Created by pk on 28.10.2016.
 */
public class MusicFragment extends Fragment implements MusicFragmentInterface {
    RecyclerView music_Recycler_View;
    Context context;
    ViewPager musicBar;
    MusicPresenter musicPresenter;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.music_fragment, container, false);
        context = v.getContext();
        Log.d("tag","onCreateView,MusicFragment");
        music_Recycler_View = (RecyclerView) v.findViewById(R.id.musicRecyclerView);
        musicBar = (ViewPager) v.findViewById(R.id.musicBar);

        musicPresenter = new MusicPresenter(this);
        musicPresenter.vkGetAudio();
        return v;
    }

    @Override
    public void setAdapter(RecyclerViewAdapter adapter) {
        music_Recycler_View.setLayoutManager(new LinearLayoutManager(getContext()));
        music_Recycler_View.setAdapter(adapter);
    }

    @Override
    public void setPlayerBar(int position) {
        musicBar.setAdapter(new MusicPagerAdapter(getFragmentManager(),position));
    }
}