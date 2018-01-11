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
import com.example.dell.maubindirectory.Object.Hospital;
import com.example.dell.maubindirectory.R;
import com.example.dell.maubindirectory.activity.MapActivity;

import java.util.ArrayList;

/**
 * Created by User on 15/08/2017.
 */

public class Hospital_adpater extends RecyclerView.Adapter<Hospital_adpater.viewHolder> {
    ArrayList<Hospital> hosp_list;
    Context context;

    public Hospital_adpater(ArrayList<Hospital> hosp_list, Context context) {
        this.hosp_list = hosp_list;
        this.context = context;
    }

    @Override
    public viewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new viewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.hospital_item, parent, false));
    }

    @Override
    public void onBindViewHolder(final viewHolder holder, final int position) {

        holder.hospital_name.setText(" "+hosp_list.get(position).getName());
        holder.hospital_phone.setText(" "+hosp_list.get(position).getPhone());
        holder.hospital_address.setText(" "+hosp_list.get(position).getAddress());
        Glide.with(context).load(hosp_list.get(position).getImage()).into(holder.hospital_logo);
        holder.hospital_map.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(context.getApplicationContext(), MapActivity.class);
                i.putExtra("name", hosp_list.get(position).getName());
                i.putExtra("lati", String.valueOf(hosp_list.get(position).getLati()));
                i.putExtra("longu", String.valueOf(hosp_list.get(position).getLonu()));
                context.startActivity(i);
            }
        });

        holder.hospital_emergency.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(Intent.ACTION_VIEW, Uri.parse("tel:999"));
                context.startActivity(i);
            }
        });
        holder.hospital_share.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent sharingIntent = new Intent(android.content.Intent.ACTION_SEND);
                sharingIntent.setType("text/plain");

                String shareBody = "Hospital - " + holder.hospital_name.getText() + "\n" + "Phone - " + holder.hospital_phone.getText() + "\n" + "Address - " + holder.hospital_address.getText();

                shareBody += "\n\nShare from \"Maubin Directory\" app.";
                sharingIntent.putExtra(android.content.Intent.EXTRA_SUBJECT, "Share from Maubin Directory app");
                sharingIntent.putExtra(android.content.Intent.EXTRA_TEXT, shareBody);
                context.startActivity(Intent.createChooser(sharingIntent, "Share"));
            }
        });
    }

    @Override
    public int getItemCount() {
        return hosp_list.size();
    }

    class viewHolder extends RecyclerView.ViewHolder {

        public ImageView hospital_logo;
        public TextView hospital_name;
        public TextView hospital_phone;
        public TextView hospital_address;
        public Button hospital_map;
        public Button hospital_share;
        public Button hospital_emergency;

        public viewHolder(View itemView) {
            super(itemView);
            hospital_name = (TextView) itemView.findViewById(R.id.hospital_name);
            hospital_phone = (TextView) itemView.findViewById(R.id.hospital_phone);
            hospital_address = (TextView) itemView.findViewById(R.id.hospital_address);
            hospital_logo = (ImageView) itemView.findViewById(R.id.hospital_logo);
            hospital_map = (Button) itemView.findViewById(R.id.hospital_map);
            hospital_share = (Button) itemView.findViewById(R.id.hospital_share);
            hospital_emergency = (Button) itemView.findViewById(R.id.hospital_call);

        }
    }
}
