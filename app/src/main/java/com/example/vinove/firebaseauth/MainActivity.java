package com.example.vinove.firebaseauth;

import android.app.Dialog;
import android.app.DialogFragment;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.FirebaseDatabase;

import model.User;

public class MainActivity extends AppCompatActivity {
    private EditText editTextUserName;
    private EditText editTextPassword;
    private FirebaseAuth mAuth;
    private FirebaseAuth.AuthStateListener authStateListener;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        editTextUserName = findViewById(R.id.editTextUserName_id);
        editTextPassword = findViewById(R.id.editTextPassword_id);

        mAuth = FirebaseAuth.getInstance();
        authStateListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                FirebaseUser user = firebaseAuth.getCurrentUser();
                if (user != null) {
                    Toast.makeText(getApplicationContext(),
                            "LoggedIn As :" + user.getEmail(), Toast.LENGTH_SHORT).show();


                } else {
                    Toast.makeText(getApplicationContext(), "User not Logged In", Toast.LENGTH_LONG).show();
                }
            }
        };


    }


    public void login(View view) {

        if (editTextUserName.getText().toString().length() == 0) {
            editTextUserName.setError("Username required");
            editTextUserName.requestFocus();
        }
        if ((editTextPassword.getText().toString().length() == 0)) {
            editTextPassword.setError("Password required");
            editTextUserName.requestFocus();
        } else {
            mAuth.signInWithEmailAndPassword(editTextUserName.getText().toString(), editTextPassword.getText().toString())
                    .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (!task.isSuccessful()) {

                                Toast.makeText(MainActivity.this, "Authentication Failed", Toast.LENGTH_LONG).show();
                            } else {

                                Intent intent = new Intent(getBaseContext(), ChatActivity.class);
                                startActivity(intent);
                            }
                        }
                    });

        }

    }

    public void register(View view) {

        {
            if ((editTextUserName.getText().toString().length() == 0)) {
                editTextUserName.setError("Username required");
                editTextUserName.requestFocus();

            }


            if ((editTextPassword.getText().toString().length() == 0)) {
                editTextPassword.setError("Password required");
                editTextUserName.requestFocus();
            } else {

                mAuth.createUserWithEmailAndPassword(editTextUserName.getText().toString(), editTextPassword.getText().toString())
                        .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                if (!task.isSuccessful()) {
                                    // Sign in success, update UI with the signed-in user's information

                                    Toast.makeText(getApplicationContext(),
                                            "Authentication Failed", Toast.LENGTH_SHORT).show();

                                } else {
/*
                                    Toast.makeText(getApplicationContext(),
                                            "Authentication Success", Toast.LENGTH_SHORT).show();
                               */

                                    UserNameDialogFragment dialog = new UserNameDialogFragment();
                                    dialog.show(getFragmentManager(), null);

                                }

                            }
                        });
            }
        }
    }

    @Override
    public void onStart() {
        super.onStart();
        // Check if user is signed in (non-null) and update UI accordingly.
        mAuth.addAuthStateListener(authStateListener);
    }

    @Override
    protected void onStop() {
        super.onStop();
        if (authStateListener != null) {
            mAuth.removeAuthStateListener(authStateListener);
        }
        mAuth.signOut();
    }


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

//                            https://fir-auth-2d438.firebaseio.com/fir-auth-2d438/<--
// -->>users/0JNvDnkN5mNgIZTR0j2KPKAUgAH2/profile/username
/**
 * FirebaseDatabase.getInstance()
 * above code grabbing below url
 * https://fir-auth-2d438.firebaseio.com/fir-auth-2d438/
 *
 *Note: And in Firebase, if the path doesn't exist it will create it(like mongoDB)
 */
                            EditText userNameField = ((AlertDialog) dialog).findViewById(R.id.username);
                            String username = null;
                            if (userNameField != null) {
                                username = userNameField.getText().toString();
                            }

                            /**
                             * by this we get the unique id that user is logged in(just created an account)
                             */
                            String userId = FirebaseAuth.getInstance().getCurrentUser().getUid();  // by this we get the reference of current user!!


                            /**
                             * How we create unique user in database
                             */

                            User aUser = new User(username, "Empty", "Empty");

                            FirebaseDatabase.getInstance().getReference("users").child(userId).child("profile").child("username").setValue(aUser);

                            Intent intent = new Intent(getActivity().getBaseContext(), ChatActivity.class);
                            startActivity(intent);

                        }
                    });

            return builder.create();
        }
    }
}

