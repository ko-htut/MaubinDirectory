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
import com.example.dell.maubindirectory.Object.University;
import com.example.dell.maubindirectory.R;
import com.example.dell.maubindirectory.activity.Pagoda_information;
import com.example.dell.maubindirectory.activity.University_information;
import com.example.dell.maubindirectory.support.Encode_Decode;

import java.util.ArrayList;

/**
 * Created by User on 13/08/2017.
 */

public class University_adapter extends RecyclerView.Adapter<University_adapter.viewHolder> {

    Context context;
    ArrayList<University> uni_list;

    public University_adapter(Context context, ArrayList<University> uni_list) {
        this.context = context;
        this.uni_list = uni_list;
    }

    @Override
    public viewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new viewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.uni_item, parent, false));
    }

    @Override
    public void onBindViewHolder(viewHolder holder, final int position) {
        Encode_Decode decode = new Encode_Decode();
        Glide.with(context).load(uni_list.get(position).getUni_image().get(0)).into(holder.uni_logo);
        Glide.with(context).load(uni_list.get(position).getUni_image().get(1)).into(holder.uni_ani);
        holder.uni_name.setText(uni_list.get(position).getName());
        holder.uni_card.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //TODO
                Intent _intent = new Intent(context.getApplicationContext(), University_information.class);
                _intent.putExtra("uni_name", uni_list.get(position).getName());
                _intent.putExtra("uni_rector", uni_list.get(position).getRector());
                _intent.putExtra("first_sem", uni_list.get(position).getFirst_sem());
                _intent.putExtra("second_sem", uni_list.get(position).getSecond_sem());
                _intent.putExtra("uni_phone", uni_list.get(position).getPhone());
                _intent.putExtra("uni_lati", String.valueOf(uni_list.get(position).getLati()));
                _intent.putExtra("uni_long", String.valueOf(uni_list.get(position).getLongu()));
                _intent.putExtra("uni_address", uni_list.get(position).getAddress());
                _intent.putExtra("uni_major", uni_list.get(position).getMajor());

                ArrayList<Uri> urilist = uni_list.get(position).getUni_image();
                _intent.putExtra("image_list", urilist);
                context.startActivity(_intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return uni_list.size();
    }

    class viewHolder extends RecyclerView.ViewHolder {

        public CardView uni_card;
        public ImageView uni_logo, uni_ani;
        public TextView uni_name;


        public viewHolder(View itemView) {
            super(itemView);
            uni_card = (CardView) itemView.findViewById(R.id.uni_card);
            uni_name = (TextView) itemView.findViewById(R.id.uni_name);
            uni_logo = (ImageView) itemView.findViewById(R.id.uni_logo);
            uni_ani = (ImageView) itemView.findViewById(R.id.uni_ani);

        }
    }
}
