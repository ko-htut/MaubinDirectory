package com.example.dell.maubindirectory.adapter;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.dell.maubindirectory.Object.Pagoda;
import com.example.dell.maubindirectory.R;
import com.example.dell.maubindirectory.activity.Pagoda_information;
import com.flaviofaria.kenburnsview.KenBurnsView;

import java.util.ArrayList;

/**
 * Created by User on 12/08/2017.
 */

public class Pagoda_adapter extends RecyclerView.Adapter<Pagoda_adapter.viewHolder> {


    Context _context;
    ArrayList<Pagoda> pagodaList;

    public Pagoda_adapter(Context _context, ArrayList<Pagoda> pagodaList) {
        this._context = _context;
        this.pagodaList = pagodaList;
    }

    @Override
    public viewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new viewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.pagoda_item, parent, false));

    }

    @Override
    public void onBindViewHolder(viewHolder holder, final int position) {
        //holder.kenBurnsView.setImageURI(pagodaList.get(position).getPago_image_Arr().get(0));
        Glide.with(_context).load(pagodaList.get(position).getPago_image_Arr().get(0)).into(holder.kenBurnsView);
        holder.pagoda_name.setText(pagodaList.get(position).getPago_name());
        holder.padoda_year.setText(pagodaList.get(position).getStart_year());
        holder.pagoda_card.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent _intent = new Intent(_context.getApplicationContext(), Pagoda_information.class);
                _intent.putExtra("pagoda_name", pagodaList.get(position).getPago_name());
                _intent.putExtra("pagoda_year", pagodaList.get(position).getStart_year());
                _intent.putExtra("pagoda_open", pagodaList.get(position).getOpentime());
                _intent.putExtra("pagoda_close", pagodaList.get(position).getClosetime());
                _intent.putExtra("pagoda_address", pagodaList.get(position).getAddress());
                _intent.putExtra("pagoda_lati", String.valueOf(pagodaList.get(position).getLati()));
                _intent.putExtra("pagoda_long", String.valueOf(pagodaList.get(position).getLongu()));
                _intent.putExtra("pagoda_height", String.valueOf(pagodaList.get(position).getHeight()));
                ArrayList<Uri> urilist = pagodaList.get(position).getPago_image_Arr();
                _intent.putExtra("image_list", urilist);
                _context.startActivity(_intent);
            }
        });
        holder.pagoda_share.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent sharingIntent = new Intent(android.content.Intent.ACTION_SEND);
                sharingIntent.setType("text/plain");

                String shareBody = "Pagoda - " + pagodaList.get(position).getPago_name() + "\n" + "Address - " + pagodaList.get(position).getAddress();

                shareBody += "\n\nShare from \"Maubin Directory\" app.";
                sharingIntent.putExtra(android.content.Intent.EXTRA_SUBJECT, "Share from Maubin Directory app");
                sharingIntent.putExtra(android.content.Intent.EXTRA_TEXT, shareBody);
                _context.startActivity(Intent.createChooser(sharingIntent, "Share"));
            }
        });


    }

    @Override
    public int getItemCount() {
        return pagodaList.size();
    }

    class viewHolder extends RecyclerView.ViewHolder {

        public CardView pagoda_card;
        public KenBurnsView kenBurnsView;
        public TextView pagoda_name;
        public TextView padoda_year;
        public Button pagoda_share;

        public viewHolder(View itemView) {
            super(itemView);
            pagoda_card = (CardView) itemView.findViewById(R.id.pagoda_card);
            pagoda_name = (TextView) itemView.findViewById(R.id.pagoda_name);
            padoda_year = (TextView) itemView.findViewById(R.id.pagoda_year);
            kenBurnsView = (KenBurnsView) itemView.findViewById(R.id.pagoda_ani);
            pagoda_share = (Button) itemView.findViewById(R.id.shar_pagoda);

        }
    }
}
