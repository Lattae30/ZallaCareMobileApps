package com.example.zalla.fragment;

import android.app.Dialog;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.zalla.MainActivity;
import com.example.zalla.R;
import com.example.zalla.SignIn;
import com.example.zalla.UserEdit;
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

import org.w3c.dom.Text;

import java.util.ArrayList;

public class UserFragment extends Fragment {
    DatabaseReference userRef;
    ArrayList<Users> usersArrayList;
    Button cancelBtn, okBtn, signOutBtn;
    Dialog mDialog;
    ImageView userImgProfile;
    TextView fullname, username, emailProfile, phone, address;

    public UserFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_user, container, false);
        getActivity().setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

        //GETIING ID FROM XML
        userImgProfile = view.findViewById(R.id.userProfile);
        fullname = view.findViewById(R.id.fullname);
        username = view.findViewById(R.id.username);
        emailProfile = view.findViewById(R.id.emailProfile);
        phone = view.findViewById(R.id.phoneField);
        address = view.findViewById(R.id.addressField);


        //CONNECT TO TABLE REFERENCE
        userRef = FirebaseDatabase.getInstance().getReference("clientUsers");
        FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();
        FirebaseUser currentUser = firebaseAuth.getCurrentUser();
        if(currentUser != null){
            //GETTING CURRENT USER EMAIL
            String email = currentUser.getEmail();

            //GETTING USER DATA FROM THE CURRENT USER IN FIREBASE
            userRef.orderByChild("email").equalTo(email).addListenerForSingleValueEvent(
                    new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot snapshot) {
                            if(snapshot.exists()){
                                for(DataSnapshot dataSnapshot : snapshot.getChildren()){
                                    String userId = dataSnapshot.getKey();
                                    String ImgProfile = dataSnapshot.child("userImgProfile").getValue(String.class);
                                    String fullName = dataSnapshot.child("fullname").getValue(String.class);
                                    String userName = dataSnapshot.child("username").getValue(String.class);
                                    String uEmail = dataSnapshot.child("email").getValue(String.class);
                                    String phoneNum = dataSnapshot.child("pNumber").getValue(String.class);
                                    String uAddress = dataSnapshot.child("address").getValue(String.class);

                                    //SET THE DATA TO FRAGMENT USER PROFILE
                                    //Displating Image Profile
                                    StorageReference storageReference = FirebaseStorage.getInstance().getReference();
                                    StorageReference imageReference = storageReference.child("userImgProfile/" + ImgProfile);

                                    imageReference.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                                        @Override
                                        public void onSuccess(Uri uri) {
                                            Picasso.get().load(uri).into(userImgProfile);
                                        }
                                    }).addOnFailureListener(new OnFailureListener() {
                                        @Override
                                        public void onFailure(@NonNull Exception e) {

                                        }
                                    });

                                    fullname.setText(fullName);
                                    username.setText(userName);
                                    emailProfile.setText(uEmail);
                                    phone.setText(phoneNum);
                                    address.setText(uAddress);
                                }
                            }
                        }
                        @Override
                        public void onCancelled(@NonNull DatabaseError error) {

                        }
                    }
            );
        }


        //Intent to Edit Profie
//        TextView editProfile = (TextView) view.findViewById(R.id.editProfile);
//        editProfile.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Intent intent = new Intent(getActivity(), UserEdit.class);
//                startActivity(intent);
//            }
//        });

        //Sign Out
        signOutBtn = (Button) view.findViewById(R.id.signOutBtn);
        mDialog = new Dialog(getActivity());
        signOutBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mDialog.setContentView(R.layout.alert_sign_out);
                mDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                mDialog.show();

                cancelBtn = mDialog.findViewById(R.id.cancelBtnSignOut);
                cancelBtn.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent(getActivity(), MainActivity.class);
                        startActivity(intent);
                        mDialog.dismiss();
                    }
                });

                okBtn = mDialog.findViewById(R.id.okBtnSignOut);
                okBtn.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent(getActivity(), SignIn.class);
                        startActivity(intent);
                        mDialog.dismiss();
                    }
                });
            }
        });
        return view;
    }
}