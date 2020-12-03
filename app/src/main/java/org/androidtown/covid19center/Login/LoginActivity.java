package org.androidtown.covid19center.Login;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.util.Log;

import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import org.androidtown.covid19center.Main.MainActivity;
import org.androidtown.covid19center.R;

import org.androidtown.covid19center.Hospital.HospitalMainActivity;
import org.androidtown.covid19center.Server.AppManager;
import org.androidtown.covid19center.Server.QuestionnaireVO;
import org.androidtown.covid19center.Server.ReservationVO;
import org.androidtown.covid19center.Server.RetrofitClient;
import org.androidtown.covid19center.Server.ServiceApi;
import org.androidtown.covid19center.Server.UsersVO;


import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoginActivity extends AppCompatActivity {

    EditText idEditText, passwordEditText;
    Button loginButton;
    private ServiceApi serviceApi;
    private Context mcontext;
    private ArrayList<ReservationVO> reservationVOArrayList;
    private ArrayList<QuestionnaireVO> questionnaireVOArrayList;

    private String id, password;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        // 네트워크가 켜져있으면 동작
        if (checkNetworkSetting() == 1) {
            setLoginInfomation();
        }

    }

    public void setLoginInfomation() {
        serviceApi = RetrofitClient.getClient().create(ServiceApi.class);

        idEditText = findViewById(R.id.idEditText);
        passwordEditText = findViewById(R.id.passwordEditText);
        loginButton = findViewById(R.id.loginButton);

        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                id = idEditText.getText().toString();
                password = passwordEditText.getText().toString();

                startLogin();
            }
        });

    }

    public int checkNetworkSetting() {
        mcontext = getApplicationContext();

        if (getOnline_23() == 0) {

            Intent intent = new Intent(getApplicationContext(), DataWarningActivity.class);
            startActivity(intent);
            finish();
        } else {
            return 1;
        }
        return 0;
    }

    public int getOnline_23() {
        int ret_code = 0;

        ConnectivityManager cm = (ConnectivityManager) mcontext.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetwork = cm.getActiveNetworkInfo();
        if (activeNetwork != null) {
            if (activeNetwork.getType() == ConnectivityManager.TYPE_WIFI && activeNetwork.isConnectedOrConnecting()) {
                // wifi 연결중
                return 1;
            } else if (activeNetwork.getType() == ConnectivityManager.TYPE_MOBILE && activeNetwork.isConnectedOrConnecting()) {
                // 모바일 네트워크 연결중
                return 2;
            } else {
                // 네트워크 오프라인 상태.
                return 0;
            }
        } else {
            //네트워크 없는 상태
            return 0;
        }

    }


    public void startLogin() {

        serviceApi.getUserData(id, password).enqueue(new Callback<List<UsersVO>>() {
            @Override
            public void onResponse(Call<List<UsersVO>> call, Response<List<UsersVO>> response) {
                boolean isLoginPossible = false;

                if (response.isSuccessful()) {
                    List<UsersVO> data = response.body();


                    for (int i = 0; i < data.size(); i++) {
                        if (data.get(i).getId().equals(id) && data.get(i).getPassword().equals(password)) {
                            Log.d("~~~~~LoginActivity", "로그인 가능");
                            Toast toast = Toast.makeText(LoginActivity.this, "로그인 성공", Toast.LENGTH_SHORT);
                            toast.show();

                            AppManager.getInstance().setUserId(id);   // user id 저장하기

                            isLoginPossible = true;

                            getReservationList();

                            break;
                        }
                    }

                    if (!isLoginPossible) {

                        Log.d("~~~~~LoginActivity", "로그인 정보 확인 필요");
                        Toast toast = Toast.makeText(LoginActivity.this, "로그인 정보를 확인해주세요.", Toast.LENGTH_SHORT);
                        toast.show();

                        idEditText.setText(null);
                        passwordEditText.setText(null);

                        idEditText.requestFocus();

                    }
                }
            }

            @Override

            public void onFailure(Call<List<UsersVO>> call, Throwable t) {
                Log.d("~~~~~LoginActivity", "실패: " + t);
                t.printStackTrace();

            }
        });
    }


    protected void getReservationList() {
        Log.d("~~~~~LoginActivity", " getReservationList");

        serviceApi.getReservationVO().enqueue(new Callback<List<ReservationVO>>() {
            @Override
            public void onResponse(Call<List<ReservationVO>> call, Response<List<ReservationVO>> response) {


                reservationVOArrayList = AppManager.getInstance().getReservationVOArrayList();
                reservationVOArrayList.clear();

                String user_id;
                int questionnaire_seq;
                String hospital_name;
                String time;
                String date;
                int visited;
                String completion_time;


                if (response.isSuccessful()) {
                    List<ReservationVO> data = response.body();

                    Log.d("~~~~~LoginActivity", "getReservationList 가져오기 성공");

                    for (int i = 0; i < data.size(); i++) {
                        user_id = data.get(i).getUser_id();
                        questionnaire_seq = data.get(i).getQuestionnaire_seq();
                        hospital_name = data.get(i).getHospital_name();
                        time = data.get(i).getTime();
                        date = data.get(i).getDate();
                        visited = data.get(i).getVisited();
                        // completion_time = data.get(i).getCompletion_time();

                        ReservationVO reservationVO = new ReservationVO(user_id, questionnaire_seq, hospital_name, time, date, visited
                                //,completion_time
                        );
                        reservationVOArrayList.add(reservationVO);

                    }

                    AppManager.getInstance().setReservationVOArrayList(reservationVOArrayList);
                    Log.d("~~~~~LoginActivity", "reservation list size:" + reservationVOArrayList.size());


                    getQuestionnaireList();

                }
            }

            @Override
            public void onFailure(Call<List<ReservationVO>> call, Throwable t) {
                Log.d("~~~~~LoginActivity", "실패: " + t);
                t.printStackTrace();
            }
        });
    }



    protected void getQuestionnaireList() {
        Log.d("~~~~~LoginActivity", " getQuestionnaireList");

        serviceApi.getQuesionnaireVO().enqueue(new Callback<List<QuestionnaireVO>>() {
            @Override
            public void onResponse(Call<List<QuestionnaireVO>> call, Response<List<QuestionnaireVO>> response) {


                questionnaireVOArrayList = AppManager.getInstance().getQuestionnaireVOArrayList();
                questionnaireVOArrayList.clear();

                int sequence;

                String user_id;
                int visited;

                String visited_detail, entrance_date;
                int contact;
                String contact_relationship, contact_period;
                int fever, muscle_ache, cough, sputum, runny_nose, dyspnea, sore_throat;
                String symptom_start_date, toDoctor;


                if (response.isSuccessful()) {
                    List<QuestionnaireVO> data = response.body();

                    Log.d("~~~~~LoginActivity", "questionnaireList 가져오기 성공");

                    for (int i = 0; i < data.size(); i++) {
                        sequence = data.get(i).getSequence();
                        user_id = data.get(i).getUser_id();
                        visited = data.get(i).getVisited();
                        visited_detail = data.get(i).getVisited_detail();
                        entrance_date = data.get(i).getEntrance_date();
                        contact = data.get(i).getContact();
                        contact_relationship = data.get(i).getContact_relationship();
                        contact_period = data.get(i).getContact_period();
                        fever = data.get(i).getFever();
                        muscle_ache = data.get(i).getMuscle_ache();
                        cough = data.get(i).getCough();
                        sputum = data.get(i).getSputum();
                        runny_nose = data.get(i).getRunny_nose();
                        dyspnea = data.get(i).getDyspnea();
                        sore_throat = data.get(i).getSore_throat();
                        symptom_start_date = data.get(i).getSymptom_start_date();
                        toDoctor = data.get(i).getToDoctor();


                        QuestionnaireVO questionnaireVO = new QuestionnaireVO(
                                sequence, user_id,
                                visited, visited_detail, entrance_date,
                                contact, contact_relationship, contact_period,
                                fever, muscle_ache, cough, sputum, runny_nose, dyspnea, sore_throat,
                                symptom_start_date, toDoctor
                        );

                        questionnaireVOArrayList.add(questionnaireVO);

                    }

                    AppManager.getInstance().setQuestionnaireVOArrayList(questionnaireVOArrayList);
                    Log.d("~~~~~LoginActivity", "questionnaire list size:" + questionnaireVOArrayList.size());


                    if (id.equals("hhh")) {
                        Intent intent = new Intent(LoginActivity.this, HospitalMainActivity.class);
                        startActivity(intent);

                        finish();
                    } else {
                        Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                        startActivity(intent);

                        finish();
                    }
                }
            }

            @Override
            public void onFailure(Call<List<QuestionnaireVO>> call, Throwable t) {
                Log.d("~~~~~LoginActivity", "실패: " + t);
                t.printStackTrace();
            }
        });
    }

}