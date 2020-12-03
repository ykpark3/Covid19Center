package org.androidtown.covid19center.Mypage;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import org.androidtown.covid19center.R;
import org.androidtown.covid19center.Search.List.ClinicItem;

import java.util.ArrayList;

public class RecordAdapter extends BaseAdapter {

    Context mContext = null; // 액티비티와 에플리케이션 정보 담을 context 생성
    LayoutInflater mLayoutInflater = null; // 레이아웃 변경 인플레이트 생성
    ArrayList<RecordItem> clinics; // 진료소 담을 리스트 생성

    public RecordAdapter(Context context, ArrayList<RecordItem> data) { // 생성자에서 초기화
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
        View view = mLayoutInflater.inflate(R.layout.medical_recode_list_item, null);
        TextView clinicName = view.findViewById(R.id.medical_recode_name_clinicName);
        TextView clinicDate = view.findViewById(R.id.medical_recode_name_date);
        TextView name = view.findViewById(R.id.medical_recode_name);

        clinicName.setText("병원 명 : "+clinics.get(position).getClinicName());
        clinicDate.setText("진료 일시 : "+clinics.get(position).getClinicDate());
        name.setText("환자 명 : "+clinics.get(position).getName());

        return view;
    }
}
