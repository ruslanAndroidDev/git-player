package com.example.pk.drawproject.MainActivity;

import android.util.Log;

import com.example.pk.drawproject.musicFragment.MusicFragment;
import com.vk.sdk.VKScope;
import com.vk.sdk.VKSdk;

/**
 * Created by pk on 05.11.2016.
 */
public class MainPresenter implements MainPresenterInterface {
    MainView mainView;
    MusicFragment musicFragment;

    private String[] scope = new String[]{VKScope.AUDIO};

    public MainPresenter(MainView mainView) {
        this.mainView = mainView;
        Log.d("tag", "MainPresenterConstructor");
    }

    @Override
    public void vkLoggin() {
        VKSdk.login(mainView, scope);
    }

    @Override
    public void isLoggedIn() {
        if (VKSdk.isLoggedIn()) {
            Log.d("tag", "vkLoggin");
            setFragment();
        } else {
            VKSdk.login(mainView, scope);
            setFragment();
        }
    }


    @Override
    public void setFragment() {
        musicFragment = new MusicFragment();
        Log.d("tag", "preSetFragment");
        mainView.setFragment(musicFragment);
        Log.d("tag","setFragment");
    }
}
