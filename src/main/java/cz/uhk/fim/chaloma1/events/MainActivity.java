package cz.uhk.fim.chaloma1.events;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.FirebaseApp;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

public class MainActivity extends AppCompatActivity {

    private FirebaseAuth mAuth;

    private FirebaseStorage storage;

    private StorageReference imageRef;

    private ImageView imageView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        FirebaseApp.initializeApp(this);

        storage = FirebaseStorage.getInstance();

        imageRef = storage.getReference("image/test.jpg");

        imageView = findViewById(R.id.imageView);

        Glide.with(this /* context */)
                .load(imageRef)
                .into(imageView);

        mAuth = FirebaseAuth.getInstance();

        FirebaseUser user = mAuth.getCurrentUser();

        Log.w("USER", user.getEmail());

        mAuth.signOut();;

        Log.w("USER", user.getEmail());

        mAuth.signInWithEmailAndPassword("email@test.cz", "heslo1").addOnSuccessListener(new OnSuccessListener<AuthResult>() {
            @Override
            public void onSuccess(AuthResult authResult) {
                Toast.makeText(MainActivity.this, authResult.getUser().getUid(), Toast.LENGTH_LONG).show();

            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(MainActivity.this, e.getMessage(), Toast.LENGTH_LONG).show();
            }
        });

        if(user == null)
        {
            Toast.makeText(this, "jsem odhlaseny", Toast.LENGTH_LONG).show();
        }else{
            Toast.makeText(this, "jsem " + user.getUid(),Toast.LENGTH_LONG).show();
        }

        /* //registrace
        mAuth.createUserWithEmailAndPassword("email@test.cz", "heslo1").addOnSuccessListener(new OnSuccessListener<AuthResult>() {
            @Override
            public void onSuccess(AuthResult authResult) {
                Toast.makeText(MainActivity.this, authResult.getUser().getUid(), Toast.LENGTH_LONG).show();

            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(MainActivity.this, e.getMessage(), Toast.LENGTH_LONG).show();
            }
        });
        */
    }


    public void onSignEvents(View view) {
        startActivity(new Intent(MainActivity.this, EventsActivity.class));
    }
}
