package com.example.pk.drawproject.data;


/**
 * Created by pk on 16.10.2016.
 */
public class VkAudioModel{
    private String title;
    private String url;
    private String artist;
    private String duration;

    private String lyrics_id;
    private boolean isChoise;

    public VkAudioModel(String title, String url, String artist, String duration,String lyrics_id) {
        this.title = title;
        this.url = url;
        this.artist = artist;
        this.duration = duration;
        this.isChoise = false;
        this.lyrics_id = lyrics_id;
    }

    public boolean isChoise() {
        return isChoise;
    }

    public void setChoise(boolean choise) {
        isChoise = choise;
    }


    public String getDuration() {
        return duration;
    }

    public String getTitle() {
        return title;
    }

    public String getUrl() {
        return url;
    }

    public String getArtist() {
        return artist;
    }
    public String getLyrics_id() {
        return lyrics_id;
    }

}
