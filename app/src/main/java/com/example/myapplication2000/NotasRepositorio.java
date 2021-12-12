package com.example.myapplication2000;

import android.app.Application;

import androidx.lifecycle.LiveData;

import java.util.List;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

public class NotasRepositorio {

    ElementosBaseDeDatos.NotasDao notasDao;
    Executor executor = Executors.newSingleThreadExecutor();

    NotasRepositorio(Application application){
        notasDao = ElementosBaseDeDatos.obtenerInstancia(application).obtenerNotasDao();
    }



    /*
          NO L'UTILITZO PERO EL POSARIA COM INICI O QUE ES FES UNA BUSQUEDA AUTOMATICA AMB VALOR EMPTY PER A QUE ES VEGIN LES NOTES NOMÉS COMENÇAR

            LiveData<List<Nota>> obtener(){
                return notasDao.obtener();
            }*/

   /* LiveData<List<com.example.myapplication2000.Nota>> masValorados() {
        return notasDao.masValorados();
    }*/

    LiveData<List<Nota>> buscar(String d) {
        return notasDao.buscar(d);
    }


    void insertar(Nota nota){
        executor.execute(new Runnable() {
            @Override
            public void run() {
                notasDao.insertar(nota);
            }
        });
    }

    void eliminar(com.example.myapplication2000.Nota nota) {
        executor.execute(new Runnable() {
            @Override
            public void run() {
                notasDao.eliminar(nota);
            }
        });
    }

    public void actualizar(com.example.myapplication2000.Nota nota , float valoracion) {
        executor.execute(new Runnable() {
            @Override
            public void run() {
                nota.valoracion = valoracion;
                notasDao.actualizar(nota);
            }
        });
    }
}