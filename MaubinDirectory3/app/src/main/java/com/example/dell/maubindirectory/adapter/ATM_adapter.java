package com.example.dell.maubindirectory.adapter;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.dell.maubindirectory.Object.ATM;
import com.example.dell.maubindirectory.R;
import com.example.dell.maubindirectory.activity.List_ATM;
import com.example.dell.maubindirectory.activity.MapActivity;

import java.util.ArrayList;

/**
 * Created by User on 15/08/2017.
 */

public class ATM_adapter extends RecyclerView.Adapter<ATM_adapter.viewHolder> {
    Context context;
    ArrayList<ATM> atm_list;
    String cate;

    public ATM_adapter(Context context, ArrayList<ATM> atm_list, String cate) {
        this.context = context;
        this.atm_list = atm_list;
        this.cate = cate;
    }

    @Override
    public viewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new viewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.atm_item, parent, false));
    }

    @Override
    public void onBindViewHolder(final viewHolder holder, final int position) {
        holder.atm_name.setText(" "+atm_list.get(position).getName());
        holder.atm_open.setText(" "+atm_list.get(position).getOpen());
        holder.atm_address.setText(" "+atm_list.get(position).getAddress());
        Glide.with(context).load(atm_list.get(position).getBank_logo()).into(holder.atm_logo);
        holder.atm_map.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(context.getApplicationContext(), MapActivity.class);
                i.putExtra("name", atm_list.get(position).getName());
                i.putExtra("lati", String.valueOf(atm_list.get(position).getLati()));
                i.putExtra("longu", String.valueOf(atm_list.get(position).getLonu()));
                context.startActivity(i);
            }
        });

        holder.atm_share.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent sharingIntent = new Intent(android.content.Intent.ACTION_SEND);
                sharingIntent.setType("text/plain");
                String shareBody = cate + " - " + holder.atm_name.getText() + "\n" + "open time - " + holder.atm_open.getText() + "\n" + "\nAddress - " + holder.atm_address.getText();

                shareBody += "\n\nShare from \"Maubin Directory\" app.";
                sharingIntent.putExtra(android.content.Intent.EXTRA_SUBJECT, "Share from Maubin Directory app");
                sharingIntent.putExtra(android.content.Intent.EXTRA_TEXT, shareBody);
                context.startActivity(Intent.createChooser(sharingIntent, "Share"));
            }
        });

    }

    @Override
    public int getItemCount() {
        return atm_list.size();
    }


    class viewHolder extends RecyclerView.ViewHolder {

        public ImageView atm_logo;
        public TextView atm_name;
        public TextView atm_open;
        public TextView atm_address;
        public Button atm_map;
        public Button atm_share;

        public viewHolder(View itemView) {
            super(itemView);
            atm_name = (TextView) itemView.findViewById(R.id.atm_name);
            atm_open = (TextView) itemView.findViewById(R.id.atm_open);
            atm_address = (TextView) itemView.findViewById(R.id.atm_address);
            atm_logo = (ImageView) itemView.findViewById(R.id.atm_logo);
            atm_map = (Button) itemView.findViewById(R.id.atm_map);
            atm_share = (Button) itemView.findViewById(R.id.atm_share);
        }
    }
}
