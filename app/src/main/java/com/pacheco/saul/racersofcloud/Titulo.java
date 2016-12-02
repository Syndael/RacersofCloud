package com.pacheco.saul.racersofcloud;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.Locale;

import activity.TopPilotosActivity;
import utils.BDUtils;
import utils.ImgUtils;


public class Titulo extends Fragment {

    TextView tv_piloto, tv_tuercas, tv_trofeos, tv_equipo;
    ImageView img_escudoTitulo, img_trofeosTitulo, img_tuercasTitulo;

    public Titulo() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_titulo, container, false);
        return v;
    }


    @Override
    public void onActivityCreated(Bundle state) {
        super.onActivityCreated(state);
        tv_piloto = (TextView) getView().findViewById(R.id.tv_piloto);
        tv_equipo = (TextView) getView().findViewById(R.id.tv_equipo);
        tv_trofeos = (TextView) getView().findViewById(R.id.tv_trofeos);
        tv_tuercas = (TextView) getView().findViewById(R.id.tv_tuercas);

        img_escudoTitulo = (ImageView) getView().findViewById(R.id.img_escudoTitulo);
        if (BDUtils.existeEscudo()) {
            img_escudoTitulo.setImageBitmap(ImgUtils.decodeScaledBitmapFromSdCard(BDUtils.getEscudoImagen(), 50, 50));
        } else {
            img_escudoTitulo.setImageBitmap(ImgUtils.decodeSampledBitmapFromResource(getResources(), R.drawable.escudo, 25, 25));
        }
        img_trofeosTitulo = (ImageView) getView().findViewById(R.id.img_trofeosTitulo);
        img_trofeosTitulo.setImageBitmap(ImgUtils.decodeSampledBitmapFromResource(getResources(), R.drawable.ic_trofeo, 100, 100));
        img_tuercasTitulo = (ImageView) getView().findViewById(R.id.img_tuercasTitulo);
        img_tuercasTitulo.setImageBitmap(ImgUtils.decodeSampledBitmapFromResource(getResources(), R.drawable.ic_tuerca, 100, 100));

        tv_piloto.setText(BDUtils.getNombrePiloto());
        tv_equipo.setText(BDUtils.getNombreEquipo());
        DecimalFormat formateador = (DecimalFormat) NumberFormat.getInstance(Locale.GERMAN);
        tv_trofeos.setText(formateador.format(BDUtils.getTrofeos()));
        tv_tuercas.setText(formateador.format(BDUtils.getTuercas()));

        img_trofeosTitulo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                v.setBackgroundColor(ImgUtils.colorPulsado);
                if (BDUtils.executeCommand(getContext())) {
                    Intent i = new Intent(getContext(), TopPilotosActivity.class);
                    i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    ImgUtils.setMenuAct(5);
                    startActivity(i);
                } else {
                    Toast.makeText(getContext(), "Error de conexión", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}
