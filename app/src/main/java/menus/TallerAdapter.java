package menus;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.animation.AnimationUtils;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.pacheco.saul.racersofcloud.R;

import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import activity.TallerCocheActivity;
import centros.TallerCocheFragment;
import utils.BDUtils;
import utils.ImgUtils;

public class TallerAdapter extends BaseAdapter {
    List<String> listaImagenesTexto;
    Context context;
    ListView lv_taller;

    private static LayoutInflater inflater = null;

    public TallerAdapter(Context context, List<String> imgenesTexto, ListView lv_taller) {
        listaImagenesTexto = imgenesTexto;
        this.lv_taller = lv_taller;
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
        Holder holder = new Holder();
        View rowView;
        rowView = inflater.inflate(R.layout.custom_listview_opciones, null);
        rowView.setAnimation(AnimationUtils.loadAnimation(context, R.anim.arribaabajo));
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
                        try {
                            List<String> opciones = new ArrayList<>();
                            String[] habs = BDUtils.getHabilidades().split(";");
                            DecimalFormat formateador = (DecimalFormat) NumberFormat.getInstance(Locale.GERMAN);
                            if (!habs[0].equals("40")) {
                                opciones.add(R.drawable.volante + ";Curva: " + habs[0] + " - " + formateador.format(1 + BDUtils.getCosteCurva()) + "t");
                            } else {
                                opciones.add(R.drawable.volante + ";Curva: " + habs[0] + " - MAX");
                            }
                            if (!habs[1].equals("40")) {
                                opciones.add(R.drawable.frenos + ";Frenado: " + habs[1] + " - " + formateador.format(1 + BDUtils.getCosteFrenado()) + "t");
                            } else {
                                opciones.add(R.drawable.frenos + ";Frenado: " + habs[1] + " - MAX");
                            }
                            if (!habs[2].equals("40")) {
                                opciones.add(R.drawable.velocidad + ";Velocidad: " + habs[2] + " - " + formateador.format(1 + BDUtils.getCosteVelocidad()) + "t");
                            } else {
                                opciones.add(R.drawable.velocidad + ";Velocidad: " + habs[2] + " - MAX");
                            }
                            lv_taller.setAdapter(new TallerPilotoAdapter(context, opciones));
                        } catch (IllegalStateException e) {
                        }
                        break;
                    case 1:
                        Intent i = new Intent(context, TallerCocheActivity.class);
                        i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                        ((Activity) context).overridePendingTransition(R.anim.fadein, R.anim.fadeout);
                        ImgUtils.setMenuAct(6);
                        context.startActivity(i);
                        break;
                    default:
                        Toast.makeText(context, "Has seleccionado " + auxImgTx[1] + " - " + position, Toast.LENGTH_LONG).show();
                }
            }
        });
        return rowView;
    }
} 