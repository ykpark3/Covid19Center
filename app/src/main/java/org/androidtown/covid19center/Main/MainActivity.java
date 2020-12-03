package org.androidtown.covid19center.Main;


import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.naver.maps.map.NaverMapSdk;

import org.androidtown.covid19center.DataBase.AppDatabase;
import org.androidtown.covid19center.DataBase.Clinic;
import org.androidtown.covid19center.Hospital.FragmentHospital;
import org.androidtown.covid19center.Map.LocationConsts;
import org.androidtown.covid19center.Mypage.FragmentMypage;
import org.androidtown.covid19center.R;
import org.androidtown.covid19center.Search.FragmentSearch;
import org.androidtown.covid19center.Search.List.ClinicItem;
import org.androidtown.covid19center.Search.List.SearchActivity;
import org.androidtown.covid19center.SelfCheck.FragmentSelfCheck;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements OnBackPressedListener {
    ArrayList<ClinicItem> clinicDataList;
    private FragmentManager fragmentManager;
    private FragmentTransaction fragmentTransaction;
    private FragmentSearch fragmentSearch;
    private FragmentMypage fragmentMypage;
    private FragmentSelfCheck fragmentSelfCheck;
    private FragmentHospital fragmentHospital;
    private BottomNavigationView bottomNavigationView; // 바텀 네비게이션 뷰
    private String inputStream;
    private String[] change;
    private String[][] token;
    private String address;
    private AppDatabase db;
    private long lastTimeBackPressed;

    private int count;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setBottomNavigation();
        // 메모장 저장
        saveData();

        count = 0; // 위치권한카운트
    }


    private void saveData() {

        // 초기화면 검색화면으로 설정
        fragmentManager = getSupportFragmentManager();
        setFragment(fragmentSearch);

        // 메모장 저장
        token = new String[616][];
        clinicDataList = new ArrayList<ClinicItem>();

        db = AppDatabase.getInstance(getBaseContext());

        NaverMapSdk.getInstance(this).setClient(
                new NaverMapSdk.NaverCloudPlatformClient(NaverConsts.CLIENT_ID));

        // db저장
        try {
            inputStream = readText("clinics.txt");
            change = inputStream.split("\\n");
            divideComma(change);
        } catch (IOException e) {
            e.printStackTrace();
        }

        db.clinicDao().getAll().observe(this, clinics -> {
            if (clinics.isEmpty()) {
                insertClinics();
            } else {
                Log.d("메모리", "비어있지않았음");
                Log.d("메모리", String.valueOf(clinics.size()));
            }
        });

//        // geocode 불러오는 코드
//        for(int i=0; i<token.length;i++)
//        {
//            address = token[i][1];
//
//            try {
//                Thread thread = new NaverApi(address);
//                thread.start();
//                thread.sleep(50);
//            } catch (InterruptedException e) {
//                e.printStackTrace();
//            }
//
//        }

    }

    private String readText(String file) throws IOException {
        InputStream is = getAssets().open(file);

        int size = is.available();

        byte[] buffer = new byte[size];
        BufferedReader read;
        is.read(buffer);
        is.close();

        String text = new String(buffer);
        return text;
    }

    private void divideComma(String[] line) {
        for (int i = 0; i < line.length; i++) {
            line[i] = line[i].replaceAll("\t", ",");
            token[i] = line[i].split(",");
        }
    }

    private void insertClinics() {

        for (int i = 0; i < token.length; i++) {

            Log.d("태순", String.valueOf(i));
            db.clinicDao().insert(new Clinic(token[i][0], token[i][1], token[i][2], token[i][3], token[i][4]));
            clinicDataList.add(new ClinicItem(token[i][0], token[i][1], token[i][2], token[i][3], token[i][4]));


        }

    }

    // 바텀 네비게이션 설정
    public void setBottomNavigation() {
        bottomNavigationView = findViewById(R.id.bottomNavi);

        // 프레그먼트 선택 시 이벤트 처리 함수
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.action_search:
                        setFragment(fragmentSearch);
                        break;
                    case R.id.action_self_check:
                        setFragment(fragmentSelfCheck);
                        break;
                    case R.id.action_mypage:
                        setFragment(fragmentMypage);
                        break;
                }

                return true;
            }
        });

        fragmentSearch = new FragmentSearch();
        fragmentSelfCheck = new FragmentSelfCheck();
        fragmentMypage = new FragmentMypage();
        fragmentHospital = new FragmentHospital();
    }

    // 프레그먼트 변경 함수
    public void setFragment(Fragment fragment) {

        fragmentTransaction = fragmentManager.beginTransaction();

        fragmentTransaction.replace(R.id.Main_Frame, fragment);
        fragmentTransaction.commit();
    }


    @Override
    public void onBackPressed() {

        List<Fragment> fragmentList = getSupportFragmentManager().getFragments();

        for (Fragment fragment : fragmentList) {
            if (fragment instanceof OnBackPressedListener) {
                ((OnBackPressedListener) fragment).onBackPressed();
                return;
            }
        }

        if (System.currentTimeMillis() - lastTimeBackPressed < 1500) {
            finish();
            return;
        }

        lastTimeBackPressed = System.currentTimeMillis();
        Toast.makeText(this, "'뒤로' 버튼을 한 번 더 누르면 종료됩니다.", Toast.LENGTH_SHORT).show();
    }


    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        Log.d("0256",String.valueOf(grantResults[0]));
        if(grantResults[0] == -1){
            Toast.makeText(this, "원활한 서비스를 위해 위치 권한을 승인 해주시길 바랍니다.", Toast.LENGTH_LONG).show();
            count++;
        }
        if(count > 1){
            Toast.makeText(this, "원활한 서비스를 위해 위치 권한을 승인 해주시길 바랍니다.", Toast.LENGTH_LONG).show();
            LocationConsts.NOW_X = 126.924;
            LocationConsts.NOW_Y = 37.516;

            Intent intent = new Intent(this, SearchActivity.class);
            startActivity(intent);
        }
    }

}