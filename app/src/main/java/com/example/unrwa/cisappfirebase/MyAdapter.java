package com.example.unrwa.cisappfirebase;

import android.content.Context;
import android.provider.ContactsContract;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class MyAdapter extends RecyclerView.Adapter<MyAdapter.MyViewHolder>  {


    Context context;
    ArrayList<Model> profiles;

    public MyAdapter(Context c, ArrayList<Model> p){
        context=c;
        profiles=p;

    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new MyViewHolder(LayoutInflater.from(context).inflate(R.layout.cardview,parent,false));
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        holder.ename.setText(profiles.get(position).getName());
        holder.eage.setText(profiles.get(position).getAge());
        holder.ephone.setText(profiles.get(position).getPhone());
        Picasso.with(context).load(profiles.get(position).getProfilepic()).fit().centerCrop().into(holder.profilePic);
                //get().load(profiles.get(position).getProfilepic()).into(holder.profilePic);
        //holder.profilePic.setimag
        //Picasso.get().load(profiles.get(position).getProfilepic()).into(holder.profilePic);
        holder.btncheck.setVisibility(View.VISIBLE);
        holder.onclick(position);

    }

    @Override
    public int getItemCount()
    {
        return profiles.size();
    }

    class MyViewHolder extends RecyclerView.ViewHolder{
        TextView ename,eage,ephone;
        ImageView profilePic;
        Button btncheck;

        public MyViewHolder(View itemView) {
            super(itemView);
            ename=(TextView) itemView.findViewById(R.id.ename);
            eage=(TextView) itemView.findViewById(R.id.eage);
            ephone=(TextView) itemView.findViewById(R.id.ephone);
            profilePic=(ImageView)itemView.findViewById(R.id.proFilePic);
            btncheck=(Button)itemView.findViewById(R.id.checkDetails);
        }
        public void onclick(final int position){
            btncheck.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Toast.makeText(context, position+"is clicked     ", Toast.LENGTH_SHORT).show();
                }
            });
        }
    }
}
