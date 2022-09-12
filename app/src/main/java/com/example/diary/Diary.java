package com.example.diary;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.example.diary.Adapter.RecyclerAdapter;
import com.example.diary.Model.DiaryClass;
import com.example.diary.databinding.ActivityDiaryBinding;
import com.example.diary.databinding.ActivityMainBinding;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.firebase.firestore.auth.User;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.util.ArrayList;
import java.util.Map;

public class Diary extends AppCompatActivity {
    FirebaseAuth auth;
    FirebaseFirestore firebaseFirestore;
    FirebaseStorage firebaseStorage;
    StorageReference storageReference;
    ArrayList<DiaryClass> diary;
    RecyclerAdapter adapter;
    ActivityDiaryBinding binding;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_diary);

        binding = ActivityDiaryBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);

        firebaseFirestore = FirebaseFirestore.getInstance();
        firebaseStorage = FirebaseStorage.getInstance();
        storageReference = firebaseStorage.getReference();
        auth = FirebaseAuth.getInstance();

        diary = new ArrayList<>();
        getData();

        binding.recyclerView.setLayoutManager(new LinearLayoutManager(this));
        adapter = new RecyclerAdapter(diary);
        binding.recyclerView.setAdapter(adapter);

    }

    @Override
    public boolean onCreateOptionsMenu(@NonNull Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.option_menu,menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if(item.getItemId() == R.id.diary){
            Intent intent = new Intent(Diary.this,Text.class);
            startActivity(intent);
            finish();
        } else if(item.getItemId() == R.id.signOut){
            auth.signOut();
            Intent intent = new Intent(Diary.this,MainActivity.class);
            startActivity(intent);
            finish();
        }

        return super.onOptionsItemSelected(item);
    }

    public void getData(){
        //DocumentReference or CollectionReference
        firebaseFirestore.collection("Diaries").orderBy("date", Query.Direction.DESCENDING).addSnapshotListener(new EventListener<QuerySnapshot>() {
            @Override
            public void onEvent(@Nullable QuerySnapshot value, @Nullable FirebaseFirestoreException error) {
                if(error != null){
                    Toast.makeText(Diary.this,error.getLocalizedMessage(), Toast.LENGTH_LONG).show();
                }
                if(value != null){
                    for(DocumentSnapshot documentSnapshot : value.getDocuments()){
                        Map<String,Object> data = documentSnapshot.getData();

                        String title = (String) data.get("title");
                        String date = (String) data.get("date");
                        String day = (String) data.get("day");

                        DiaryClass diaryClass = new DiaryClass(title,day);
                        diary.add(diaryClass);
                    }
                    adapter.notifyDataSetChanged();
                }
            }
        });
    }
}