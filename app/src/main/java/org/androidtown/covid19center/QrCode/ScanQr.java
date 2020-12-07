package org.androidtown.covid19center.QrCode;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;

import org.androidtown.covid19center.R;

public class ScanQr extends AppCompatActivity {
    private IntentIntegrator qrScan;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scan_qr);

        qrScan = new IntentIntegrator(this);
        qrScan.setOrientationLocked(false); // default가 세로모드인데 휴대폰 방향에 따라 가로, 세로로 자동 변경됩니다.
        qrScan.setBeepEnabled(false); //스캔시 소리 안나게 변경
        qrScan.initiateScan();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        IntentResult intentResult = IntentIntegrator.parseActivityResult(requestCode, resultCode, data);

        if(intentResult != null){
            if(intentResult.getContents()==null){
                Toast.makeText(this, "QR 스캔에 실패하였습니다.", Toast.LENGTH_SHORT).show();

                finish();
            }else{
                Toast.makeText(this, "예약 확인이 완료되었습니다.", Toast.LENGTH_SHORT).show();

                finish();
            }
        }else{
            super.onActivityResult(requestCode, resultCode, data);
        }

    }
}
