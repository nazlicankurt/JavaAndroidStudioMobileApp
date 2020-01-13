package com.nazlicankurt.login;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class SignUpActivity extends AppCompatActivity {
private FirebaseAuth firebaseAuth;
    EditText emailText4,passwordText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);

        firebaseAuth= FirebaseAuth.getInstance();
        emailText4=findViewById(R.id.emailText4);
        passwordText=findViewById(R.id.passwordText);


    }

    public void signUp(View view) {

        if(emailText4.getText().toString().equals("")||passwordText.getText().toString().equals("")){

        }
        else{


        String email= emailText4.getText().toString();
        String password= passwordText.getText().toString();

        firebaseAuth.createUserWithEmailAndPassword(email,password).addOnSuccessListener(new OnSuccessListener<AuthResult>() {
            @Override
            public void onSuccess(AuthResult authResult) {
                Toast.makeText(SignUpActivity.this,"Kullanıcı Oluşturuldu", Toast.LENGTH_LONG).show();

                Intent intent= new Intent(SignUpActivity.this,FeedActivity.class);
                startActivity(intent);
finish();
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(SignUpActivity.this, e.getLocalizedMessage().toString(), Toast.LENGTH_LONG).show();
            }

        });
    }
        }
    public void signMain(View view ) {
        Intent intento= new Intent(SignUpActivity.this, FeedActivity.class);
        startActivity(intento);
        finish();

    }


}
