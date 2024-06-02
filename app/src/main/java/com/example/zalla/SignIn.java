package com.example.zalla;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.InputType;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;

public class SignIn extends AppCompatActivity {

    private boolean passwordShowing = false;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_in);

        final EditText emailField = findViewById(R.id.emailField);
        final EditText passwordField = findViewById(R.id.passwordField);
        final ImageView passwordIcon = findViewById(R.id.passwordIcon);
        final TextView signUpBtn = findViewById(R.id.signUpBtn);

        final Button signIn = findViewById(R.id.signInBtn);

        passwordIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //check password
                if (passwordShowing){
                    passwordShowing = false;

                    passwordField.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
                    passwordIcon.setImageResource(R.drawable.password_show);
                }
                else {
                    passwordShowing = true;

                    passwordField.setInputType(InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD);
                    passwordIcon.setImageResource(R.drawable.password_hide);
                }
                passwordField.setSelection(passwordField.length());
            }
        });

        signUpBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(SignIn.this, SignUp.class));

            }
        });

        //BTN SIGN IN ON CLICK LISTENER
        signIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //CHECK EMPTY FIELD
                if(emailField.getText().toString().isEmpty()){
                    emailField.setError("Please fill your email!");
                    return;
                }
                if(passwordField.getText().toString().isEmpty()){
                    passwordField.setError("Please input you password!");
                    return;
                }

                FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();
                String email = emailField.getText().toString().trim();
                String password = passwordField.getText().toString().trim();
                firebaseAuth.signInWithEmailAndPassword(email, password)
                        .addOnCompleteListener(task -> {
                            if(task.isSuccessful()){
                                //SUCCESS LOGIN
                                Intent intent = new Intent(SignIn.this, MainActivity.class);
                                startActivity(intent);
                                Toast.makeText(getApplicationContext(),"Login Success, wellcome to Zalla Care",Toast.LENGTH_SHORT).show();
                                finish();
                            } else {
                                Toast.makeText(getApplicationContext(),"Failed to login...",Toast.LENGTH_SHORT).show();
                            }
                        });
            }
        });

    }

}