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
import com.example.dell.maubindirectory.Object.Bank;
import com.example.dell.maubindirectory.R;
import com.example.dell.maubindirectory.activity.Bank_information;

import java.util.ArrayList;

/**
 * Created by User on 13/08/2017.
 */

public class Bank_adpater extends RecyclerView.Adapter<Bank_adpater.viewHolder> {


    ArrayList<Bank> bank_list;
    Context context;

    public Bank_adpater(ArrayList<Bank> bank_list, Context context) {
        this.bank_list = bank_list;
        this.context = context;
    }

    @Override
    public viewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new viewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.bank_item, parent, false));
    }

    @Override
    public void onBindViewHolder(viewHolder holder, final int position) {
        holder.bank_name.setText(bank_list.get(position).getName());
        holder.bank_address.setText(bank_list.get(position).getAddress());
        Glide.with(context).load(bank_list.get(position).getBank_image_list().get(0)).into(holder.bank_logo);
        holder.bank_card.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(context.getApplicationContext(), Bank_information.class);
                i.putExtra("bank_name", bank_list.get(position).getName());
                i.putExtra("bank_open", bank_list.get(position).getOpentime());
                i.putExtra("bank_close", bank_list.get(position).getClosetime());
                i.putExtra("bank_phone", bank_list.get(position).getPhone());
                i.putExtra("bank_lati", String.valueOf(bank_list.get(position).getLati()));
                i.putExtra("bank_long", String.valueOf(bank_list.get(position).getLongu()));
                i.putExtra("bank_address", bank_list.get(position).getAddress());
                ArrayList<Uri> urilist = bank_list.get(position).getBank_image_list();
                i.putExtra("bank_image_list", urilist);

                context.startActivity(i);
            }
        });
    }

    @Override
    public int getItemCount() {
        return bank_list.size();
    }

    class viewHolder extends RecyclerView.ViewHolder {

        public CardView bank_card;
        public ImageView bank_logo;
        public TextView bank_name;
        public TextView bank_address;



        public viewHolder(View itemView) {
            super(itemView);
            bank_card = (CardView) itemView.findViewById(R.id.bank_card);
            bank_name = (TextView) itemView.findViewById(R.id.bank_name_card);
            bank_address = (TextView) itemView.findViewById(R.id.bank_address_card);
            bank_logo = (ImageView) itemView.findViewById(R.id.bank_logo);

        }
    }
}
