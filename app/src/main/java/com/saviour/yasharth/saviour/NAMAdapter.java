package com.saviour.yasharth.saviour;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

public class NAMAdapter extends RecyclerView.Adapter<NAMAdapter.MyViewHolder>{

    private LayoutInflater inflater;
    private List<EmergencyContacts> objectlist;

    public NAMAdapter(Context context, List<EmergencyContacts> objectlist) {
        inflater = LayoutInflater.from(context);
        this.objectlist = objectlist;
    }

    class MyViewHolder extends RecyclerView.ViewHolder{

        private TextView name;
        private TextView phone;
        private ImageView imgDelete;
        private int position;
        private EmergencyContacts currentObject;

        public MyViewHolder(View itemView) {
            super(itemView);
            name = (TextView) itemView.findViewById(R.id.nametv);
            phone = (TextView) itemView.findViewById(R.id.textph);
            imgDelete = (ImageView) itemView.findViewById(R.id.deletename);
        }

        public void setdata(EmergencyContacts current, int position) {
            this.name.setText(current.getName());
            this.phone.setText(current.getPhoneno());
            this.position = position;
            this.currentObject = current;
        }
    }


    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.card_layout,parent,false);
        MyViewHolder holder =  new MyViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        EmergencyContacts current = objectlist.get(position);
        holder.setdata(current,position);

    }

    @Override
    public int getItemCount() {
        return objectlist.size();
    }
}
