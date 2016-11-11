package com.example.pk.drawproject.model;

import java.util.ArrayList;

/**
 * Created by pk on 06.11.2016.
 */
public interface ModelInterface {
   void getVkSoundListWithListener(DataLoadedCallBack callBack);


    interface DataLoadedCallBack {
        void onDataLoadSucces(ArrayList<VkAudio> vkAudios);

       // void onDataLoadError();
    }
}
