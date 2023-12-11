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
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;


public class frag3 extends Fragment {

    private List<EatDB> eatList = new ArrayList<>();

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
        Button btn2 = view.findViewById(R.id.btn2);
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
                getActivity().finish(); // 현재 액티비티 또는 프래그먼트 닫기ㅁㄴ
            }
        });
        btn2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                retrieveEatDBData();
            }
        });

        return view;
    }
    private void retrieveEatDBData() {
        String uid = mFirebaseAuth.getCurrentUser().getUid();
        DatabaseReference userEatDBRef = mDatabaseRef.child("UserAccount").child(uid).child("eatDB");

        userEatDBRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                eatList.clear();
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    EatDB eatDB = snapshot.getValue(EatDB.class);
                    if (eatDB != null) {
                        eatList.add(eatDB);
                    }
                }

                // 데이터가 변경될 때마다 리스트뷰를 업데이트
                updateListView();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                // 데이터 가져오기 실패 처리
                Toast.makeText(getActivity(), "데이터 가져오기 실패", Toast.LENGTH_SHORT).show();
            }
        });
    }
    private void updateListView() {

        ListView listView = view.findViewById(R.id.View);

        ArrayAdapter<String> adapter = new ArrayAdapter<>(getActivity(), android.R.layout.simple_list_item_1);

        for (EatDB eatDB : eatList) {
            String strTN = eatDB.getStrTN();
            adapter.add(strTN);
        }
        listView.setAdapter(adapter);
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
