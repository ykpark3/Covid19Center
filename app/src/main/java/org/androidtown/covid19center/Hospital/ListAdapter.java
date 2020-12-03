package org.androidtown.covid19center.Hospital;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import org.androidtown.covid19center.R;
import org.androidtown.covid19center.SelfCheck.QuestionnaireActivity;
import org.androidtown.covid19center.Server.ReservationVO;

import java.util.ArrayList;

public class ListAdapter extends BaseAdapter {

    Context mContext = null;
    LayoutInflater mLayoutInflater = null;
    ArrayList<ReservationVO> list;
    String date;

    public ListAdapter(Context context, ArrayList<ReservationVO> data, String date) {
        mContext = context;
        list = data;
        this.date = date;
        mLayoutInflater = LayoutInflater.from(mContext);
    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public long getItemId(int position) { return position; }

    @Override
    public ReservationVO getItem(int position) {
        return list.get(position);
    }

    @Override
    public View getView(int position, View converView, ViewGroup parent) {
        View view = mLayoutInflater.inflate(R.layout.listview_reservation, null);

        TextView listview_user_id = (TextView) view.findViewById(R.id.listview_user_id);
        TextView listview_hospital = (TextView) view.findViewById(R.id.listview_hospital);
        TextView listview_date = (TextView) view.findViewById(R.id.listview_date);
        TextView listview_time = (TextView) view.findViewById(R.id.listview_time);
        Button finish_button = view.findViewById(R.id.finish_button);
        LinearLayout linearLayout = view.findViewById(R.id.layout_listview);

        listview_user_id.setText(list.get(position).getUser_id());
//        //네이밍 수정하기
//        listview_hospital.setText(String.valueOf(list.get(position).getVisited()));
        listview_date.setText(list.get(position).getDate());
        listview_time.setText(list.get(position).getTime());


        //리스트뷰 클릭시 환자 문진표 띄우기
        linearLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //환자 문진표 intent하기
                Intent intent = new Intent(view.getContext(), CheckQuestionnaireActivity.class);
                intent.putExtra("questionnaire sequence", list.get(position).getQuestionnaire_seq());
                view.getContext().startActivity(intent);
            }
        });

        //진료 완료시 클릭 비활성화
        if(list.get(position).getVisited() == 1){
            //리스트뷰 배경색 변경, 비활성화
            view.setBackgroundColor(Color.rgb(226,226,226));
            view.setEnabled(false);

            //진료완료 버튼 배경생 변경, 비활성화
            finish_button.setBackgroundResource(R.drawable.background_gray_rectangle);
            finish_button.setEnabled(false);
        }

        //진료 완료 묻는 팝업창 띄우기
        finish_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                Intent intent = new Intent(mContext, ManagementPopupActivity.class);
//                intent.putExtra("questionnaire_seq", list.get(position).getQuestionnaire_seq());
//                intent.putExtra("position", position);
//                intent.putExtra("reservationList", list);
//                mContext.startActivity(intent);

                ManagementDialog dialog = new ManagementDialog(mContext);

                dialog.setManagementDialogListener(new ManagementDialog.ManagementDialogListener() {
                    @Override
                    public void onOkClicked() {
                        //여기서 true값으로 리스트 값 변경하기!!!!!!!!!!
                        list.get(position).setVisited(1);

                        updateItem(list);
                    }

                    @Override
                    public void onCancelClicked() {

                    }
                });
                dialog.show();
            }
        });

        return view;
    }

    //환자 리스트 추가
    public void addItem(ReservationVO reservationVO) {
        list.add(list.size(), reservationVO);
        this.notifyDataSetChanged();
    }

    //환자 리스트 날짜별 변경
    public void updateItem(ArrayList<ReservationVO> nList) {
        list = nList;
        this.notifyDataSetChanged();
    }
}