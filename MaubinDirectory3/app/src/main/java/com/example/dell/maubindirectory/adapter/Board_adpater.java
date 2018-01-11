package com.example.dell.maubindirectory.adapter;

import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.example.dell.maubindirectory.Object.Board;
import com.example.dell.maubindirectory.R;
import com.example.dell.maubindirectory.activity.MapActivity;

import java.util.ArrayList;

/**
 * Created by User on 16/08/2017.
 */

public class Board_adpater extends RecyclerView.Adapter<Board_adpater.viewHolder> {


    ArrayList<Board> board_list;
    Context context;

    public Board_adpater(ArrayList<Board> board_list, Context context) {
        this.board_list = board_list;
        this.context = context;
    }

    @Override
    public viewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new viewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.board_item, parent, false));
    }

    @Override
    public void onBindViewHolder(final viewHolder holder, final int position) {
        holder.name.setText("\t\t" + board_list.get(position).getName());
        holder.price.setText("\t\t" + board_list.get(position).getPrice_per_month() + " kyats per month");
        holder.phone.setText("\t\t" + board_list.get(position).getPhone());
        holder.address.setText("\t\t" + board_list.get(position).getAddress());
        if (board_list.get(position).getGender().toLowerCase().equals("female") || board_list.get(position).getGender().toLowerCase().equals("woman")) {
            holder.gender.setText("\t\tWoman");
            holder.gender.setCompoundDrawablesWithIntrinsicBounds(R.drawable.human_female, 0, 0, 0);
        } else {
            holder.gender.setText("\t\tMan");
            holder.gender.setCompoundDrawablesWithIntrinsicBounds(R.drawable.human_male, 0, 0, 0);
        }
        //holder.gender.setText("\t\t" + board_list.get(position).getGender());

        holder.map.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(context.getApplicationContext(), MapActivity.class);
                i.putExtra("name", board_list.get(position).getName());
                i.putExtra("lati", String.valueOf(board_list.get(position).getLati()));
                i.putExtra("longu", String.valueOf(board_list.get(position).getLongu()));
                context.startActivity(i);
            }
        });

        holder.share.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent sharingIntent = new Intent(android.content.Intent.ACTION_SEND);
                sharingIntent.setType("text/plain");

                String shareBody = "Boarding house - " + board_list.get(position).getName() + "(" + board_list.get(position).getGender() + ")" + "\n" + "Price per month - " + board_list.get(position).getPrice_per_month() + " kyats per month" + "\n" + "Phone - " + board_list.get(position).getPhone() + "\nAddress - " + board_list.get(position).getAddress();

                shareBody += "\n\nShare from \"Maubin Directory\" app.";
                sharingIntent.putExtra(android.content.Intent.EXTRA_SUBJECT, "Share from Maubin Directory app");
                sharingIntent.putExtra(android.content.Intent.EXTRA_TEXT, shareBody);
                context.startActivity(Intent.createChooser(sharingIntent, "Share"));
            }
        });
    }

    @Override
    public int getItemCount() {
        return board_list.size();
    }

    class viewHolder extends RecyclerView.ViewHolder {

        public TextView name;
        public TextView price;
        public TextView phone;
        public TextView address;
        public TextView gender;

        public Button map;
        public Button share;

        public viewHolder(View itemView) {
            super(itemView);
            name = (TextView) itemView.findViewById(R.id.b_name);
            price = (TextView) itemView.findViewById(R.id.price_per_month);
            phone = (TextView) itemView.findViewById(R.id.b_phone);
            address = (TextView) itemView.findViewById(R.id.b_address);
            map = (Button) itemView.findViewById(R.id.b_map);
            share = (Button) itemView.findViewById(R.id.b_share);
            gender = (TextView) itemView.findViewById(R.id.b_gender);
        }
    }
}
