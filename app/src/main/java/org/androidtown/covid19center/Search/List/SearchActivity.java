package org.androidtown.covid19center.Search.List;


import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;

import androidx.appcompat.app.AppCompatActivity;

import org.androidtown.covid19center.DataBase.AppDatabase;
import org.androidtown.covid19center.Map.LocationConsts;
import org.androidtown.covid19center.R;
import org.androidtown.covid19center.Search.ClinicActivity;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class SearchActivity extends AppCompatActivity {

    private ArrayList<ClinicItem> clinicDataList; // 진료소 담을 리스트 생성
    private ArrayList<ClinicItem> copyList; // 복사리스트
    private EditText clinicSearch; // 진료소 검색
    private ClinicAdapter myAdapter;
    private final Handler mHandler = null;
    private AppDatabase db;
    private ListView listView;
    private List<Double> list;
    private ImageButton backButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);

        clinicSearch = findViewById(R.id.editClinicSearch);
        backButton = findViewById(R.id.search_backButton);
        clinicDataList = new ArrayList<ClinicItem>();
        copyList = new ArrayList<ClinicItem>();
        listView = findViewById(R.id.clinicListView); // 리스트뷰 생성
        myAdapter = new ClinicAdapter(this, clinicDataList); // 진료소 리스트 관리할 어뎁터 생성

        listView.setAdapter(myAdapter); // 리스트뷰에 어뎁터 탑제

        searchInClinic();

        addClinicList();

        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
    }

    private void searchInClinic() {
        clinicSearch.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                String text = clinicSearch.getText().toString();
                search(text);
            }

        });

        // 리스트 아이템 클릭 시 발생 이벤트 콜백함수
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView parent, View v, int position, long id) {
                Intent intent = new Intent(getApplicationContext(), ClinicActivity.class);
                intent.putExtra("clinicName", clinicDataList.get(position).getClinicName());
                intent.putExtra("clinicAddress", clinicDataList.get(position).getClinicAddress());
                intent.putExtra("clinicCallNumber", clinicDataList.get(position).getClinicCallNumber());
                intent.putExtra("clinicDistance", clinicDataList.get(position).getClinicDistance());
                intent.putExtra("clinicX", clinicDataList.get(position).getX());
                intent.putExtra("clinicY", clinicDataList.get(position).getY());
                Log.d("test1", "X" + clinicDataList.get(position).getX());
                Log.d("test1", "Y" + clinicDataList.get(position).getY());
                startActivity(intent);
            }
        });

    }

    // 검색을 수행하는 메소드
    private void search(String charText) {

        clinicDataList.clear();

        Log.d("사이즈", String.valueOf(copyList.size()));

        // 문자 입력이 없을때는 모든 데이터를 보여준다.
        if (charText.length() == 0) {
            clinicDataList.addAll(copyList);
        }
        // 문자 입력을 할때..
        else {
            // 리스트의 모든 데이터를 검색한다.
            for (int i = 0; i < copyList.size(); i++) {
                // arraylist의 모든 데이터에 입력받은 단어(charText)가 포함되어 있으면 true를 반환한다.
                if (copyList.get(i).getClinicName().toLowerCase().contains(charText)) {
                    // 검색된 데이터를 리스트에 추가한다.
                    clinicDataList.add(copyList.get(i));
                }

                myAdapter = new ClinicAdapter(this, clinicDataList); // 진료소 리스트 관리할 어뎁터 생성
                listView.setAdapter(myAdapter); // 리스트뷰에 어뎁터 탑제

            }
        }
        // 리스트 데이터가 변경되었으므로 아답터를 갱신하여 검색된 데이터를 화면에 보여준다.
        myAdapter.notifyDataSetChanged();
    }

    private void addClinicList() {
        db = AppDatabase.getInstance(getBaseContext());

        DistanceComparator comparator = new DistanceComparator(); // 비교

        db.clinicDao().getAll().observe(this, clinics -> {

            for (int i = 0; i < clinics.size() - 1; i++) {
                ClinicItem clinicItem = new ClinicItem(clinics.get(i).getClinicName(), clinics.get(i).getClinicCallNumber(), clinics.get(i).getClinicAddress(), clinics.get(i).getX(), clinics.get(i).getY(), "yes");

                if (LocationConsts.NOW_X == 126.924 && LocationConsts.NOW_Y == 37.516) {
                    clinicDataList.add(clinicItem);
                } else if (clinicItem.getClinicDistance() < 20000) {
                    clinicDataList.add(clinicItem);
                }

            }

            Collections.sort(clinicDataList, comparator);

            myAdapter = new ClinicAdapter(this, clinicDataList); // 진료소 리스트 관리할 어뎁터 생성
            listView.setAdapter(myAdapter); // 리스트뷰에 어뎁터 탑제
            copyList.addAll(clinicDataList);
            myAdapter.notifyDataSetChanged();
        });

    }

}