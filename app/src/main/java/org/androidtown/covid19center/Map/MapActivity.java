package org.androidtown.covid19center.Map;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;

import com.naver.maps.geometry.LatLng;
import com.naver.maps.map.LocationTrackingMode;
import com.naver.maps.map.MapFragment;
import com.naver.maps.map.NaverMap;
import com.naver.maps.map.OnMapReadyCallback;
import com.naver.maps.map.overlay.InfoWindow;
import com.naver.maps.map.overlay.Marker;
import com.naver.maps.map.util.FusedLocationSource;

import org.androidtown.covid19center.DataBase.AppDatabase;
import org.androidtown.covid19center.R;
import org.androidtown.covid19center.Search.ClinicActivity;
import org.androidtown.covid19center.Search.List.ClinicItem;

import java.util.ArrayList;
import java.util.List;

public class MapActivity extends AppCompatActivity implements OnMapReadyCallback {

    private static final int LOCATTION_PERMISSION_REQUEST_CODE = 1000;
    MapFragment mapFragment;
    private AppDatabase db;
    private ArrayList<ClinicItem> clinicDataList; // 진료소 담을 리스트 생성
    private int number;
    private FusedLocationSource locationSource;
    private NaverMap naverMap;
    private Button button;
    private ImageButton backButton;
    private String[] clinicInfo;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_map);

        setContentView(R.layout.fragment_map);
        FragmentManager fm = getSupportFragmentManager();
        mapFragment = (MapFragment) fm.findFragmentById(R.id.map);
        clinicInfo = new String[5];
        button = findViewById(R.id.map_button);
        backButton = findViewById(R.id.map_backButton);

        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        button.setVisibility(View.GONE);

        if (mapFragment == null) {
            mapFragment = MapFragment.newInstance();
            fm.beginTransaction().replace(R.id.map, mapFragment).commit();
        }

        locationSource = new FusedLocationSource(this, LOCATTION_PERMISSION_REQUEST_CODE);
        mapFragment.getMapAsync(MapActivity.this);

    }


    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           @NonNull String[] permissions, @NonNull int[] grantResults) {

        if (locationSource.onRequestPermissionsResult(
                requestCode, permissions, grantResults)) {
            if (!locationSource.isActivated()) { // 권한 거부됨
                naverMap.setLocationTrackingMode(LocationTrackingMode.None);
            }
            return;
        }
        super.onRequestPermissionsResult(
                requestCode, permissions, grantResults);
    }

    @Override
    public void onMapReady(@NonNull NaverMap naverMap) {
        this.naverMap = naverMap;
        naverMap.setLocationSource(locationSource);
        naverMap.setLocationTrackingMode(LocationTrackingMode.Follow);
        List<Marker> markers = new ArrayList<>();
        InfoWindow infoWindow = new InfoWindow();

        db = AppDatabase.getInstance(getBaseContext());

        clinicDataList = new ArrayList<ClinicItem>();

        number = 0;

        button.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), ClinicActivity.class);
                startActivity(intent);
                finish();
            }
        });

        db.clinicDao().getAll().observe(this, clinics -> {

            for (int i = 0; i < clinics.size() - 1; i++) {
                Marker marker = new Marker();
                //clinicDataList.add(new ClinicItem(clinics.get(i).getClinicName(),clinics.get(i).getClinicCallNumber(), clinics.get(i).getClinicAddress(), clinics.get(i).getX(), clinics.get(i).getY()));
                marker.setTag(clinics.get(i).getClinicName());
                //marker.setCaptionText(clinics.get(i).getClinicName()+","+clinics.get(i).getClinicCallNumber()+","+clinics.get(i).getClinicAddress());
                marker.setSubCaptionText(clinics.get(i).getClinicName() + "," + clinics.get(i).getClinicAddress() + "," + clinics.get(i).getClinicCallNumber() + "," + clinics.get(i).getX() + "," + clinics.get(i).getY());
                marker.setWidth(50);
                marker.setHeight(80);
                marker.setPosition(new LatLng(Double.parseDouble(clinics.get(i).getY()), Double.parseDouble(clinics.get(i).getX())));
                marker.setOnClickListener(overlay -> {

                    if (marker.getInfoWindow() == null) {

                        clinicInfo = passClinicsInfo(marker.getSubCaptionText());
                        infoWindow.open(marker);
                        button.setVisibility(View.VISIBLE);
                        button.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                Intent intent = new Intent(
                                        getApplicationContext(), // 현재 화면의 제어권자
                                        ClinicActivity.class); // 다음 넘어갈 클래스 지정
                                intent.putExtra("clinicName", clinicInfo[0]);
                                intent.putExtra("clinicAddress", clinicInfo[1]);
                                intent.putExtra("clinicCallNumber", clinicInfo[2]);
                                intent.putExtra("clinicX", clinicInfo[3]);
                                intent.putExtra("clinicY", clinicInfo[4]);
                                intent.putExtra("clinicDistance", 0);
                                startActivity(intent);
                                finish();
                            }
                        });

                    } else {
                        infoWindow.close();
                        button.setVisibility(View.GONE);
                    }

                    return true;
                });

                infoWindow.close();
                markers.add(marker);
                number++;
            }

            if (number == 615) {
                for (Marker marker : markers) {
                    marker.setMap(naverMap);
                }
            }
        });

        infoWindow.setAdapter(new InfoWindow.DefaultTextAdapter(getBaseContext()) {
            @NonNull
            @Override
            public CharSequence getText(@NonNull InfoWindow infoWindow) {
                return (CharSequence) infoWindow.getMarker().getTag();
            }
        });

    }

    private String[] passClinicsInfo(String info) {
        String[] clinicInfoArray = new String[5];
        clinicInfoArray = info.split(",");
        return clinicInfoArray;
    }


}
