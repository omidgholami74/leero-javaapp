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

public class imgdaste_adapter extends RecyclerView.Adapter<imgdaste_adapter.imgdastViewHolder> {
    private Context context;
    private List<String[]> img_dasts;
       ImageView imgaeview;

      public   imgdaste_adapter (Context context, List<String[]> img_dasts,ImageView imgaeview){
          this.imgaeview=imgaeview;
        this.context = context;
        this.img_dasts = img_dasts;
    }
    @NonNull
    @Override
    public imgdastViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(context).inflate(R.layout.img_dastebandi,viewGroup,false);
        return new imgdastViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull imgdastViewHolder holder, final int i) {

        String[] images =img_dasts.get(i);
        holder.Imagcat.setImageResource(context.getResources().getIdentifier(img_dasts.get(i)[0], "drawable",context.getPackageName()));
        Log.e("test",img_dasts.get(i)[0]+"");
        holder.Imagcat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

               Resources res =context.getResources();
              String mDrawableName =img_dasts.get(i)[0]+"" ;
               int resID = res.getIdentifier(mDrawableName , "drawable",context.getPackageName());
              Drawable drawable = res.getDrawable(resID );
              imgaeview.setImageDrawable(drawable );
            }
        });
    }

    @Override
    public int getItemCount() {
        return img_dasts.size();
    }

    class imgdastViewHolder extends RecyclerView.ViewHolder{
        private ImageView Imagcat;


        public imgdastViewHolder(@NonNull View itemView) {
            super(itemView);
            Imagcat=(ImageView) itemView.findViewById(R.id.imgcat);

        }
    }
}
