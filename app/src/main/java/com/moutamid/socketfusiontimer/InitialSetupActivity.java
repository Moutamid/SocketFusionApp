package com.moutamid.socketfusiontimer;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.fxn.stash.Stash;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ServerValue;

public class InitialSetupActivity extends AppCompatActivity {

    private EditText etEmail;
    private Button btnNext;
    private DatabaseReference databaseReference;
    private DatabaseReference userStatusRef;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_initial_setup);

        etEmail = findViewById(R.id.etEmail);
        btnNext = findViewById(R.id.btnNext);

        databaseReference = FirebaseDatabase.getInstance().getReference("SocketApp").child("Users");

        btnNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email = etEmail.getText().toString().trim();
                if (!email.isEmpty() && android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
                    // Use email as the userID
                    String userId = email.replace(".", ",");
                    userStatusRef = databaseReference.child(userId).child("status");
                    userStatusRef.setValue("online");
                    DatabaseReference emailRef = databaseReference.child(userId).child("email");
                    emailRef.setValue(email);
                    setupUserPresence(userId);
                    Stash.put("email", email);
                    Intent intent = new Intent(InitialSetupActivity.this, MainActivity.class);
                    startActivity(intent);
                    finish();
                } else {
                    Toast.makeText(InitialSetupActivity.this, "Please enter a valid email", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private void setupUserPresence(String userId) {
        final DatabaseReference userRef = FirebaseDatabase.getInstance().getReference("SocketApp").child("Users").child(userId);

        userRef.child("status").onDisconnect().setValue("offline");
        userRef.child("last_seen").onDisconnect().setValue(ServerValue.TIMESTAMP);

        userRef.child("status").setValue("online");
    }
}
