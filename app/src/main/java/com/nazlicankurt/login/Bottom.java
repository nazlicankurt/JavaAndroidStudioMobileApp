package com.nazlicankurt.login;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ShareCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.android.gms.common.GooglePlayServicesNotAvailableException;
import com.google.android.gms.common.GooglePlayServicesRepairableException;
import com.google.android.gms.location.places.Place;
import com.google.android.gms.location.places.ui.PlacePicker;
import com.google.android.material.bottomsheet.BottomSheetDialog;
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

public class Bottom extends AppCompatActivity {
    Button button1;
    ImageView searchSe;
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
        setContentView(R.layout.activity_bottom);

        button1 =findViewById(R.id.button1);
        searchSe =findViewById(R.id.searchSe);
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


    button1.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            BottomSheetDialog bottomSheetDialog=new BottomSheetDialog(Bottom.this);
            bottomSheetDialog.setContentView(R.layout.bottom_sheet_dialog);
            bottomSheetDialog.setCanceledOnTouchOutside(false);

        bottomSheetDialog.show();
        }
    });}


    private void getDataFromFirestore() {
        CollectionReference collectionReference =firebaseFirestore.collection("Posts");
        collectionReference.orderBy("data", Query.Direction.DESCENDING).addSnapshotListener(new EventListener<QuerySnapshot>() {
            @Override
            public void onEvent(@Nullable QuerySnapshot queryDocumentSnapshots, @Nullable FirebaseFirestoreException e) {
                if(e!=null) {
                    Toast.makeText(Bottom.this,e.getLocalizedMessage().toString(),Toast.LENGTH_LONG).show();
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




    public void helpHome(View view) {
    Intent intent= new Intent(Bottom.this, Helping.class);
    startActivity(intent);
}
    public void goChat(View view) {
        Intent intent= new Intent(Bottom.this, ChatActivity.class);
        startActivity(intent);
    }

    public void selectProfil( View view) {
        Intent intent= new Intent(Bottom.this, Profile.class);
        startActivity(intent);
        finish();

    }
    public void listele(View view) {
        Intent intent= new Intent(Bottom.this, Anasayfam.class);
        startActivity(intent);
        finish();
    }
    public void listele2(View view) {
        Intent intent= new Intent(Bottom.this, Anasayfam.class);
        startActivity(intent);
    }
    public void listele3(View view) {
        Intent intent= new Intent(Bottom.this, Anasayfam.class);
        startActivity(intent);
    }
    public void aramaYap(View view) {

    }

}
