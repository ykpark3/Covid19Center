package org.androidtown.covid19center.QrCode;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;

import android.widget.ImageView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.WriterException;
import com.google.zxing.common.BitMatrix;
import com.journeyapps.barcodescanner.BarcodeEncoder;

import org.androidtown.covid19center.R;
import org.androidtown.covid19center.Server.AppManager;
import org.androidtown.covid19center.Server.ReservationVO;
import org.json.JSONException;
import org.json.JSONObject;


import java.util.ArrayList;

public class CreateQr extends AppCompatActivity {
    private ImageView qr_code;
    private ImageButton backButton;
    private String qr_data;

    ReservationVO reservationVO;

    private ArrayList<ReservationVO> reservationList;

    public CreateQr() {

        reservationList = new ArrayList<ReservationVO>();
        reservationList =  AppManager.getInstance().getReservationVOArrayList();

        reservationVO = new ReservationVO(
                reservationList.get(reservationList.size()-1).getUser_id(),
                reservationList.get(reservationList.size()-1).getQuestionnaire_seq(),
                reservationList.get(reservationList.size()-1).getHospital_name(),
                reservationList.get(reservationList.size()-1).getTime(),
                reservationList.get(reservationList.size()-1).getDate(),
                reservationList.get(reservationList.size()-1).getVisited());

        //reservationVO = new ReservationVO("user_qr_테스트", 1, "hospital_1", "11:30", "11/19", 0);
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_create_qr);
        ImageView qr_code = (ImageView) findViewById(R.id.qr_code);
        ImageButton backButton = (ImageButton) findViewById(R.id.create_qr_backButton);

        qr_data = createJson().toString(); //원하는 내용 저장

        MultiFormatWriter multiFormatWriter = new MultiFormatWriter();

        BitMatrix bitMatrix = null;

        try {
            //@@@
            //qr코드에 문진표 json 저장해야됨!
            bitMatrix = multiFormatWriter.encode(qr_data, BarcodeFormat.QR_CODE, 200, 200);

            BarcodeEncoder barcodeEncoder = new BarcodeEncoder();
            Bitmap bitmap = barcodeEncoder.createBitmap(bitMatrix);
            qr_code.setImageBitmap(bitmap);
        } catch (WriterException e) {
            e.printStackTrace();
        }

        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
                finish();
            }
        });
    }

    //Json 형식으로 만들기
    public Object createJson() {
        JSONObject object = new JSONObject();

        try {
            object.put("user_id", reservationVO.getUser_id());
            object.put("questionnaire_seq", reservationVO.getQuestionnaire_seq());
            object.put("hospital", reservationVO.getHospital_name());
            object.put("date", reservationVO.getDate());
            object.put("time", reservationVO.getTime());

        } catch (JSONException e) {
            e.printStackTrace();
        }

        return object;
    }
}
