package com.example.pk.drawproject.presenters;

import android.util.Log;

import com.example.pk.drawproject.interfaces.MainPresenterInterface;
import com.example.pk.drawproject.ui.activies.MainActivity;
import com.example.pk.drawproject.ui.fragments.MusicFragment;
import com.example.pk.drawproject.ui.fragments.PlayerFragment;
import com.vk.sdk.VKScope;
import com.vk.sdk.VKSdk;

/**
 * Created by pk on 05.11.2016.
 */
public class MainPresenter implements MainPresenterInterface {
    MainActivity mainActivity;
    static MainPresenter instance;
    PlayerFragment playerfragment;

    private String[] scope = new String[]{VKScope.AUDIO};

    private MainPresenter() {
    }

    public static MainPresenter getInstance() {
        if (instance == null) {
            instance = new MainPresenter();
            return instance;
        } else {
            return instance;
        }
    }

    @Override
    public void attachView(MainActivity mainActivity) {
        this.mainActivity = mainActivity;
    }

    @Override
    public void attachPlayerFragment(PlayerFragment fragment) {
        this.playerfragment = fragment;
    }

    @Override
    public void showBar(int position) {
        mainActivity.showPlayerFragment();
        playerfragment.fillingField(MusicPresenter.data.get(position).getTitle(),MusicPresenter.data.get(position).getArtist());
    }

    @Override
    public void barButtonClick() {
        if (MusicPresenter.mediaPlayer.isPlaying()){
            MusicPresenter.mediaPlayer.stop();
            playerfragment.setButtonOnStart();
        }else{
            MusicPresenter.mediaPlayer.start();
            playerfragment.setButtonOnPause();
        }
    }

    @Override
    public void login() {
        if (VKSdk.isLoggedIn()) {
            Log.d("tag", "vkLoggin");
            setFragment();
        } else {
            VKSdk.login(mainActivity, scope);
            setFragment();
        }
    }

    @Override
    public void setFragment() {
        mainActivity.addFragments(new MusicFragment());
    }
}
