package org.androidtown.covid19center.Mypage;

import androidx.appcompat.app.AppCompatActivity;
import android.app.DatePickerDialog;
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
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;


import org.androidtown.covid19center.R;
import org.androidtown.covid19center.SelfCheck.NumberpickerDialog;
import org.androidtown.covid19center.Server.AppManager;
import org.androidtown.covid19center.Server.QuestionnaireData;
import org.androidtown.covid19center.Server.QuestionnaireVO;
import org.androidtown.covid19center.Server.ReservationVO;

import org.androidtown.covid19center.Server.RetrofitClient;
import org.androidtown.covid19center.Server.ServiceApi;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;


import java.util.HashMap;
import java.util.List;
import java.util.Locale;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class QuestionnaireModificationActivity extends AppCompatActivity implements NumberPicker.OnValueChangeListener {

    private RadioGroup visitedCheck_radioGroup;
    private RadioButton visitedTrue_radioButton;
    private RadioButton visitedFalse_radioButton;
    private EditText countryEditText;
    private TextView entranceDateTextView;

    private RadioGroup contact_radioGroup;
    private RadioButton contactTrue_radioButton;
    private RadioButton contactFalse_radioButton;
    private EditText relationEditText;
    private TextView termTextView;

    private CheckBox none_checkBox;
    private CheckBox fever_checkBox, muscle_ache_checkBox, cough_checkBox, sputum_checkBox, runny_nose_checkBox, dyspnea_checkBox, sore_throat_checkBox;
    private TextView startVirusDateTextView;

    private final Calendar myCalender = Calendar.getInstance();
    private ImageButton backButton;
    private Button nextButton;

    int sequence;
    String user_id;

    private boolean isVisited;
    private String visitedDetail;
    private String entrance_date;
    private boolean isContacted;
    private String contact_relationship;
    private String contact_period;
    private boolean hasFever, hasMuscle_ache, hasCough, hasSputum, hasRunnyNose, hasDyspnea, hasSoreThroat;
    private String symptom_start_date;
    private String toDoctor;

    /*
    private String clinicName;
    private String clinicReservationTime;
     */
    private ServiceApi serviceApi;
    private ArrayList<QuestionnaireVO> questionnaireVOArrayList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        Log.d("~~~~~QuestionnaireModif", "onCreate");

        serviceApi = RetrofitClient.getClient().create(ServiceApi.class);

        setContentView(R.layout.activity_questionnaire_modification);

        setLayoutElement();

        getQuestionnaire();
    }


    private void setLayoutElement() {
        Log.d("~~~~~QuestionnaireModif", "setLayoutElement");

        visitedCheck_radioGroup = findViewById(R.id.questionnarie_visited_radioGroup);
        visitedCheck_radioGroup.setOnCheckedChangeListener(visitedCheckRadioGroupButtonChangeListener);
        visitedTrue_radioButton = findViewById(R.id.questionnarie_visited_radioButton_true);
        visitedFalse_radioButton = findViewById(R.id.questionnarie_visited_radioButton_false);
        countryEditText = findViewById(R.id.questionnarie_editText_country);
        entranceDateTextView = findViewById(R.id.questionnarie_textView_date);


        contact_radioGroup = findViewById(R.id.questionnarie_contact_radioGroup);
        contact_radioGroup.setOnCheckedChangeListener(contactRadioGroupButtonChangeListener);
        contactTrue_radioButton = findViewById(R.id.questionnarie_contact_radioButton_true);
        contactFalse_radioButton = findViewById(R.id.questionnarie_contact_radioButton_false);
        relationEditText = findViewById(R.id.questionnarie_editText_relation);
        termTextView = findViewById(R.id.questionnarie_textView_term);

        none_checkBox = findViewById(R.id.questionnarie_radioButton_false);
        fever_checkBox = findViewById(R.id.questionnarie_fever_radioButton_true);
        muscle_ache_checkBox = findViewById(R.id.questionnarie_muscle_ache_radioButton_true);
        cough_checkBox = findViewById(R.id.questionnarie_cough_radioButton_true);
        sputum_checkBox = findViewById(R.id.questionnarie_sputum_radioButton_true);
        runny_nose_checkBox = findViewById(R.id.questionnarie_runny_nose_radioButton_true);
        dyspnea_checkBox = findViewById(R.id.questionnarie_dyspnea_radioButton_true);
        sore_throat_checkBox = findViewById(R.id.questionnarie_sore_throat_radioButton_true);
        startVirusDateTextView = findViewById(R.id.questionnarie_textView_start_date);

        nextButton = findViewById(R.id.questionnarie_button);
        backButton = findViewById(R.id.questionnarie_backButton);

        // clinicReservationTime = null;
        symptom_start_date = null;
        visitedDetail = null;
        contact_relationship = null;
        contact_period = null;
        entrance_date = null;

        nextButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setCheckedInfo();


                /** toDoctor 내용 추가해주기
                 *
                 */
                updateQuestionnaireData(new QuestionnaireData(
                        sequence,
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

                Toast.makeText(getApplicationContext(), "문진표가 수정되었습니다.", Toast.LENGTH_SHORT).show();
                finish();

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
                myCalender.set(Calendar.YEAR, year);
                myCalender.set(Calendar.MONTH, month);
                myCalender.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                updateLabel();
            }
        };

        DatePickerDialog.OnDateSetListener virusStartDate = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                myCalender.set(Calendar.YEAR, year);
                myCalender.set(Calendar.MONTH, month);
                myCalender.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                updateVirusStartLabel();
            }
        };

        entranceDateTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d("~~~~~QuestionnaireModif", "entranceDateTextView click");
                new DatePickerDialog(QuestionnaireModificationActivity.this, date, myCalender.get(Calendar.YEAR), myCalender.get(Calendar.MONTH), myCalender.get(Calendar.DAY_OF_MONTH)).show();
            }
        });

        termTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Log.d("~~~~~QuestionnaireModif", "termTextView click");
                showNumberPicker(v, "접촉기간", "1", 30, 0, 1, 1);
            }
        });

        startVirusDateTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d("~~~~~QuestionnaireModif", "startVirusDateTextView click");

                new DatePickerDialog(QuestionnaireModificationActivity.this, virusStartDate, myCalender.get(Calendar.YEAR), myCalender.get(Calendar.MONTH), myCalender.get(Calendar.DAY_OF_MONTH)).show();
            }
        });

    }


    protected void getQuestionnaire() {
        Log.d("~~~~~QuestionnaireModif", " getQuestionnaire");
        int visited;
        int contact;
        int fever, muscle_ache, cough, sputum, runny_nose, dyspnea, sore_throat;

                /*
                String visited_detail, entrance_date;
                String contact_relationship, contact_period;
                String symptom_start_date, toDoctor;
                 */


        questionnaireVOArrayList = new ArrayList<QuestionnaireVO>();
        questionnaireVOArrayList = AppManager.getInstance().getQuestionnaireVOArrayList();

        Log.d("~~~~~QuestionnaireModif","questionnaireVOArrayList size: "+questionnaireVOArrayList.size());

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

        setQuestionnaire();
    }




    private void updateQuestionnaireData(QuestionnaireData questionnaireData) {
        Log.d("~~~~~QuestionnaireModif", "updateQuestionnaireData");

        Call<ResponseBody> dataCall = serviceApi.modifyQuestionnaireData(sequence, questionnaireData);

        dataCall.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                String result = null;
                try {
                    Log.d("~~~~~QuestionnaireModif", "response" + response.code());
                    result = response.body().string();

                } catch (IOException e) {
                    Log.d("~~~~~QuestionnaireModif", String.valueOf(e));
                    e.printStackTrace();
                }
                Log.i("~~~~~QuestionnaireModif", "result: " + result);
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                Log.i("~~~~~QuestionnaireModif", "fail");
                if (t instanceof IOException) {
                    // Handle IO exception, maybe check the network and try again.
                    Log.i("~~~~~QuestionnaireModif", "t" + t);
                }
            }
        });
    }


    private void setQuestionnaire() {
        Log.d("~~~~~QuestionnaireModif", "setQuestionnaire");

        if (isVisited) {
            visitedTrue_radioButton.setChecked(true);
        } else {
            visitedFalse_radioButton.setChecked(true);
        }
        countryEditText.setText(visitedDetail);

        if (entrance_date.equals("")) {
            Log.d("~~~~~QuestionnaireModif", "entrance date 공란");
            entranceDateTextView.setText(R.string.entrance_date_text);
            entranceDateTextView.setTextColor(Color.LTGRAY);
        } else {
            entranceDateTextView.setText(entrance_date);
            entranceDateTextView.setTextColor(Color.BLACK);
        }

        if (isContacted) {
            contactTrue_radioButton.setChecked(true);
        } else {
            contactFalse_radioButton.setChecked(true);
        }
        relationEditText.setText(contact_relationship);

        if (contact_period.equals("")) {
            termTextView.setText(R.string.term_text);
            termTextView.setTextColor(Color.LTGRAY);
        } else {
            termTextView.setText(entrance_date);
            termTextView.setTextColor(Color.BLACK);
        }


        if (hasFever) {
            fever_checkBox.setChecked(true);
        }
        if (hasMuscle_ache) {
            muscle_ache_checkBox.setChecked(true);
        }
        if (hasCough) {
            cough_checkBox.setChecked(true);
        }
        if (hasSputum) {
            sputum_checkBox.setChecked(true);
        }
        if (hasRunnyNose) {
            runny_nose_checkBox.setChecked(true);
        }
        if (hasDyspnea) {
            dyspnea_checkBox.setChecked(true);
        }
        if (hasSoreThroat) {
            sore_throat_checkBox.setChecked(true);
        }

        if (!hasFever && !hasMuscle_ache && !hasCough && !hasSputum && !hasRunnyNose && !hasDyspnea && !hasSoreThroat) {
            none_checkBox.setChecked(true);
        }

        if (symptom_start_date.equals("")) {
            startVirusDateTextView.setText(R.string.term_text);
            startVirusDateTextView.setTextColor(Color.LTGRAY);
        } else {
            startVirusDateTextView.setText(entrance_date);
            startVirusDateTextView.setTextColor(Color.BLACK);
        }
    }

    RadioGroup.OnCheckedChangeListener contactRadioGroupButtonChangeListener = new RadioGroup.OnCheckedChangeListener() {
        @Override
        public void onCheckedChanged(RadioGroup group, int checkedId) {
            if (checkedId == R.id.questionnarie_contact_radioButton_true) {
                isContacted = true;
            } else if (checkedId == R.id.questionnarie_contact_radioButton_false) {
                isContacted = false;
            }
        }
    };

    RadioGroup.OnCheckedChangeListener visitedCheckRadioGroupButtonChangeListener = new RadioGroup.OnCheckedChangeListener() {
        @Override
        public void onCheckedChanged(RadioGroup group, int checkedId) {

            if (checkedId == R.id.questionnarie_visited_radioButton_true) {
                isVisited = true;
            } else if (checkedId == R.id.questionnarie_visited_radioButton_false) {
                isVisited = false;
            }

        }
    };

    private void setCheckedInfo() {

        Log.d("~~~~~QuestionnaireModif", "setCheckedInfo");

        if (fever_checkBox.isChecked() == true) {
            hasFever = true;
        } else {
            hasFever = false;
        }

        if (muscle_ache_checkBox.isChecked() == true) {
            hasMuscle_ache = true;
        } else {
            hasMuscle_ache = false;
        }

        if (cough_checkBox.isChecked() == true) {
            hasCough = true;
        } else {
            hasCough = false;
        }

        if (runny_nose_checkBox.isChecked() == true) {
            hasRunnyNose = true;
        } else {
            hasRunnyNose = false;
        }

        if (dyspnea_checkBox.isChecked() == true) {
            hasDyspnea = true;
        } else {
            hasDyspnea = false;
        }

        if (sputum_checkBox.isChecked() == true) {
            hasSputum = true;
        } else {
            hasSputum = false;
        }

        if (sore_throat_checkBox.isChecked() == true) {
            hasSoreThroat = true;
        } else {
            hasSoreThroat = false;
        }

        contact_relationship = String.valueOf(relationEditText.getText());
        visitedDetail = String.valueOf(countryEditText.getText());
    }

    /*
    private void setIntentInfo(){
        clinicName = getIntent().getExtras().getString("clinicName");
        clinicReservationTime = getIntent().getExtras().getString("clinicTime");
    }

     */

    private void updateLabel() {
        Log.d("~~~~~QuestionnaireModif", "updateLabel");
        String myFormat = "yyyy/MM/dd"; //In which you need put here
        SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.KOREA);
        entranceDateTextView.setText(sdf.format(myCalender.getTime()));
        entranceDateTextView.setTextColor(Color.BLACK);
        entrance_date = sdf.format(myCalender.getTime());
    }

    private void updateVirusStartLabel() {
        Log.d("~~~~~QuestionnaireModif", "updateVirusStartLabel");

        String myFormat = "yyyy/MM/dd"; //In which you need put here
        SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.KOREA);
        startVirusDateTextView.setText(sdf.format(myCalender.getTime()));
        startVirusDateTextView.setTextColor(Color.BLACK);
        symptom_start_date = sdf.format(myCalender.getTime());
    }

    @Override
    public void onValueChange(NumberPicker picker, int oldVal, int newVal) {

        Log.d("~~~~~QuestionnaireModif", "onValueChange");
        termTextView.setText(String.valueOf(newVal) + "일");
        termTextView.setTextColor(Color.BLACK);
        contact_period = String.valueOf(newVal);
    }

    public void showNumberPicker(View view, String title, String subtitle, int maxvalue, int minvalue, int step, int defvalue) {

        Log.d("~~~~QuestionnaireModif~", "showNumberPicker");
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
}