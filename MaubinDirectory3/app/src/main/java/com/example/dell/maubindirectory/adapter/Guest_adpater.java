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
import com.example.dell.maubindirectory.Object.GuestHouse;
import com.example.dell.maubindirectory.R;
import com.example.dell.maubindirectory.activity.Guest_informatin;
import com.example.dell.maubindirectory.activity.Market_information;

import java.util.ArrayList;

/**
 * Created by User on 15/08/2017.
 */

public class Guest_adpater extends RecyclerView.Adapter<Guest_adpater.viewHolder> {
    ArrayList<GuestHouse> guest_list;
    Context context;


    public Guest_adpater(ArrayList<GuestHouse> guest_list, Context context) {
        this.guest_list = guest_list;
        this.context = context;
    }

    @Override
    public viewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new viewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.bank_item, parent, false));
    }

    @Override
    public void onBindViewHolder(viewHolder holder,final int position) {
        holder.market_name.setText(guest_list.get(position).getName());
        holder.market_category.setText(guest_list.get(position).getAddress());
        Glide.with(context).load(guest_list.get(position).getImage_list().get(0)).into(holder.market_logo);
        holder.market_card.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(context.getApplicationContext(), Guest_informatin.class);
                i.putExtra("guest_name", guest_list.get(position).getName());
                i.putExtra("guest_type", guest_list.get(position).getType());
                i.putExtra("guest_address", guest_list.get(position).getAddress());
                i.putExtra("guest_phone", guest_list.get(position).getPhone());
                i.putExtra("guest_lati", String.valueOf(guest_list.get(position).getLati()));
                i.putExtra("guest_long", String.valueOf(guest_list.get(position).getLongti()));
                ArrayList<Uri> urilist = guest_list.get(position).getImage_list();
                i.putExtra("guest_image_list", urilist);
                context.startActivity(i);
            }
        });
    }

    @Override
    public int getItemCount() {
        return guest_list.size();
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
