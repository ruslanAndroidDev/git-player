package com.example.pk.drawproject.main;

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
        mainView.showSearchToolbar(true);
        mainView.showProgressFragment();
    }

    @Override
    public void clickBackButton() {
        mainView.showDefaultToolbar();
        mainView.showMusicListFragment();
    }

    @Override
    public void login() {
        if (!VKSdk.isLoggedIn()) {
            mainView.login();
        }
        mainView.showMusicListFragment();
    }
}
