package com.example.myapplication2000;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import java.util.List;

public class TasquesViewModel extends AndroidViewModel {


    TasquesRepositorio tasquesRepositorio;

    public TasquesViewModel(@NonNull Application application) {
        super(application);
        tasquesRepositorio = new TasquesRepositorio(application);
    }

    LiveData<List<Tasca>> obtener(){
        return tasquesRepositorio.obtener();
    }

    void insertar(Tasca tasca){
        tasquesRepositorio.insertar(tasca);
    }

    void eliminar(Tasca tasca){
        tasquesRepositorio.eliminar(tasca);
    }

    void actualizar(Tasca tasca, boolean b){
        tasca.check=b;
        tasquesRepositorio.actualizar(tasca);
    }



}
