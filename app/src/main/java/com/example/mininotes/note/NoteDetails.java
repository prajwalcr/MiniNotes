package com.example.mininotes.note;

import android.content.Intent;
import android.os.Bundle;

import com.example.mininotes.R;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.text.method.ScrollingMovementMethod;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

public class NoteDetails extends AppCompatActivity {

    TextView content, title;
    Intent data;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_note_details);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        data = getIntent();

        content = findViewById(R.id.noteDetailsContent);
        title = findViewById(R.id.noteDetailsTitle);

        content.setMovementMethod(new ScrollingMovementMethod());
        content.setText(data.getStringExtra("content"));
        content.setBackgroundColor(getResources().getColor(data.getIntExtra("color", 0), null));
        title.setText(data.getStringExtra("title"));

        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent i = new Intent(view.getContext(), EditNote.class);
                i.putExtra("title", data.getStringExtra("title"));
                i.putExtra("content", data.getStringExtra("content"));
                i.putExtra("noteId", data.getStringExtra("noteId"));
                startActivity(i);

            }
        });
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if(item.getItemId() == android.R.id.home){
            onBackPressed();
        }
        return super.onOptionsItemSelected(item);
    }
}
