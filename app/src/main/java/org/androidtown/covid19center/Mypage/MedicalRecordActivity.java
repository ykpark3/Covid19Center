package org.androidtown.covid19center.Mypage;

import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ListView;

import androidx.appcompat.app.AppCompatActivity;

import org.androidtown.covid19center.R;
import java.util.ArrayList;


public class MedicalRecordActivity extends AppCompatActivity {

    private ArrayList<RecordItem> clinicDataList; // 진료소 담을 리스트 생성
    private RecordAdapter myAdapter;
    private final Handler mHandler = null;
    private ListView listView;
    private ImageButton backButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_medical_record);

        backButton = findViewById(R.id.medical_records_backButton);
        clinicDataList = new ArrayList<RecordItem>();
        listView = findViewById(R.id.medical_records_listview); // 리스트뷰 생성
        myAdapter = new RecordAdapter(this, clinicDataList); // 진료소 리스트 관리할 어뎁터 생성

        listView.setAdapter(myAdapter); // 리스트뷰에 어뎁터 탑제

        addClinicList();

        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
    }


    private void addClinicList() {


            RecordItem clinicItem = new RecordItem("대전성모병원", "2020/11/20", "박태순");
            RecordItem clinicItem2 = new RecordItem("충남대학병원", "2020/11/21", "박태순");
            clinicDataList.add(clinicItem);
            clinicDataList.add(clinicItem2);
            myAdapter = new RecordAdapter(this, clinicDataList); // 진료소 리스트 관리할 어뎁터 생성
            listView.setAdapter(myAdapter); // 리스트뷰에 어뎁터 탑제

            myAdapter.notifyDataSetChanged();

    }

}