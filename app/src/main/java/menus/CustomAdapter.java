package menus;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.pacheco.saul.racersofcloud.MainActivity;
import com.pacheco.saul.racersofcloud.R;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;

import carreras.ModuloCarreras;
import cz.msebera.android.httpclient.NameValuePair;
import cz.msebera.android.httpclient.message.BasicNameValuePair;
import online.CrearPilotosAsyncTask;
import utils.BDUtils;
import utils.ImgUtils;

public class CustomAdapter extends BaseAdapter {
    List<String> listaImagenesTexto;
    Context context;

    private static LayoutInflater inflater = null;

    public CustomAdapter(Context context, List<String> imgenesTexto) {
        listaImagenesTexto = imgenesTexto;
        this.context = context;
        inflater = (LayoutInflater) context.
                getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public int getCount() {
        return listaImagenesTexto.size();
    }

    @Override
    public Object getItem(int position) {
        return position;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    public class Holder {
        TextView tv;
        ImageView img;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        String[] auxImgTx;
        final Holder holder = new Holder();
        View rowView;
        rowView = inflater.inflate(R.layout.custom_listview_opciones, null);
        holder.tv = (TextView) rowView.findViewById(R.id.tvCLVOpciones1);
        holder.img = (ImageView) rowView.findViewById(R.id.imgCLVOpciones);
        auxImgTx = listaImagenesTexto.get(position).split(";");
        holder.tv.setText(auxImgTx[1]);
        Glide.with(context).load(Integer.parseInt(auxImgTx[0])).asBitmap().into(holder.img);

        rowView.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                String[] auxImgTx;
                auxImgTx = listaImagenesTexto.get(position).split(";");
                v.setBackgroundColor(ImgUtils.colorPulsado);
                switch (position) {
                    case 0:
                        iniciarCarrera(1);
                        break;
                    case 1:
                        iniciarCarrera(2);
                        break;
                    case 2:
                        iniciarCarrera(3);
                        break;
                    case 3:
                        if (BDUtils.executeCommand(context)) {
                            iniciarCarrera(4);
                        } else {
                            Toast.makeText(context, "Error de conexión", Toast.LENGTH_SHORT).show();
                            v.setBackgroundColor(ImgUtils.colorDesPulsado);
                        }
                        break;
                    case 4:
                        if (BDUtils.existeClan()) {
                            if (BDUtils.executeCommand(context)) {
                                iniciarCarrera(5);
                            } else {
                                Toast.makeText(context, "Error de conexión", Toast.LENGTH_SHORT).show();
                                v.setBackgroundColor(ImgUtils.colorDesPulsado);
                            }
                        } else {
                            Toast.makeText(context, "No tienes clan", Toast.LENGTH_SHORT).show();
                            v.setBackgroundColor(ImgUtils.colorDesPulsado);
                        }
                        break;
                    default:
                        Toast.makeText(context, "Has seleccionado " + auxImgTx[1] + " - " + position + "  -  " + BDUtils.getIdentificadorPiloto(), Toast.LENGTH_LONG).show();
                }
            }
        });
        return rowView;
    }

    private void iniciarCarrera(int dificultad) {
        Intent i = new Intent(context, ModuloCarreras.class);
        i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        i.putExtra("dificultad", dificultad);
        context.startActivity(i);
        ((Activity) context).overridePendingTransition(R.anim.fadein, R.anim.fadeout);
    }
} 