package com.example.dell.maubindirectory.adapter;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.dell.maubindirectory.Object.Market;
import com.example.dell.maubindirectory.R;
import com.example.dell.maubindirectory.activity.Bank_information;
import com.example.dell.maubindirectory.activity.Market_information;
import com.example.dell.maubindirectory.support.Encode_Decode;

import java.util.ArrayList;

/**
 * Created by User on 15/08/2017.
 */

public class Market_adapter extends RecyclerView.Adapter<Market_adapter.viewHolder> {

    ArrayList<Market> market_list;
    Context context;


    public Market_adapter(ArrayList<Market> market_list, Context context) {
        this.market_list = market_list;
        this.context = context;
    }

    @Override
    public viewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new viewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.bank_item, parent, false));

    }

    @Override
    public void onBindViewHolder(viewHolder holder, final int position) {

        holder.market_name.setText(market_list.get(position).getName());
        holder.market_category.setText(market_list.get(position).getAddress());
        Glide.with(context).load(market_list.get(position).getLogo().get(0)).into(holder.market_logo);
        holder.market_card.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(context.getApplicationContext(), Market_information.class);
                i.putExtra("market_name", market_list.get(position).getName());
                i.putExtra("market_open", market_list.get(position).getOpen_time());
                i.putExtra("market_type", market_list.get(position).getType());
                i.putExtra("market_close", market_list.get(position).getClose_time());
                i.putExtra("market_phone", market_list.get(position).getPhone());
                i.putExtra("market_lati", String.valueOf(market_list.get(position).getLati()));
                i.putExtra("market_long", String.valueOf(market_list.get(position).getLongti()));
                i.putExtra("market_address", market_list.get(position).getAddress());
                ArrayList<Uri> urilist = market_list.get(position).getLogo();
                i.putExtra("market_image_list", urilist);
                context.startActivity(i);
            }
        });
    }

    @Override
    public int getItemCount() {
        return market_list.size();
    }


    class viewHolder extends RecyclerView.ViewHolder {

        public CardView market_card;
        public ImageView market_logo;
        public TextView market_name;
        public TextView market_category;

        public viewHolder(View itemView) {
            super(itemView);
            market_card = (CardView) itemView.findViewById(R.id.bank_card);
            market_name = (TextView) itemView.findViewById(R.id.bank_name_card);
            market_category = (TextView) itemView.findViewById(R.id.bank_address_card);
            market_logo = (ImageView) itemView.findViewById(R.id.bank_logo);

        }
    }
}
