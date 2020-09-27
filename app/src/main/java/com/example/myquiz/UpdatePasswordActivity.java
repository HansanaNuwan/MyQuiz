package com.example.myquiz;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCanceledListener;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.EmailAuthProvider;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class UpdatePasswordActivity extends AppCompatActivity {

    private EditText Eemail ,Ppass1 , Ppass2;
    private Button updatepass;
    private FirebaseAuth firebaseAuth;
    private Dialog loadingdialog;
    private FirebaseUser user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_password);

        firebaseAuth = FirebaseAuth.getInstance();

        loadingdialog = new Dialog(UpdatePasswordActivity.this);
        loadingdialog.setContentView(R.layout.loading_progressbar);
        loadingdialog.setCancelable(false);
        loadingdialog.getWindow().setBackgroundDrawableResource(R.drawable.progress_background);
        loadingdialog.getWindow().setLayout(ViewGroup.LayoutParams.WRAP_CONTENT,ViewGroup.LayoutParams.WRAP_CONTENT);

        Eemail = findViewById(R.id.email);
        Ppass1 = findViewById(R.id.password);
        Ppass2 = findViewById(R.id.newpassword);
        updatepass = findViewById(R.id.updatePass);

        user = FirebaseAuth.getInstance().getCurrentUser();

        if(user != null){

            if(user.getEmail() != null){

                Eemail.setText(user.getEmail());

            }

        }else{

        }


        updatepass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                loadingdialog.show();

                String password = Ppass1.getText().toString();
                String repassword = Ppass2.getText().toString();


                if(!password.isEmpty()){


                    if(!repassword.isEmpty()){

                        if(password.equals(repassword)){

                            if(repassword.length() >= 6){

                                user.updatePassword(repassword).addOnCompleteListener(new OnCompleteListener<Void>() {
                                    @Override
                                    public void onComplete(@NonNull Task<Void> task) {

                                        Toast.makeText(UpdatePasswordActivity.this,"Password updated successfull",Toast.LENGTH_SHORT).show();

                                        Intent intent = new Intent(UpdatePasswordActivity.this,CategoryActivity2.class);
                                        startActivity(intent);
                                        loadingdialog.dismiss();
                                        finish();

                                    }
                                }).addOnFailureListener(new OnFailureListener() {
                                    @Override
                                    public void onFailure(@NonNull Exception e) {

                                        Toast.makeText(UpdatePasswordActivity.this,"Password update Fail",Toast.LENGTH_SHORT).show();
                                        loadingdialog.dismiss();
                                    }
                                });

                            }


                        }else{

                            Ppass2.setError("Password not equal!");
                            loadingdialog.dismiss();


                        }

                    }else{

                        Ppass2.setError("This feild is empty!");
                        loadingdialog.dismiss();


                    }

                }else {

                    Ppass1.setError("This feild is empty!");
                    loadingdialog.dismiss();


                }



            }
        });


    }
}