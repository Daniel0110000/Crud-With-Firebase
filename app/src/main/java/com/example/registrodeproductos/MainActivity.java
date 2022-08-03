package com.example.registrodeproductos;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.registrodeproductos.Adapters.RecyclerViewAdapter;
import com.example.registrodeproductos.models.Products;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private List<Products> productsList;
    private RecyclerViewAdapter adapter;
    private ProgressBar progressBar;
    private ImageView imgNotProducts;
    private TextView textNotProduct;

    private FirebaseFirestore db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        recyclerView = findViewById(R.id.recycler);
        imgNotProducts = findViewById(R.id.notProduct);
        textNotProduct = findViewById(R.id.text_notProduct);
        progressBar = findViewById(R.id.progress_recycler);
        db = FirebaseFirestore.getInstance();
        productsList = new ArrayList<>();

        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        adapter = new RecyclerViewAdapter(productsList, this);
        recyclerView.setAdapter(adapter);
        
        getDataOfFirebase();

        findViewById(R.id.btn_floating_add_product).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent newProduct = new Intent(MainActivity.this, addNewProduct.class);
                startActivity(newProduct);
                finish();
            }
        });

    }

    private void getDataOfFirebase() {
        db.collection("Products").get()
                .addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                    @Override
                    public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                        if(!queryDocumentSnapshots.isEmpty()){
                            progressBar.setVisibility(View.INVISIBLE);
                            recyclerView.setVisibility(View.VISIBLE);
                            List<DocumentSnapshot> list = queryDocumentSnapshots.getDocuments();
                            for(DocumentSnapshot d : list){
                                Products p = d.toObject(Products.class);
                                p.setID(d.getId());
                                productsList.add(p);
                                adapter.notifyDataSetChanged();

                            }
                        }else{
                            recyclerView.setVisibility(View.INVISIBLE);
                            progressBar.setVisibility(View.INVISIBLE);
                            imgNotProducts.setVisibility(View.VISIBLE);
                            textNotProduct.setVisibility(View.VISIBLE);
                        }
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(MainActivity.this, "Error", Toast.LENGTH_SHORT).show();
                    }
                });
    }


    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }
}
//</Programer: Daniel>