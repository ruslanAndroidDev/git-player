package com.example.pk.drawproject.main;

import android.util.Log;

import com.example.pk.drawproject.main.MainPresenter;
import com.example.pk.drawproject.main.MainView;
import com.example.pk.drawproject.musicFragment.MusicFragment;
import com.vk.sdk.VKSdk;

/**
 * Created by pk on 05.11.2016.
 */
public class MainPresenterImpl implements MainPresenter {
    MainView mainView;

    public MainPresenterImpl(MainView mainView) {
        this.mainView = mainView;
    }

    @Override
    public void showBar(int position) {
        mainView.showPlayerFragment();
    }

    @Override
    public void clickSearchButton() {
        mainView.showSearchToolbar();
    }

    @Override
    public void clickBackButton() {
        mainView.showDefaultToolbar();
    }

    @Override
    public void barButtonClick() {
    }

    @Override
    public void login() {
        if (VKSdk.isLoggedIn()) {
            Log.d("tag", "vkLoggin");
            setFragment();
        } else {
            mainView.login();
            setFragment();
        }
    }

    @Override
    public void setFragment() {
        mainView.addFragments(new MusicFragment());
    }
}
