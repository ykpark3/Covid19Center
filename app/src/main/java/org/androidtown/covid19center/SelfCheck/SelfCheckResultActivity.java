package org.androidtown.covid19center.SelfCheck;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import org.androidtown.covid19center.R;

public class SelfCheckResultActivity extends AppCompatActivity {

    public int mCheckNum;
    private ImageButton backButton;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_self_check_result);

        backButton = (ImageButton)findViewById(R.id.self_check_result_back_button);

        //자가진단 체크 개수 전달받기
        Intent intent = getIntent();
        mCheckNum = intent.getExtras().getInt("checkNum");

        changeView(mCheckNum);

        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
                finish();
            }
        });
    }

    private void changeView(int check_num) {
        // LayoutInflater 초기화.
        LayoutInflater inflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        FrameLayout frame = (FrameLayout) findViewById(R.id.self_result_frame) ;
        if (frame.getChildCount() > 0) {
            // FrameLayout에서 뷰 삭제.
            frame.removeViewAt(0);
        }

        // XML에 작성된 레이아웃을 View 객체로 변환.
        View view = null ;
        switch (check_num) {
            case 0:{
                view = inflater.inflate(R.layout.self_result_page1, frame, false) ;
                break ;
            }

            case 1:
            case 2:
            case 3:
            case 4:
            case 5:{
                view = inflater.inflate(R.layout.self_result_page2, frame, false) ;
                break ;
            }

            default:
            {
                view = inflater.inflate(R.layout.self_result_page3, frame, false) ;
                break ;
            }
        }

        // FrameLayout에 뷰 추가.
        if (view != null) {
            frame.addView(view) ;
        }
    }
}
