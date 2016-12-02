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

import java.util.ArrayList;
import java.util.List;

import menus.CustomAdapter;
import utils.ImgUtils;

public class CarreraFragment extends Fragment {
    ListView lv_opciones;
    ImageView img_frgCarreraFondoCircuito;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_carrera, container, false);
    }

    @Override
    public void onActivityCreated(Bundle state) {
        super.onActivityCreated(state);
        new ImgUtils().fondo(getView().getContext(), R.drawable.cuadros, (RelativeLayout) getView().findViewById(R.id.rl_frgCarrera));
        lv_opciones = (ListView) getView().findViewById(R.id.lv_opciones);
        try {
            List<String> opciones = new ArrayList<>();
            opciones.add(R.drawable.verde + ";GP Fácil");
            opciones.add(R.drawable.ambar + ";GP Medio");
            opciones.add(R.drawable.rojo + ";GP Difícil");
            opciones.add(R.drawable.online + ";Online");
            opciones.add(R.drawable.clanonline + ";Clan");
            lv_opciones.setAdapter(new CustomAdapter(getContext(), opciones));
        } catch (IllegalStateException e) {
        }
        img_frgCarreraFondoCircuito = (ImageView) getView().findViewById(R.id.img_frgCarreraFondoCircuito);
        img_frgCarreraFondoCircuito.setImageBitmap(ImgUtils.decodeSampledBitmapFromResource(getResources(), R.drawable.race, 250, 250));
    }
}
