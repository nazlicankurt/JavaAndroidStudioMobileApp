package com.nazlicankurt.login;

import android.os.Bundle;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.Map;

public class Anasayfam extends AppCompatActivity {
    FirebaseFirestore firebaseFirestore;
    FirebaseAuth firebaseAuth;
    ArrayList<String> userEmailFrommFB;
    ArrayList<String> userCommentFromFB;
    ArrayList<String> userImageFromFB;
    RecyclerAnasayfa recyclerAnasayfa;
    RecyclerView recyclerView;


    @Override


    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_anasayfam);

        userCommentFromFB = new ArrayList<>();
        userEmailFrommFB = new ArrayList<>();
        userImageFromFB = new ArrayList<>();

        firebaseAuth = FirebaseAuth.getInstance();
        firebaseFirestore = FirebaseFirestore.getInstance();
        getDataFromFirestore();



        recyclerView = (RecyclerView)findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerAnasayfa = new RecyclerAnasayfa(userEmailFrommFB, userCommentFromFB, userImageFromFB);
 recyclerView.setAdapter(recyclerAnasayfa);


    }


    private void getDataFromFirestore() {
        CollectionReference collectionReference =firebaseFirestore.collection("Posts");
        collectionReference.orderBy("data", Query.Direction.DESCENDING).addSnapshotListener(new EventListener<QuerySnapshot>() {
            @Override
            public void onEvent(@Nullable QuerySnapshot queryDocumentSnapshots, @Nullable FirebaseFirestoreException e) {
                if(e!=null) {
                    Toast.makeText(Anasayfam.this,e.getLocalizedMessage().toString(),Toast.LENGTH_LONG).show();
                }
                if(queryDocumentSnapshots != null) {
                    for(DocumentSnapshot snapshot: queryDocumentSnapshots.getDocuments()) {
                        Map<String,Object> data=snapshot.getData();
                        String comment =(String)data.get("comment");
                        String userEmail =(String)data.get("useremail");
                        String downloadurl =(String)data.get("downloadurl");
                       userCommentFromFB.add(comment);
                       userEmailFrommFB.add(userEmail);
                       userImageFromFB.add(downloadurl);
                       recyclerAnasayfa.notifyDataSetChanged();
                    }
                }
            }
        });
    }


}
