package centros;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.pacheco.saul.racersofcloud.R;

import java.util.ArrayList;
import java.util.List;

import menus.PiezasAdapter;
import menus.TiendaAdapter;
import utils.BDUtils;
import utils.ImgUtils;

public class TallerCocheFragment extends Fragment {
    ImageView coche;
    ListView lv_piezas;
    Button tienda;
    TextView tvNombreCoche, titTallerCoche;
    boolean tiTaller;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_taller_coche, container, false);
    }

    @Override
    public void onActivityCreated(Bundle state) {
        super.onActivityCreated(state);
        new ImgUtils().fondo(getView().getContext(), R.drawable.cuadros, (RelativeLayout) getView().findViewById(R.id.rl_fragment_taller_coche));

        tiTaller = false;
        tvNombreCoche = (TextView) getView().findViewById(R.id.tvNombreCoche);
        tvNombreCoche.setText(BDUtils.getNombreCoche());
        titTallerCoche = (TextView) getView().findViewById(R.id.titTallerCoche);
        coche = (ImageView) getView().findViewById(R.id.imgRLTC);
        coche.setImageBitmap(ImgUtils.decodeSampledBitmapFromResource(getResources(), R.drawable.coche, 100, 100));
        lv_piezas = (ListView) getView().findViewById(R.id.lv_piezas);
        tienda = (Button) getView().findViewById(R.id.btLLTCTienda);
        refresh();
        tienda.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                BDUtils.crearPiezas();
                if (!tiTaller) {
                    tiTaller = true;
                } else {
                    tiTaller = false;
                }
                refresh();
            }
        });
    }

    private void refresh() {
        if (tiTaller) {
            titTallerCoche.setText("Tienda");
            tienda.setText("Volver");
            List<String> opciones = BDUtils.piezasDisponibles();
            lv_piezas.setAdapter(new TiendaAdapter(getView().getContext(), opciones));
        } else {
            titTallerCoche.setText("Piezas Montadas Actualmente");
            tienda.setText("Comprar Piezas");
            List<String> opciones = new ArrayList<>();
            opciones.add("Alerón Montado:" + BDUtils.getAleron());
            opciones.add("Frenos Montados:" + BDUtils.getFrenos());
            opciones.add("Neumáticos Montados:" + BDUtils.getNeumaticos());
            opciones.add("Morro Montado:" + BDUtils.getMorro());
            opciones.add("Motor Montado:" + BDUtils.getMotor());
            opciones.add("Volante Montado:" + BDUtils.getVolante());
            lv_piezas.setAdapter(new PiezasAdapter(getView().getContext(), opciones));
        }
    }
}
