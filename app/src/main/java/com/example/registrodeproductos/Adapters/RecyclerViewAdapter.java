package com.example.registrodeproductos.Adapters;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.recyclerview.widget.RecyclerView;

import com.example.registrodeproductos.R;
import com.example.registrodeproductos.models.Products;
import com.example.registrodeproductos.updateDeleteProduct;

import java.util.List;

public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewAdapter.ViewHolder> {

    private List<Products> productsList;
    private Context context;

    public RecyclerViewAdapter(List<Products> productsList, Context context) {
        this.productsList = productsList;
        this.context = context;
    }

    @NonNull
    @Override
    public RecyclerViewAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(context).inflate(R.layout.layout_list_products, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerViewAdapter.ViewHolder holder, int position) {
        holder.print(position);
    }

    @Override
    public int getItemCount() {
        return productsList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{

        private TextView name, brand, number;
        private ImageView btn_info;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            name = itemView.findViewById(R.id.name_product);
            brand = itemView.findViewById(R.id.brand_product);
            number = itemView.findViewById(R.id.txt_number_product);
            btn_info = itemView.findViewById(R.id.btn_info);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent updateDelete = new Intent(context, updateDeleteProduct.class);
                    updateDelete.putExtra("ID", productsList.get(getAdapterPosition()).getID());
                    updateDelete.putExtra("name", productsList.get(getAdapterPosition()).getNameProducts());
                    updateDelete.putExtra("description", productsList.get(getAdapterPosition()).getDescriptionProducts());
                    updateDelete.putExtra("price", productsList.get(getAdapterPosition()).getPriceProducts());
                    updateDelete.putExtra("brand", productsList.get(getAdapterPosition()).getBrandProducts());
                    context.startActivity(updateDelete);

                }
            });

        }

        private void print(int i){
            name.setText(productsList.get(i).getNameProducts());
            brand.setText(productsList.get(i).getBrandProducts());
            number.setText(String.valueOf(i));
            btn_info.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    AlertDialog.Builder builder = new AlertDialog.Builder(context, R.style.MyDialogTheme);
                    String message = "Description: " + productsList.get(i).getDescriptionProducts() + "<br/>" + "Price: " + productsList.get(i).getPriceProducts();
                    builder.setMessage(Html.fromHtml("<font color='#000000'>" + message + "</font>"));
                    builder.setPositiveButton(Html.fromHtml("<font color='#000000'>Closed</font>"), new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {

                        }
                    });
                    builder.create().show();
                }
            });
        }
    }

}

//</Programer: Daniel>