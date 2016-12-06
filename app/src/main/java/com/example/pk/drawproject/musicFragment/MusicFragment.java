package com.example.pk.drawproject.musicFragment;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.pk.drawproject.PlayerService;
import com.example.pk.drawproject.R;
import com.example.pk.drawproject.model.VkAudioModel;
import com.example.pk.drawproject.musicFragment.recycler.RecyclerItemClickListener;
import com.example.pk.drawproject.musicFragment.recycler.RecyclerViewAdapter;

import java.util.ArrayList;

/**
 * Created by pk on 28.10.2016.
 */
public class MusicFragment extends Fragment implements MusicFragmentView {
    RecyclerView music_Recycler_View;
    MusicPresenterImpl musicPresenterImpl;

    @Override
    public void onDetach() {
        super.onDetach();
        Log.d("tag", "onDetach");
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        musicPresenterImpl = new MusicPresenterImpl(this);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.music_fragment, container, false);
        Log.d("tag", "onCreateView,MusicFragment");
        music_Recycler_View = (RecyclerView) v.findViewById(R.id.musicRecyclerView);
        musicPresenterImpl.loadMusicItems();
        return v;
    }

    @Override
    public void setAdapter(final ArrayList<VkAudioModel> vkAudios) {
        music_Recycler_View.setLayoutManager(new LinearLayoutManager(getContext()));
        RecyclerViewAdapter adapter = new RecyclerViewAdapter(vkAudios, getContext());
        music_Recycler_View.setAdapter(adapter);
        adapter.setRecyclerItemClickListener(new RecyclerItemClickListener() {
            @Override
            public void onItemClickListener(int position) {
                musicPresenterImpl.itemClick(position);
            }
        });
        final ArrayList<String> url = new ArrayList<>();
        final ArrayList<String> title = new ArrayList<>();
        final Intent intent = new Intent(getContext(), PlayerService.class);
        Handler handler = new Handler();
        Runnable runnable = new Runnable() {
            @Override
            public void run() {
                for (int i = 0; i < vkAudios.size(); i++) {
                    url.add(vkAudios.get(i).getUrl());
                    title.add(vkAudios.get(i).getTitle());
                }
                intent.putStringArrayListExtra("url", url);
                intent.putStringArrayListExtra("title", title);
                intent.setAction(PlayerService.SET_DATA);
                getContext().startService(intent);
            }
        };
        handler.post(runnable);
    }

    @Override
    public void playSound(int songPosition) {
        Intent intent = new Intent(getContext(), PlayerService.class);
        intent.setAction(PlayerService.PLAY_SOUND);
        intent.putExtra("position", songPosition);
        getContext().startService(intent);
    }
}