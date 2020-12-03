package org.androidtown.covid19center.Hospital;

import android.content.Intent;
import android.os.Bundle;

import android.util.Log;

import android.widget.CheckBox;
import android.widget.RadioButton;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import org.androidtown.covid19center.R;

import org.androidtown.covid19center.Server.QuestionnaireVO;


import org.androidtown.covid19center.Server.AppManager;
import org.androidtown.covid19center.Server.QuestionnaireVO;

import java.util.ArrayList;

public class CheckQuestionnaireActivity extends AppCompatActivity {

    QuestionnaireVO qeQuestionnaireVO;
    private RadioButton visited_true;
    private RadioButton visited_false;
    private TextView visited_country;
    private TextView entry_date;
    private RadioButton contact_true;
    private RadioButton contact_false;
    private TextView relation;
    private TextView period;


//    private CheckBox symptom_false;

    private CheckBox fever;
    private CheckBox muscle_ache;
    private CheckBox cough;
    private CheckBox sputum;
    private CheckBox runny_nose;
    private CheckBox dyspnea;
    private CheckBox sore_throat;
    private TextView start_date;

    private TextView to_doctor;



    private int ques_seq;
    private ArrayList<QuestionnaireVO> questionnaireVOArrayList;




    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_check_questionnaire);

        visited_true = findViewById(R.id.questionnarie_visited_true);
        visited_false = findViewById(R.id.questionnarie_visited_false);
        visited_country = findViewById(R.id.questionnarie_visited_country);
        entry_date = findViewById(R.id.questionnarie_entry_date);

        contact_true = findViewById(R.id.questionnarie_contact_true);
        contact_false = findViewById(R.id.questionnarie_contact_false);
        relation = findViewById(R.id.questionnarie_relation);
        period = findViewById(R.id.questionnarie_period);

//        symptom_false = findViewById(R.id.questionnarie_symptom_false);
        fever = findViewById(R.id.questionnarie_fever);
        muscle_ache = findViewById(R.id.questionnarie_muscle_ache);
        cough = findViewById(R.id.questionnarie_cough);
        sputum = findViewById(R.id.questionnarie_sputum);
        runny_nose = findViewById(R.id.questionnarie_runny_nose);
        dyspnea = findViewById(R.id.questionnarie_dyspnea);
        sore_throat = findViewById(R.id.questionnarie_sore_throat);
        start_date = findViewById(R.id.questionnarie_start_date);

        to_doctor = findViewById(R.id.questionnaire_to_doctor);



        //클릭한 환자 문진표 시퀀스 받아오기
        Intent intent = getIntent();
        ques_seq = intent.getExtras().getInt("questionnaire sequence");
        Log.d("~~~~FragmentHospital","ques_seq: "+ques_seq);

        //해당 sequence값 문진표 서버에서 받아오기!

        initList();

        checkQuestionnaire();
    }



    public void initList() {
        Log.d("~~~~~FragmentHospital","-----initlist-----");

        questionnaireVOArrayList = AppManager.getInstance().getQuestionnaireVOArrayList();
        Log.d("~~~~~FragmentHospital","question size: "+questionnaireVOArrayList.size());

        for(int index = 0; index< questionnaireVOArrayList.size(); index++){

            Log.d("~~~~~FragmentHospital","index: "+index+"\n"+ "questionnaireVOArrayList sequence :"+questionnaireVOArrayList.get(index).getSequence());

            if(ques_seq == questionnaireVOArrayList.get(index).getSequence()) {

                Log.d("~~~~~FragmentHospital","if문");
                qeQuestionnaireVO = new QuestionnaireVO(
                        questionnaireVOArrayList.get(index).getSequence(),
                        questionnaireVOArrayList.get(index).getUser_id(),
                        questionnaireVOArrayList.get(index).getVisited(),
                        questionnaireVOArrayList.get(index).getVisited_detail(),
                        questionnaireVOArrayList.get(index).getEntrance_date(),
                        questionnaireVOArrayList.get(index).getContact(),
                        questionnaireVOArrayList.get(index).getContact_relationship(),
                        questionnaireVOArrayList.get(index).getContact_period(),
                        questionnaireVOArrayList.get(index).getFever(),
                        questionnaireVOArrayList.get(index).getMuscle_ache(),
                        questionnaireVOArrayList.get(index).getCough(),
                        questionnaireVOArrayList.get(index).getSputum(),
                        questionnaireVOArrayList.get(index).getRunny_nose(),
                        questionnaireVOArrayList.get(index).getDyspnea(),
                        questionnaireVOArrayList.get(index).getSore_throat(),
                        questionnaireVOArrayList.get(index).getSymptom_start_date(),
                        questionnaireVOArrayList.get(index).getToDoctor());
            }
        }

        Log.d("~~~~~FragmentHospital","-----initlist end-----");


    }

    public void checkQuestionnaire() {


        Log.d("~~~~~FragmentHospital","checkQuestionnaire");

        //위험지역 방문 여부
        if (qeQuestionnaireVO.getVisited() == 1){

            visited_true.setChecked(true);

            visited_country.setText(qeQuestionnaireVO.getVisited_detail());
            entry_date.setText(qeQuestionnaireVO.getEntrance_date());

        }else{

            visited_false.setChecked(true);
        }

        //접촉 여부

        if(qeQuestionnaireVO.getContact() == 1){

            contact_true.setChecked(true);

            relation.setText(qeQuestionnaireVO.getContact_relationship());
            period.setText(qeQuestionnaireVO.getContact_period());

        }else{

            contact_false.setChecked(true);
        }

        //해당하는 증상 체크
        if(qeQuestionnaireVO.getFever()==1) fever.setChecked(true);
        if(qeQuestionnaireVO.getMuscle_ache()==1) muscle_ache.setChecked(true);
        if(qeQuestionnaireVO.getCough()==1) cough.setChecked(true);
        if(qeQuestionnaireVO.getSputum()==1) sputum.setChecked(true);
        if(qeQuestionnaireVO.getRunny_nose()==1) runny_nose.setChecked(true);
        if(qeQuestionnaireVO.getDyspnea()==1) dyspnea.setChecked(true);
        if(qeQuestionnaireVO.getSore_throat()==1) sore_throat.setChecked(true);
        if(qeQuestionnaireVO.getSymptom_start_date() != null) start_date.setText(qeQuestionnaireVO.getSymptom_start_date());

        if(qeQuestionnaireVO.getToDoctor() != null) to_doctor.setText(qeQuestionnaireVO.getToDoctor());


    }
}
