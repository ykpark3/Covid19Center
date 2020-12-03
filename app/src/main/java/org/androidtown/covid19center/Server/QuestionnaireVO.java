package org.androidtown.covid19center.Server;

public class QuestionnaireVO {

    int sequence;

    String user_id;
    int visited;

    String visited_detail, entrance_date;
    int contact;
    String contact_relationship, contact_period;

    int fever, muscle_ache, cough, sputum, runny_nose, dyspnea, sore_throat;

    String symptom_start_date, toDoctor;

    public QuestionnaireVO
            (int sequence,
             String user_id,
             int visited,
             String visited_detail,
             String entrance_date,
             int contact,
             String contact_relationship,
             String contact_period,

             int fever,int muscle_ache, int cough, int sputum, int runny_nose, int dyspnea, int sore_throat,

             String symptom_start_date,
             String toDoctor) {

        this.sequence = sequence;
        this.user_id = user_id;
        this.visited = visited;
        this.visited_detail = visited_detail;
        this.entrance_date = entrance_date;
        this.contact = contact;
        this.contact_relationship = contact_relationship;
        this.contact_period = contact_period;
        this.fever = fever;
        this.cough = cough;
        this.muscle_ache = muscle_ache;
        this.cough = cough;
        this.sputum = sputum;
        this.runny_nose = runny_nose;
        this.dyspnea = dyspnea;
        this.sore_throat = sore_throat;
        this.symptom_start_date = symptom_start_date;
        this.toDoctor = toDoctor;

    }

    /*
    public QuestionnaireVO
            (String user_id,
             int visited,
             String visited_detail,
             String entrance_date,
             int contact,
             String contact_relationship,
             String contact_period,
             int fever,int muscle_ache, int cough, int sputum, int runny_nose, int dyspnea, int sore_throat,
             String symptom_start_date,
             String toDoctor) {

        this.user_id = user_id;
        this.visited = visited;
        this.visited_detail = visited_detail;
        this.entrance_date = entrance_date;
        this.contact = contact;
        this.contact_relationship = contact_relationship;
        this.contact_period = contact_period;
        this.fever = fever;
        this.muscle_ache = muscle_ache;
        this.cough = cough;
        this.sputum = sputum;
        this.runny_nose = runny_nose;
        this.dyspnea = dyspnea;
        this.sore_throat = sore_throat;
        this.symptom_start_date = symptom_start_date;
        this.toDoctor = toDoctor;
    }

     */



    public int getSequence() { return sequence;}
    public void setSequence(int sequence) {this.sequence = sequence; }


    public String getUser_id() {
        return user_id;
    }
    public void setUser_id(String id) {
        this.user_id = user_id;
    }


    public int getVisited() {
        return visited;
    }
    public void setVisited(int visited) {
        this.visited = visited;
    }

    public String getVisited_detail() {
        return visited_detail;
    }
    public void setVisited_detail(String visited_detail) {
        this.visited_detail = visited_detail;
    }

    public String getEntrance_date() {return entrance_date;}
    public void setEntrance_date(String entrance_date) {
        this.entrance_date = entrance_date;
    }

    public int getContact() {
        return contact;
    }
    public void setContact(int contact) {
        this.contact = contact;
    }

    public String getContact_relationship() {
        return contact_relationship;
    }
    public void setContact_relationship(String contact_relationship) {
        this.contact_relationship = contact_relationship;
    }

    public String getContact_period() {
        return contact_period;
    }
    public void setContact_period(String contact_period) {
        this.contact_period = contact_period;
    }

    public int getFever() {
        return fever;
    }

    public void setFever(int fever) {
        this.fever = fever;
    }

    public int getMuscle_ache() {
        return muscle_ache;
    }

    public void setMuscle_ache(int muscle_ache) {
        this.muscle_ache = muscle_ache;
    }

    public int getCough() {
        return cough;
    }

    public void setCough(int cough) {
        this.cough = cough;
    }


    public int getSputum() {
        return sputum;
    }
    public void setSputum(int sputum) {
        this.sputum = sputum;
    }

    public int getRunny_nose() {
        return runny_nose;
    }
    public void setRunny_nose(int runny_nose) {
        this.runny_nose = runny_nose;
    }

    public int getDyspnea() {
        return dyspnea;
    }
    public void setDyspnea(int dyspnea) {
        this.dyspnea = dyspnea;
    }

    public int getSore_throat() {
        return sore_throat;
    }
    public void setSore_throat(int sore_throat) {
        this.sore_throat = sore_throat;
    }

    public String getSymptom_start_date() {
        return symptom_start_date;
    }
    public void setSymptom_start_date(String symptom_start_date) {
        this.symptom_start_date = symptom_start_date;
    }

    public String getToDoctor() {return toDoctor;}
    public void setToDoctor(String toDoctor) {this.toDoctor = toDoctor;}
}
