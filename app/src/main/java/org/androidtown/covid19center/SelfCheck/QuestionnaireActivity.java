package org.androidtown.covid19center.SelfCheck;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.NumberPicker;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import org.androidtown.covid19center.R;
import org.androidtown.covid19center.Search.LottieReservationCompleteActivity;
import org.androidtown.covid19center.Server.AppManager;
import org.androidtown.covid19center.Server.QuestionnaireData;
import org.androidtown.covid19center.Server.QuestionnaireVO;
import org.androidtown.covid19center.Server.ReservationData;
import org.androidtown.covid19center.Server.ReservationVO;
import org.androidtown.covid19center.Server.RetrofitClient;
import org.androidtown.covid19center.Server.ServiceApi;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class QuestionnaireActivity extends AppCompatActivity implements NumberPicker.OnValueChangeListener, View.OnClickListener {

    private EditText countryEditText;
    private TextView entranceDateTextView;
    private EditText relationEditText;
    private TextView termTextView;
    private TextView startVirusDateTextView;
    private final Calendar myCalender = Calendar.getInstance();
    private ImageButton backButton;
    private Button nextButton;
    private RadioGroup visitedCheck_radioGroup, contact_radioGroup;
    private CheckBox none_checkBox ,fever_checkBox, muscle_ache_checkBox, cough_checkBox, sputum_checkBox, runny_nose_checkBox, dyspnea_checkBox, sore_throat_checkBox;

    private boolean isVisited;
    private String visitedDetail;
    private boolean isContacted;
    private String contact_relationship;
    private String contact_period;
    private boolean hasFever, hasMuscle_ache, hasCough, hasSputum, hasRunnyNose, hasDyspnea, hasSoreThroat;
    private boolean none_checked;
    private String symptom_start_date;
    private StringBuffer symptomStringBuffer;
    private boolean isSymptomChecked;
    private String entrance_date;
    private String clinicName;
    private String clinicReservationTime;
    private String clinicAddress;
    private String clinicCallNumber;
    private ServiceApi serviceApi;
    private int questionnaireSequence;
    private EditText toDoctorEditText;
    private String toDoctorMessage;

    private Boolean isFirstQuestion;
    private Boolean isSecondQuestion;
    private Boolean isThirdQuestion;
    private Boolean isFourthQuestion;




    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        serviceApi = RetrofitClient.getClient().create(ServiceApi.class);

        setContentView(R.layout.activity_questionnaire);
        setLayoutElement();
        setIntentInfo();
    }

    private void setLayoutElement(){

        countryEditText = findViewById(R.id.questionnarie_editText_country);
        entranceDateTextView = findViewById(R.id.questionnarie_textView_date);
        relationEditText = findViewById(R.id.questionnarie_editText_relation);
        termTextView = findViewById(R.id.questionnarie_textView_term);
        startVirusDateTextView = findViewById(R.id.questionnarie_textView_start_date);
        nextButton = findViewById(R.id.questionnarie_button);
        backButton = findViewById(R.id.questionnarie_backButton);

        contact_radioGroup = findViewById(R.id.questionnarie_contact_radioGroup);
        contact_radioGroup.setOnCheckedChangeListener(contactRadioGroupButtonChangeListener);
        visitedCheck_radioGroup = findViewById(R.id.questionnarie_visited_radioGroup);
        visitedCheck_radioGroup.setOnCheckedChangeListener(visitedCheckRadioGroupButtonChangeListener);

        none_checkBox = findViewById(R.id.questionnarie_radioButton_false);

        fever_checkBox = findViewById(R.id.questionnarie_fever_radioButton_true);
        muscle_ache_checkBox = findViewById(R.id.questionnarie_muscle_ache_radioButton_true);
        cough_checkBox = findViewById(R.id.questionnarie_cough_radioButton_true);
        sputum_checkBox = findViewById(R.id.questionnarie_sputum_radioButton_true);
        runny_nose_checkBox = findViewById(R.id.questionnarie_runny_nose_radioButton_true);
        dyspnea_checkBox = findViewById(R.id.questionnarie_dyspnea_radioButton_true);
        sore_throat_checkBox = findViewById(R.id.questionnarie_sore_throat_radioButton_true);


        toDoctorEditText = findViewById(R.id.questionnarie_editText_toDoctor);

        clinicReservationTime = "";
        symptom_start_date = "";
        visitedDetail = "";
        contact_relationship = "";
        contact_period = "";
        entrance_date = "";

        toDoctorMessage = "";
        isFirstQuestion = false;
        isSecondQuestion = false;
        isThirdQuestion = false;
        isFourthQuestion = false;

        none_checkBox.setOnClickListener(this::onClick);
        fever_checkBox.setOnClickListener(this::onClick);
        muscle_ache_checkBox.setOnClickListener(this::onClick);
        cough_checkBox.setOnClickListener(this::onClick);
        sputum_checkBox.setOnClickListener(this::onClick);
        runny_nose_checkBox.setOnClickListener(this::onClick);
        dyspnea_checkBox.setOnClickListener(this::onClick);
        sore_throat_checkBox.setOnClickListener(this::onClick);

        nextButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                setCheckedInfo();

                /** toDoctor 내용 추가해주기
                 *
                 */
                sendQuestionnaireData(new QuestionnaireData(
                        AppManager.getInstance().getUserId(),
                        isVisited,
                        visitedDetail,
                        entrance_date,
                        isContacted,
                        contact_relationship,
                        contact_period,
                        hasFever,
                        hasMuscle_ache,
                        hasCough,
                        hasSputum,
                        hasRunnyNose,
                        hasDyspnea,
                        hasSoreThroat,
                        symptom_start_date,
                        "toDoctor"));




                Intent intent = new Intent(getApplicationContext(), LottieReservationCompleteActivity.class);
                sendIntentInfo(intent);
                startActivity(intent);

            }
        });

        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        DatePickerDialog.OnDateSetListener date = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                myCalender.set(Calendar.YEAR,year);
                myCalender.set(Calendar.MONTH, month);
                myCalender.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                updateLabel();
            }
        };

        DatePickerDialog.OnDateSetListener virusStartDate = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                myCalender.set(Calendar.YEAR,year);
                myCalender.set(Calendar.MONTH, month);
                myCalender.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                updateVirusStartLabel();
            }
        };

        entranceDateTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new DatePickerDialog(QuestionnaireActivity.this, date, myCalender.get(Calendar.YEAR), myCalender.get(Calendar.MONTH), myCalender.get(Calendar.DAY_OF_MONTH)).show();
            }
        });

        termTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showNumberPicker(v, "접촉기간", "1", 30, 0,1, 1);
            }
        });

        startVirusDateTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new DatePickerDialog(QuestionnaireActivity.this, virusStartDate, myCalender.get(Calendar.YEAR), myCalender.get(Calendar.MONTH), myCalender.get(Calendar.DAY_OF_MONTH)).show();
            }
        });

    }

    private Boolean checkQuestion(){


        if(isFirstQuestion.equals(false) || isSecondQuestion.equals(false) || isThirdQuestion.equals(false)){
            Log.d("2048", "123");
            return false;
        } else{
            Log.d("2048", "124");
            return true;
        }

    }

    private void addQuestionnaireVOArrayList() {
        Log.d("~~~~~QuestionnaireAct","addQuestionnaireVOArrayList");

        /**
         * boolean -> int 형 변환
         */
        int visited;
        int contact;
        int fever, muscle_ache, cough, sputum, runny_nose, dyspnea, sore_throat;

        if(isVisited) {
            visited = 1;
        } else {
            visited = 0;
        }

        if(isContacted) {
            contact = 1;
        } else {
            contact = 0;
        }

        if(hasFever) {
            fever = 1;
        } else {
            fever = 0;
        }

        if(hasMuscle_ache) {
            muscle_ache = 1;
        } else {
            muscle_ache = 0;
        }

        if(hasCough) {
            cough = 1;
        } else {
            cough = 0;
        }

        if(hasSputum) {
            sputum = 1;
        } else {
            sputum = 0;
        }

        if(hasRunnyNose) {
            runny_nose = 1;
        } else {
            runny_nose = 0;
        }

        if(hasDyspnea) {
            dyspnea = 1;
        } else {
            dyspnea = 0;
        }

        if(hasSoreThroat) {
            sore_throat = 1;
        } else {
            sore_throat = 0;
        }


        /**
         * AppManager에 저장된 QuestionnaireVO에 추가
         */
        ArrayList<QuestionnaireVO> questionnaireVOArrayList = AppManager.getInstance().getQuestionnaireVOArrayList();
        questionnaireVOArrayList.add(new QuestionnaireVO(
                questionnaireSequence,
                AppManager.getInstance().getUserId(),
                visited, visitedDetail, entrance_date,
                contact, contact_relationship, contact_period,
                fever, muscle_ache, cough, sputum, runny_nose, dyspnea, sore_throat,
                symptom_start_date,
                "toDoctor"));

        AppManager.getInstance().setQuestionnaireVOArrayList(questionnaireVOArrayList);
    }

    private void sendIntentInfo(Intent intent){

        intent.putExtra("userId",AppManager.getInstance().getUserId());
        intent.putExtra("clinicDate",clinicReservationTime);
        intent.putExtra("clinicName",clinicName);
        intent.putExtra("clinicAddress",clinicAddress);
        intent.putExtra("clinicCallNumber",clinicCallNumber);
        intent.putExtra("nationalCheck",isVisited);
        intent.putExtra("nationalPlace",visitedDetail);
        intent.putExtra("nationalDate",entrance_date);
        intent.putExtra("contactCheck", isContacted);
        intent.putExtra("contactRelationShip", contact_relationship);
        intent.putExtra("contactRelationDate", contact_period);
        intent.putExtra("symptomCheck", isSymptomChecked);
        intent.putExtra("symptomList", String.valueOf(symptomStringBuffer));
        intent.putExtra("symptomDate", symptom_start_date);
    }


    private void sendQuestionnaireData(QuestionnaireData questionnaireData) {

        Call<ResponseBody> dataCall = serviceApi.sendQuestionnaireData(questionnaireData);
        dataCall.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                String result = null;
                try {
                    Log.d("~~~~~","response"+response.code());
                    result = response.body().string();


                } catch (IOException e) {
                    Log.d("~~~~~", String.valueOf(e));
                    e.printStackTrace();
                }
                Log.i("~~~~~","result: "+ result);

                getQuestionnaireSequence();
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                Log.i("~~~~~","fail");
                if (t instanceof IOException) {
                    // Handle IO exception, maybe check the network and try again.
                    Log.i("~~~~~","t"+t);
                }
            }
        });
    }

    RadioGroup.OnCheckedChangeListener visitedCheckRadioGroupButtonChangeListener = new RadioGroup.OnCheckedChangeListener() {
        @Override
        public void onCheckedChanged(RadioGroup group, int checkedId) {

            if(checkedId == R.id.questionnarie_visited_radioButton_true){
                isVisited = true;
                isFirstQuestion = true;
            } else if(checkedId == R.id.questionnarie_visited_radioButton_false){
                isVisited = false;
                isFirstQuestion = true;

            }
        }
    };

    private void sendReservationData(ReservationData reservationData) {
        Log.d("~~~~~","sendReservationData");

        Call<ResponseBody> dataCall = serviceApi.sendReservationData(reservationData);
        dataCall.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                String result = null;
                try {
                    Log.d("~~~~~","response: "+response.code());
                    result = response.body().string();

                } catch (IOException e) {
                    Log.d("~~~~~", String.valueOf(e));
                    e.printStackTrace();
                }
                Log.i("~~~~~", "result: "+result);


                /*
                Intent intent = new Intent(getApplicationContext(), LottieReservationCompleteActivity.class);
                sendIntentInfo(intent);
                startActivity(intent);

                 */

            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                Log.i("~~~~~","fail");
                if (t instanceof IOException) {
                    // Handle IO exception, maybe check the network and try again.
                    Log.i("~~~~~","t"+t);
                }
            }
        });
    }


    RadioGroup.OnCheckedChangeListener contactRadioGroupButtonChangeListener = new RadioGroup.OnCheckedChangeListener() {
        @Override
        public void onCheckedChanged(RadioGroup group, int checkedId) {
            if(checkedId == R.id.questionnarie_contact_radioButton_true){
                isContacted = true;
                isSecondQuestion = true;
            } else if(checkedId == R.id.questionnarie_contact_radioButton_false){
                isContacted = false;
                isSecondQuestion = true;
            }
        }
    };




    protected void getQuestionnaireSequence() {
        serviceApi.getQuesionnaireVO().enqueue(new Callback<List<QuestionnaireVO>>() {

            @Override
            public void onResponse(Call<List<QuestionnaireVO>> call, Response<List<QuestionnaireVO>> response) {

                if (response.isSuccessful()) {
                    List<QuestionnaireVO> data = response.body();

                    questionnaireSequence = data.get(data.size()-1).getSequence();

                    Log.d("~~~~~","questionnaire sequence: "+ questionnaireSequence);

                    long now = System.currentTimeMillis();
                    Date nowDate = new Date(now);
                    SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
                    String getTime = simpleDateFormat.format(nowDate);


                    /**
                     * AppManager에 저장된 ReservationVO에 추가
                     */
                    ArrayList<ReservationVO> reservationVOArrayList = AppManager.getInstance().getReservationVOArrayList();
                    reservationVOArrayList.add(new ReservationVO(
                            AppManager.getInstance().getUserId(),
                            questionnaireSequence,
                            clinicName,
                            clinicReservationTime.substring(clinicReservationTime.lastIndexOf(" ")),
                            clinicReservationTime.substring(0, clinicReservationTime.indexOf(",")),
                            0));
                    AppManager.getInstance().setReservationVOArrayList(reservationVOArrayList);



                    addQuestionnaireVOArrayList();


                    sendReservationData(new ReservationData(
                            AppManager.getInstance().getUserId(),
                            questionnaireSequence,
                            clinicName,
                            clinicReservationTime.substring(clinicReservationTime.lastIndexOf(" ")),
                            clinicReservationTime.substring(0, clinicReservationTime.indexOf(",")),
                            false,
                            getTime));


                }
            }

            @Override
            public void onFailure(Call<List<QuestionnaireVO>> call, Throwable t) {
                Log.d("~~~~~", "실패: " + t);
                t.printStackTrace();
            }
        });
    }




    private void setCheckedInfo(){

        symptomStringBuffer = new StringBuffer();
        isSymptomChecked = false;


        if(fever_checkBox.isChecked() == true){
            isThirdQuestion = true;
            hasFever = true;
            isSymptomChecked = true;
            symptomStringBuffer.append("37.5도 이상 열,");
        } else{
            hasFever = false;
        }

        if(muscle_ache_checkBox.isChecked() == true){
            isThirdQuestion = true;
            hasMuscle_ache = true;
            isSymptomChecked = true;
            symptomStringBuffer.append("전신통/근육통,");
        } else{
            hasMuscle_ache = false;
        }

        if(cough_checkBox.isChecked() == true){
            hasCough = true;
            isThirdQuestion = true;
            isSymptomChecked = true;
            symptomStringBuffer.append("기침,");
        } else{
            hasCough = false;
        }

        if(runny_nose_checkBox.isChecked() == true){
            isThirdQuestion = true;
            hasRunnyNose = true;
            isSymptomChecked = true;
            symptomStringBuffer.append("콧물,");
        } else{
            hasRunnyNose = false;
        }

        if(dyspnea_checkBox.isChecked() == true){
            isThirdQuestion = true;
            hasDyspnea = true;
            isSymptomChecked = true;
            symptomStringBuffer.append("호흡곤란,");
        } else{
            hasDyspnea = false;
        }

        if(sputum_checkBox.isChecked() == true){
            isThirdQuestion = true;
            hasSputum = true;
            isSymptomChecked = true;
            symptomStringBuffer.append("가래,");
        } else{
            hasSputum = false;
        }

        if(sore_throat_checkBox.isChecked() == true){
            isThirdQuestion = true;
            hasSoreThroat = true;
            isSymptomChecked = true;
            symptomStringBuffer.append("인후통,");
        } else{
            hasSoreThroat = false;
        }

        if(symptomStringBuffer.length() !=0)
        {
            symptomStringBuffer.deleteCharAt(symptomStringBuffer.length()-1);
        }

        contact_relationship = String.valueOf(relationEditText.getText());
        visitedDetail = String.valueOf(countryEditText.getText());

        toDoctorMessage = String.valueOf(toDoctorEditText.getText());

    }


    private void setIntentInfo(){
        clinicName = getIntent().getExtras().getString("clinicName");
        clinicReservationTime = getIntent().getExtras().getString("clinicTime");
        clinicCallNumber = getIntent().getExtras().getString("clinicCallNumber");
        clinicAddress = getIntent().getExtras().getString("clinicAddress");
    }

    private void updateLabel(){
        String myFormat = "yyyy/MM/dd"; //In which you need put here
        SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.KOREA);
        entranceDateTextView.setText(sdf.format(myCalender.getTime()));
        entranceDateTextView.setTextColor(Color.BLACK);
        entrance_date = sdf.format(myCalender.getTime());
    }

    private void updateVirusStartLabel(){
        String myFormat = "yyyy/MM/dd"; //In which you need put here
        SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.KOREA);
        startVirusDateTextView.setText(sdf.format(myCalender.getTime()));
        startVirusDateTextView.setTextColor(Color.BLACK);
        symptom_start_date = sdf.format(myCalender.getTime());
    }

    @Override
    public void onValueChange(NumberPicker picker, int oldVal, int newVal) {
        termTextView.setText(String.valueOf(newVal)+ "일");
        termTextView.setTextColor(Color.BLACK);
        contact_period = String.valueOf(newVal);
    }

    public void showNumberPicker(View view, String title, String subtitle, int maxvalue, int minvalue, int step, int defvalue){
        NumberpickerDialog newFragment = new NumberpickerDialog();
        //Dialog에는 bundle을 이용해서 파라미터를 전달한다
        Bundle bundle = new Bundle(6); // 파라미터는 전달할 데이터 개수
        bundle.putString("title", title); // key , value
        bundle.putString("subtitle", subtitle); // key , value
        bundle.putInt("maxvalue", maxvalue); // key , value
        bundle.putInt("minvalue", minvalue); // key , value
        bundle.putInt("step", step); // key , value
        bundle.putInt("defvalue", defvalue); // key , value
        newFragment.setArguments(bundle);
        //class 자신을 Listener로 설정한다
        newFragment.setValueChangeListener(this);
        newFragment.show(getSupportFragmentManager(), "null");
    }

    @Override
    public void onClick(View v) {

        if(v.getId() == R.id.questionnarie_radioButton_false){
            fever_checkBox.setChecked(false);
            muscle_ache_checkBox.setChecked(false);
            cough_checkBox.setChecked(false);
            sputum_checkBox.setChecked(false);
            runny_nose_checkBox.setChecked(false);
            dyspnea_checkBox.setChecked(false);
            sore_throat_checkBox.setChecked(false);
        } else if(v.getId() == R.id.questionnarie_fever_radioButton_true || v.getId() == R.id.questionnarie_muscle_ache_radioButton_true
                || v.getId() == R.id.questionnarie_sputum_radioButton_true || v.getId() == R.id.questionnarie_runny_nose_radioButton_true
                || v.getId() == R.id.questionnarie_dyspnea_radioButton_true || v.getId() == R.id.questionnarie_cough_radioButton_true
                || v.getId() == R.id.questionnarie_sore_throat_radioButton_true ){
            none_checkBox.setChecked(false);
        }
    }
}