package com.pacheco.saul.racersofcloud;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import utils.BDUtils;
import utils.ImgUtils;

public class Principal extends Fragment {
    ImageView imgEscudoPrincipal, img_cochePirncipal, img_pilotoPrincipal;
    TextView tv_consejos;

    public Principal() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_principal, container, false);
    }


    @Override
    public void onActivityCreated(Bundle state) {
        super.onActivityCreated(state);
        new ImgUtils().fondo(getView().getContext(), R.drawable.cuadros, (RelativeLayout) getView().findViewById(R.id.ll_principal));

        imgEscudoPrincipal = (ImageView) getView().findViewById(R.id.imgEscudoPrincipal);
        if (BDUtils.existeEscudo()) {
            imgEscudoPrincipal.setImageBitmap(ImgUtils.decodeScaledBitmapFromSdCard(BDUtils.getEscudoImagen(), 500, 500));
        } else {
            imgEscudoPrincipal.setImageBitmap(ImgUtils.decodeSampledBitmapFromResource(getResources(), R.drawable.escudo, 500, 500));
        }

        img_cochePirncipal = (ImageView) getView().findViewById(R.id.img_cochePirncipal);
        img_cochePirncipal.setImageBitmap(ImgUtils.decodeSampledBitmapFromResource(getResources(), R.drawable.coche, 100, 100));
        img_pilotoPrincipal = (ImageView) getView().findViewById(R.id.img_pilotoPrincipal);
        img_pilotoPrincipal.setImageBitmap(ImgUtils.decodeSampledBitmapFromResource(getResources(), R.drawable.piloto, 100, 100));

        tv_consejos = (TextView) getView().findViewById(R.id.tv_consejos);
        switch ((int) Math.round(Math.random() * 5)) {
            case 1:
                tv_consejos.setText(R.string.confIni1);
                break;
            case 2:
                tv_consejos.setText(R.string.confIni2);
                break;
            case 3:
                tv_consejos.setText(R.string.confIni3);
                break;
            case 4:
                tv_consejos.setText(R.string.confIni4);
                break;
            default:
                tv_consejos.setText(R.string.confIni5);
        }
    }
}
