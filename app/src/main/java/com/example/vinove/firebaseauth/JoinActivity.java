package com.example.vinove.firebaseauth;

import android.content.ClipData;
import android.content.ClipboardManager;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.EditText;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

public class JoinActivity extends AppCompatActivity {

    EditText url;
    EditText e1;
   // ClipboardManager clipboardManager2;
    //ClipData clipData2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_join);
        url = findViewById(R.id.url);
        e1 = findViewById(R.id.result);
    }

    public void search(View view) {
        DatabaseReference ref = FirebaseDatabase.getInstance().getReference();
        Query query = ref.child("session").orderByChild("URL").equalTo(this.url.getText().toString());
        query.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                boolean somedata = false;
                for (DataSnapshot snap : dataSnapshot.getChildren()) {
                    somedata = true;
                    //openDialog();

                    e1.setText("" + snap.child("Title").getValue());
                }
                if (!somedata) {
                    e1.setText("Invalid url");
                } else {

                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Log.e("my-log", "data : " + databaseError.getMessage());
            }
        });
    }

   /* public void paste(View view) {


        clipData2 = clipboardManager2.getPrimaryClip();
        ClipData.Item item = clipData2.getItemAt(0);
        String copied = item.getText().toString();
        url.setText(copied);

    }*/

   /*
   public static class UserNameDialogFragment extends DialogFragment {

        @Override
        public Dialog onCreateDialog(Bundle savedInstanceState) {
            AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
            // Get the layout inflater
            LayoutInflater inflater = getActivity().getLayoutInflater();

            // Inflate and set the layout for the dialog
            // Pass null as the parent view because its going in the dialog layout
            builder.setView(inflater.inflate(R.layout.username_dialog, null))
                    // Add action buttons
                    .setPositiveButton(R.string.register, new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int id) {
                            // sign in the user ...

                            EditText userNameField = ((AlertDialog) dialog).findViewById(R.id.username);
                            String username = null;
                            if (userNameField != null) {
                                username = userNameField.getText().toString();
                            }







                        }
                    });

            return builder.create();
        }
    */
}
