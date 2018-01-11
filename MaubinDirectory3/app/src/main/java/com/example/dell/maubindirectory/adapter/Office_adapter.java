package com.example.dell.maubindirectory.adapter;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Typeface;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.dell.maubindirectory.Object.Office;
import com.example.dell.maubindirectory.R;
import com.example.dell.maubindirectory.activity.MapActivity;
import com.example.dell.maubindirectory.support.Encode_Decode;

import java.util.ArrayList;


/**
 * Created by User on 16/08/2017.
 */

public class Office_adapter extends RecyclerView.Adapter<Office_adapter.viewHolder> {

    private Context _context;
    private ArrayList<Office> _listOfficeChild;

    public Office_adapter(Context context,
                          ArrayList<Office> _listOfficeChild) {
        this._context = context;
        this._listOfficeChild = _listOfficeChild;
    }

    @Override
    public viewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new viewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item, parent, false));
    }

    @Override
    public void onBindViewHolder(final viewHolder holder, final int position) {

        holder.name.setText("\t\t" + _listOfficeChild.get(position).getName());
        holder.phone.setText("\t\t" + _listOfficeChild.get(position).getPhone());
        holder.address.setText("\t\t" + _listOfficeChild.get(position).getAddress());
        Glide.with(_context).load(_listOfficeChild.get(position).getLogo()).into(holder.logo);
        holder.map.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(_context.getApplicationContext(), MapActivity.class);
                i.putExtra("name", _listOfficeChild.get(position).getName());
                i.putExtra("lati", String.valueOf(_listOfficeChild.get(position).getLati()));
                i.putExtra("longu", String.valueOf(_listOfficeChild.get(position).getLongu()));
                _context.startActivity(i);
            }
        });

        holder.share.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent sharingIntent = new Intent(android.content.Intent.ACTION_SEND);
                sharingIntent.setType("text/plain");

                String shareBody = "Office - " + holder.name.getText() + "\n" + "Phone - " + holder.phone.getText() + "\nAddress - " + holder.address.getText();

                shareBody += "\n\nShare from \"Maubin Directory\" app.";
                sharingIntent.putExtra(android.content.Intent.EXTRA_SUBJECT, "Share from Maubin Directory app");
                sharingIntent.putExtra(android.content.Intent.EXTRA_TEXT, shareBody);
                _context.startActivity(Intent.createChooser(sharingIntent, "Share"));
            }
        });
    }

    @Override
    public int getItemCount() {
        return _listOfficeChild.size();
    }


    class viewHolder extends RecyclerView.ViewHolder {

        public TextView name;
        public TextView phone;
        public TextView address;
        public ImageView logo;
        public Button map;
        public Button share;

        public viewHolder(View itemView) {
            super(itemView);
            name = (TextView) itemView.findViewById(R.id.office_name);
            logo = (ImageView) itemView.findViewById(R.id.office_logo);
            phone = (TextView) itemView.findViewById(R.id.office_phone);
            address = (TextView) itemView.findViewById(R.id.office_address);
            map = (Button) itemView.findViewById(R.id.office_map);
            share = (Button) itemView.findViewById(R.id.office_share);

        }
    }
}
