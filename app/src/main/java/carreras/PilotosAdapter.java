package carreras;

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

import java.util.List;

public class PilotosAdapter extends BaseAdapter {
    List<String> listaImagenesTexto;
    Context context;

    private static LayoutInflater inflater = null;

    public PilotosAdapter(Context context, List<String> imgenesTexto) {
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
        TextView tv, tve, tvh, tvT;
        ImageView img;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        String[] auxImgTx;
        Holder holder = new Holder();
        View rowView;
        rowView = inflater.inflate(R.layout.custom_listview_pilotoss, null);
        rowView.setAnimation(AnimationUtils.loadAnimation(context, R.anim.arribaabajo));
        holder.tv = (TextView) rowView.findViewById(R.id.tvCLVOpciones1);
        holder.tvh = (TextView) rowView.findViewById(R.id.tvCLVOpcionesH);
        holder.tve = (TextView) rowView.findViewById(R.id.tvCLVOpcionesEquipo);
        holder.tvT = (TextView) rowView.findViewById(R.id.tvTiemposCarrera);
        holder.img = (ImageView) rowView.findViewById(R.id.imgCLVOpciones);
        auxImgTx = listaImagenesTexto.get(position).split(";");
        holder.tv.setText(auxImgTx[1]);
        holder.img.setImageResource(Integer.parseInt(auxImgTx[0]));
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
        holder.tvT.setText(auxImgTx[8]);
        return rowView;
    }
} 