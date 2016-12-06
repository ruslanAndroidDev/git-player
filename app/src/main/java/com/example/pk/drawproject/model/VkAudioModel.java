package com.example.pk.drawproject.model;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by pk on 16.10.2016.
 */
public class VkAudioModel implements Parcelable {
    private String title;
    private String url;
    private String artist;
    private String duration;

    public VkAudioModel(String title, String url, String artist, String duration) {
        this.title = title;
        this.url = url;
        this.artist = artist;
        this.duration = duration;
    }

    protected VkAudioModel(Parcel in) {
        title = in.readString();
        url = in.readString();
        artist = in.readString();
        duration = in.readString();
    }

    public static final Creator<VkAudioModel> CREATOR = new Creator<VkAudioModel>() {
        @Override
        public VkAudioModel createFromParcel(Parcel in) {
            return new VkAudioModel(in);
        }

        @Override
        public VkAudioModel[] newArray(int size) {
            return new VkAudioModel[size];
        }
    };

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

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(title);
        dest.writeString(url);
        dest.writeString(artist);
        dest.writeString(duration);
    }
}
