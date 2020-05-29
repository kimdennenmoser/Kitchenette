package com.example.navigationdrawerfromscratch;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.navigationdrawerfromscratch.lebensmittel.Food;
import com.squareup.picasso.Picasso;

import java.util.List;

public class ProductAdapter extends RecyclerView.Adapter<ProductAdapter.ProductViewHolder> {

    private Context mCtx;
    private List<Food> productList;

    public ProductAdapter(Context mCtx, List<Food> productList) {
        this.mCtx = mCtx;
        this.productList = productList;
    }

    @NonNull
    @Override
    public ProductViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(mCtx);
        View view = inflater.inflate(R.layout.list_layout, null);
        return new ProductViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ProductViewHolder holder, int position) {
        Food product = productList.get(position);

        holder.foodName.setText(product.getName());
        holder.foodInfo.setText(product.getInfo());
        //holder.imageView.setImageDrawable(product.getImage());
        Picasso.get().load(product.getImage()).into(holder.imageView);

    }

    @Override
    public int getItemCount() {
        return productList.size();
    }

    class ProductViewHolder extends  RecyclerView.ViewHolder {

        ImageView imageView;
        TextView foodName, foodInfo;


        public ProductViewHolder(@NonNull View itemView) {
            super(itemView);


            imageView = (ImageView) itemView.findViewById(R.id.imageView);
            foodName = (TextView) itemView.findViewById(R.id.foodName);
            foodInfo = (TextView) itemView.findViewById(R.id.foodInfo);
        }
    }


}
