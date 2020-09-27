package com.example.myquiz;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.ViewGroup;
import android.widget.GridView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;
import java.util.List;

import static com.example.myquiz.SplashActivity.catList;
import static com.example.myquiz.SplashActivity.selected_cat_index;

public class SetsActivity extends AppCompatActivity {

    private GridView sets_grid;
    private FirebaseFirestore firestore;
    private Dialog loadingdialog;
    public static List<String> setsIDs = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sets);

        Toolbar toolbar = findViewById(R.id.setToolbar);
        setSupportActionBar(toolbar);

        getSupportActionBar().setTitle(catList.get(selected_cat_index).getName());

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        sets_grid = findViewById(R.id.setsGridview);

        loadingdialog = new Dialog(SetsActivity.this);
        loadingdialog.setContentView(R.layout.loading_progressbar);
        loadingdialog.setCancelable(false);
        loadingdialog.getWindow().setBackgroundDrawableResource(R.drawable.progress_background);
        loadingdialog.getWindow().setLayout(ViewGroup.LayoutParams.WRAP_CONTENT,ViewGroup.LayoutParams.WRAP_CONTENT);
        loadingdialog.show();

        firestore = FirebaseFirestore.getInstance();

        loadSets();

    }

    public void loadSets(){

        setsIDs.clear();

        firestore.collection("QUIZ").document(catList.get(selected_cat_index).getId())
                .get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
            @Override
            public void onSuccess(DocumentSnapshot documentSnapshot) {

                long noOfSets = (long)documentSnapshot.get("SETS");

                for(int i = 1; i<= noOfSets; i++){

                    setsIDs.add(documentSnapshot.getString("SET"+String.valueOf(i)+"_ID"));
                }

                if (setsIDs.size() == 0){

                    Toast.makeText(SetsActivity.this,"No Quizez available",Toast.LENGTH_SHORT).show();

                }

                SetsAdapter adapter = new SetsAdapter(setsIDs.size());
                sets_grid.setAdapter(adapter);

                loadingdialog.dismiss();

            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {

                Toast.makeText(SetsActivity.this,e.getMessage(),Toast.LENGTH_SHORT).show();
                loadingdialog.dismiss();
            }
        });

    }




    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        if(item.getItemId() == android.R.id.home){
            SetsActivity.this.finish();
        }
        return super.onOptionsItemSelected(item);
    }
}