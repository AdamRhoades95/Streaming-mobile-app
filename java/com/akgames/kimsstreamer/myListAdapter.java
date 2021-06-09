package com.akgames.kimsstreamer;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;

import java.util.ArrayList;

import androidx.recyclerview.widget.RecyclerView;

public class myListAdapter extends RecyclerView.Adapter<myListAdapter.ViewHolder>{
    private ArrayList<String> mTitle = new ArrayList<>();
    private ArrayList<String> mIcon = new ArrayList<>();
    private Context context;

    public myListAdapter(Context context, ArrayList<String> mTitle, ArrayList<String> mIcon) {
        this.mTitle = mTitle;
        this.mIcon = mIcon;
        this.context = context;
    }

    @Override
    public myListAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.mng_item,parent,false);
        return new myListAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(myListAdapter.ViewHolder holder, int position) {
        Glide.with(context).asBitmap().load(mIcon.get(position)).into(holder.icon);
        holder.title.setText(mTitle.get(position));

        holder.icon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(context, mIcon.get(position),Toast.LENGTH_SHORT).show();

                //code here for db connection to retrieve sellected music object matching image icon








                //db connect here
                AlertDialog.Builder builder = new AlertDialog.Builder(context);

                builder.setTitle("Confirm");
                builder.setMessage("Are you sure?");

                builder.setPositiveButton("YES", new DialogInterface.OnClickListener() {

                    public void onClick(DialogInterface dialog, int which) {
                        // Do nothing but close the dialog
                        Toast.makeText(context, "delete ok",Toast.LENGTH_SHORT).show();
//                        Intent intent = new Intent(context.getApplicationContext(),musicChoice.class);
//                        context.startActivity(intent);
                        dialog.dismiss();
                    }
                });

                builder.setNegativeButton("NO", new DialogInterface.OnClickListener() {

                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Toast.makeText(context, "keep it ok",Toast.LENGTH_SHORT).show();
                        // Do nothing
                        dialog.dismiss();
                    }
                });

                AlertDialog alert = builder.create();
                alert.show();
                //finish();



            }
        });
    }

    @Override
    public int getItemCount() {
        return mTitle.size();
    }

    public  class ViewHolder extends RecyclerView.ViewHolder{
        ImageView icon;
        TextView title;

        public ViewHolder(View itemView) {
            super(itemView);
            icon = itemView.findViewById(R.id.mngImage);
            title = itemView.findViewById(R.id.uploadTitle);
        }
    }
}
