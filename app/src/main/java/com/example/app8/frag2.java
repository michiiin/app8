package com.example.app8;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.SearchView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class frag2 extends Fragment {

    private View view;
    private ArrayList<SingleItem> list = new ArrayList<>();
    private SimpleTextAdapter adapter;
    private ArrayList<SingleItem> original_list; // 추가
    private ArrayList<SingleItem> search_list; // 추
    private EditText editText;
    private ArrayList<SingleItem> originalList = new ArrayList<>();  // 변경
    private ArrayList<SingleItem> searchList = new ArrayList<>(); // 추가




    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.frag2, container, false);

        // 리스트에 아이템 추가
        list.add(new SingleItem("포카칩 오리지널맛", "오리온", R.drawable.pc_ori));//2
        list.add(new SingleItem("오리지널 뺴뺴로", "롯데", R.drawable.pepeori));//3
        list.add(new SingleItem("누드초코 뺴뺴로", "롯데", R.drawable.nude));//5
        list.add(new SingleItem("크런키 뺴뺴로", "롯데", R.drawable.crunky));//6
        list.add(new SingleItem("아몬드 뺴뺴로", "롯데", R.drawable.amond));//7
        list.add(new SingleItem("화이트 쿠키 뺴뺴로", "롯데", R.drawable.wc));//8
        list.add(new SingleItem("초코쿠키 뺴뺴로", "롯데", R.drawable.cc));//9
        list.add(new SingleItem("초코파이", "오리온", R.drawable.cp));//10
        list.add(new SingleItem("칸쵸", "롯데", R.drawable.ch));//11
        list.add(new SingleItem("허니버터칩", "해테", R.drawable.hb));//12
        list.add(new SingleItem("꼬북칩", "오리온", R.drawable.kko));//13
        list.add(new SingleItem("쿠크다스 화이트", "크라운", R.drawable.das));//14
        list.add(new SingleItem("프링글스 오리지널맛", "켈로그", R.drawable.prin));//15
        list.add(new SingleItem("프링글스 사워크림 & 어니언맛", "켈로그", R.drawable.prinoni));//16
        list.add(new SingleItem("프링글스 치즈맛", "켈로그", R.drawable.princh));//17
        list.add(new SingleItem("프링글스 BBQ맛", "켈로그", R.drawable.bbq));//18
        list.add(new SingleItem("프링글스 피자맛", "켈로그", R.drawable.pizza));//19
        list.add(new SingleItem("프링글스 핫 앤 스파이시맛", "켈로그", R.drawable.spicy));//20
        list.add(new SingleItem("오징어 땅콩", "오리온", R.drawable.ozing));//21
        list.add(new SingleItem("몽쉘", "롯데", R.drawable.mong));//22
        list.add(new SingleItem("카스타드", "롯데", R.drawable.cas));//23
        list.add(new SingleItem("도리토스 나쵸지즈맛", "롯데", R.drawable.dori));//24
        list.add(new SingleItem("치토스 바베큐맛", "롯데", R.drawable.chito));//25
        list.add(new SingleItem("칙촉", "롯데", R.drawable.chic));//26
        list.add(new SingleItem("마가렛트", "롯데", R.drawable.maga));//27
        list.add(new SingleItem("빈츠", "롯데", R.drawable.bin));//28
        list.add(new SingleItem("홈런볼", "해테", R.drawable.homerun));//29
        list.add(new SingleItem("꼬깔콘 고소한맛", "롯데", R.drawable.corn));//30

        originalList.addAll(list);

        // recyclerView, adapter 연결
        RecyclerView recyclerView = view.findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        adapter = new SimpleTextAdapter(list);
        recyclerView.setAdapter(adapter);


        adapter.setOnItemClickListener(new SimpleTextAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(int position) {
                // SingleItem을 클릭한 경우 처리
                SingleItem clickedItem = list.get(position);
                // 클릭된 아이템 정보를 가지고 detail activity 시작
                Intent myIntent = new Intent(getActivity(), ItemDetailActivity.class);
                myIntent.putExtra("clickedItem", clickedItem);
                startActivity(myIntent);

            }
        });

        editText = view.findViewById(R.id.editText);

        original_list = new ArrayList<>(list);
        search_list = new ArrayList<>();

        editText = view.findViewById(R.id.editText); // editText를 XML에서 정의해야 합니다.

        editText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                String searchText = editText.getText().toString();
                search_list.clear();

                if(searchText.isEmpty()){
                    adapter.setItems(original_list);
                }
                else {
                    // 검색 단어를 포함하는지 확인
                    for (int a = 0; a < original_list.size(); a++) {
                        if (original_list.get(a).name.toLowerCase().contains(searchText.toLowerCase())) {
                            search_list.add(original_list.get(a));
                        }
                    }
                    adapter.setItems(search_list);
                }
            }
        });

        return view;
    }
}
