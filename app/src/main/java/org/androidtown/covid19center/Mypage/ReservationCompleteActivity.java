package org.androidtown.covid19center.Mypage;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;


import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import org.androidtown.covid19center.R;
import org.androidtown.covid19center.Server.AppManager;
import org.androidtown.covid19center.Server.QuestionnaireVO;
import org.androidtown.covid19center.Server.ReservationVO;
import org.androidtown.covid19center.Server.RetrofitClient;
import org.androidtown.covid19center.Server.ServiceApi;

import java.util.ArrayList;


import org.androidtown.covid19center.R;

import com.airbnb.lottie.LottieAnimationView;

import org.androidtown.covid19center.R;
import org.androidtown.covid19center.Server.AppManager;





public class ReservationCompleteActivity extends AppCompatActivity {

    private String user_id; // 예약자 이름
    private final String nameExplain = "예약자 : ";
    private String clinicDate; // 예약 날짜
    private final String clinicDateExplain = "예약 날짜 : ";
    private String clinicName; // 병원 이름
    private final String clinicNameExplain = "예약 병원 : ";
    private String clinicAddress; // 병원 주소
    private final String clinicAddressExplain = "병원 주소 : ";
    private String clinicCallNumber; // 병원 전화번호
    private final String clinicCallNumberExplain = "전화번호 : ";

    private boolean isVisited; // 1번 문항 트루 펄스
    private String visitedDetail; // 갔다온 장소
    private final String nationPlaceExplain = "방문국가/지역/장소 : ";
    private String entrance_date; // 입국 날짜
    private final String nationalDateExplain = "입국 날짜 : ";
    private boolean isContacted; // 2번 문항 트루 펄스
    private String contact_relationship; // 본인과의 관계
    private final String contactRelationShipExplain = "본인과의 관계 : ";
    private String contact_period; // 접촉 기간

    private final String relationDateExplain = "접촉 기간 : ";
    private boolean symptomCheck; // 증상
    private String symptomList; // 증상 리스트
    private final String symptomListExplain = "관련 증상 : ";

    private String symptom_start_date; // 증상 날짜
    private final String symptomDateExplain = "증상 날짜 : ";

    private String doctorMessage;
    private final String doctorMessageExplain = "의사에게 전달 사항 : ";



    private boolean hasFever, hasMuscle_ache, hasCough, hasSputum, hasRunnyNose, hasDyspnea, hasSoreThroat;
    private StringBuffer symptoms;
    private ImageButton callButton;
    private TextView nameTextView;
    private TextView dateTextView;
    private TextView clinicNameTextView;
    private TextView clinicAddressTextView;
    private TextView clinicCallNumberTextView;
    private TextView nationalYesOrNoTextView;
    private TextView nationalPlaceTextView;
    private TextView nationalDateTextView;
    private TextView contactYesOrNoTextView;
    private TextView contactRelationTextView;
    private TextView contactDateTextView;
    private TextView symptomYesOrNoTextView;
    private TextView symptomListTextView;
    private TextView symptomDateTextView;

    private TextView doctorMessageTextView;

    private ImageButton backButton;

    private Button questionnaireModificationButton;

    private ArrayList<QuestionnaireVO> questionnaireVOArrayList;
    private ServiceApi serviceApi;
    private int sequence;
    private String toDoctor;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_reservation_complete);


        serviceApi = RetrofitClient.getClient().create(ServiceApi.class);

        setElement();
        //setIntentInfomation();

        getQuesionnaire();


        callButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setAction(Intent.ACTION_DIAL);
                intent.setData(Uri.parse("tel:" + clinicCallNumber));
                startActivity(intent);
            }
        });

        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
                finish();
            }
        });

        questionnaireModificationButton = findViewById(R.id.modificationButton);

        questionnaireModificationButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d("~~~~~ReservationCompl", "questionnaire button click");
                goToModificationActivity();
            }
        });
    }


    private void goToModificationActivity() {
        Intent intent = new Intent(this, QuestionnaireModificationActivity.class);
        startActivity(intent);

    }


    protected void getQuesionnaire() {
        Log.d("~~~~~ReservationComp", " getQuesionnaire");


        int visited;
        int contact;
        int fever, muscle_ache, cough, sputum, runny_nose, dyspnea, sore_throat;


        questionnaireVOArrayList = new ArrayList<QuestionnaireVO>();
        questionnaireVOArrayList = AppManager.getInstance().getQuestionnaireVOArrayList();

        Log.d("~~~~~ReservationComp", "questionnaireVOArrayList size: " + questionnaireVOArrayList.size());

        sequence = questionnaireVOArrayList.get(questionnaireVOArrayList.size() - 1).getSequence();
        user_id = questionnaireVOArrayList.get(questionnaireVOArrayList.size() - 1).getUser_id();
        visited = questionnaireVOArrayList.get(questionnaireVOArrayList.size() - 1).getVisited();
        visitedDetail = questionnaireVOArrayList.get(questionnaireVOArrayList.size() - 1).getVisited_detail();
        entrance_date = questionnaireVOArrayList.get(questionnaireVOArrayList.size() - 1).getEntrance_date();
        contact = questionnaireVOArrayList.get(questionnaireVOArrayList.size() - 1).getContact();
        contact_relationship = questionnaireVOArrayList.get(questionnaireVOArrayList.size() - 1).getContact_relationship();
        contact_period = questionnaireVOArrayList.get(questionnaireVOArrayList.size() - 1).getContact_period();
        fever = questionnaireVOArrayList.get(questionnaireVOArrayList.size() - 1).getFever();
        muscle_ache = questionnaireVOArrayList.get(questionnaireVOArrayList.size() - 1).getMuscle_ache();
        cough = questionnaireVOArrayList.get(questionnaireVOArrayList.size() - 1).getCough();
        sputum = questionnaireVOArrayList.get(questionnaireVOArrayList.size() - 1).getSputum();
        runny_nose = questionnaireVOArrayList.get(questionnaireVOArrayList.size() - 1).getRunny_nose();
        dyspnea = questionnaireVOArrayList.get(questionnaireVOArrayList.size() - 1).getDyspnea();
        sore_throat = questionnaireVOArrayList.get(questionnaireVOArrayList.size() - 1).getSore_throat();
        symptom_start_date = questionnaireVOArrayList.get(questionnaireVOArrayList.size() - 1).getSymptom_start_date();
        toDoctor = questionnaireVOArrayList.get(questionnaireVOArrayList.size() - 1).getToDoctor();


        /** int -> boolean 형 변환
         * radioButton check할 때는 boolean형 사용
         *
         */
        isVisited = visited != 0;
        isContacted = contact != 0;
        hasFever = fever != 0;
        hasMuscle_ache = muscle_ache != 0;
        hasCough = cough != 0;
        hasSputum = sputum != 0;
        hasRunnyNose = runny_nose != 0;
        hasDyspnea = dyspnea != 0;
        hasSoreThroat = sore_throat != 0;

        getReservationData();

    }


    private void getReservationData() {
        Log.d("~~~~~ReservationCompl", " getReservationData");

        ArrayList<ReservationVO> reservationVOArrayList;
        reservationVOArrayList = AppManager.getInstance().getReservationVOArrayList();

        boolean hasReservationData = false;

        Log.d("~~~~~","sequence: "+sequence);
        Log.d("~~~~~","reservation list size: "+reservationVOArrayList.size());

        for (int index = 0; index < reservationVOArrayList.size(); index++) {

            Log.d("~~~~~ReservationCompl", "index: "+ index + "  sequence: " +reservationVOArrayList.get(index).getQuestionnaire_seq());

            if (sequence == reservationVOArrayList.get(index).getQuestionnaire_seq()) {

                clinicDate = reservationVOArrayList.get(index).getDate() + " " + reservationVOArrayList.get(index).getTime();
                clinicName = reservationVOArrayList.get(index).getHospital_name();

                /**
                 * 주소를 어떻게 해야할지 몰라서 임시로 넣음
                 */
                clinicAddress = "서울특별시 광진구 화양동 능동로 120-1";
                clinicCallNumber = "1588-1533";

                hasReservationData = true;
                break;
            }

        }

        if (!hasReservationData) {
            Toast.makeText(getApplicationContext(), "예약 내역이 없습니다.", Toast.LENGTH_SHORT).show();
            finish();
        }

        changeSymptomsToString();
        setElementInfo();
    }



    private void changeSymptomsToString() {
        symptoms = new StringBuffer();

        if (hasFever) {
            symptoms.append("37.5도 이상 열,");
        }

        if (hasMuscle_ache) {
            symptoms.append("전신통/근육통,");
        }

        if (hasCough) {
            symptoms.append("기침,");
        }

        if (hasSputum) {
            symptoms.append("가래,");
        }

        if (hasRunnyNose) {
            symptoms.append("콧물,");
        }

        if (hasDyspnea) {
            symptoms.append("호흡곤란,");
        }

        if (hasSoreThroat) {

            symptoms.append("인후통,");
        }

        // 마지막 쉼표 지워주기
        if (symptoms.length() != 0) {
            symptoms.deleteCharAt(symptoms.length() - 1);
            symptomCheck = true;
        } else {

            symptomCheck = false;
        }

    }


    private void setElement() {

        backButton = findViewById(R.id.reservation_complete_backButton);
        nameTextView = findViewById(R.id.reservation_complete_userName);
        dateTextView = findViewById(R.id.reservation_complete_date);
        clinicNameTextView = findViewById(R.id.reservation_complete_clinicName);
        clinicAddressTextView = findViewById(R.id.reservation_complete_clinicAddress);
        clinicCallNumberTextView = findViewById(R.id.reservation_complete_callNumber);
        nationalYesOrNoTextView = findViewById(R.id.reservation_complete_national_check_textView);
        nationalPlaceTextView = findViewById(R.id.reservation_complete_national_true_textView);
        nationalDateTextView = findViewById(R.id.reservation_complete_national_true_date_textView);
        contactYesOrNoTextView = findViewById(R.id.reservation_complete_contact_check_textView);
        contactRelationTextView = findViewById(R.id.reservation_complete_contact_true_textView);
        contactDateTextView = findViewById(R.id.reservation_complete_contact_true_date_textView);
        symptomYesOrNoTextView = findViewById(R.id.reservation_complete_symptom_check_textView);
        symptomListTextView = findViewById(R.id.reservation_complete_symptom_true_textView);
        symptomDateTextView = findViewById(R.id.reservation_complete_symptom_date_textView);
        callButton = findViewById(R.id.reservation_complete_callButton);

        doctorMessageTextView = findViewById(R.id.reservation_complete_doctor_message_textView);
    }




    private void setElementInfo() {

        dateTextView.setText(clinicDateExplain + clinicDate);
        clinicNameTextView.setText(clinicNameExplain + clinicName);
        clinicAddressTextView.setText(clinicAddressExplain + clinicAddress);
        clinicCallNumberTextView.setText(clinicCallNumberExplain + clinicCallNumber);

        doctorMessageTextView.setText(doctorMessageExplain + doctorMessage);

        if (isVisited == true) {
            nationalYesOrNoTextView.setText("있음");
            nationalPlaceTextView.setText(nationPlaceExplain + visitedDetail);
            nationalDateTextView.setText(nationalDateExplain + entrance_date);
            nationalPlaceTextView.setVisibility(View.VISIBLE);
            nationalDateTextView.setVisibility(View.VISIBLE);
        } else {

            nationalYesOrNoTextView.setText("없음");
            nationalPlaceTextView.setVisibility(View.GONE);
            nationalDateTextView.setVisibility(View.GONE);
        }

        if (isContacted == true) {
            contactYesOrNoTextView.setText("있음");
            contactRelationTextView.setText(contactRelationShipExplain + contact_relationship);
            contactDateTextView.setText(relationDateExplain + contact_period + "일");
            contactRelationTextView.setVisibility(View.VISIBLE);
            contactDateTextView.setVisibility(View.VISIBLE);
        } else {
            contactYesOrNoTextView.setText("없음");
            contactRelationTextView.setVisibility(View.GONE);
            contactDateTextView.setVisibility(View.GONE);
        }


        if (symptomCheck == true) {
            symptomYesOrNoTextView.setText("있음");
            symptomListTextView.setText(symptomListExplain + symptoms);
            symptomDateTextView.setText(symptomDateExplain + symptom_start_date);
            symptomListTextView.setVisibility(View.VISIBLE);
            symptomDateTextView.setVisibility(View.VISIBLE);
        } else {
            symptomYesOrNoTextView.setText("없음");
            symptomListTextView.setVisibility(View.GONE);
            symptomDateTextView.setVisibility(View.GONE);
        }
    }

    private void setIntentInfomation() {

        Intent intent = getIntent(); // 데이터 수신

        clinicDate = intent.getExtras().getString("clinicDate");
        clinicName = intent.getExtras().getString("clinicName");
        clinicAddress = intent.getExtras().getString("clinicAddress");
        clinicCallNumber = intent.getExtras().getString("clinicCallNumber");

        isVisited = intent.getExtras().getBoolean("nationalCheck");
        visitedDetail = intent.getExtras().getString("nationalPlace");
        entrance_date = intent.getExtras().getString("nationalDate");
        isContacted = intent.getExtras().getBoolean("contactCheck");
        contact_relationship = intent.getExtras().getString("contactRelationShip");
        contact_period = intent.getExtras().getString("contactRelationDate");
        symptomList = intent.getExtras().getString("symptomList");
        symptom_start_date = intent.getExtras().getString("symptomDate");
        if (symptomList.length() == 0) {
            symptomCheck = false;
        } else {
            symptomCheck = true;
        }

    }

}
