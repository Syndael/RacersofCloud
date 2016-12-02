package com.pacheco.saul.racersofcloud;

import android.content.Context;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import utils.BDUtils;

public class MainActivity extends AppCompatActivity {
    private static SQLiteDatabase dbAux;
    SQLiteDatabase db;

    public static SQLiteDatabase getDb() {
        return dbAux;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        db = openOrCreateDatabase("RoC.db", Context.MODE_PRIVATE, null);
        dbAux = db;
        db.execSQL(BDUtils.tablaPiloto());
        db.execSQL(BDUtils.tablaHabilidadesPiloto());
        db.execSQL(BDUtils.tablaImagenPiloto());
        db.execSQL(BDUtils.tablaCoche());
        db.execSQL(BDUtils.tablaPrecios());
        db.execSQL(BDUtils.tablaCarreras());
        db.execSQL(BDUtils.tablaPiezas());
        db.execSQL(BDUtils.tablaClan());
        if (!BDUtils.hayRegistros()) {
            BDUtils.crearPiloto(db, getApplicationContext());
            BDUtils.crearCircuitos(db);
            Intent i = new Intent(getApplicationContext(), ConfInicio.class);
            i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(i);
        } else {
            BDUtils.crearCircuitos(db);
        }
    }
}
