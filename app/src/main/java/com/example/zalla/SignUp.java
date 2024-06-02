package com.example.zalla;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;

import android.content.Intent;
import android.os.Bundle;
import android.text.InputType;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.auth.FirebaseAuth;

public class SignUp extends AppCompatActivity {
    EditText fullnameInp, usernameInp, emailInp, pNumberInp,
            passwordInp, conPasswordInp, addressInp;
    TextView signInBtn;
    ImageView userImgProfile;
    DatabaseReference userRef;
    private boolean passwordShowing = false;
    private boolean conPasswordShowing = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        //DATABASE CONFIG
        userRef = FirebaseDatabase.getInstance().getReference("clientUsers");

        fullnameInp = findViewById(R.id.fNameField);
        usernameInp = findViewById(R.id.usernameField);
        emailInp = findViewById(R.id.emailField);
        pNumberInp = findViewById(R.id.pNumberField);
        addressInp = findViewById(R.id.addressFiled);

        passwordInp = findViewById(R.id.passwordField);
        conPasswordInp = findViewById(R.id.conPasswordField);

        final ImageView passwordIcon = findViewById(R.id.passwordIcon);
        final ImageView conPasswordIcon = findViewById(R.id.conPasswordIcon);
        final AppCompatButton signUpBtn = findViewById(R.id.signUpBtn);
        signInBtn = findViewById(R.id.signInBtn);

        passwordIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //check password
                if (passwordShowing){
                    passwordShowing = false;

                    passwordInp.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
                    passwordIcon.setImageResource(R.drawable.password_show);
                }
                else {
                    passwordShowing = true;

                    passwordInp.setInputType(InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD);
                    passwordIcon.setImageResource(R.drawable.password_hide);
                }
                passwordInp.setSelection(passwordInp.length());
            }
        });

        conPasswordIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //check password
                if (conPasswordShowing){
                    conPasswordShowing = false;

                    conPasswordInp.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
                    passwordIcon.setImageResource(R.drawable.password_show);
                }
                else {
                    conPasswordShowing = true;

                    conPasswordInp.setInputType(InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD);
                    conPasswordIcon.setImageResource(R.drawable.password_hide);
                }
                conPasswordInp.setSelection(conPasswordInp.length());
            }
        });




        signUpBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //INPUT VALIDATION
                if(fullnameInp.getText().toString().trim().isEmpty()){
                    fullnameInp.setError("Please fill your fullname!");
                    return;
                }
                if(usernameInp.getText().toString().trim().isEmpty()){
                    usernameInp.setError("Please fill your username!");
                    return;
                }
                if(emailInp.getText().toString().trim().isEmpty()){
                    emailInp.setError("Please fill your email!");
                    return;
                }
                if(addressInp.getText().toString().trim().isEmpty()){
                    addressInp.setError("Please complete your address");
                    return;
                }
                if(pNumberInp.getText().toString().trim().isEmpty()){
                    pNumberInp.setError("Please fill your phone number");
                    return;
                }
                if(passwordInp.getText().toString().trim().isEmpty()){
                    passwordInp.setError("Please fill account password");
                    return;
                }
                //VALIDATION PASSWORD FOR AT LEAST HAS 6 LENGTH WORDS
                if(passwordInp.getText().toString().trim().length() <6 ){
                    passwordInp.setError("Password must have atleast 6 characters!");
                    return;
                }

                //CHECK PASSWORD AND CONFIRMATION PASSWORD
                if(!passwordInp.getText().toString().equals(conPasswordInp.getText().toString())){
                    conPasswordInp.setError("Password and Co-Password didn't mastch!");
                    return;
                }

                //Making user authentication and new user table
                String userImgProfile = "";
                String fullname = fullnameInp.getText().toString().trim();
                String username = usernameInp.getText().toString().trim();
                String email = emailInp.getText().toString().trim();
                String address = addressInp.getText().toString().trim();
                String phone = pNumberInp.getText().toString().trim();
                String password = passwordInp.getText().toString().trim();
                FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();
                firebaseAuth.createUserWithEmailAndPassword(email, password)
                        .addOnCompleteListener(task -> {
                            if(task.isSuccessful()){
                                signUp(userImgProfile, fullname, email, address, phone, password);
                                Intent intent = new Intent(SignUp.this, OTPVerification.class);
                                startActivity(intent);
                                finish();
                            } else{
                                String errorMessage = task.getException().getMessage();
                                Toast.makeText(getApplicationContext(), errorMessage, Toast.LENGTH_SHORT).show();
                            }
                        });
            }
        });

        signInBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }


    //SIGN UP USER TO FIREBASE METHOD
    private  void signUp(String userImgProfile, String fullname, String email, String address, String pNumber,
                         String password){
        String username = usernameInp.getText().toString().trim();
        //GENERATE NEW USER ID
        DatabaseReference newUser = userRef.push();
        String userId = newUser.getKey(); //Retrieve the new user ID

        DatabaseReference childReference = newUser.child("username");
        childReference.setValue(username);
        
        childReference = newUser.child("userImgProfile");
        childReference.setValue(userImgProfile);
        
        childReference = newUser.child("fullname");
        childReference.setValue(fullname);

        childReference = newUser.child("email");
        childReference.setValue(email);

        childReference = newUser.child("address");
        childReference.setValue(address);

        childReference = newUser.child("pNumber");
        childReference.setValue(pNumber);

        childReference = newUser.child("password");
        childReference.setValue(password).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if(task.isSuccessful()){
                    Toast.makeText(getApplicationContext(), "Sign Up Successfull!", Toast.LENGTH_SHORT).show();
                } else{
                    Toast.makeText(getApplicationContext(), "Failed to Sign Up!", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}