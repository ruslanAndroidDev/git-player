package com.example.pk.drawproject.view.playerBar;
import com.example.pk.drawproject.musicFragment.MusicPresenter;


/**
 * Created by pk on 06.11.2016.
 */
public class PlayerBarPresenter implements PlayerBarPresenterInterface {
    SoundFragment soundFragment;

    public PlayerBarPresenter(SoundFragment soundFragment) {
        this.soundFragment = soundFragment;
    }

    @Override
    public void buttonClick() {
        if (MusicPresenter.mediaPlayer.isPlaying()) {
            soundFragment.setMainBtnOnPause();
        } else {
            soundFragment.setMainBtnOnPlay();
        }
    }
}
