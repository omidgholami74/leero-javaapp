package com.example.omid.omidbms.adapters;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Environment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.omid.omidbms.R;
import com.example.omid.omidbms.structures.Group;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

/**
 * Created by Quoc Nguyen on 13-Dec-16.
 */

public class FoodListAdapter extends BaseAdapter {

    private Context context;
    private  int layout;
    private ArrayList<Group> foodsList;

    public FoodListAdapter(Context context, int layout, ArrayList<Group> foodsList) {
        this.context = context;
        this.layout = layout;
        this.foodsList = foodsList;
    }


    @Override
    public int getCount() {
        return foodsList.size();
    }

    @Override
    public Object getItem(int position) {
        return foodsList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    private class ViewHolder{
        ImageView imageView;
        TextView txtName, txtPrice;
    }

    @Override
    public View getView(int position, View view, ViewGroup viewGroup) {

        View row = view;
        ViewHolder holder = new ViewHolder();

        if(row == null){
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            row = inflater.inflate(layout, null);

            holder.txtName = (TextView) row.findViewById(R.id.txtName);
           // holder.txtPrice = (TextView) row.findViewById(R.id.txtPrice);
            holder.imageView = (ImageView) row.findViewById(R.id.img_food);
            row.setTag(holder);
        }
        else {
            holder = (ViewHolder) row.getTag();
        }

        Group food = foodsList.get(position);

        holder.txtName.setText(food.getName());
        String photoPath = context.getExternalFilesDir(Environment.DIRECTORY_PICTURES).toString()+"/"+food.getImage();
        Bitmap bitmap1 = BitmapFactory.decodeFile(photoPath);
        holder.imageView.setImageBitmap(bitmap1);

        return row;
    }
}
