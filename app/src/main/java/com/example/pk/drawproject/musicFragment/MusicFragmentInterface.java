package com.example.pk.drawproject.musicFragment;

import com.example.pk.drawproject.musicFragment.recyclerView.RecyclerViewAdapter;

/**
 * Created by pk on 05.11.2016.
 */
public interface MusicFragmentInterface {
    void setAdapter(RecyclerViewAdapter adapter);
    void setPlayerBar(int position);
}
