package com.example.finalandroid.DataBase;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.example.finalandroid.Entities.Movimiento;

import java.util.List;

@Dao
public interface IMovimientoDao {

    @Insert
    void insertAll(List<Movimiento> m);

    @Query("SELECT * FROM Movimiento")
    List<Movimiento> getAll();

    @Query("SELECT * FROM Movimiento WHERE idMovimiento = :id")
    Movimiento getMovimiento(int id);

    @Query("SELECT * FROM Movimiento WHERE idCuenta = :idCuenta")
    List<Movimiento> getMovimientoCuenta(int idCuenta);

    @Insert
    void insert(Movimiento m);

    @Update
    void update(Movimiento m);

    @Delete
    void delete(Movimiento m);
}
