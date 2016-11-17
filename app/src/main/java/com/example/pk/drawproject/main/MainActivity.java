package com.example.pk.drawproject.main;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageButton;

import com.example.pk.drawproject.PlayerService;
import com.example.pk.drawproject.R;
import com.vk.sdk.VKAccessToken;
import com.vk.sdk.VKCallback;
import com.vk.sdk.VKScope;
import com.vk.sdk.VKSdk;
import com.vk.sdk.api.VKError;

public class MainActivity extends AppCompatActivity implements MainView {
    ImageButton searchButton;
    MainPresenterImpl presenter;
    FrameLayout playerLayout;
    private String[] scope = new String[]{VKScope.AUDIO};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        searchButton = (ImageButton) findViewById(R.id.searchButton);
        playerLayout = (FrameLayout) findViewById(R.id.player_container);
        Log.d("tag", "OnCreateMainActivity");

        presenter = new MainPresenterImpl(this);
        presenter.login();
    }


    @Override
    public void showPlayerFragment() {
        playerLayout.setVisibility(View.VISIBLE);
    }

    @Override
    public void destroyServise() {
        stopService(new Intent(this, PlayerService.class));
    }

    @Override
    public void login() {
        VKSdk.login(this, scope);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (!VKSdk.onActivityResult(requestCode, resultCode, data, new VKCallback<VKAccessToken>() {
            @Override
            public void onResult(VKAccessToken res) {
                presenter.setFragment();
            }

            @Override
            public void onError(VKError error) {
// Произошла ошибка авторизации (например, пользователь запретил авторизацию)
            }
        })) {
            super.onActivityResult(requestCode, resultCode, data);
        }
    }

    @Override
    public void addFragments(Fragment fragment) {
        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        ft.add(R.id.main_container, fragment);
        ft.commit();
        FragmentTransaction playertransaction = getSupportFragmentManager().beginTransaction();
       // playertransaction.add(R.id.player_container, new PlayerFragment());
        playertransaction.commit();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        presenter.onDestroy();

    }
}
