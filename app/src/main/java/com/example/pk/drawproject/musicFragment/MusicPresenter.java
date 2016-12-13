package com.example.pk.drawproject.musicFragment;

/**
 * Created by pk on 05.11.2016.
 */
public interface MusicPresenter {
    void loadMyMusicItems();

    void itemClick(int position);

    void loadSearchableAudioList(String text);

}
