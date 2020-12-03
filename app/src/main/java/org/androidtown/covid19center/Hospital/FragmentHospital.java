package org.androidtown.covid19center.Hospital;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import org.androidtown.covid19center.QrCode.ScanQr;
import org.androidtown.covid19center.R;

//의료진용 첫 페이지
//qr스캔, 예약관리 버튼
public class FragmentHospital extends Fragment {

    private FrameLayout qr_scan;
    private FrameLayout reservation;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_hospital2, container, false);

        qr_scan = view.findViewById(R.id.hospital_qr_scan2);
        reservation = view.findViewById(R.id.hospital_reservation2);

        qr_scan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //qr코드 스캔 activity intent
                Intent intent = new Intent(getContext(), ScanQr.class);
                startActivity(intent);
            }
        });

        reservation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //예약 관리 activity intnet
                Intent intent = new Intent(getContext(), ReservationManagementActivity.class);
                startActivity(intent);
            }
        });

        return view;
    }
}
