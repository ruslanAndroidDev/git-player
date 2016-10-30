package com.example.pk.drawproject.model;

/**
 * Created by pk on 16.10.2016.
 */
public class VkAudio {
    private String title;
    private String url;
    private String artist;
    private String duration;

    public VkAudio(String title, String url, String artist, String duration) {
        this.title = title;
        this.url = url;
        this.artist = artist;
        this.duration = duration;
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
