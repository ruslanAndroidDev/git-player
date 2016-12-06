package com.example.pk.drawproject.main;

import android.util.Log;

import com.example.pk.drawproject.main.MainPresenter;
import com.example.pk.drawproject.main.MainView;
import com.example.pk.drawproject.musicFragment.MusicFragment;
import com.example.pk.drawproject.search.ProgressFragment;
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
    public void clickSearchButton() {
        mainView.showSearchToolbar();
        mainView.showProgressFragment();
    }

    @Override
    public void clickBackButton() {
        mainView.showDefaultToolbar();
        mainView.showMainFragment();
    }

    @Override
    public void login() {
        if (VKSdk.isLoggedIn()) {
            Log.d("tag", "vkLoggin");
            mainView.showMainFragment();
        } else {
            mainView.login();
            mainView.showMainFragment();
        }
    }
}
