package centros;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RelativeLayout;

import com.pacheco.saul.racersofcloud.R;

import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import menus.TallerAdapter;
import menus.TallerPilotoAdapter;
import menus.TiendaAdapter;
import utils.BDUtils;
import utils.ImgUtils;
import utils.TallerUtils;

public class TallerFragment extends Fragment {
    ListView lv_taller;
    ImageView img_pilotoTaller, img_cocheTaller;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_taller, container, false);
    }

    @Override
    public void onActivityCreated(Bundle state) {
        super.onActivityCreated(state);
        new ImgUtils().fondo(getView().getContext(), R.drawable.cuadros, (RelativeLayout) getView().findViewById(R.id.rl_frgTaller));

        img_pilotoTaller = (ImageView) getView().findViewById(R.id.img_pilotoTaller);
        img_pilotoTaller.setImageBitmap(ImgUtils.decodeSampledBitmapFromResource(getResources(), R.drawable.pilotomitad, 100, 100));
        img_cocheTaller = (ImageView) getView().findViewById(R.id.img_cocheTaller);
        img_cocheTaller.setImageBitmap(ImgUtils.decodeSampledBitmapFromResource(getResources(), R.drawable.coche, 100, 100));

        lv_taller = (ListView) getView().findViewById(R.id.lv_taller);
        try {
            List<String> opciones = new ArrayList<>();
            if (TallerUtils.isMejoraPiloto()) {
                String[] habs = BDUtils.getHabilidades().split(";");
                DecimalFormat formateador = (DecimalFormat) NumberFormat.getInstance(Locale.GERMAN);
                if (!habs[0].equals("40")) {
                    opciones.add(R.drawable.volante + ";Curva: " + habs[0] + " - " + formateador.format(1 + BDUtils.getCosteCurva()) + "t");
                } else {
                    opciones.add(R.drawable.volante + ";Curva: " + habs[0] + " - MAX");
                }
                if (!habs[1].equals("40")) {
                    opciones.add(R.drawable.frenos + ";Frenado: " + habs[1] + " - " + formateador.format(1 + BDUtils.getCosteFrenado()) + "t");
                } else {
                    opciones.add(R.drawable.frenos + ";Frenado: " + habs[1] + " - MAX");
                }
                if (!habs[2].equals("40")) {
                    opciones.add(R.drawable.velocidad + ";Velocidad: " + habs[2] + " - " + formateador.format(1 + BDUtils.getCosteVelocidad()) + "t");
                } else {
                    opciones.add(R.drawable.velocidad + ";Velocidad: " + habs[2] + " - MAX");
                }
                lv_taller.setAdapter(new TallerPilotoAdapter(getContext(), opciones));
            } else {
                opciones.add(R.drawable.pilotomitad + ";Mejorar Piloto");
                opciones.add(R.drawable.coche + ";Taller de Coches | Tienda");
                lv_taller.setAdapter(new TallerAdapter(getContext(), opciones, lv_taller));
            }
            TallerUtils.setMejorapiloto(false);
        } catch (IllegalStateException e) {
        }
    }
}
