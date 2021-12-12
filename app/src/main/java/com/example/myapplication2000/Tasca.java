package com.example.myapplication2000;


import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity
public class Tasca {

    @PrimaryKey(autoGenerate = true)
    public int id;

    public String tasca;
    public boolean check;

    public Tasca(String tasca,boolean check) {

        this.tasca=tasca;
        this.check=check;
    }

}
