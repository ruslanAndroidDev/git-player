package com.example.pk.drawproject.ui;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.pk.drawproject.MediaPlayerHelper;
import com.example.pk.drawproject.R;
import com.example.pk.drawproject.model.VkAudio;
import com.example.pk.drawproject.myInterfaces.AdapterCallback;

import java.util.ArrayList;

/**
 * Created by pk on 17.10.2016.
 */
public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewAdapter.MyViewHolder> {
    ArrayList<VkAudio> audiodata;
    Context context;
    int audioposition;
    MediaPlayerHelper player;
    private static AdapterCallback mRecyclerAdapterCallback;


    public RecyclerViewAdapter(ArrayList<VkAudio> audiodata, Context context, AdapterCallback callback) {
        this.context = context;
        this.audiodata = audiodata;
        mRecyclerAdapterCallback = callback;
    }
    public static void registerForCallbeck(AdapterCallback callback){
        mRecyclerAdapterCallback = callback;
        Log.d("tag","registerForCallbeck");
    }

    public class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        TextView title;
        TextView group;
        TextView duration;
        Context context;

        public MyViewHolder(View itemView) {
            super(itemView);
            context = itemView.getContext();
            this.title = (TextView) itemView.findViewById(R.id.titleTv);
            this.group = (TextView) itemView.findViewById(R.id.groupTv);
            this.duration = (TextView) itemView.findViewById(R.id.durationTv);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            audioposition = getAdapterPosition();
            mRecyclerAdapterCallback.onRecyclerItemClick(audioposition);
            if (player != null) {
                player.reset();
                player.playSound(audiodata.get(audioposition).getUrl());
            } else {
                player = new MediaPlayerHelper();
                player.playSound(audiodata.get(audioposition).getUrl());
            }
        }

    }


    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.audio_item, parent, false);
        MyViewHolder myViewHolder = new MyViewHolder(view);
        return myViewHolder;
    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, final int position) {
        final TextView title = holder.title;
        final TextView group = holder.group;
        final TextView duration = holder.duration;
        title.setText(audiodata.get(position).getTitle());
        group.setText(audiodata.get(position).getArtist());
        int duration_milisec = Integer.parseInt(audiodata.get(position).getDuration());
        duration.setText(duration_milisec / 60 + ":" + duration_milisec % 60);
    }

    @Override
    public int getItemCount() {
        return audiodata.size();
    }

}

