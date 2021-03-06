package online;

import android.os.AsyncTask;
import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.UnsupportedEncodingException;
import java.util.List;

import cz.msebera.android.httpclient.HttpResponse;
import cz.msebera.android.httpclient.NameValuePair;
import cz.msebera.android.httpclient.client.HttpClient;
import cz.msebera.android.httpclient.client.entity.UrlEncodedFormEntity;
import cz.msebera.android.httpclient.client.methods.HttpPost;
import cz.msebera.android.httpclient.impl.client.HttpClientBuilder;
import cz.msebera.android.httpclient.util.EntityUtils;

/**
 * Created by Saul on 03/03/2016.
 */
public class CrearPilotosAsyncTask extends AsyncTask<List<NameValuePair>, Void, String> {

    @Override
    protected String doInBackground(List<NameValuePair>... params) {
        String piloto = null;
        HttpClient httpClient = HttpClientBuilder.create().build();
        HttpPost httpPost = new HttpPost(String.format("http://roc2.servegame.com/roc/api/maspilotos.php"));
        try {
            httpPost.setEntity(new UrlEncodedFormEntity(params[0]));
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        try {
            HttpResponse resp = httpClient.execute(httpPost);
            String respStr = EntityUtils.toString(resp.getEntity());
            Gson gson = new GsonBuilder().create();
            RoConline rc = gson.fromJson(respStr, RoConline.class);
            piloto = rc.getPilotos().get(0).toString();
        } catch (Exception ex) {
            Log.e("ServicioRest", "Error!", ex);
        }
        return piloto;
    }
}
