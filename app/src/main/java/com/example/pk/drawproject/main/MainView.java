package com.example.pk.drawproject.main;


/**
 * Created by pk on 05.11.2016.
 */
public interface MainView {
    void showProgressFragment();
    void showMusicListFragment();
    void showSearchToolbar(boolean openKeyboard);
    void showDefaultToolbar();
    void login();
}
