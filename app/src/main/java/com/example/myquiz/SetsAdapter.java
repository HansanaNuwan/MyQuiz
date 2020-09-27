package com.example.myquiz;

import android.content.Intent;
import android.util.ArrayMap;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.Map;

import static com.example.myquiz.SetsActivity.setsIDs;
import static com.example.myquiz.SplashActivity.catList;
import static com.example.myquiz.SplashActivity.selected_cat_index;

public class SetsAdapter extends BaseAdapter {


    private int numOfSets;
    private FirebaseFirestore firestore;

    public SetsAdapter(int numOfSets) {
        this.numOfSets = numOfSets;
    }

    @Override
    public int getCount() {
        return numOfSets;
    }

    @Override
    public Object getItem(int i) {
        return null;
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public View getView(final int i, View view, final ViewGroup viewGroup) {

        View view1;

        if(view == null){

            view1 = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.set_item_layout,viewGroup,false);

        }else{

            view1 = view;

        }

        view1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(viewGroup.getContext(), QuestionActivity.class);
                intent.putExtra("SETNO",i);
                viewGroup.getContext().startActivity(intent);

            }
        });

        ((TextView) view1.findViewById(R.id.setNo_tv)).setText(String.valueOf("Quiz " +(i+1)));

        return view1;
    }
}
