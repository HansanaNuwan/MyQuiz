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
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class SignInActivity extends AppCompatActivity {

    private EditText Email ,Ppass1 , Ppass2;
    private Button register;
    private FirebaseAuth firebaseAuth;
    private Dialog loadingdialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_in);

        firebaseAuth = FirebaseAuth.getInstance();

        loadingdialog = new Dialog(SignInActivity.this);
        loadingdialog.setContentView(R.layout.loading_progressbar);
        loadingdialog.setCancelable(false);
        loadingdialog.getWindow().setBackgroundDrawableResource(R.drawable.progress_background);
        loadingdialog.getWindow().setLayout(ViewGroup.LayoutParams.WRAP_CONTENT,ViewGroup.LayoutParams.WRAP_CONTENT);

        Email = findViewById(R.id.email);
        Ppass1 = findViewById(R.id.password);
        Ppass2 = findViewById(R.id.repassword);
        register = findViewById(R.id.registerButton);



        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                loadingdialog.show();

                String email = Email.getText().toString();
                String password = Ppass1.getText().toString();
                String repassword = Ppass2.getText().toString();

                if(!email.isEmpty()&&!password.isEmpty()&&!repassword.isEmpty()){

                    if(password.equals(repassword)){

                        if(password.length() >= 6){

                            firebaseAuth.createUserWithEmailAndPassword(email,password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                                @Override
                                public void onComplete(@NonNull Task<AuthResult> task) {

                                    if(task.isSuccessful()){

                                        startActivity(new Intent(SignInActivity.this,LoginActivity.class));
                                        Toast.makeText(SignInActivity.this,"Registration Complete..",Toast.LENGTH_SHORT).show();
                                        finish();
                                        loadingdialog.dismiss();

                                    }else{

                                        Toast.makeText(SignInActivity.this,"Something wend wrong!!",Toast.LENGTH_SHORT).show();
                                        loadingdialog.dismiss();

                                    }

                                }
                            }).addOnFailureListener(new OnFailureListener() {
                                @Override
                                public void onFailure(@NonNull Exception e) {

                                    Toast.makeText(SignInActivity.this,"Error!",Toast.LENGTH_SHORT).show();
                                    loadingdialog.dismiss();


                                }
                            }).addOnCanceledListener(new OnCanceledListener() {
                                @Override
                                public void onCanceled() {

                                    Toast.makeText(SignInActivity.this,"Canceled Try Again!",Toast.LENGTH_SHORT).show();
                                    loadingdialog.dismiss();


                                }
                            });

                        }else {
                            Toast.makeText(SignInActivity.this,"Enter more that 6 Characters",Toast.LENGTH_SHORT).show();
                            loadingdialog.dismiss();

                        }

                    }else{
                        Toast.makeText(SignInActivity.this,"Password not match",Toast.LENGTH_SHORT).show();
                        loadingdialog.dismiss();

                    }

                }else{
                    Toast.makeText(SignInActivity.this,"Please fill all values",Toast.LENGTH_SHORT).show();
                    loadingdialog.dismiss();

                }

            }
        });

    }
}