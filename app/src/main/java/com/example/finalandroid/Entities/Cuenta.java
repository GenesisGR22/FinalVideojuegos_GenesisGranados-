package com.example.finalandroid.Entities;

import java.util.List;

public class Cuenta {
    //@PrimaryKey(autoGenerate = true)
    private int idCuenta;
    private String nombre;
    private List<Movimiento> movimientos;

    public int getIdCuenta() {
        return idCuenta;
    }

    public void setIdCuenta(int idCuenta) {
        this.idCuenta = idCuenta;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public List<Movimiento> getMovimientos() {
        return movimientos;
    }

    public void setMovimientos(List<Movimiento> movimientos) {
        this.movimientos = movimientos;
    }
}
