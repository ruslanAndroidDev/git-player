package com.example.pk.drawproject.data;


/**
 * Created by pk on 16.10.2016.
 */
public class VkAudioModel{
    private String title;
    private String url;
    private String artist;
    private String duration;
    private boolean isChoise;

    public VkAudioModel(String title, String url, String artist, String duration) {
        this.title = title;
        this.url = url;
        this.artist = artist;
        this.duration = duration;
        this.isChoise = false;
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

}
