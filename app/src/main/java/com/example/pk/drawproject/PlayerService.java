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
import java.util.ArrayList;

/**
 * Created by pk on 12.11.2016.
 */
public class PlayerService extends Service implements MediaPlayer.OnPreparedListener, MediaPlayer.OnCompletionListener {
    private int currentSoundPosition;
    MediaPlayer mPlayer;
    private final IBinder binder = new PlayerBinder();
    private ArrayList<String> song;

    public void setSong(ArrayList<String> song) {
        this.song = song;
    }

    @Override
    public void onPrepared(MediaPlayer mp) {
        mPlayer.start();
    }

    @Override
    public void onCompletion(MediaPlayer mp) {
        currentSoundPosition++;
        try {
            playSounds(currentSoundPosition);
        } catch (IOException e) {
            e.printStackTrace();
        }
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
        mPlayer.setOnCompletionListener(this);
    }

    public void playSounds(int position) throws IOException {
        currentSoundPosition = position;
        if (mPlayer == null) {
            createPlayer();
        }
        mPlayer.reset();
        mPlayer.setDataSource(song.get(position));
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
