package com.example.pk.drawproject.main;

import android.support.v4.app.Fragment;

/**
 * Created by pk on 05.11.2016.
 */
public interface MainView {
    void addFragments(Fragment fragment);
    void showPlayerFragment();
    void login();
}
