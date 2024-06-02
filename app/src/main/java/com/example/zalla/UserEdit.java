package com.example.zalla;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentTransaction;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.net.Uri;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.zalla.fragment.UserFragment;
import com.example.zalla.model.Users;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class UserEdit extends AppCompatActivity {
    DatabaseReference userRef;
    ArrayList<Users> usersArrayList;
    ImageView userImgProfile;
    EditText emailEdit, passwordEdit, phoneEdit, addressEdit;
    Users user;
    String userId;
    Button saveChanges;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_edit);

        this.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

        userRef = FirebaseDatabase.getInstance().getReference("clientUsers");

        //GETIING ID
        userImgProfile = findViewById(R.id.userImgEdit);
        emailEdit = findViewById(R.id.emailFieldEdit);
        passwordEdit = findViewById(R.id.passwordFieldEdit);
        phoneEdit = findViewById(R.id.pNumberFieldEdit);
        addressEdit = findViewById(R.id.addressEdit);
        saveChanges = findViewById(R.id.saveChanges);

        String email = getEmail();

        //LOAD USER DATA
        loadUserDataFromFirebase(email);

        //SUBMIT OR SAVE CHANGES BUTTON
        saveChanges.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Validation for the username field
                if (emailEdit.getText().toString().trim().isEmpty()){
                    emailEdit.setError("Username must be filled!");
                    return;
                }
                //Validation for the email field
                if (passwordEdit.getText().toString().trim().isEmpty()){
                    passwordEdit.setError("Email must be filled!");
                    return;
                }
                //Check whether if the email have the correct pattern from the pattern made from android
                if (!Patterns.EMAIL_ADDRESS.matcher(emailEdit.getText().toString().trim()).matches()){
                    emailEdit.setError("Please enter valid email!");
                    return;
                }
                //Validation for the password field
                if (passwordEdit.getText().toString().trim().isEmpty()){
                    passwordEdit.setError("Password must be filled!");
                    return;
                }
                // Vaidation length of password min 6 char
                if(passwordEdit.getText().toString().trim().length() < 6){
                    passwordEdit.setError("Password at least 6 character");
                    return;
                }

                //Alert dialog
                AlertDialog.Builder alertBuilder= new AlertDialog.Builder(UserEdit.this);
                alertBuilder.setTitle("Setting Account");
                alertBuilder.setMessage("Save Changes?").setCancelable(false).setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        updateUserToFirebase();
                    }
                }).setNegativeButton("No", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                    }
                });
                alertBuilder.show();
            }
        });
}

    //GET EMAIL METHOD
    private String getEmail(){
        FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();
        FirebaseUser currentUser = firebaseAuth.getCurrentUser();
        if(currentUser != null){
            return currentUser.getEmail();
        } else{
            return null;
        }
    }
    private void loadUserDataFromFirebase(String uEmail) {
        userRef.orderByChild("email").equalTo(uEmail).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                    userId = dataSnapshot.getKey();
                    emailEdit.setText(dataSnapshot.child("email").getValue(String.class));
                    passwordEdit.setText(dataSnapshot.child("password").getValue(String.class));
                    phoneEdit.setText(dataSnapshot.child("pNumber").getValue(String.class));
                    addressEdit.setText(dataSnapshot.child("address").getValue(String.class));
                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
    private void updateUserToFirebase(){
        String uEmail = emailEdit.getText().toString().trim();
        String password = passwordEdit.getText().toString().trim();
        String phone = phoneEdit.getText().toString().trim();
        String address = addressEdit.getText().toString().trim();

        FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();
        FirebaseUser currentUser = firebaseAuth.getCurrentUser();
        //AUTENTIKASI UPDATE PASSWORD
        currentUser.updatePassword(password).addOnSuccessListener(
                new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void update) {
                        userRef.child(userId).child("email").setValue(uEmail);
                        userRef.child(userId).child("pNumber").setValue(phone);
                        userRef.child(userId).child("password").setValue(password);
                        userRef.child(userId).child("address").setValue(address)
                                .addOnSuccessListener(new OnSuccessListener<Void>() {
                                    @Override
                                    public void onSuccess(Void unused) {
                                        // Data user berhasil diupdate
                                        Toast.makeText(UserEdit.this, "Account settings updated successfully", Toast.LENGTH_SHORT).show();
                                        // Lakukan navigasi atau tindakan lain setelah update berhasil
                                        Intent intent = new Intent(UserEdit.this,MainActivity.class);
                                        startActivity(intent);
                                        finish();
                                    }
                                }).addOnFailureListener(new OnFailureListener() {
                                    @Override
                                    public void onFailure(@NonNull Exception e) {
                                        Toast.makeText(UserEdit.this, "Failed to update user data", Toast.LENGTH_SHORT).show();
                                    }
                                });
                    }
                }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(UserEdit.this, e.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }
}