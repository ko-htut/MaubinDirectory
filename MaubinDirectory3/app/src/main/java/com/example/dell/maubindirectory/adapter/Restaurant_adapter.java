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
import com.example.dell.maubindirectory.Object.Restaurant;
import com.example.dell.maubindirectory.R;
import com.example.dell.maubindirectory.activity.Res_information;

import java.util.ArrayList;

/**
 * Created by User on 15/08/2017.
 */

public class Restaurant_adapter extends RecyclerView.Adapter<Restaurant_adapter.viewHolder> {


    ArrayList<Restaurant> restaurant_list;
    Context context;

    public Restaurant_adapter(ArrayList<Restaurant> restaurant_list, Context context) {
        this.restaurant_list = restaurant_list;
        this.context = context;
    }

    @Override
    public viewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new viewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.bank_item, parent, false));
    }

    @Override
    public void onBindViewHolder(viewHolder holder, final int position) {
        holder.restaurant_name.setText(restaurant_list.get(position).getName());
        holder.restaurant_category.setText(restaurant_list.get(position).getType());
        Glide.with(context).load(restaurant_list.get(position).getImage_list().get(0)).into(holder.restaurant_logo);
        holder.restaurant_card.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(context.getApplicationContext(), Res_information.class);
                i.putExtra("name", restaurant_list.get(position).getName());
                i.putExtra("address", restaurant_list.get(position).getAddress());
                i.putExtra("type", restaurant_list.get(position).getType());
                i.putExtra("close", restaurant_list.get(position).getClose());
                i.putExtra("open", restaurant_list.get(position).getOpen());
                i.putExtra("phone", restaurant_list.get(position).getPhone());
                i.putExtra("lati", String.valueOf(restaurant_list.get(position).getLati()));

                i.putExtra("long", String.valueOf(restaurant_list.get(position).getLongu()));
                i.putExtra("order", String.valueOf(restaurant_list.get(position).isOrder()));

                ArrayList<Uri> urilist = restaurant_list.get(position).getImage_list();
                i.putExtra("image_list", urilist);
                context.startActivity(i);
            }
        });
    }

    @Override
    public int getItemCount() {
        return restaurant_list.size();
    }

    class viewHolder extends RecyclerView.ViewHolder {

        public CardView restaurant_card;
        public ImageView restaurant_logo;
        public TextView restaurant_name;
        public TextView restaurant_category;

        public viewHolder(View itemView) {
            super(itemView);
            restaurant_card = (CardView) itemView.findViewById(R.id.bank_card);
            restaurant_name = (TextView) itemView.findViewById(R.id.bank_name_card);
            restaurant_category = (TextView) itemView.findViewById(R.id.bank_address_card);
            restaurant_logo = (ImageView) itemView.findViewById(R.id.bank_logo);
        }
    }
}
