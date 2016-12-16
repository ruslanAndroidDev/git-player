package com.example.pk.drawproject;

import android.app.Notification;
import android.app.PendingIntent;
import android.app.Service;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.support.v4.app.TaskStackBuilder;
import android.util.Log;
import android.widget.RemoteViews;

import com.example.pk.drawproject.main.MainActivity;

import java.io.IOException;
import java.util.ArrayList;

/**
 * Created by pk on 12.11.2016.
 */
public class PlayerService extends Service implements MediaPlayer.OnPreparedListener, MediaPlayer.OnCompletionListener {
    private int currentSoundPosition;
    MediaPlayer mPlayer;

    Notification notification;
    Notification.Builder builder;
    public static final int PLAYER_NOTIFY = 345;

    public static final String PLAY_SOUND = "ua.vkplayer.PLAYSOUND";
    public static final String SET_DATA = "ua.vkplayer.SET_DATA";
    public static final String MAIN_BTN = "ua.vkplayer.MAIN_BTN";
    public static final String RESUME = "ua.vkplayer.RESUME";
    public static final String PLAY_NEXT = "ua.vkplayer.PLAY_NEXT";
    public static final String PLAY_PREVOIUS = "ua.vkplayer.PLAY_PREVOIUS";
    public static final String CLOSE_SERVICE = "ua.vkplayer.CLOSE_SERVICE";
    public final int CODE_PAUSE = 1;
    public final int CODE_NEXT = 2;
    public final int CODE_PREVIOUS = 3;
    public final int CODE_CLOSE = 4;

    public ArrayList<String> title;
    public ArrayList<String> url;

    MusicIntentReceiver musicIntentReceiver;

    @Override
    public void onCreate() {
        super.onCreate();
        musicIntentReceiver = new MusicIntentReceiver();
        IntentFilter filter = new IntentFilter(Intent.ACTION_HEADSET_PLUG);
        registerReceiver(musicIntentReceiver, filter);
    }

    @Override
    public void onPrepared(MediaPlayer mp) {
        mPlayer.start();
    }

    @Override
    public void onCompletion(MediaPlayer mp) {
        playSounds(++currentSoundPosition);
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    public void createPlayer() {
        mPlayer = new MediaPlayer();
        mPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);
        mPlayer.setOnPreparedListener(this);
        mPlayer.setOnCompletionListener(this);
    }

    public void playSounds(int position) {
        currentSoundPosition = position;
        if (mPlayer == null) {
            createPlayer();
        }
        mPlayer.reset();
        try {
            mPlayer.setDataSource(url.get(position));
        } catch (IOException e) {
            e.printStackTrace();
        }
        mPlayer.prepareAsync();
        updateNotification();
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Log.d("tag", "onStartCommand");
        String flag = intent.getAction();
        Log.d("tag", "flag" + flag);
        if (flag.equals(SET_DATA)) {
            url = intent.getStringArrayListExtra("url");
            title = intent.getStringArrayListExtra("title");
            Log.d("tag", "setItems,data.size + service" + url.size());
        }
        if (flag.equals(MAIN_BTN)) {
            int icon;
            //1 - SetPauseIcon
            //2 - SetStartIcon
            if (mPlayer.isPlaying()) {
                mPlayer.pause();
                icon = 2;
            } else {
                mPlayer.start();
                icon = 1;
            }
            updateNotification(icon);
        }
        if (flag.equals(RESUME))
            mPlayer.start();
        if (flag.equals(PLAY_SOUND)) {
            Log.d("tag", "в playSound рийшло + " + intent.getIntExtra("position", 0));
            playSounds(intent.getIntExtra("position", 0));
            startForeground(PLAYER_NOTIFY, notification);
        }

        if (flag.equals(PLAY_NEXT))
            playSounds(++currentSoundPosition);
        if (flag.equals(PLAY_PREVOIUS))
            playSounds(--currentSoundPosition);
        if (flag.equals(CLOSE_SERVICE)) {
            mPlayer.stop();
            mPlayer.reset();
            stopForeground(true);
        }
        return START_NOT_STICKY;
    }

    RemoteViews remoteViews;

    private void createNotification() {
        Intent intent1 = new Intent(this, MainActivity.class);
        TaskStackBuilder stackBuilder = TaskStackBuilder.create(this);
        stackBuilder.addParentStack(MainActivity.class);
        stackBuilder.addNextIntent(intent1);
        remoteViews = new RemoteViews(getPackageName(), R.layout.notification);
        remoteViews.setTextViewText(R.id.notify_tv_title, title.get(currentSoundPosition));

        Intent maim_btnIntent = new Intent(getApplicationContext(), PlayerService.class);
        maim_btnIntent.setAction(PlayerService.MAIN_BTN);

        Intent nextIntent = new Intent(getApplicationContext(), PlayerService.class);
        nextIntent.setAction(PlayerService.PLAY_NEXT);

        Intent previousIntent = new Intent(getApplicationContext(), PlayerService.class);
        previousIntent.setAction(PlayerService.PLAY_PREVOIUS);

        Intent closeIntent = new Intent(getApplicationContext(), PlayerService.class);
        closeIntent.setAction(CLOSE_SERVICE);

        PendingIntent maim_btn = PendingIntent.getService(this, CODE_PAUSE, maim_btnIntent, 0);
        PendingIntent next = PendingIntent.getService(this, CODE_NEXT, nextIntent, 0);
        PendingIntent previous = PendingIntent.getService(this, CODE_PREVIOUS, previousIntent, 0);
        PendingIntent close = PendingIntent.getService(this, CODE_CLOSE, closeIntent, 0);

        remoteViews.setOnClickPendingIntent(R.id.notif_maim_btn, maim_btn);
        remoteViews.setOnClickPendingIntent(R.id.notif_next, next);
        remoteViews.setOnClickPendingIntent(R.id.notif_previous, previous);
        remoteViews.setOnClickPendingIntent(R.id.notif_close, close);

        builder = new Notification.Builder(this)
                .setSmallIcon(R.drawable.music)
                .setPriority(Notification.PRIORITY_MAX)
                .setContent(remoteViews);
        notification = builder.build();
        notification.bigContentView = remoteViews;
    }

    private void updateNotification() {
        if (remoteViews == null)
            createNotification();
        remoteViews.setTextViewText(R.id.notify_tv_title, title.get(currentSoundPosition));
        startForeground(PLAYER_NOTIFY, notification);
    }

    private void updateNotification(int btnFlag) {
        if (btnFlag == 1) {
            remoteViews.setImageViewResource(R.id.notif_maim_btn, R.drawable.ic_pause_white_48dp);
        } else {
            remoteViews.setImageViewResource(R.id.notif_maim_btn, R.drawable.ic_play);
        }
        startForeground(PLAYER_NOTIFY, notification);
    }

    private class MusicIntentReceiver extends BroadcastReceiver {
        @Override
        public void onReceive(Context context, Intent intent) {
            if (intent.getAction().equals(Intent.ACTION_HEADSET_PLUG)) {
                int state = intent.getIntExtra("state", -1);
                switch (state) {
                    case 0:
                        // Headset is unplugged
                        if (mPlayer==null){

                        }else if (mPlayer.isPlaying()) {
                            mPlayer.pause();
                            int icon = 2;
                            updateNotification(icon);
                        }
                        break;
                    case 1:
                        //Headset is plugged
                        break;
                }
            }
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        unregisterReceiver(musicIntentReceiver);
    }
}
