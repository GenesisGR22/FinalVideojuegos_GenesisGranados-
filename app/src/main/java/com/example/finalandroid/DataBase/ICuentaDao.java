package com.example.finalandroid.DataBase;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.example.finalandroid.Entities.Cuenta;

import java.util.List;

@Dao
public interface ICuentaDao {

    @Insert
    void insertAll(List<Cuenta> c);

    @Query("SELECT * FROM Cuenta")
    List<Cuenta> getAll();

    @Query("SELECT * FROM Cuenta WHERE idCuenta = :id")
    Cuenta getCuenta(int id);

    @Insert
    void insert(Cuenta c);

    @Update
    void update(Cuenta c);

    @Delete
    void delete(Cuenta c);
}
