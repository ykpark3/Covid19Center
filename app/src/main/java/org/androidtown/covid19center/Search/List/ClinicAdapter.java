package org.androidtown.covid19center.Search.List;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import org.androidtown.covid19center.R;

import java.util.ArrayList;

public class ClinicAdapter extends BaseAdapter {

    Context mContext = null; // 액티비티와 에플리케이션 정보 담을 context 생성
    LayoutInflater mLayoutInflater = null; // 레이아웃 변경 인플레이트 생성
    ArrayList<ClinicItem> clinics; // 진료소 담을 리스트 생성

    public ClinicAdapter(Context context, ArrayList<ClinicItem> data) { // 생성자에서 초기화
        mContext = context;
        clinics = data;
        mLayoutInflater = LayoutInflater.from(mContext);
    }

    @Override
    public int getCount() {
        return clinics.size();
    }

    @Override
    public Object getItem(int i) {
        return clinics.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int position, View converView, ViewGroup parent) {
        View view = mLayoutInflater.inflate(R.layout.clinic_item_list, null);
        TextView clinicName = view.findViewById(R.id.textClinic);
        TextView clinicCallNumber = view.findViewById(R.id.textCallNumber);
        TextView clinicAddress = view.findViewById(R.id.textAddress);
        TextView clinicDistance = view.findViewById(R.id.textDistance);

        clinicName.setText(clinics.get(position).getClinicName());
        clinicCallNumber.setText(clinics.get(position).getClinicCallNumber());
        clinicAddress.setText(clinics.get(position).getClinicAddress());
        clinicDistance.setText(String.format("%.1f", clinics.get(position).getClinicDistance() / 1000) + "km");

        return view;
    }
}
