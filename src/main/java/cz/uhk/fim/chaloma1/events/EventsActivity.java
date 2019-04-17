package cz.uhk.fim.chaloma1.events;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.Timestamp;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.HashMap;
import java.util.Map;

public class EventsActivity extends AppCompatActivity {

    FirebaseFirestore db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_events);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        db = FirebaseFirestore.getInstance();

        // cteni DB
        db.collection("events").get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if(task.isSuccessful()){
                    Log.w("DOCUMENT", "result size je " + task.getResult().size());
                    for(QueryDocumentSnapshot document : task.getResult()){





                        Timestamp created = document.getTimestamp("created");
                        String desc = document.getString("description");
                        String title = document.getString("title");

                        Toast.makeText(EventsActivity.this, title + " " + desc + " " + created,Toast.LENGTH_LONG).show();
                        Log.d("DOCUMENT", document.getId() + " => " + document.getData());
                    }
                }else{
                    // TODO chyba nacitani
                }
            }
        });


        

        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View view) {

            /*    // DELETE DOCUMENT
                db.collection("events").document("6m7XN0WzrYTNENGBdqsu").delete().addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (task.isSuccessful()){
                            Toast.makeText(EventsActivity.this, "Odebrany event", Toast.LENGTH_SHORT).show();
                        }else{
                            Toast.makeText(EventsActivity.this, "Neco se pokazilo", Toast.LENGTH_SHORT).show();
                        }

                    }
                }); */


                // ADD DOCUMENT do DB
                Map<String, Object> data = new HashMap<>();
                data.put("title", "Titulek nove udalosti");
                data.put("description", "Popisek udalosti");
                data.put("created", Timestamp.now());

                db.collection("events").add(data).addOnCompleteListener(new OnCompleteListener<DocumentReference>() {
                    @Override
                    public void onComplete(@NonNull Task<DocumentReference> task) {
                        if (task.isSuccessful()){
                            Toast.makeText(EventsActivity.this, "Pridany event", Toast.LENGTH_SHORT).show();
                        }else{
                            Toast.makeText(EventsActivity.this, "Neco se pokazilo", Toast.LENGTH_SHORT).show();
                        }

                    }
                });
                
                
                
            }
        });
    }

}
