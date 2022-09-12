package com.example.diary;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.example.diary.databinding.ActivityDiaryBinding;
import com.example.diary.databinding.ActivityTextBinding;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FieldValue;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.util.HashMap;

public class Text extends AppCompatActivity {
    ActivityTextBinding binding;
    FirebaseFirestore firebaseFirestore;
    FirebaseStorage firebaseStorage;
    StorageReference storageReference;
    FirebaseAuth auth;
    FirebaseUser user;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_text);

        firebaseFirestore = FirebaseFirestore.getInstance();
        firebaseStorage = FirebaseStorage.getInstance();
        storageReference = firebaseStorage.getReference();
        auth = FirebaseAuth.getInstance();

        user = auth.getCurrentUser();


        binding = ActivityTextBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);
    }

    public void save(View view){

        String title = binding.editTextTextPersonName2.getText().toString();
        String text = binding.editTextTextMultiLine.getText().toString();
        String day = binding.editTextTextPersonName.getText().toString();

        FirebaseUser userD = auth.getCurrentUser();

        String email = userD.getEmail();

        HashMap<String, Object> diary = new HashMap<>();
        diary.put("title",title);
        diary.put("text",text);
        diary.put("day",day);
        diary.put("email",email);
        diary.put("date", FieldValue.serverTimestamp());

        firebaseFirestore.collection("Diaries").add(diary).addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
            @Override
            public void onSuccess(DocumentReference documentReference) {
                Intent intent = new Intent(Text.this,Diary.class);
                startActivity(intent);
                finish();
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(Text.this,e.getLocalizedMessage(),Toast.LENGTH_LONG).show();
            }
        });
    }
}