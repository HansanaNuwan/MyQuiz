package com.example.myquiz;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.res.ResourcesCompat;

import android.app.Dialog;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.daimajia.androidanimations.library.Techniques;
import com.daimajia.androidanimations.library.YoYo;
import com.google.android.gms.tasks.OnCanceledListener;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class LoginActivity extends AppCompatActivity {

    private TextView appName;
    private EditText email , password;
    private Button login , register;
    private Dialog loadingdialog;
    private FirebaseAuth firebaseAuth;
    private long backPressedTime;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        appName = findViewById(R.id.logintitle);

        Typeface typeface = ResourcesCompat.getFont(this,R.font.blacklist);
        appName.setTypeface(typeface);

        YoYo.with(Techniques.BounceInUp).duration(5000).playOn(appName);

        email = findViewById(R.id.email);
        password = findViewById(R.id.password);
        login = findViewById(R.id.login);
        register = findViewById(R.id.register);

        loadingdialog = new Dialog(LoginActivity.this);
        loadingdialog.setContentView(R.layout.loading_progressbar);
        loadingdialog.setCancelable(false);
        loadingdialog.getWindow().setBackgroundDrawableResource(R.drawable.progress_background);
        loadingdialog.getWindow().setLayout(ViewGroup.LayoutParams.WRAP_CONTENT,ViewGroup.LayoutParams.WRAP_CONTENT);

        firebaseAuth = FirebaseAuth.getInstance();

        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                loadingdialog.show();

                String memail = email.getText().toString();
                String mpass = password.getText().toString();

                if(!memail.isEmpty() && !mpass.isEmpty()){

                    firebaseAuth.signInWithEmailAndPassword(memail,mpass)
                            .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                                @Override
                                public void onComplete(@NonNull Task<AuthResult> task) {

                                    if(task.isSuccessful()){
                                        //sign in complete
                                        Toast.makeText(LoginActivity.this,"Logged in Successfull!",Toast.LENGTH_SHORT).show();
                                        Intent intent = new Intent(LoginActivity.this,MainActivity.class);
                                        startActivity(intent);
                                        loadingdialog.dismiss();
                                        finish();

                                    }else{

                                        Toast.makeText(LoginActivity.this,"Something went Wrong !!",Toast.LENGTH_SHORT).show();
                                        return;
                                    }
                                }
                            }).addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {

                           // Toast.makeText(LoginActivity.this,"Error",Toast.LENGTH_SHORT).show();
                           // loadingdialog.dismiss();
                           // return;

                        }
                    }).addOnCanceledListener(new OnCanceledListener() {
                        @Override
                        public void onCanceled() {
                            Toast.makeText(LoginActivity.this,"Canceled",Toast.LENGTH_SHORT).show();
                            loadingdialog.dismiss();
                        }
                    });

                }else{
                    Toast.makeText(LoginActivity.this,"Enter values",Toast.LENGTH_SHORT).show();
                    loadingdialog.dismiss();
                }

            }
        });

        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(LoginActivity.this,SignInActivity.class);
                startActivity(intent);

            }
        });

    }

    @Override
    public void onBackPressed() {
        if (backPressedTime + 2000 > System.currentTimeMillis()) {
            finish();
        } else {
            Toast.makeText(this, "Press back again to Exit", Toast.LENGTH_SHORT).show();
        }
        backPressedTime = System.currentTimeMillis();
    }


}