package com.example.dell.maubindirectory.adapter;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.dell.maubindirectory.Object.Other;
import com.example.dell.maubindirectory.R;
import com.example.dell.maubindirectory.activity.List_image;
import com.example.dell.maubindirectory.activity.MapActivity;

import java.util.ArrayList;

/**
 * Created by User on 15/08/2017.
 */

public class Other_adapter extends RecyclerView.Adapter<Other_adapter.viewHolder> {
    ArrayList<Other> other_list;
    Context context;

    public Other_adapter(ArrayList<Other> other_list, Context context) {
        this.other_list = other_list;
        this.context = context;
    }

    @Override
    public viewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new viewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.other_place_item, parent, false));
    }

    @Override
    public void onBindViewHolder(final viewHolder holder, final int position) {
        holder.name.setText(" "+other_list.get(position).getName());
        holder.address.setText(" "+other_list.get(position).getAddress());
        Glide.with(context).load(other_list.get(position).getImage_list().get(0)).into(holder.logo);

        holder.map.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(context.getApplicationContext(), MapActivity.class);
                i.putExtra("name", other_list.get(position).getName());
                i.putExtra("lati", String.valueOf(other_list.get(position).getLati()));
                i.putExtra("longu", String.valueOf(other_list.get(position).getLongu()));
                context.startActivity(i);
            }
        });

        holder.share.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent sharingIntent = new Intent(android.content.Intent.ACTION_SEND);
                sharingIntent.setType("text/plain");

                String shareBody = "Pagoda - " + holder.name.getText() + "\n" + "Address - " + holder.address.getText();

                shareBody += "\n\nShare from \"Maubin Directory\" app.";
                sharingIntent.putExtra(android.content.Intent.EXTRA_SUBJECT, "Share from Maubin Directory app");
                sharingIntent.putExtra(android.content.Intent.EXTRA_TEXT, shareBody);
                context.startActivity(Intent.createChooser(sharingIntent, "Share"));
            }
        });

        holder.photo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(context, List_image.class);
                i.putExtra("title", holder.name.getText());
                ArrayList<Uri> urilist = other_list.get(position).getImage_list();
                i.putExtra("image_list", urilist);
                context.startActivity(i);
            }
        });
    }

    @Override
    public int getItemCount() {
        return other_list.size();
    }


    class viewHolder extends RecyclerView.ViewHolder {

        public ImageView logo;
        public TextView name;
        public TextView address;
        public Button map;
        public Button share;
        public Button photo;

        public viewHolder(View itemView) {
            super(itemView);
            name = (TextView) itemView.findViewById(R.id.hospital_name);
            address = (TextView) itemView.findViewById(R.id.hospital_address);
            logo = (ImageView) itemView.findViewById(R.id.hospital_logo);
            map = (Button) itemView.findViewById(R.id.hospital_map);
            share = (Button) itemView.findViewById(R.id.hospital_share);
            photo = (Button) itemView.findViewById(R.id.hospital_call);
        }
    }
}
