package com.example.pk.drawproject.interfaces;

import com.example.pk.drawproject.ui.activies.MainActivity;
import com.example.pk.drawproject.ui.fragments.PlayerFragment;

/**
 * Created by pk on 05.11.2016.
 */
public interface MainPresenterInterface {
    void login();

    void setFragment();

    void attachView(MainActivity mainActivity);

    void attachPlayerFragment(PlayerFragment fragment);

    void showBar(int position);

    void barButtonClick();

    void destroy();
}
