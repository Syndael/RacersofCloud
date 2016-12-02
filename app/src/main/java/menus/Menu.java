package menus;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;

import activity.CarreraActivity;
import activity.ConfActivity;
import activity.TallerActivity;
import utils.ImgUtils;

import com.pacheco.saul.racersofcloud.MainActivity;
import com.pacheco.saul.racersofcloud.R;

public class Menu extends Fragment {
    ImageView imgInicio, imgCarrera, imgTaller, imgConf;
    LinearLayout layInicio, layCarrera, layTaller, layConf;
    int colorPulsado = ImgUtils.colorPulsado;

    public Menu() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_menu, container, false);
    }

    @Override
    public void onActivityCreated(Bundle state) {
        super.onActivityCreated(state);

        imgInicio = (ImageView) getView().findViewById(R.id.imgInicio);
        imgCarrera = (ImageView) getView().findViewById(R.id.imgCarreraIntent);
        imgTaller = (ImageView) getView().findViewById(R.id.imgTaller);
        imgConf = (ImageView) getView().findViewById(R.id.imgConf);

        imgInicio.setImageBitmap(ImgUtils.decodeSampledBitmapFromResource(getResources(), R.drawable.ic_home, 100, 100));
        imgCarrera.setImageBitmap(ImgUtils.decodeSampledBitmapFromResource(getResources(), R.drawable.ic_bandera_menu, 100, 100));
        imgTaller.setImageBitmap(ImgUtils.decodeSampledBitmapFromResource(getResources(), R.drawable.ic_llaveinglesa, 100, 100));
        imgConf.setImageBitmap(ImgUtils.decodeSampledBitmapFromResource(getResources(), R.drawable.ic_settings, 100, 100));

        layInicio = (LinearLayout) getView().findViewById(R.id.layInicio);
        layCarrera = (LinearLayout) getView().findViewById(R.id.layCarrera);
        layTaller = (LinearLayout) getView().findViewById(R.id.layTaller);
        layConf = (LinearLayout) getView().findViewById(R.id.layConf);

        cmbColor();

        imgInicio.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                layInicio.setBackgroundColor(colorPulsado);
                cambiarMenu(MainActivity.class, 1);
            }
        });
        imgCarrera.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                layCarrera.setBackgroundColor(colorPulsado);
                cambiarMenu(CarreraActivity.class, 2);
            }
        });
        imgTaller.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                layTaller.setBackgroundColor(colorPulsado);
                cambiarMenu(TallerActivity.class, 3);
            }
        });
        imgConf.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                layConf.setBackgroundColor(colorPulsado);
                cambiarMenu(ConfActivity.class, 4);
            }
        });
    }

    private void cambiarMenu(Class clase, int x) {
        if (x != ImgUtils.getMenuAct() || x == 3) {
            Intent i = new Intent(getContext(), clase);
            i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(i);
            ((Activity) getContext()).overridePendingTransition(R.anim.fadein, R.anim.fadeout);
            ImgUtils.setMenuAct(x);
        }
    }

    private void cmbColor() {
        int colorSel = ImgUtils.colorSeleccionado;
        int colorNoSel = ImgUtils.colorBase;
        layInicio.setBackgroundColor(colorNoSel);
        layCarrera.setBackgroundColor(colorNoSel);
        layTaller.setBackgroundColor(colorNoSel);
        layConf.setBackgroundColor(colorNoSel);

        switch (ImgUtils.getMenuAct()) {
            case 1:
                layInicio.setBackgroundColor(colorSel);
                break;
            case 2:
                layCarrera.setBackgroundColor(colorSel);
                break;
            case 3:
                layTaller.setBackgroundColor(colorSel);
                break;
            case 4:
                layConf.setBackgroundColor(colorSel);
        }
    }
}
