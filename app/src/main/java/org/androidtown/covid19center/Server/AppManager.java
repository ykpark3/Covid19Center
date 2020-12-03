package org.androidtown.covid19center.Server;


import java.util.ArrayList;

public class AppManager {
    private static AppManager instance;


    private String userId;
    private ArrayList<ReservationVO> reservationVOArrayList = new ArrayList<>();
    private  ArrayList<QuestionnaireVO> questionnaireVOArrayList = new ArrayList<>();


    public static AppManager getInstance() {
        if(instance == null) {
            instance = new AppManager();
        }
        return instance;
    }



    public String getUserId() { return userId; }
    public void setUserId(String userId) {
        this.userId = userId;
    }

    public ArrayList<ReservationVO> getReservationVOArrayList() {
        return reservationVOArrayList;
    }
    public void setReservationVOArrayList(ArrayList<ReservationVO> reservationVOArrayList) {
        this.reservationVOArrayList = reservationVOArrayList;
    }

    public ArrayList<QuestionnaireVO> getQuestionnaireVOArrayList() {return questionnaireVOArrayList; }
    public void setQuestionnaireVOArrayList(ArrayList<QuestionnaireVO> questionnaireVOArrayList) {
        this.questionnaireVOArrayList = questionnaireVOArrayList;
    }


}
