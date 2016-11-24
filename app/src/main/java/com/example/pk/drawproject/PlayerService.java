package com.example.pk.drawproject;

import android.app.Notification;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Intent;
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
    // тут зберігаємо url пісень для програшу
    private ArrayList<String> song;

    Notification notification;
    Notification.Builder builder;
    public static final int PLAYER_NOTIFY = 345;

    public static final String PLAY_SOUND = "ua.vkplayer.PLAYSOUND";
    public static final String PAUSE = "ua.vkplayer.PAUSE";
    public static final String RESUME = "ua.vkplayer.RESUME";
    public static final String PLAY_NEXT = "ua.vkplayer.PLAY_NEXT";
    public static final String PLAY_PREVOIUS = "ua.vkplayer.PLAY_PREVOIUS";
    public static final String ADD_PLAYLIST = "ua.vkplayer.ADD_PLAYLIST";
    public final int CODE_PAUSE = 1;


    public void setSong(ArrayList<String> song) {
        this.song = song;
    }

    @Override
    public void onPrepared(MediaPlayer mp) {
        mPlayer.start();
    }

    @Override
    public void onCompletion(MediaPlayer mp) {
        ++currentSoundPosition;
        playSounds(currentSoundPosition);
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
            Log.d("tag", "songSize " + song.size());
            mPlayer.setDataSource(song.get(position));
        } catch (IOException e) {
            e.printStackTrace();
        }
        mPlayer.prepareAsync();
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Log.d("tag", "onStartCommand");
        String flag = intent.getAction();
        if (flag.equals(PAUSE))
            mPlayer.pause();
        if (flag.equals(RESUME))
            mPlayer.start();
        if (flag.equals(PLAY_SOUND)) {
            Log.d("tag", "в playSound рийшло + " + intent.getIntExtra("position", 0));
            playSounds(intent.getIntExtra("position", 0));
            startForeground(PLAYER_NOTIFY, createNotification());
        }

        if (flag.equals(PLAY_NEXT))
            playSounds(++currentSoundPosition);
        if (flag.equals(PLAY_PREVOIUS))
            playSounds(--currentSoundPosition);
        if (flag.equals(ADD_PLAYLIST)) {
            setSong(intent.getStringArrayListExtra("list"));
        }
        return START_NOT_STICKY;
    }

    RemoteViews remoteViews;

    private Notification createNotification() {
        Intent intent1 = new Intent(this, MainActivity.class);
        TaskStackBuilder stackBuilder = TaskStackBuilder.create(this);
        stackBuilder.addParentStack(MainActivity.class);
        stackBuilder.addNextIntent(intent1);
//        PendingIntent pendingIntent = stackBuilder.getPendingIntent(0, PendingIntent.FLAG_UPDATE_CURRENT);
        remoteViews = new RemoteViews(getPackageName(), R.layout.notification);

        Intent pauseIntent = new Intent(getApplicationContext(), PlayerService.class);
        pauseIntent.setAction(PlayerService.PAUSE);

        PendingIntent pause = PendingIntent.getService(this, CODE_PAUSE, pauseIntent, 0);

        remoteViews.setOnClickPendingIntent(R.id.notif_pause, pause);

        builder = new Notification.Builder(this)
                .setSmallIcon(R.drawable.music)
                .setPriority(Notification.PRIORITY_MAX)
                .setContent(remoteViews);
        notification = builder.build();
        notification.bigContentView = remoteViews;
        return notification;
    }


    @Override
    public void onCreate() {
        super.onCreate();
        Log.d("tag", "OnCreate");
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Log.d("tag", "OnDestroy");
    }
}
