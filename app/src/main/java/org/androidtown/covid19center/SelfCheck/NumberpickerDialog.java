package org.androidtown.covid19center.SelfCheck;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.graphics.Color;
import android.os.Bundle;
import android.widget.NumberPicker;

import androidx.fragment.app.DialogFragment;

import org.androidtown.covid19center.R;

public class NumberpickerDialog extends DialogFragment {
    private NumberPicker.OnValueChangeListener valueChangeListener;
    String title;
    String subTitle;
    int minvalue;
    int maxvalue;
    int step;  // 선택가능 값들의 간격
    int defvalue; // 시작 숫자

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState){
        final NumberPicker numberPicker = new NumberPicker(getActivity());


        title = getArguments().getString("title");
        subTitle = getArguments().getString("subTitle");
        minvalue = getArguments().getInt("minvalue");
        maxvalue = getArguments().getInt("maxvalue");
        step = getArguments().getInt("step");
        defvalue = getArguments().getInt("defvalue");

        String[] myValues = getArrayWithSteps(minvalue, maxvalue, step);

        numberPicker.setMinValue(0);
        numberPicker.setMaxValue((maxvalue - minvalue) / step);
        numberPicker.setDisplayedValues(myValues);

        numberPicker.setValue((defvalue - minvalue) / step);
        numberPicker.setDescendantFocusability(NumberPicker.FOCUS_BLOCK_DESCENDANTS);
        numberPicker.setBackgroundColor(Color.parseColor("#7c80ee"));

        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

        builder.setTitle(title);

        builder.setMessage(subTitle);

        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                valueChangeListener.onValueChange(numberPicker, numberPicker.getValue(), numberPicker.getValue());
            }
        });

        builder.setNegativeButton("CANCEL", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        });

        builder.setView(numberPicker);

        return builder.create();
    }

    public NumberPicker.OnValueChangeListener getValueChangeListener(){
        return valueChangeListener;
    }

    public void setValueChangeListener(NumberPicker.OnValueChangeListener valueChangeListener){
        this.valueChangeListener = valueChangeListener;
    }

    public String[] getArrayWithSteps(int min, int max, int step) {

        int number_of_array = (max - min) / step + 1;

        String[] result = new String[number_of_array];

        for (int i = 0; i < number_of_array; i++) {
            result[i] = String.valueOf(min + step * i);
        }
        return result;
    }

}
