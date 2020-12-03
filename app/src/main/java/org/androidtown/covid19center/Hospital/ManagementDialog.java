package org.androidtown.covid19center.Hospital;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.annotation.NonNull;

import org.androidtown.covid19center.R;

public class ManagementDialog extends Dialog implements View.OnClickListener{

    private Button ok_btn, cancel_btn;
    private Context context;
    private ManagementDialogListener managementDialogListener;

    public ManagementDialog(@NonNull Context context) {
        super(context);
        this.context = context;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.dialog_management);

        ok_btn = (Button)findViewById(R.id.ok_btn);
        cancel_btn = (Button)findViewById(R.id.cancel_btn);

        ok_btn.setOnClickListener(this);
        cancel_btn.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.ok_btn:{

                //리스트 값 변경@@@

                managementDialogListener.onOkClicked();

                dismiss();
                break;
            }

            case R.id.cancel_btn:{
                
                cancel();
                break;
            }
        }
    }

    //인터페이스 설정
    interface ManagementDialogListener{
        void onOkClicked();
        void onCancelClicked();
    }

    //호출할 리스너 초기화
    public void setManagementDialogListener(ManagementDialogListener managementDialogListener){
        this.managementDialogListener = managementDialogListener;
    }


}
