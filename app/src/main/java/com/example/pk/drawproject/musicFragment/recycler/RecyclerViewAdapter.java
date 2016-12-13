package com.example.pk.drawproject.musicFragment.recycler;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.pk.drawproject.R;
import com.example.pk.drawproject.data.VkAudioModel;

import java.util.ArrayList;

import es.claucookie.miniequalizerlibrary.EqualizerView;

/**
 * Created by pk on 17.10.2016.
 */
public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewAdapter.MyViewHolder> {
    ArrayList<VkAudioModel> audiodata;
    Context context;
    private RecyclerItemClickListener recyclerItemClickListener;
    int currentPosition = -1;

    public void setRecyclerItemClickListener(RecyclerItemClickListener recyclerItemClickListener) {
        this.recyclerItemClickListener = recyclerItemClickListener;
    }

    public RecyclerViewAdapter(ArrayList<VkAudioModel> audiodata, Context context) {
        this.context = context;
        this.audiodata = audiodata;
    }

    public class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        TextView title;
        TextView group;
        TextView duration;
        Context context;
        EqualizerView equalizer;

        public MyViewHolder(View itemView) {
            super(itemView);
            context = itemView.getContext();
            this.title = (TextView) itemView.findViewById(R.id.titleTv);
            this.equalizer = (EqualizerView) itemView.findViewById(R.id.equalizer);
            this.group = (TextView) itemView.findViewById(R.id.groupTv);
            this.duration = (TextView) itemView.findViewById(R.id.durationTv);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            if (currentPosition == -1) {
                audiodata.get(getAdapterPosition()).setChoise(true);
                currentPosition = getAdapterPosition();
                notifyItemChanged(getAdapterPosition());

                recyclerItemClickListener.onItemClickListener(getAdapterPosition());
            } else {
                Log.d("tag", "currentPosition =! -1");
                audiodata.get(currentPosition).setChoise(false);
                Log.d("tag", " audiodata.get(currentPosition).isChoise" + audiodata.get(currentPosition).isChoise());
                notifyItemChanged(currentPosition);
                audiodata.get(getAdapterPosition()).setChoise(true);
                Log.d("tag", "getAdapterPosition() " + getAdapterPosition() + "включено");
                currentPosition = getAdapterPosition();
                notifyItemChanged(getAdapterPosition());

                recyclerItemClickListener.onItemClickListener(getAdapterPosition());
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
        final EqualizerView equalizerView = holder.equalizer;
        title.setText(audiodata.get(position).getTitle());
        group.setText(audiodata.get(position).getArtist());
        int duration_milisec = Integer.parseInt(audiodata.get(position).getDuration());
        duration.setText(duration_milisec / 60 + ":" + duration_milisec % 60);
        if (audiodata.get(position).isChoise()) {
            equalizerView.setVisibility(View.VISIBLE);
            equalizerView.animateBars();
        } else {
            Log.d("tag", position + " stop animating");
            equalizerView.stopBars();
            equalizerView.setVisibility(View.GONE);
        }
    }

    @Override
    public int getItemCount() {
        return audiodata.size();
    }

}

