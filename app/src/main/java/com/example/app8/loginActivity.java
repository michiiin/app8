package com.example.app8;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class loginActivity extends AppCompatActivity {

    private FirebaseAuth mFirebaseAuth; //파이어베이스 인증
    private DatabaseReference mDatabaseRef; // 실시간데이터베이스
    private EditText txt_email,txt_pwd; // 로그인 입력필드



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        mFirebaseAuth = FirebaseAuth.getInstance();
        mDatabaseRef = FirebaseDatabase.getInstance().getReference("app8") ;

        txt_email = findViewById(R.id.txt_email);
        txt_pwd = findViewById(R.id.txt_pwd);

        Button signup_button = findViewById(R.id.signup_button);
        Button login_button =findViewById(R.id.login_button);


        login_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //로그인 요청

                String strEmail = txt_email.getText().toString();
                String strPwd = txt_pwd.getText().toString();

                // 입력값이 비어 있는지 확인
                if (strEmail.isEmpty() || strPwd.isEmpty()) {
                    Toast.makeText(loginActivity.this, "아이디와 비밀번호를 입력해주세요", Toast.LENGTH_SHORT).show();
                    return;
                }

                mFirebaseAuth.signInWithEmailAndPassword(strEmail,strPwd).addOnCompleteListener(loginActivity.this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()){
                            //로그인 성공
                            Intent intent = new Intent(loginActivity.this, ActivityMain.class);
                            startActivity(intent);
                            finish();//현재 액티비티 파기
                        }else {
                            Toast.makeText(loginActivity.this, "로그인 실패: " + task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    }
                });

            }
        });



        signup_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // 회원가입 화면으로 이동

                Intent intent = new Intent(loginActivity.this, signinActivity.class );
                startActivity(intent);
            }
        });
    }
}