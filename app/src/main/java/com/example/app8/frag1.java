package com.example.app8;

import android.content.Intent;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import okhttp3.MediaType;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.Body;
import retrofit2.http.Header;
import retrofit2.http.POST;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class frag1 extends Fragment {
    Double calories, protein, carbs, fat;
    private FirebaseAuth mFirebaseAuth; //파이어베이스 인증
    private DatabaseReference mDatabaseRef; // 실시간데이터베이스
    private View view;
    private TextView txtResult;
    private Button btn1, btn2;
    private EditText TN,TG,TC;
    List<String> list = new ArrayList<>();

    public interface OpenAIApiService {
        @POST("v1/chat/completions")
        Call<ResponseBody> getBarcodeInfo(@Header("Authorization") String authHeader, @Body RequestBody body);
    }


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view= inflater.inflate(R.layout.frag1, container, false);
        txtResult = view.findViewById(R.id.txtResult);
        btn1 = view.findViewById(R.id.btn1);
        btn2 = view.findViewById(R.id.btn2);
        TN = view.findViewById(R.id.TN);
        TG = view.findViewById(R.id.TG);
        TC = view.findViewById(R.id.listView);
        mFirebaseAuth = FirebaseAuth.getInstance();
        FirebaseUser firebaseUser = mFirebaseAuth.getCurrentUser();
        mDatabaseRef = FirebaseDatabase.getInstance().getReference("app8") ;


        btn1.setOnClickListener(new View.OnClickListener() {
            @Override

            public void onClick(View v) {
                calories = null;
                callOpenAIApi();
            }
        });
        btn2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                LocalDateTime now = LocalDateTime.now();
                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd-HH-mm-ss");
                String formattedDate = now.format(formatter);
                if(calories!=null){
                    String strTN = TN.getText().toString();
                    EatDB eatDB = new EatDB(calories, protein, carbs, fat, strTN);

                    mDatabaseRef.child("UserAccount").child(firebaseUser.getUid()).child("eatDB").child(formattedDate).setValue(eatDB);
                }

            }
        });

        return view;
    }
    private void callOpenAIApi() {
        String strTN;
        String strTG="1";
        String strTC;
        strTN = TN.getText().toString();
        strTG = TG.getText().toString();
        strTC = TC.getText().toString();
        String strT = strTC + " " + strTN + "(" + strTG+ "g)의 영양정보";
        HttpLoggingInterceptor loggingInterceptor = new HttpLoggingInterceptor();
        loggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
        OkHttpClient.Builder httpClient = new OkHttpClient.Builder();
        httpClient.addInterceptor(loggingInterceptor);
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://api.openai.com/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        frag1.OpenAIApiService service = retrofit.create(frag1.OpenAIApiService.class);
        String apiKey = "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";
        String authHeader = "Bearer " + apiKey;
        JSONArray arr = new JSONArray();
        JSONObject baseAi = new JSONObject();
        JSONObject userMsg = new JSONObject();
        try {
            baseAi.put("role", "system");
            baseAi.put("content", "질문에 대한 칼로리, 단백질, 탄수화물, 지방을 \"칼로리:(), 단백질:(), 탄수화물:(), 지방:()\" 형식으로 ()안에 값을 넣어주세요 그리고 답변은 간결하게 해주세요 ");
            userMsg.put("role", "user");
            userMsg.put("content", strT);
            arr.put(baseAi);
            arr.put(userMsg);
        } catch (JSONException e) {
            throw new RuntimeException(e);
        }
        JSONObject jsonBody = new JSONObject();
        try {

            jsonBody.put("messages", arr);
            jsonBody.put("max_tokens", 50);
            jsonBody.put("model", "gpt-3.5-turbo");
        } catch (JSONException e) {
            e.printStackTrace();
        }
        RequestBody requestBody = RequestBody.create(jsonBody.toString(), MediaType.parse("application/json"));
        Log.e("API Error", "IOExceptiaaasdasdasdasdon");
        Call<ResponseBody> call = service.getBarcodeInfo(authHeader, requestBody);
        Log.e("API Error", "IOExceptiaasdasdaaon");
        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                if (response.isSuccessful()) {
                    JSONObject jsonObject = null;
                    try {
                        RequestBody requestBody = RequestBody.create(jsonBody.toString(), MediaType.parse("application/json"));
                        Call<ResponseBody> call2 = service.getBarcodeInfo(authHeader, requestBody);
                        jsonObject = new JSONObject(response.body().string());
                        JSONArray jsonArray = jsonObject.getJSONArray("choices");
                        String result = jsonArray.getJSONObject(0).getJSONObject("message").getString("content");
                        txtResult.setText(result);
                        String[] parts = result.split(", ");

                        try {
                            calories = Double.parseDouble(parts[0].split(": ")[1].replaceAll("kcal", "").trim());
                            protein = Double.parseDouble(parts[1].split(": ")[1].replaceAll("g", "").trim());
                            carbs = Double.parseDouble(parts[2].split(": ")[1].replaceAll("g", "").trim());
                            fat = Double.parseDouble(parts[3].split(": ")[1].replaceAll("g", "").trim());
                            String caloriesString = String.valueOf(calories);

//                            EatDB eatDB = new EatDB(calories, protein, carbs, fat);
//                            mDatabaseRef.child("UserAccount").child("eatDB").setValue(eatDB);


                        } catch (NumberFormatException | ArrayIndexOutOfBoundsException | NullPointerException e) {
                            txtResult.setText("정보를 알수 없습니다. 다시 입력 부탁드립니다.");
                            calories = null;
                        }

                    } catch (IOException | JSONException e) {
                        e.printStackTrace();
                        txtResult.setText("허허허허허허허 에러야");
                        calories = null;
                    }
                }else {
                    try {
                        String errorResponse = response.errorBody().string();
                        Log.e("API Error Response", errorResponse);
                    } catch (IOException e) {
                        Log.e("API Error", "에러에요", e);
                    }
                }
            }
            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                Log.e("API Error", "에러입니다", t);
                txtResult.setText("asd");
            }
        });
    }
}
