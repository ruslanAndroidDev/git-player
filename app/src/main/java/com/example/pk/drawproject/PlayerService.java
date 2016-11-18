package com.example.pk.drawproject;

import android.app.Service;
import android.content.Intent;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.os.Binder;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.util.Log;

import java.io.IOException;

/**
 * Created by pk on 12.11.2016.
 */
public class PlayerService extends Service implements MediaPlayer.OnPreparedListener {
    private int currentSoundPosition;
    MediaPlayer mPlayer;
    private final IBinder binder = new PlayerBinder();

    @Override
    public void onPrepared(MediaPlayer mp) {
        mPlayer.start();
    }

    public class PlayerBinder extends Binder {
        public PlayerService getPlayer() {
            return PlayerService.this;
        }
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        Log.d("tag", "onBindService: ");
        return binder;
    }

    public void createPlayer() {
        mPlayer = new MediaPlayer();
        mPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);
        mPlayer.setOnPreparedListener(this);
    }

    public void playSounds(String url) throws IOException {
        //currentSoundPosition = position;
        if (mPlayer == null) {
            createPlayer();
        }
        mPlayer.setDataSource(url);
        mPlayer.prepareAsync();
    }

    public void pause() {
        mPlayer.pause();
    }

    public void resumePlaying() {
        mPlayer.start();
    }

    public boolean isPlaying() {
        return mPlayer.isPlaying();
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Log.d("tag", "onStartCommand");
        return super.onStartCommand(intent, flags, startId);

    }
}
