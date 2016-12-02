package menus;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AnimationUtils;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.pacheco.saul.racersofcloud.R;

import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.List;
import java.util.Locale;

import utils.ImgUtils;

public class TopPilotosAdapter extends BaseAdapter {
    List<String> listaImagenesTexto;
    Context context;

    private static LayoutInflater inflater = null;

    public TopPilotosAdapter(Context context, List<String> imgenesTexto) {
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
        TextView tv, tve, tvh, tvtop, tvtrofeos;
        ImageView trofeos;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        String[] auxImgTx;
        Holder holder = new Holder();
        View rowView;
        rowView = inflater.inflate(R.layout.custom_listview_toppilotos, null);
        rowView.setAnimation(AnimationUtils.loadAnimation(context, R.anim.arribaabajo));
        holder.tv = (TextView) rowView.findViewById(R.id.tvCLVOpciones1);
        holder.tvh = (TextView) rowView.findViewById(R.id.tvCLVOpcionesH);
        holder.tve = (TextView) rowView.findViewById(R.id.tvCLVOpcionesEquipo);
        holder.tvtop = (TextView) rowView.findViewById(R.id.tvCLVOpcionesTop);
        holder.tvtrofeos = (TextView) rowView.findViewById(R.id.tvCLVOpcionesTrofeos);
        holder.trofeos = (ImageView) rowView.findViewById(R.id.imgTrofeosTop);
        holder.trofeos.setImageResource(R.drawable.ic_trofeo);
        auxImgTx = listaImagenesTexto.get(position).split(";");

        DecimalFormat formateador = (DecimalFormat) NumberFormat.getInstance(Locale.GERMAN);
        holder.tvtrofeos.setText(formateador.format(Integer.parseInt(auxImgTx[7])));
        holder.tv.setText(auxImgTx[1]);
        holder.tvtop.setText(auxImgTx[0]);
        if (auxImgTx[0].equals("1")) {
            holder.tvtop.setTextColor(Color.argb(255, 252, 226, 54));
        } else if (auxImgTx[0].equals("2")) {
            holder.tvtop.setTextColor(Color.argb(255, 188, 188, 188));
        } else if (auxImgTx[0].equals("3")) {
            holder.tvtop.setTextColor(Color.argb(255, 230, 138, 0));
        }
        rowView.setBackgroundColor(Color.argb(180, 0, 0, 0));
        if (auxImgTx[2].equals("1")) {
            rowView.setBackgroundColor(Color.argb(180, 0, 148, 217));
        }
        holder.tvh.setText("Curva: " + auxImgTx[3] + " Frenada: " + auxImgTx[4] + " Velocidad: " + auxImgTx[5]);
        if (!auxImgTx[6].equals("l")) {
            holder.tve.setText(auxImgTx[6]);
        } else {
            holder.tve.setText("");
        }

        return rowView;
    }
} 