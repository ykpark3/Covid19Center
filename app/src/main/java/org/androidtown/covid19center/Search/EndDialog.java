package org.androidtown.covid19center.Search;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;

import org.androidtown.covid19center.R;
import org.androidtown.covid19center.SelfCheck.QuestionnaireActivity;

import java.util.ArrayList;


import java.util.ArrayList;



import java.util.ArrayList;



public class EndDialog extends Dialog implements View.OnClickListener {

    private Context mContext;
    private ArrayList<String> clinicInfoList;
    private String timeMessage;
    private String clinicMessage;
    private TextView textView_clinic_message;
    private TextView btn_cancel;
    private TextView textView_message;
    private TextView btn_ok;


    public EndDialog(@NonNull Context context, String time, String clinic, ArrayList<String> clinicInfoList) {

        super(context);

        mContext = context;

        timeMessage = time;

        clinicMessage = clinic;

        this.clinicInfoList = new ArrayList<>();

        this.clinicInfoList = clinicInfoList;

    }



    @Override

    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);

        setContentView(R.layout.option_codetype_dialog);

        btn_cancel = (TextView) findViewById(R.id.btn_cancel);
        btn_ok = (TextView) findViewById(R.id.btn_ok);
        textView_message = (TextView) findViewById(R.id.message_textView);
        textView_clinic_message = (TextView) findViewById(R.id.message_clinic_textView);

        textView_message.setText("예약 일자 : " + timeMessage);
        textView_clinic_message.setText("진료소 : " + clinicMessage);
        btn_cancel.setOnClickListener(this);

        btn_ok.setOnClickListener(this);
    }





    @Override

    public void onClick(View v) {

        switch (v.getId()) {

            case R.id.btn_cancel:

                dismiss();

                break;



            case R.id.btn_ok:



                Intent intent = new Intent(mContext, QuestionnaireActivity.class);

                intent.putExtra("clinicName", clinicMessage);
                intent.putExtra("clinicTime", timeMessage);
                intent.putExtra("clinicAddress", clinicInfoList.get(2));
                intent.putExtra("clinicCallNumber", clinicInfoList.get(3));
                mContext.startActivity(intent);


                dismiss();

                break;

        }

    }

}
