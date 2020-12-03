package org.androidtown.covid19center.Mypage;

import org.androidtown.covid19center.Map.LocationConsts;
import org.androidtown.covid19center.Map.LocationInfo;

public class RecordItem {

    private String clinicName; // 진료소 명
    private String clinicDate; // 예약 시간
    private String name; // 예약자 명

    public RecordItem(String clinicName, String clinicDate, String name){
        this.clinicDate = clinicDate;
        this.clinicName = clinicName;
        this.name = name;
    }

    public String getClinicName() {
        return clinicName;
    }

    public void setClinicName(String clinicName) {
        this.clinicName = clinicName;
    }

    public String getClinicDate() {
        return clinicDate;
    }

    public void setClinicDate(String clinicDate) {
        this.clinicDate = clinicDate;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
