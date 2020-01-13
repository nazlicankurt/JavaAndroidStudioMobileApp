package com.nazlicankurt.login;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
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
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QuerySnapshot;

public class FeedActivity extends AppCompatActivity {
    private FirebaseAuth firebaseAuth;
    EditText emailText4, passwordText;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_feed);

        firebaseAuth= FirebaseAuth.getInstance();
        emailText4=findViewById(R.id.emailText4);
        passwordText=findViewById(R.id.passwordText);
        firebaseAuth = FirebaseAuth.getInstance();
        FirebaseUser user= firebaseAuth.getCurrentUser();
        if(user!=null) {
            Intent intent =new Intent(getApplicationContext(), Bottom.class);
            startActivity(intent);
        }


    }

    public void changeUye(View view) {
        Intent intent= new Intent(FeedActivity.this, SignUpActivity.class);
        startActivity(intent);


    }

    public void signIn(View view) {
        if(emailText4.getText().toString().equals("")||passwordText.getText().toString().equals("")){

        }
        else{
            String email= emailText4.getText().toString();
            String password= passwordText.getText().toString();

            firebaseAuth.signInWithEmailAndPassword(email,password).addOnSuccessListener(new OnSuccessListener<AuthResult>() {

                                                                                             @Override
                                                                                             public void onSuccess(AuthResult authResult) {
                                                                                                 Toast.makeText(FeedActivity.this,"Giriş Başarılı", Toast.LENGTH_LONG).show();
                                                                                                 Intent intent= new Intent(FeedActivity.this, Bottom.class);
                                                                                                 startActivity(intent);

                                                                                }
                                                                                         }
            ).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    Toast.makeText(FeedActivity.this, e.getLocalizedMessage().toString(),Toast.LENGTH_LONG).show();

                }
            });
        } }
}
