package com.example.app8;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.MimeTypeMap;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.io.IOException;


public class frag3 extends Fragment {

    private View view;
    private FirebaseAuth mFirebaseAuth; // 파이어베이스 인증
    private DatabaseReference mDatabaseRef; // 실시간데이터베이스

    private EditText editTextNickname;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.frag3, container, false);

        mFirebaseAuth = FirebaseAuth.getInstance();
        mDatabaseRef = FirebaseDatabase.getInstance().getReference("app8");

        editTextNickname = view.findViewById(R.id.editTextNickname);

        Button updateProfileButton = view.findViewById(R.id.btn_update_profile);
        Button logoutButton = view.findViewById(R.id.btn_logout);

        updateProfileButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                updateProfile(editTextNickname.getText().toString());
            }
        });

        logoutButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), loginActivity.class);
                startActivity(intent);
                getActivity().finish(); // 현재 액티비티 또는 프래그먼트 닫기
            }
        });

        return view;
    }

    private void updateProfile(String newNickname) {
        // 닉네임만 업데이트
        updateNicknameInDatabase(newNickname);
    }

    private void updateNicknameInDatabase(String newNickname) {
        // 사용자의 UID를 얻어오기
        String uid = mFirebaseAuth.getCurrentUser().getUid();

        // UserProfile 객체 생성
        UserProfile userProfile = new UserProfile();
        userProfile.setNickname(newNickname);

        // Firebase 실시간 데이터베이스에 닉네임 정보 업데이트
        mDatabaseRef.child("UserProfile").child(uid).child("nickname").setValue(newNickname)
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        Toast.makeText(getActivity(), "닉네임 업데이트 성공", Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(getActivity(), "닉네임 업데이트 실패", Toast.LENGTH_SHORT).show();
                    }
                });
    }
}