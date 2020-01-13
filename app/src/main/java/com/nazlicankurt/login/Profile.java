package com.nazlicankurt.login;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.ImageDecoder;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.IOException;
import java.util.UUID;

public class Profile<onActivityResul> extends AppCompatActivity {
 ImageView imageUser;
 Bitmap bitmap;
 Button button4;
 Uri imageData;
 int SELECT_PHOTO =1;
 private StorageReference storageReference;
 private FirebaseDatabase database;
 private DatabaseReference databaseReference;
 private FirebaseAuth mAuth;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        imageUser= findViewById(R.id.imageUser);
        button4= findViewById(R.id.button4);

        storageReference= FirebaseStorage.getInstance().getReference();
        database= FirebaseDatabase.getInstance();
        databaseReference= database.getReference();
        mAuth= FirebaseAuth.getInstance();

        imageUser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_PICK);
                intent.setType("image/s");
                startActivityForResult(intent, SELECT_PHOTO);
            }
        });
    }
    public void download(View view) {
       final UUID uuidImage = UUID.randomUUID();
        String imageName = "images/"+uuidImage+ ".jpg";
        StorageReference newReference = storageReference.child(imageName);
        newReference.putFile(imageData).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {

StorageReference profileImageRef = FirebaseStorage.getInstance().getReference("images/" + uuidImage + ".jpg");
profileImageRef.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
    @Override
    public void onSuccess(Uri uri) {
        String downloadURL = uri.toString();
        System.out.println("download URL:" +downloadURL);

    }
});
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
              Toast.makeText(getApplicationContext(),e.getLocalizedMessage().toString(),Toast.LENGTH_LONG).show();
            }
        });

    }
 public void upload(View view) {
     if(ContextCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE)!= PackageManager.PERMISSION_GRANTED) {
         ActivityCompat.requestPermissions(this,new String[] {Manifest.permission.READ_EXTERNAL_STORAGE}, 5);
     }
     else {
         Intent intentTogallery = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
         startActivityForResult(intentTogallery, 3);
     }
    }

    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        if(requestCode==8) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                startActivityForResult(intent, 6);
            }
        }
            super.onRequestPermissionsResult(requestCode, permissions, grantResults);
            }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode== SELECT_PHOTO && resultCode==RESULT_OK && data!= null && data.getData() != null) {
             imageData = data.getData();
            try {
                    bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), imageData);
                    imageUser.setImageBitmap(bitmap);
            } catch (IOException e) {
                e.printStackTrace();
            }

        }}
}

