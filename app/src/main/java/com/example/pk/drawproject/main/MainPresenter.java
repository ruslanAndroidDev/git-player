package com.example.pk.drawproject.main;

import com.example.pk.drawproject.ui.fragments.PlayerFragment;

/**
 * Created by pk on 05.11.2016.
 */
public interface MainPresenter {
    void login();

    void setFragment();

    void showBar(int position);

    void barButtonClick();

    void onDestroy();
}
