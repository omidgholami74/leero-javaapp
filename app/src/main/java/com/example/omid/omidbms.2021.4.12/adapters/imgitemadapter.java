package com.example.omid.omidbms.adapters;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.drawable.Drawable;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;


import com.example.omid.omidbms.R;

import java.util.List;

public class imgitemadapter extends RecyclerView.Adapter<imgitemadapter.imgdastViewHolder> {
    private Context context;
    private List<String[]> imgitem_list;
    ImageView imgaeview;



    public   imgitemadapter (Context context, List<String[]> img_dasts,ImageView imgaeview){
        this.imgaeview=imgaeview;
        this.context = context;
        this.imgitem_list = imgitem_list;
    }
    @NonNull
    @Override
    public imgdastViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(context).inflate(R.layout.img_dastebandi,viewGroup,false);
        return new imgdastViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull imgdastViewHolder holder, final int i) {

        String[] images =imgitem_list.get(i);
        holder.imgitem.setImageResource(context.getResources().getIdentifier(imgitem_list.get(i)[0], "drawable",context.getPackageName()));
        Log.e("test",imgitem_list.get(i)[0]+"");
        holder.imgitem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Resources res =context.getResources();
                String mDrawableName =imgitem_list.get(i)[0]+"" ;
                int resID = res.getIdentifier(mDrawableName , "drawable",context.getPackageName());
                Drawable drawable = res.getDrawable(resID );
                imgaeview.setImageDrawable(drawable );
            }
        });
    }

    @Override
    public int getItemCount() {
        return imgitem_list.size();
    }

    class imgdastViewHolder extends RecyclerView.ViewHolder{
        private ImageView imgitem;


        public imgdastViewHolder(@NonNull View itemView) {
            super(itemView);
            imgitem=(ImageView) itemView.findViewById(R.id.imgcat);

        }
    }
}
