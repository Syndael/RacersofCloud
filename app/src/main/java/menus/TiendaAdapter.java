package menus;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;
import android.widget.Toast;

import com.pacheco.saul.racersofcloud.R;

import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import activity.TallerActivity;
import activity.TallerCocheActivity;
import utils.BDUtils;
import utils.ImgUtils;
import utils.TallerUtils;

public class TiendaAdapter extends BaseAdapter {
    List<String> listaPiezas;
    Context context;
    View vAux;

    private static LayoutInflater inflater = null;

    public TiendaAdapter(Context context, List<String> imgenesTexto) {
        listaPiezas = imgenesTexto;
        this.context = context;
        inflater = (LayoutInflater) context.
                getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public int getCount() {
        return listaPiezas.size();
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
        TextView tv, tvHab, tvPr;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        final String[] auxStr;
        final Holder holder = new Holder();
        View rowView;
        rowView = inflater.inflate(R.layout.custom_listview_piezas, null);
        holder.tv = (TextView) rowView.findViewById(R.id.tvCLVOpciones1);
        holder.tvHab = (TextView) rowView.findViewById(R.id.tvCLVOpcionesH);
        holder.tvPr = (TextView) rowView.findViewById(R.id.tvCLVOpcionesPr);
        auxStr = listaPiezas.get(position).split(":");
        holder.tv.setText(auxStr[0] + " " + auxStr[1] + " ");
        holder.tvHab.setText("Mejora: " + auxStr[2] + "c " + auxStr[3] + "f " + auxStr[4] + "v");

        DecimalFormat formateador = (DecimalFormat) NumberFormat.getInstance(Locale.GERMAN);
        holder.tvPr.setText("Coste: " + formateador.format(Integer.parseInt(auxStr[5])));
        rowView.setOnClickListener(new View.OnClickListener() {
                                       @Override
                                       public void onClick(View v) {
                                           vAux = v;
                                           v.setBackgroundColor(ImgUtils.colorPulsado);
                                           if (Integer.parseInt(listaPiezas.get(position).split(":")[5]) <= BDUtils.getTuercas()) {

                                               DecimalFormat formateador = (DecimalFormat) NumberFormat.getInstance(Locale.GERMAN);
                                               String auxPrecio = formateador.format(Integer.parseInt(listaPiezas.get(position).split(":")[5]));
                                               final String msg = "Comprar " + listaPiezas.get(position).split(":")[1] + " por " + auxPrecio + "t.";
                                               new AlertDialog.Builder(context)
                                                       .setTitle("Comprar " + listaPiezas.get(position).split(":")[0])
                                                       .setMessage(msg)
                                                       .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                                                           public void onClick(DialogInterface dialog, int which) {
                                                               String concatenado = listaPiezas.get(position).split(":")[1] + ":" + listaPiezas.get(position).split(":")[2] + ":" + listaPiezas.get(position).split(":")[3] + ":" + listaPiezas.get(position).split(":")[4];
                                                               switch (listaPiezas.get(position).split(":")[0]) {
                                                                   case "Alerón":
                                                                       BDUtils.setAleron(concatenado, Integer.parseInt(listaPiezas.get(position).split(":")[5]));
                                                                       break;
                                                                   case "Frenos":
                                                                       BDUtils.setFrenos(concatenado, Integer.parseInt(listaPiezas.get(position).split(":")[5]));
                                                                       break;
                                                                   case "Neumáticos":
                                                                       BDUtils.setNeumaticos(concatenado, Integer.parseInt(listaPiezas.get(position).split(":")[5]));
                                                                       break;
                                                                   case "Morro":
                                                                       BDUtils.setMorro(concatenado, Integer.parseInt(listaPiezas.get(position).split(":")[5]));
                                                                       break;
                                                                   case "Motor":
                                                                       BDUtils.setMotor(concatenado, Integer.parseInt(listaPiezas.get(position).split(":")[5]));
                                                                       break;
                                                                   default:
                                                                       BDUtils.setVolante(concatenado, Integer.parseInt(listaPiezas.get(position).split(":")[5]));
                                                               }
                                                               Intent i = new Intent(context, TallerCocheActivity.class);
                                                               i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                                                               context.startActivity(i);
                                                           }
                                                       })
                                                       .setNegativeButton(android.R.string.no, new DialogInterface.OnClickListener() {
                                                           public void onClick(DialogInterface dialog, int which) {
                                                               vAux.setBackgroundColor(ImgUtils.colorDesPulsado);
                                                           }
                                                       })
                                                       .setIcon(android.R.drawable.ic_dialog_alert)
                                                       .show();
                                           } else {
                                               Toast.makeText(context, "Tuercas insuficientes", Toast.LENGTH_LONG).show();
                                               v.setBackgroundColor(ImgUtils.colorDesPulsado);
                                           }
                                       }
                                   }

        );
        return rowView;
    }
} 