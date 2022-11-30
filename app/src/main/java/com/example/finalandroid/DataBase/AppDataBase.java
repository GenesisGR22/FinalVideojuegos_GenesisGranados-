package com.example.finalandroid.DataBase;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import com.example.finalandroid.Entities.Cuenta;
import com.example.finalandroid.Entities.Movimiento;

@Database(entities = {Cuenta.class, Movimiento.class}, version = 1)
public abstract class AppDataBase extends RoomDatabase {

    public abstract ICuentaDao iCuentaDao();
    public abstract IMovimientoDao iMovimientoDao();

    public static AppDataBase getInstance(Context context){
        return Room.databaseBuilder(context,AppDataBase.class,"Examen")
                .allowMainThreadQueries()
                .build();
    }
}
