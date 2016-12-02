package centros;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.RelativeLayout;

import com.pacheco.saul.racersofcloud.R;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;

import menus.TopPilotosAdapter;
import online.ObtenerTopPilotosAsyncTask;
import utils.ImgUtils;

public class TopPilotosFragment extends Fragment {
    ListView lv_opciones;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_toppilotos, container, false);
    }

    @Override
    public void onActivityCreated(Bundle state) {
        super.onActivityCreated(state);
        new ImgUtils().fondo(getView().getContext(), R.drawable.cuadros, (RelativeLayout) getView().findViewById(R.id.rl_frgCarrera));
        lv_opciones = (ListView) getView().findViewById(R.id.lv_opciones);
        try {
            List<String> pilotosOnline = null;
            List<String> aux = new ArrayList<>();
            try {
                int contador = 1;
                pilotosOnline = new ObtenerTopPilotosAsyncTask().execute().get();
                for (String pilotoAux : pilotosOnline) {
                    String[] pilotoAuxStr = pilotoAux.split(";");
                    aux.add(contador + ";" + pilotoAuxStr[1] + ";0;" + pilotoAuxStr[4] + ";" + pilotoAuxStr[5] + ";" + pilotoAuxStr[6] + ";" + pilotoAuxStr[2]+ ";" + pilotoAuxStr[3]);
                    contador++;
                }
                lv_opciones.setAdapter(new TopPilotosAdapter(getContext(), aux));
            } catch (InterruptedException e) {
                e.printStackTrace();
            } catch (ExecutionException e) {
                e.printStackTrace();
            }
        } catch (IllegalStateException e) {
        }
    }
}
