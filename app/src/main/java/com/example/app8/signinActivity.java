package com.example.app8;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class signinActivity extends AppCompatActivity {

    private FirebaseAuth mFirebaseAuth; //파이어베이스 인증
    private DatabaseReference mDatabaseRef; // 실시간데이터베이스
    private EditText txt_email,txt_pwd; // 회원가입 입력필드
    private Button msignin_button; //회원가입 버튼




    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signin);


        mFirebaseAuth = FirebaseAuth.getInstance();
        mDatabaseRef = FirebaseDatabase.getInstance().getReference("app8") ;

        txt_email = findViewById(R.id.txt_email);
        txt_pwd = findViewById(R.id.txt_pwd);
        msignin_button = findViewById(R.id.signin_button);

        msignin_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //회원가입 처리 시작
                String strEmail = txt_email.getText().toString();
                String strPwd = txt_pwd.getText().toString();


                //firebaseauth 진행

                mFirebaseAuth.createUserWithEmailAndPassword(strEmail, strPwd).addOnCompleteListener(signinActivity.this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if(task.isSuccessful()){
                            // 회원가입 성공 시, 현재 사용자 정보 가져오기
                            FirebaseUser firebaseUser = mFirebaseAuth.getCurrentUser();
                            // 사용자 계정 정보를 담는 객체 생성
                            UserAccount account = new UserAccount();
                            account.setEmailId(firebaseUser.getEmail());
                            account.setIdToken(firebaseUser.getUid());
                            account.setPassword(strPwd);

                            // Firebase 실시간 데이터베이스에 사용자 정보 저장
                            mDatabaseRef.child("UserAccount").child(firebaseUser.getUid()).setValue(account);

                            Toast.makeText(signinActivity.this, "회원가입 성공", Toast.LENGTH_SHORT).show();

                        } else {
                            Toast.makeText(signinActivity.this, "회원가입 실패", Toast.LENGTH_SHORT).show();
                        }
                    }
                });



            }
        });





    }
}