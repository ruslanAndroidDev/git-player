package com.example.pk.drawproject;

import android.media.AudioManager;
import android.media.MediaPlayer;
import android.os.AsyncTask;
import android.util.Log;

import java.io.IOException;

/**
 * Created by pk on 24.10.2016.
 */
public class MediaPlayerHelper {
    static MediaPlayer player;

    public static MediaPlayer getPlayer() {
        if (player!=null) {
            return player;
        }
        else {
            player = new MediaPlayer();
            player.setAudioStreamType(AudioManager.STREAM_MUSIC);
            return player;
        }
    }


    public MediaPlayerHelper() {
        Log.d("tag", "MediaPlayer constructor");
        player = new MediaPlayer();
        player.setAudioStreamType(AudioManager.STREAM_MUSIC);
    }

    public void playSound(String url) {
        Log.d("tag", "MediaPlayer - playSound");
        AudioPlayerTask task = new AudioPlayerTask();
        task.execute(url);
    }

    public void reset() {
        Log.d("tag", "MediaPlayer - reset");
        player.reset();
    }


    class AudioPlayerTask extends AsyncTask<String, Void, Void> {

        @Override
        protected Void doInBackground(String... params) {
            try {
                player.setDataSource(params[0]);
                player.prepare();
            } catch (IOException e) {
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            player.start();
        }
    }
}
