package org.androidtown.covid19center.Server;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;


import retrofit2.http.PUT;
import retrofit2.http.Path;

import retrofit2.http.Query;
import retrofit2.http.QueryMap;

public interface ServiceApi {

    @GET("/users")
    Call<List<UsersVO>> getUserData(@Query("id") String id, @Query("password") String password);

    @GET("/reservation")
    Call<List<ReservationVO>> getReservationVO();

    @GET("/questionnaire")
    Call<List<QuestionnaireVO>> getQuesionnaireVO();


    @POST("/reservationInsert")
    Call<ResponseBody> sendReservationData(@Body ReservationData reservationData);

    @POST("/questionnaire")
    Call<ResponseBody> sendQuestionnaireData(@Body QuestionnaireData questionnaireData);

    @PUT("/questionnaire/{sequence}")
    Call<ResponseBody> modifyQuestionnaireData(@Path ("sequence") int sequence,
                                               @Body QuestionnaireData questionnaireModifiedData);

}
