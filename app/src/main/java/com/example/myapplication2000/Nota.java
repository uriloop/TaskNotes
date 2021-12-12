package com.example.myapplication2000;


import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity
public class Nota {
    @PrimaryKey(autoGenerate = true)
    public
    int id;

    public String titol;
    public String text;
    public float valoracion;

    public Nota(String titol, String text) {
        this.titol = titol;
        this.text = text;
    }
}
