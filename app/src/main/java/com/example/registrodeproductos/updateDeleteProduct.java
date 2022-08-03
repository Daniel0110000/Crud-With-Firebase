package com.example.registrodeproductos;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.text.Html;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.registrodeproductos.models.Products;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.FirebaseFirestore;

import org.w3c.dom.Text;

public class updateDeleteProduct extends AppCompatActivity {

    private EditText input_name, input_description, input_price, input_brand;
    private FirebaseFirestore db;
    private ImageView btn_delete, btn_override;
    private String ID, name, description, brand, price;
    private Button btn_update;
    private TextView txt_indicator;
    private ProgressBar progressBar_update;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_delete_product);

        db = FirebaseFirestore.getInstance();

        ID = getIntent().getStringExtra("ID");

        input_name = findViewById(R.id.input_name_product);
        input_description = findViewById(R.id.input_description_product);
        input_price = findViewById(R.id.input_price_product);
        input_brand = findViewById(R.id.input_brand_product);
        txt_indicator = findViewById(R.id.txt_indicator);
        progressBar_update = findViewById(R.id.progress_update);

        name = getIntent().getStringExtra("name");
        description = getIntent().getStringExtra("description");
        brand = getIntent().getStringExtra("brand");
        price = getIntent().getStringExtra("price");


        btn_override = findViewById(R.id.btn_override);
        btn_delete = findViewById(R.id.btn_delete);

        findViewById(R.id.btn_back).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(updateDeleteProduct.this, MainActivity.class));
                finish();
            }
        });


        setDataInputs();

        btn_delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder builder = new AlertDialog.Builder(updateDeleteProduct.this, R.style.MyDialogTheme);
                builder.setMessage(Html.fromHtml("<font color='#000000'>Do you to remove this product?</font>"));
                builder.setPositiveButton(Html.fromHtml("<font color='#FF0000'>Confirm</font>"), new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        deleteProduct();
                    }
                });
                builder.setNegativeButton(Html.fromHtml("<font color='#000000'>Cancel</font>"), new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        Toast.makeText(updateDeleteProduct.this, "Product not deleted", Toast.LENGTH_SHORT).show();
                    }
                });
                builder.create().show();
            }
        });

        btn_update = findViewById(R.id.btn_update);
        btn_override = findViewById(R.id.btn_override);
        btn_override.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder builder = new AlertDialog.Builder(updateDeleteProduct.this, R.style.MyDialogTheme);
                builder.setMessage(Html.fromHtml("<font color='#000000'>Do you want to update this product?</font>"));
                builder.setPositiveButton(Html.fromHtml("<font color='#FF0000'>Confirm</font>"), new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        btn_update.setVisibility(View.VISIBLE);
                        txt_indicator.setText("Update product");
                    }
                });
                builder.setNegativeButton(Html.fromHtml("<font color='#000000'>Cancel</font>"), new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                    }
                });
                builder.create().show();

            }
        });

        btn_update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String name_txt = input_name.getText().toString();
                String description_txt = input_description.getText().toString();
                String brand_txt = input_brand.getText().toString();
                String price_txt = input_price.getText().toString();

                if(name_txt.isEmpty()){
                    input_name.setError("Please enter the product name.");
                }else if(description_txt.isEmpty()){
                    input_description.setError("Please enter a product description");
                }else if(brand_txt.isEmpty()){
                    input_brand.setError("Please enter a brand");
                }else if(price_txt.isEmpty()){
                    input_price.setError("Please enter the price of the product");
                }else{
                    btn_update.setVisibility(View.INVISIBLE);
                    progressBar_update.setVisibility(View.VISIBLE);
                    updateProduct(name_txt, description_txt, brand_txt, price_txt);
                }
            }
        });

    }

    private void setDataInputs(){

        input_name.setText(name);
        input_description.setText(description);
        input_price.setText(price);
        input_brand.setText(brand);

    }

    private void deleteProduct(){
        db.collection("Products")
                .document(ID)
                .delete().addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if(task.isSuccessful()){
                            Toast.makeText(updateDeleteProduct.this, "Product removed successfully", Toast.LENGTH_SHORT).show();
                            startActivity(new Intent(updateDeleteProduct.this, MainActivity.class));
                        }else{
                            Toast.makeText(updateDeleteProduct.this, "Product not deleted", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }

    private void updateProduct(String nameL, String descriptionL, String brandL, String priceL) {
        Products updateProductS = new Products(nameL, descriptionL, brandL, priceL);
        db.collection("Products").document(ID)
                .set(updateProductS)
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void unused) {
                        btn_update.setVisibility(View.VISIBLE);
                        progressBar_update.setVisibility(View.INVISIBLE);
                        Toast.makeText(updateDeleteProduct.this, "Product updated successfully", Toast.LENGTH_SHORT).show();
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(updateDeleteProduct.this, "Product not updated", Toast.LENGTH_SHORT).show();
                    }
                });
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        startActivity(new Intent(updateDeleteProduct.this, MainActivity.class));
        finish();
    }
}

//</Programer: Daniel>