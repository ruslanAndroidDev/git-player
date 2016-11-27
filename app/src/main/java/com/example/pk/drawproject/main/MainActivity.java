package com.example.pk.drawproject.main;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.pk.drawproject.R;
import com.example.pk.drawproject.search.SearchFragment;
import com.vk.sdk.VKAccessToken;
import com.vk.sdk.VKCallback;
import com.vk.sdk.VKScope;
import com.vk.sdk.VKSdk;
import com.vk.sdk.api.VKError;

public class MainActivity extends AppCompatActivity implements MainView, View.OnClickListener {
    ImageButton searchButton;
    MainPresenterImpl presenter;
    FrameLayout playerLayout;
    ImageView btn_back;
    EditText searchEdit;
    TextView toolbarTitle;
    SearchFragment searchFragment;
    private String[] scope = new String[]{VKScope.AUDIO};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        searchFragment = new SearchFragment();
        btn_back = (ImageView) findViewById(R.id.btn_back);
        btn_back.setOnClickListener(this);
        toolbarTitle = (TextView) findViewById(R.id.toolbarTitle);
        searchButton = (ImageButton) findViewById(R.id.searchButton);
        searchButton.setOnClickListener(this);
        searchEdit = (EditText) findViewById(R.id.searchEdit);
        presenter = new MainPresenterImpl(this);
        presenter.login();
        searchEdit.addTextChangedListener(new TextWatcher() {

            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i2, int i3) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i2, int i3) {

            }

            Handler handler = new Handler(Looper.getMainLooper() /*UI thread*/);
            Runnable workRunnable;

            @Override
            public void afterTextChanged(final Editable s) {
                handler.removeCallbacks(workRunnable);
                workRunnable = new Runnable() {
                    @Override
                    public void run() {
                        searchFragment.loadItem(s.toString());
                    }
                };
                handler.postDelayed(workRunnable, 500 /*delay*/);
            }
        });
    }


    @Override
    public void showPlayerFragment() {
        playerLayout.setVisibility(View.VISIBLE);
    }

    @Override
    public void showSearchFragment() {
        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        ft.replace(R.id.main_container, searchFragment);
        ft.commit();
    }


    @Override
    public void login() {
        VKSdk.login(this, scope);
    }

    public void showSearchToolbar() {
        toolbarTitle.setVisibility(View.GONE);
        searchEdit.setVisibility(View.VISIBLE);
        btn_back.setVisibility(View.VISIBLE);
        searchEdit.requestFocus();
        InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.toggleSoftInput(0, 0);
    }

    public void showDefaultToolbar() {
        toolbarTitle.setVisibility(View.VISIBLE);
        searchEdit.setVisibility(View.GONE);
        btn_back.setVisibility(View.GONE);
        InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(searchEdit.getWindowToken(), 0);
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
    public void onClick(View v) {
        if (v.getId() == R.id.searchButton) {
            presenter.clickSearchButton();
            showSearchFragment();

        } else if (v.getId() == R.id.btn_back) {
            presenter.clickBackButton();
        }
    }
}
