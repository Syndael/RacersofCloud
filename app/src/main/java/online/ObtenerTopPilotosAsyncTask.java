package online;

import android.os.AsyncTask;
import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;

import cz.msebera.android.httpclient.HttpResponse;
import cz.msebera.android.httpclient.NameValuePair;
import cz.msebera.android.httpclient.client.HttpClient;
import cz.msebera.android.httpclient.client.entity.UrlEncodedFormEntity;
import cz.msebera.android.httpclient.client.methods.HttpGet;
import cz.msebera.android.httpclient.client.methods.HttpPost;
import cz.msebera.android.httpclient.impl.client.HttpClientBuilder;
import cz.msebera.android.httpclient.util.EntityUtils;

/**
 * Created by Saul on 25/02/2016.
 */
public class ObtenerTopPilotosAsyncTask extends AsyncTask<List<NameValuePair>, Void, List<String>> {

    @Override
    protected List<String> doInBackground(List<NameValuePair>... params) {
        List<String> pilotos = null;
        HttpClient httpClient = HttpClientBuilder.create().build();
        HttpGet httpGet = new HttpGet(String.format("http://roc2.servegame.com/roc/api/toppilotos.php"));
        try {
            HttpResponse resp = httpClient.execute(httpGet);
            String respStr = EntityUtils.toString(resp.getEntity());
            Gson gson = new GsonBuilder().create();
            RoConline roconline = gson.fromJson(respStr, RoConline.class);
            pilotos = new ArrayList<>();
            for (Pilotos i : roconline.getPilotos()) {
                pilotos.add(i.getId() + ";" + i.getNombre() + ";" + i.getEquipo() + ";" + i.getTrofeos() + ";" + i.getCurva() + ";" + i.getFrenado() + ";" + i.getVelocidad() + ";" + i.getAleron() + ";" + i.getFrenos() + ";" + i.getNeumaticos() + ";" + i.getMorro() + ";" + i.getMotor() + ";" + i.getVolante() + ";" + i.getCodigoclan());
            }

        } catch (Exception ex) {
            Log.e("ServicioRest", "Error!", ex);
        }
        return pilotos;
    }
}