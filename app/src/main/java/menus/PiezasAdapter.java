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
import com.pacheco.saul.racersofcloud.R;

import java.util.List;

import carreras.ModuloCarreras;
import utils.BDUtils;

public class PiezasAdapter extends BaseAdapter {
    List<String> listaPiezas;
    Context context;

    private static LayoutInflater inflater = null;

    public PiezasAdapter(Context context, List<String> imgenesTexto) {
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
        String[] auxStr;
        final Holder holder = new Holder();
        View rowView;
        rowView = inflater.inflate(R.layout.custom_listview_piezas, null);
        holder.tv = (TextView) rowView.findViewById(R.id.tvCLVOpciones1);
        holder.tvHab = (TextView) rowView.findViewById(R.id.tvCLVOpcionesH);
        holder.tvPr = (TextView) rowView.findViewById(R.id.tvCLVOpcionesPr);
        auxStr = listaPiezas.get(position).split(":");
        holder.tv.setText(auxStr[0]);
        holder.tvHab.setText("Mejoras: " + auxStr[2] + "c " + auxStr[3] + "f " + auxStr[4] + "v");
        holder.tvPr.setText(auxStr[1]);
        return rowView;
    }
} 