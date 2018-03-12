package com.example.vinove.firebaseauth;

import android.content.ClipData;
import android.content.ClipboardManager;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.HashMap;

public class CreateSessionActivity extends AppCompatActivity {


    EditText title, url;
    //    EditText team1, team2, team3, team4;
    ClipboardManager clipboardManager;
    AutoCompleteTextView autoCompleteTextView;
    private ClipData clipData;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_session);

        title = findViewById(R.id.title);
        url = findViewById(R.id.url);
/*        team1 = findViewById(R.id.team1);
        team2 = findViewById(R.id.team2);
        team3 = findViewById(R.id.team3);
        team4 = findViewById(R.id.team4);*/
        autoCompleteTextView = findViewById(R.id.actv);
        clipboardManager = (ClipboardManager) getSystemService(CLIPBOARD_SERVICE);

        String[] values = getResources().getStringArray(R.array.array_name);
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(getApplicationContext(), android.R.layout.simple_spinner_item, values);
        autoCompleteTextView.setAdapter(adapter);
        autoCompleteTextView.setThreshold(3);

    }


    public void createSession(View v) {
        String title = this.title.getText().toString();
        String url = this.url.getText().toString();
  /*      String team1 = this.team1.getText().toString();
        String team2 = this.team2.getText().toString();
        String team3 = this.team3.getText().toString();
        String team4 = this.team4.getText().toString();
*/
        DatabaseReference ref = FirebaseDatabase.getInstance().getReference();
        HashMap<Object, String> data = new HashMap<>();
        data.put("Title", title);
        data.put("URL", url);
/*        data.put("Team-1", team1);
        data.put("Team-2", team2);
        data.put("Team-3", team3);
        data.put("Team-4", team4);*/
        Task t = ref.child("session").push().setValue(data);
        ref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                Toast.makeText(getApplicationContext(), "Session has been Successfully created, I hope you have copied the URL", Toast.LENGTH_LONG).show();
                finish();
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Toast.makeText(getApplicationContext(), "Data error", Toast.LENGTH_SHORT).show();
            }
        });
    }

    public void copy(View view) {
        String text = url.getText().toString();
        clipData = ClipData.newPlainText("text", text);
        clipboardManager.setPrimaryClip(clipData);

    }
}
