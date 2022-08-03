package com.example.registrodeproductos;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.example.registrodeproductos.models.Products;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

public class addNewProduct extends AppCompatActivity {

    private Button btn_add_new_product;
    private EditText name_product, description_product, brand_product, price_product;
    private String name_txt, description_txt, brand_txt, price_txt;
    private ProgressBar loading;
    private ImageView btn_back;
    
    private FirebaseFirestore db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_add_product);

        db = FirebaseFirestore.getInstance();
        initUI();
    }

    private void initUI(){
        name_product = findViewById(R.id.input_name_product);
        description_product = findViewById(R.id.input_description_product);
        brand_product = findViewById(R.id.input_brand_product);
        price_product = findViewById(R.id.input_price_product);
        btn_add_new_product = findViewById(R.id.btn_add_product);
        loading = findViewById(R.id.loading_new_product);

        btn_add_new_product.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                name_txt = name_product.getText().toString();
                description_txt = description_product.getText().toString();
                brand_txt = brand_product.getText().toString();
                price_txt = price_product.getText().toString();

                if(name_txt.isEmpty()){
                    name_product.setError("Please enter the product name.");
                }else if(description_txt.isEmpty()){
                    description_product.setError("Please enter a product description");
                }else if(brand_txt.isEmpty()){
                    brand_product.setError("Please enter a brand");
                }else if(price_txt.isEmpty()){
                    price_product.setError("Please enter the price of the product");
                }else{
                    addDataToFirestore(name_txt, description_txt, brand_txt, price_txt);
                    btn_add_new_product.setVisibility(View.INVISIBLE);
                    loading.setVisibility(View.VISIBLE);
                }

            }
        });

        btn_back = findViewById(R.id.btn_back);
        btn_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent main = new Intent(addNewProduct.this, MainActivity.class);
                startActivity(main);
                finish();
            }
        });
    }

    private void addDataToFirestore(String name_txt, String description_txt, String brand_txt, String price_txt) {
        CollectionReference dbReference = db.collection("Products");
        Products products = new Products(name_txt, description_txt, brand_txt, price_txt);
        dbReference.add(products).addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
            @Override
            public void onSuccess(DocumentReference documentReference) {
                Toast.makeText(addNewProduct.this, "New product inserted successfully", Toast.LENGTH_SHORT).show();
                btn_add_new_product.setVisibility(View.VISIBLE);
                loading.setVisibility(View.INVISIBLE);
                name_product.setText("");
                description_product.setText("");
                brand_product.setText("");
                price_product.setText("");
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(addNewProduct.this, "Error inserting the product", Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        startActivity(new Intent(addNewProduct.this, MainActivity.class));
        finish();
    }
}

//</Programer: Daniel>