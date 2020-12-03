package org.androidtown.covid19center.Search;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.util.SparseBooleanArray;
import android.util.TypedValue;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.CalendarView;
import android.widget.GridView;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import org.androidtown.covid19center.R;
import org.androidtown.covid19center.Search.Time.MyAdapter;
import org.androidtown.covid19center.Search.Time.SubTimeAdapter;
import org.androidtown.covid19center.Search.Time.SubTimeItem;
import org.androidtown.covid19center.Search.Time.TimeListDecoration;

import java.util.ArrayList;

public class ReservationActivity extends AppCompatActivity {

    private TextView clinicName;
    private TextView clinicDate;
    private TextView warningMessage;
    private TextView reservaitionWarningText;
    private Button reservationButton;
    private ImageButton backButton;
    private CalendarView calendarView;
    private ArrayList<String> reservationTime;
    private String reservationTimeString;
    private String passClinicAddress;
    private String passClinicCallNumber;
    private String stringTemp;
    private RecyclerView listView;
    private ArrayList<View> views;
    private ArrayList<View> timeViews;
    private boolean canNextPage;
    private MyAdapter adapter;
    private ArrayList<String> passInfoList;
    private SubTimeAdapter subTimeAdapter;
    private final SparseBooleanArray mSelectedItems = new SparseBooleanArray(0);
    private ArrayList<String> listTime;
    private StringBuffer timeStringBuffer;
    private Context mContext;
    private EndDialog mEndDialog;
    private final View.OnClickListener onClickItem = new View.OnClickListener() {

        @Override
        public void onClick(View v) {

            int str = v.hashCode();

            String time = String.valueOf(v.getTag());

            int passTime;
            time = time.replace(":", "");

            if (time.substring(0, 1).contains("0")) {
                time = time.substring(1, 4);
                passTime = Integer.parseInt(time.substring(0, 1));
            } else {
                passTime = Integer.parseInt(time.substring(0, 2));
            }

            if (mSelectedItems.get(str, false)) {
                mSelectedItems.put(str, false);
                Log.d("0131", String.valueOf(str));
                v.setBackgroundResource(R.drawable.round_button_background);
            } else {
                mSelectedItems.put(str, true);
                views.add(v);

                views.get(0).setBackgroundResource(R.drawable.round_button_background);

                if (views.size() > 1) {
                    views.remove(0);
                }
                v.setBackgroundResource(R.drawable.on_round_button_background);
            }

            mSelectedItems.clear();
            canNextPage = false;
            setGridView(passTime);
        }
    };

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        reservationTime = new ArrayList<>();


        passInfoList = new ArrayList<>();

        views = new ArrayList<>();
        timeViews = new ArrayList<>();
        setLayoutElement();
        setIntentInfomation();

        setNextButton();
        subTimeAdapter = new SubTimeAdapter(getApplicationContext());
        timeStringBuffer = new StringBuffer();
        canNextPage = false;
    }

    @Override
    protected void onStart() {
        super.onStart();
    }

    private void setLayoutElement() {
        setContentView(R.layout.activity_reservation);
        clinicName = findViewById(R.id.reservation_clinicName);
        clinicName.setTextSize(TypedValue.COMPLEX_UNIT_DIP, 20);

        calendarView = findViewById(R.id.reservation_calendarView);
        reservationButton = findViewById(R.id.reservation_button);
        warningMessage = findViewById(R.id.reservation_calenderText);
        backButton = findViewById(R.id.reservation_backButton);

        reservaitionWarningText = findViewById(R.id.reservation_warning_message);
        warningMessage.setVisibility(View.VISIBLE);

        setCalenderView();
        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

    }

    private void setNextButton(){
        reservationButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(canNextPage){

                    reservationTimeString = reservationTime.get(reservationTime.size()-1);

                    passInfoList.add(reservationTimeString);
                    passInfoList.add(String.valueOf(clinicName.getText()));
                    passInfoList.add(passClinicAddress);
                    passInfoList.add(passClinicCallNumber);


                    Log.d("0154", String.valueOf(passInfoList.get(0)));
                    Log.d("0154", String.valueOf(passInfoList.get(1)));
                    Log.d("0154", String.valueOf(passInfoList.get(2)));
                    Log.d("0154", String.valueOf(passInfoList.get(3)));

                    mEndDialog = new EndDialog(ReservationActivity.this, reservationTimeString, String.valueOf(clinicName.getText()),passInfoList);

                    mEndDialog.setCancelable(false);

                    mEndDialog.show();

                } else{

                }
            }
        });
    }


    private void setCalenderView() {

        calendarView.setOnDateChangeListener(new CalendarView.OnDateChangeListener() {
            @Override
            public void onSelectedDayChange(@NonNull CalendarView view, int year, int month, int dayOfMonth) {
                month += 1;
                reservationTime.clear();

                //reservationTime.add(String.format("%d년 %d월 %d일", year, month, dayOfMonth));

                reservationTime.add(String.format("%d/%d/%d", year, month, dayOfMonth));


                setTimeListView();
            }
        });
    }

    private void setIntentInfomation() {

        Intent intent = getIntent(); // 데이터 수신

        passClinicAddress = intent.getExtras().getString("clinicAddress");
        passClinicCallNumber = intent.getExtras().getString("clinicCallNumber");

        stringTemp = intent.getExtras().getString("clinicName");
        clinicName.setText(stringTemp);

        listView = findViewById(R.id.time_listView);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);
        listView.setLayoutManager(layoutManager);

        TimeListDecoration decoration = new TimeListDecoration();
        listView.addItemDecoration(decoration);
    }


    private void setTimeListView() {

        warningMessage.setVisibility(View.GONE);

        ArrayList<String> itemList = new ArrayList<>();

        itemList.add("08:00");
        itemList.add("09:00");
        itemList.add("10:00");
        itemList.add("11:00");
        itemList.add("12:00");
        itemList.add("13:00");
        itemList.add("14:00");
        itemList.add("15:00");
        itemList.add("16:00");
        itemList.add("17:00");
        itemList.add("18:00");

        adapter = new MyAdapter(this, itemList, onClickItem);
        listView.setAdapter(adapter);
    }

    private void setGridView(int time) {

        GridView gridView = (GridView) findViewById(R.id.reservation_gridView);
        listTime = new ArrayList<>();
        listTime.clear();

        warningMessage.setText("예약 가능한 시간만 표시됩니다.");

        setListTime(time, listTime);

        gridView.setAdapter(subTimeAdapter);

        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {


                if(position == 0)
                {
                    reservationTime.add(reservationTime.get(0)+", "+reservationTime.get(1)+"00");
                } else if(position == 1){
                    reservationTime.add(reservationTime.get(0)+", "+reservationTime.get(1)+"15");
                } else if(position == 2){
                    reservationTime.add(reservationTime.get(0)+", "+reservationTime.get(1)+"30");
                } else if(position == 3){
                    reservationTime.add(reservationTime.get(0)+", "+reservationTime.get(1)+"45");
                }

                canNextPage = true;


                view.setBackgroundResource(R.drawable.background_purple_rectangle);

                timeViews.add(view);


                if (timeViews.size() > 1) {

                    Log.d("1542","123");
                    if(timeViews.get(0).equals(view) && view.getBackground().equals(R.drawable.background_gray_rectangle)){
                        view.setBackgroundResource(R.drawable.background_purple_rectangle);
                    } else if(timeViews.get(0).equals(view) && view.getBackground().equals(R.drawable.background_purple_rectangle)){
                        view.setBackgroundResource(R.drawable.background_gray_rectangle);
                    } else{
                        view.setBackgroundResource(R.drawable.background_purple_rectangle);
                        timeViews.get(0).setBackgroundResource(R.drawable.background_gray_rectangle);
                    }

                    timeViews.clear();
                    timeViews.add(view);

                }
            }
        });
    }


    private void setListTime(int num, ArrayList<String> stringTime) {

        int[] plusTime = new int[]{0, 15, 30, 45};
        int time = num * 100;
        String timeString = null;

        subTimeAdapter.clearItem();

        for (int i = 0; i < plusTime.length; i++) {

            timeString = String.valueOf(time + plusTime[i]);
            timeStringBuffer.append(timeString);


            if (Integer.parseInt(timeString) < 1000) {
                timeStringBuffer.insert(0, "0");

            }

            timeStringBuffer.insert(2, ":");

            subTimeAdapter.addItem(new SubTimeItem(String.valueOf(timeStringBuffer)));

            stringTime.add(timeString);

            reservationTime.add(1,String.valueOf(timeStringBuffer.substring(0,3)));

            timeStringBuffer.delete(0, 5);
        }

    }

}