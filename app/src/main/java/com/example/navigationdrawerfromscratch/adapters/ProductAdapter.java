package com.example.navigationdrawerfromscratch.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.navigationdrawerfromscratch.R;
import com.example.navigationdrawerfromscratch.lebensmittel.Food;
import com.squareup.picasso.Picasso;

import java.util.List;

public class ProductAdapter extends RecyclerView.Adapter<ProductAdapter.ProductViewHolder> {

    private Context mCtx;
    private List<Food> productList;
    private OnNoteListener mOnNoteListener;

    public ProductAdapter(Context mCtx, List<Food> productList, OnNoteListener onNoteListener) {
        this.mCtx = mCtx;
        this.productList = productList;
        this.mOnNoteListener = onNoteListener;
    }

    @NonNull
    @Override
    public ProductViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(mCtx);
        View view = inflater.inflate(R.layout.list_layout, null);
        return new ProductViewHolder(view, mOnNoteListener);
    }

    @Override
    public void onBindViewHolder(@NonNull ProductViewHolder holder, int position) {
        Food product = productList.get(position);

        holder.foodName.setText(product.getName());
        holder.foodInfo.setText(product.getInfo());
        Picasso.get().load(product.getImage()).into(holder.imageView);

    }

    @Override
    public int getItemCount() {
        return productList.size();
    }

    class ProductViewHolder extends  RecyclerView.ViewHolder implements View.OnClickListener {

        ImageView imageView;
        TextView foodName, foodInfo;
        OnNoteListener onNoteListener;


        public ProductViewHolder(@NonNull View itemView, OnNoteListener onNoteListener) {
            super(itemView);


            imageView = (ImageView) itemView.findViewById(R.id.imageView);
            foodName = (TextView) itemView.findViewById(R.id.foodName);
            foodInfo = (TextView) itemView.findViewById(R.id.foodInfo);
            this.onNoteListener = onNoteListener;

            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            onNoteListener.onFoodClick(getAdapterPosition());
        }
    }

    public interface OnNoteListener{
        void onFoodClick(int position);
    }


}
