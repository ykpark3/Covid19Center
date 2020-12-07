package org.androidtown.covid19center.Hospital;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;

import androidx.fragment.app.FragmentActivity;

import org.androidtown.covid19center.Login.LoginActivity;
import org.androidtown.covid19center.R;
import org.androidtown.covid19center.Server.AppManager;
import org.androidtown.covid19center.Server.ReservationVO;
import org.androidtown.covid19center.Server.RetrofitClient;
import org.androidtown.covid19center.Server.ServiceApi;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class HospitalMainActivity extends FragmentActivity {

    //private ServiceApi serviceApi;
   // private ArrayList<ReservationVO> reservationVOArrayList;
    private ImageButton logoutButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hospital_main);
        logoutButton = findViewById(R.id.hospital_logout_button);
        Log.d("~~~~~", "HospitalMainActivity oncreate");

       // serviceApi = RetrofitClient.getClient().create(ServiceApi.class);

        // getReservationList();

        /*
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.add(R.id.fragmentHospital, new FragmentHospital()).commit();

         */
        logoutButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getBaseContext(), LoginActivity.class);
                startActivity(intent);
            }
        });
    }

    /*
    protected void getReservationList() {
        Log.d("~~~~~"," getReservationList");

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


                if(response.isSuccessful()) {
                    List<ReservationVO> data = response.body();

                    Log.d("~~~~~", "getReservationList 가져오기 성공");

                    for(int i=0; i<data.size(); i++) {
                        user_id = data.get(i).getUser_id();
                        questionnaire_seq = data.get(i).getQuestionnaire_seq();
                        hospital_name = data.get(i).getHospital_name();
                        time = data.get(i).getTime();
                        date = data.get(i).getDate();
                        visited = data.get(i).getVisited();
                       // completion_time = data.get(i).getCompletion_time();

                        ReservationVO reservationVO = new ReservationVO(user_id,questionnaire_seq,hospital_name,time,date,visited
                                //,completion_time
                        );
                        reservationVOArrayList.add(reservationVO);

                    }

                    AppManager.getInstance().setReservationVOArrayList(reservationVOArrayList);
                    Log.d("~~~~~","list size:"+reservationVOArrayList.size());
                }
            }

            @Override
            public void onFailure(Call<List<ReservationVO>> call, Throwable t) {
                Log.d("~~~~~","실패: "+ t);
                t.printStackTrace();
            }
        });


     */
}
