package com.example.mininotes.auth;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.mininotes.MainActivity;
import com.example.mininotes.R;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.EmailAuthProvider;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserProfileChangeRequest;

public class Register extends AppCompatActivity {

    EditText rUserName, rUserEmail, rUserPassword, rUserConfPassword;
    Button syncAcc;
    TextView login;
    ProgressBar progressBar;
    FirebaseAuth fAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        getSupportActionBar().setTitle("Connect To MiniNotes Account");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        rUserName = findViewById(R.id.userName);
        rUserEmail = findViewById(R.id.userEmail);
        rUserPassword = findViewById(R.id.password);
        rUserConfPassword = findViewById(R.id.passwordConfirm);

        syncAcc = findViewById(R.id.createAccount);
        login = findViewById(R.id.login);

        progressBar = findViewById(R.id.progressBar4);

        fAuth = FirebaseAuth.getInstance();

        syncAcc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final String uUserName = rUserName.getText().toString();
                String uUserEmail = rUserEmail.getText().toString();
                String uUserPassword = rUserPassword.getText().toString();
                String uUserConfPassword = rUserConfPassword.getText().toString();

                if(uUserName.isEmpty() || uUserEmail.isEmpty() || uUserPassword.isEmpty() || uUserConfPassword.isEmpty()){
                    Toast.makeText(Register.this, "All Fields Are Required", Toast.LENGTH_SHORT).show();
                    return;
                }
                
                if(! uUserPassword.equals(uUserConfPassword)){
                    rUserConfPassword.setError("Passwords Do Not Match");
                    return;
                }

                progressBar.setVisibility(View.VISIBLE);

                AuthCredential credential = EmailAuthProvider.getCredential(uUserEmail, uUserPassword);
                fAuth.getCurrentUser().linkWithCredential(credential).addOnSuccessListener(new OnSuccessListener<AuthResult>() {
                    @Override
                    public void onSuccess(AuthResult authResult) {
                        Toast.makeText(Register.this, "Notes Are Synced.", Toast.LENGTH_SHORT).show();
                        progressBar.setVisibility(View.INVISIBLE);

                        startActivity(new Intent(getApplicationContext(), MainActivity.class));
                        finish();

                        FirebaseUser user = fAuth.getCurrentUser();
                        UserProfileChangeRequest request = new UserProfileChangeRequest.Builder()
                                .setDisplayName(uUserName)
                                .build();
                        user.updateProfile(request);
                        startActivity(new Intent(getApplicationContext(), MainActivity.class));

                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(Register.this, "Failed To Connect. Try Again.", Toast.LENGTH_SHORT).show();
                        progressBar.setVisibility(View.INVISIBLE);
                    }
                });


                
            }
        });

        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(), Login.class));
            }
        });

    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        startActivity(new Intent(this, MainActivity.class));
        finish();
        return super.onOptionsItemSelected(item);
    }
}
