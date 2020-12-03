package org.androidtown.covid19center.DataBase;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

import static androidx.room.OnConflictStrategy.REPLACE;

@Dao
public interface ClinicDao {

    @Query("SELECT * FROM clinic")
    LiveData<List<Clinic>> getAll();

    @Insert(onConflict = REPLACE)
    void insert(Clinic clinic);

    @Update
    void update(Clinic clinic);

    @Delete
    void delete(Clinic clinic);

    //Delete all query
    @Delete
    void reset(List<Clinic> clinic);

    //Update query
    @Query("UPDATE clinic SET clinic_name = :sText WHERE cid = :sID")
    void update(int sID, String sText);

}