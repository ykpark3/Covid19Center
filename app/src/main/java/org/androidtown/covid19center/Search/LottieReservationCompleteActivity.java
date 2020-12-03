package org.androidtown.covid19center.Search;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.airbnb.lottie.LottieAnimationView;

import org.androidtown.covid19center.Main.MainActivity;
import org.androidtown.covid19center.Mypage.ReservationCompleteActivity;
import org.androidtown.covid19center.R;

public class LottieReservationCompleteActivity extends AppCompatActivity {

    private Handler mHandler = new Handler();
    private String clinicDate; // 예약 날짜
    private String clinicName; // 병원 이름
    private String clinicAddress; // 병원 주소
    private String clinicCallNumber; // 병원 전화번호
    private boolean nationalCheck; // 1번 문항 트루 펄스
    private String nationalPlace; // 갔다온 장소
    private String nationalDate; // 갔다온 기간
    private boolean contactCheck; // 2번 문항 트루 펄스
    private String contactRelationShip; // 본인과의 관계
    private String contactRelationDate; // 접촉 기간
    private boolean symptomCheck; // 증상
    private String symptomList; // 증상 리스트
    private String symptomDate; // 증상 날짜

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activiiy_lottie_reservation_complete);


        //getIntentInfo();

        LottieAnimationView lottieAnimationView = (LottieAnimationView) findViewById(R.id.lottie_reservation_complete);
        lottieAnimationView.playAnimation();
        lottieAnimationView.loop(true);
    }

    @Override
    protected void onStart() {
        super.onStart();

        mHandler.postDelayed(mMyTask, 3000); // 3초후에 실행
    }

    private Runnable mMyTask = new Runnable() {
        @Override
        public void run() {
            Intent intent = new Intent(getApplicationContext(), MainActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);


            //sendIntent(intent);
            startActivity(intent);
            finish();
        }
    };

    private void sendIntent(Intent intent){

        intent.putExtra("clinicDate",clinicDate);
        intent.putExtra("clinicName",clinicName);
        intent.putExtra("clinicAddress",clinicAddress);
        intent.putExtra("clinicCallNumber",clinicCallNumber);
        intent.putExtra("nationalCheck",nationalCheck);
        intent.putExtra("nationalPlace",nationalPlace);
        intent.putExtra("nationalDate",nationalDate);
        intent.putExtra("contactCheck", contactCheck);
        intent.putExtra("contactRelationShip", contactRelationShip);
        intent.putExtra("contactRelationDate", contactRelationDate);
        intent.putExtra("symptomCheck", symptomCheck);
        intent.putExtra("symptomList", symptomList);
        intent.putExtra("symptomDate", symptomDate);
    }

    private void getIntentInfo(){
        Intent intent = getIntent();
        clinicDate = intent.getExtras().getString("clinicDate");
        clinicName = intent.getExtras().getString("clinicName");
        clinicAddress = intent.getExtras().getString("clinicAddress");
        clinicCallNumber = intent.getExtras().getString("clinicCallNumber");
        nationalCheck = intent.getExtras().getBoolean("nationalCheck");
        nationalPlace = intent.getExtras().getString("nationalPlace");
        nationalDate = intent.getExtras().getString("nationalDate");
        contactCheck = intent.getExtras().getBoolean("contactCheck");
        contactRelationShip = intent.getExtras().getString("contactRelationShip");
        contactRelationDate = intent.getExtras().getString("contactRelationDate");
        symptomCheck = intent.getExtras().getBoolean("symptomCheck");
        symptomList = intent.getExtras().getString("symptomList");
        symptomDate = intent.getExtras().getString("symptomDate");
    }
}
