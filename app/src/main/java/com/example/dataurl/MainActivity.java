package com.example.dataurl;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.QuickContactBadge;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.FirebaseApp;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.firestore.FirebaseFirestore;

import java.net.MalformedURLException;
import java.net.URL;


public class MainActivity extends AppCompatActivity {
    EditText editText;
    TextView result;
    Button btn;
    FirebaseDatabase databaseReference;
    FirebaseAuth firebaseAuth;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        editText = findViewById(R.id.edit);
        result = findViewById(R.id.textView);
        btn = findViewById(R.id.button);

        FirebaseApp.initializeApp(this);
        firebaseAuth = FirebaseAuth.getInstance();
        databaseReference = FirebaseDatabase.getInstance();

        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                shortenURL();
            }
        });
    }
    private void shortenURL(){
        String longUrl = editText.getText().toString();
        if(isValidUrl(longUrl)){
            DatabaseReference reference = databaseReference.getReference("urls");
            String key = reference.push().getKey();
            reference.child(key).setValue(longUrl);

            String shortUrl = "https://DATAURL.com/" +key;
            result.setText(shortUrl);
            result.setVisibility(View.VISIBLE);
        }else{
            Toast.makeText(this,"Invalid URL",Toast.LENGTH_SHORT).show();
        }
    }
    private boolean isValidUrl(String url){
        try{
            new URL(url);
            return true;
        }catch (MalformedURLException e){
            return false;
        }
    }
}
