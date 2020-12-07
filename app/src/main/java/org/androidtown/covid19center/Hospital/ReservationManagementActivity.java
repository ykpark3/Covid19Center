package org.androidtown.covid19center.Hospital;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.CalendarView;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import org.androidtown.covid19center.R;
import org.androidtown.covid19center.Server.AppManager;
import org.androidtown.covid19center.Server.ReservationVO;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

//의료진용 예약 관리 페이지
public class ReservationManagementActivity extends AppCompatActivity {

    AppCompatActivity activity = this;

    //임시 리스트
    private ArrayList<ReservationVO> reservationList;
    private ArrayList<ReservationVO> selectedList;

    private String date; //캘린더에서 선택한 날짜

    private TextView textview_notify_ques;
    private ImageButton backButton;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_reservation_management);
        CalendarView calendarView = findViewById(R.id.hospital_calendar);
//        textview_notify_ques = findViewById(R.id.textview_notify_ques);
        LinearLayout layout_listview_contents = findViewById(R.id.layout_listview_contents);
        View deviding_line = findViewById(R.id.view_deviding_line);
        backButton = (ImageButton)findViewById(R.id.self_check_result_back_button);


        //임시 예약 환자 리스트 생성
        initList();

        //date 초기값 오늘 날짜 저장
        Date currentTime = Calendar.getInstance().getTime();

        date = new SimpleDateFormat("yyyy/MM/dd", Locale.getDefault()).format(currentTime);

        Log.d("date확인", date);

        //오늘 날짜 예약 리스트 받아오기
        selectListItem();

        if(selectedList.size() == 0){
            layout_listview_contents.setVisibility(View.INVISIBLE);
            deviding_line.setVisibility(View.INVISIBLE);
        }else{
            layout_listview_contents.setVisibility(View.VISIBLE);
            deviding_line.setVisibility(View.VISIBLE);
        }

        ListView listView = findViewById(R.id.reservation_listview);
        ListAdapter listAdapter = new ListAdapter(this,selectedList, date);

        listView.setAdapter(listAdapter);

        //날짜 클릭시 날짜별 환자 리스트로 리스트뷰 갱신
        calendarView.setOnDateChangeListener(new CalendarView.OnDateChangeListener() {
            @Override
            public void onSelectedDayChange(@NonNull CalendarView view, int year, int month, int dayOfMonth) {
                month += 1;

                date = year + "/" + month + "/" +  dayOfMonth;

                //날짜별 환자 리스트 받아오기
                selectListItem();
                listAdapter.updateItem(selectedList);
                listAdapter.notifyDataSetChanged();

                if(selectedList.size() == 0){
                    layout_listview_contents.setVisibility(View.INVISIBLE);
                    deviding_line.setVisibility(View.INVISIBLE);
                }else{
                    layout_listview_contents.setVisibility(View.VISIBLE);
                    deviding_line.setVisibility(View.VISIBLE);
                }
            }
        });

        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
                finish();
            }
        });
    }


    @Override
    protected void onResume() {
        super.onResume();

        selectedList = null;

        Intent intent2 = getIntent();
        selectedList = (ArrayList<ReservationVO>)intent2.getSerializableExtra("modifiedList");
    }

    //임시 예약 데이터@@
    public void initList(){

        Log.d("~~~~~","init list");

        reservationList = new ArrayList<ReservationVO>();

        reservationList =  AppManager.getInstance().getReservationVOArrayList();
        Log.d("~~~~~","reservatonList size:  "+reservationList.size());
    }

    //해당 날짜의 예약 리스트 select
    public void selectListItem(){
        selectedList = new ArrayList<ReservationVO>();

        for (int i=0;i<reservationList.size();i++){
            if(reservationList.get(i).getDate().equals(date)){
                selectedList.add(selectedList.size(),reservationList.get(i));
            }
        }
    }
}
