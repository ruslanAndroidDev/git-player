package com.example.pk.drawproject.model;

import java.util.ArrayList;

/**
 * Created by pk on 06.11.2016.
 */
public interface OnDataLoadInterface {

    interface DataLoadedCallBack {
        void onDataLoadSucces(ArrayList<VkAudioModel> vkAudios);
    }
}
