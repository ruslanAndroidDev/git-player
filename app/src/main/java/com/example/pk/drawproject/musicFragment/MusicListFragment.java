package com.example.pk.drawproject.musicFragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.pk.drawproject.PlayerService;
import com.example.pk.drawproject.R;
import com.example.pk.drawproject.data.PlayerInterfaces;
import com.example.pk.drawproject.data.VkAudioModel;
import com.example.pk.drawproject.main.MainActivity;
import com.example.pk.drawproject.musicFragment.recycler.RecyclerItemClickListener;
import com.example.pk.drawproject.musicFragment.recycler.RecyclerViewAdapter;

import java.util.ArrayList;

/**
 * Created by pk on 28.10.2016.
 */
public class MusicListFragment extends Fragment implements MusicFragmentView, PlayerInterfaces.FragmentAction {
    RecyclerView music_Recycler_View;
    MusicPresenterImpl musicPresenterImpl;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        musicPresenterImpl = new MusicPresenterImpl(this, getContext());
        MainActivity.setListener(this);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.music_fragment, container, false);
        music_Recycler_View = (RecyclerView) v.findViewById(R.id.musicRecyclerView);
        musicPresenterImpl = new MusicPresenterImpl(this, getContext());
        return v;
    }

    @Override
    public void showMyAudioList() {
        musicPresenterImpl.loadMyMusicItems();
    }

    @Override
    public void showSearchAudioList(String text) {
        musicPresenterImpl.loadSearchableAudioList(text);
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
    }

    @Override
    public void playSound(int songPosition) {
        Intent intent = new Intent(getContext(), PlayerService.class);
        intent.setAction(PlayerService.PLAY_SOUND);
        intent.putExtra("position", songPosition);
        getContext().startService(intent);
    }
}