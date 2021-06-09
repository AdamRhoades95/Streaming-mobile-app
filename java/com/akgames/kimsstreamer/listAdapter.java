package com.akgames.kimsstreamer;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;


import com.bumptech.glide.Glide;

import java.util.List;

import androidx.recyclerview.widget.RecyclerView;

public class listAdapter extends RecyclerView.Adapter<listAdapter.ViewHolder>{
    //private ArrayList<String> mTitle = new ArrayList<>();
    //private ArrayList<String> mIcon = new ArrayList<>();
    private Context context;
    private List<newUpload> muploads;
    int q;
    private OnItemClickListener mlistener;

    public listAdapter(Context context, List<newUpload> muploads) {
        this.context = context;
        this.muploads = muploads;
    }

//    public listAdapter(Context context, ArrayList<String> mTitle, ArrayList<String> mIcon) {
//        this.mTitle = mTitle;
//        this.mIcon = mIcon;
//        this.context = context;
//        q=0;
//    }
//    public listAdapter(Context context, ArrayList<String> mTitle, ArrayList<String> mIcon,int q) {
//        this.mTitle = mTitle;
//        this.mIcon = mIcon;
//        this.context = context;
//        this.q=q;
//    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {

        newUpload uploadCurrent = muploads.get(position);
        holder.title.setText(uploadCurrent.getName());
        //Picasso.with(context)
        Glide.with(context).asBitmap().load(uploadCurrent.getImageUrl()).placeholder(R.mipmap.ic_launcher).into(holder.icon);
//        holder.title.setText(mTitle.get(position));
//
//        holder.icon.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Toast.makeText(context, mIcon.get(position), Toast.LENGTH_SHORT).show();
//
//                //code here for db connection to retrieve sellected music object matching image icon
//
//
//                if (q == 0) {
//                    //db connect here
//                    Intent intent = new Intent(context.getApplicationContext(), musicChoice.class);
//                    context.startActivity(intent);
//                }
//                else if(q==1){
//                    Intent intent = new Intent(context.getApplicationContext(), movieChoice.class);
//                    context.startActivity(intent);
//                }
//                else if(q==2){
//                    //Intent intent = new Intent(context.getApplicationContext(), podcastChoice.class);
//                    //context.startActivity(intent);
//                }
//                //finish();
//
//
//
//            }
//        });
    }

    @Override
    public int getItemCount() {
        return muploads.size();
    }

    public  class ViewHolder extends RecyclerView.ViewHolder implements  View.OnClickListener{
        ImageView icon;
        TextView title;

        public ViewHolder(View itemView) {
            super(itemView);
            icon = itemView.findViewById(R.id.findimage);
            title = itemView.findViewById(R.id.iconTitle);

            itemView.setOnClickListener(this);
            //itemView.setOnCreateContextMenuListener();
        }

        @Override
        public void onClick(View v) {
            if(mlistener != null){
                int position = getAdapterPosition();
                if(position != RecyclerView.NO_POSITION){
                    newUpload uploadCurrent = muploads.get(position);

                    mlistener.onItemClicked(position, uploadCurrent);
                }
            }
        }
    }

    public interface OnItemClickListener{
        void onItemClicked(int position, newUpload uploadCurrent);
        void onWhatClicked(int position);
        void onDeleteClicked(int position);

    }
    public void setOnItemClickListener(OnItemClickListener listener){
        mlistener = listener;
    }
}
