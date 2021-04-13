package com.example.mininotes;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class Splash extends AppCompatActivity {

    FirebaseAuth fAuth;
    ConstraintLayout constraintLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        fAuth = FirebaseAuth.getInstance();
        constraintLayout = findViewById(R.id.splashLayout);

        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {

                // Check if user is logged in

                if(fAuth.getCurrentUser() != null){
                    Toast.makeText(Splash.this, "Logged in as " + fAuth.getCurrentUser(), Toast.LENGTH_SHORT).show();
                    startActivity(new Intent(getApplicationContext(), MainActivity.class));
                    finish();
                }
                else{
                    // create new anonymous account
                    fAuth.signInAnonymously().addOnSuccessListener(new OnSuccessListener<AuthResult>() {
                        @Override
                        public void onSuccess(AuthResult authResult) {
                            Toast.makeText(Splash.this, "Logged in with temporary account", Toast.LENGTH_SHORT).show();
                            startActivity(new Intent(getApplicationContext(), MainActivity.class));
                            finish();
                        }
                    }).addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            Toast.makeText(Splash.this, "Error" + e.getMessage(), Toast.LENGTH_SHORT).show();

                            Snackbar snackbar = Snackbar
                                    .make(constraintLayout, "Check Your Internet Connection", Snackbar.LENGTH_INDEFINITE)
                                    .setAction("RETRY", new View.OnClickListener() {
                                        @Override
                                        public void onClick(View view) {
                                            startActivity(new Intent(getApplicationContext(), Splash.class));
                                        }
                                    })
                                    .setActionTextColor(Color.RED);
                            snackbar.show();
                        }
                    });
                }


            }
        }, 2000);
    }
}
