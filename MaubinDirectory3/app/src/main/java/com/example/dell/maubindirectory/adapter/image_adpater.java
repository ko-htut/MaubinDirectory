package com.example.dell.maubindirectory.adapter;

import android.content.Context;
import android.net.Uri;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.example.dell.maubindirectory.R;

import java.util.ArrayList;

/**
 * Created by User on 17/08/2017.
 */

public class image_adpater extends RecyclerView.Adapter<image_adpater.viewHolder> {

    ArrayList<Uri> image;
    Context context;

    public image_adpater(ArrayList<Uri> image, Context context) {
        this.image = image;
        this.context = context;
    }

    @Override
    public viewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new viewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.image_item, parent, false));
    }

    @Override
    public void onBindViewHolder(viewHolder holder, int position) {
        Glide.with(context).load(image.get(position)).into(holder.imageView);
    }

    @Override
    public int getItemCount() {
        return image.size();
    }

    class viewHolder extends RecyclerView.ViewHolder {
        public ImageView imageView;

        public viewHolder(View itemView) {
            super(itemView);
            imageView = (ImageView) itemView.findViewById(R.id.imagei);
        }
    }
}
