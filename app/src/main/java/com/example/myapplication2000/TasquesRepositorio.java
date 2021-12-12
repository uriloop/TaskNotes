package com.example.myapplication2000;

import android.app.Application;

import androidx.lifecycle.LiveData;

import java.util.List;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

public class TasquesRepositorio {



    ElementosBaseDeDatos.NotasDao tasquesDao;
    Executor executor= Executors.newSingleThreadExecutor();

    TasquesRepositorio(Application application){
        tasquesDao = ElementosBaseDeDatos.obtenerInstancia(application).obtenerNotasDao();

    }


    LiveData<List<Tasca>> obtener(){

        return tasquesDao.obtenerT();
    }

    void insertar(Tasca tasca){
        if (tasca.tasca.length()!=0) {

            executor.execute(new Runnable() {
                @Override
                public void run() {
                    tasquesDao.insertarT(tasca);
                }
            });
        } }

    void eliminar(Tasca tasca) {
        executor.execute(new Runnable() {
            @Override
            public void run() {
                tasquesDao.eliminarT(tasca);
            }
        });
    }

    public void actualizar(Tasca tasca) {


        executor.execute(new Runnable() {

            @Override
            public void run() {

                tasquesDao.actualizarT(tasca);

            }
        });
    }

}
