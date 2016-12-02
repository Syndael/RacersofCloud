package menus;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.animation.AnimationUtils;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.pacheco.saul.racersofcloud.R;

import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.List;
import java.util.Locale;

import activity.TallerActivity;
import utils.BDUtils;
import utils.ImgUtils;
import utils.TallerUtils;

public class TallerPilotoAdapter extends BaseAdapter {
    List<String> listaImagenesTexto;
    Context context;
    View vAux;

    private static LayoutInflater inflater = null;

    public TallerPilotoAdapter(Context context, List<String> imgenesTexto) {
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
        Holder holder = new Holder();
        View rowView;
        rowView = inflater.inflate(R.layout.custom_listview_opciones, null);
        rowView.setAnimation(AnimationUtils.loadAnimation(context, R.anim.arribaabajo));
        holder.tv = (TextView) rowView.findViewById(R.id.tvCLVOpciones1);
        holder.img = (ImageView) rowView.findViewById(R.id.imgCLVOpciones);
        auxImgTx = listaImagenesTexto.get(position).split(";");
        holder.tv.setText(auxImgTx[1]);
        //holder.img.setImageResource(Integer.parseInt(auxImgTx[0]));
        Glide.with(context).load(Integer.parseInt(auxImgTx[0])).asBitmap().into(holder.img);

        rowView.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                vAux = v;
                v.setBackgroundColor(ImgUtils.colorPulsado);
                String[] auxImgTx;
                final String[] habsaux = BDUtils.getHabilidades().split(";");
                auxImgTx = listaImagenesTexto.get(position).split(";");
                switch (position) {
                    case 0:
                        nuevoPopUp("Curva", BDUtils.getPrecioCurva(), position, v);
                        break;
                    case 1:
                        nuevoPopUp("Frenado", BDUtils.getPrecioFreando(), position, v);
                        break;
                    case 2:
                        nuevoPopUp("Velocidad", BDUtils.getPrecioVelocidad(), position, v);
                        break;
                    default:
                        Toast.makeText(context, "Has seleccionado " + auxImgTx[1] + " - " + position, Toast.LENGTH_LONG).show();
                }
            }
        });
        return rowView;
    }

    private void nuevoPopUp(final String str, final String precio, final int hab, View v) {

        String[] preciosStr = precio.split(";");
        final long[] precios = new long[preciosStr.length];
        long costeReal = 0;
        for (int i = 0; i < preciosStr.length; i++) {
            if (i == 1) {
                switch (hab) {
                    case 0:
                        costeReal = BDUtils.getCosteCurva();
                        break;
                    case 1:
                        costeReal = BDUtils.getCosteFrenado();
                        break;
                    case 2:
                        costeReal = BDUtils.getCosteVelocidad();
                        break;
                    default:
                }
                costeReal++;
            }
            precios[i] = Long.parseLong(preciosStr[i]);
        }
        if (esHabMax(hab)) {
            Toast.makeText(context, "Habilidad maxima alcanzada", Toast.LENGTH_LONG).show();
            v.setBackgroundColor(ImgUtils.colorDesPulsado);
        } else if (BDUtils.getTuercas() >= costeReal) {
            DecimalFormat formateador = (DecimalFormat) NumberFormat.getInstance(Locale.GERMAN);
            String auxPrecio = formateador.format(costeReal);
            final String msg = "Aumentar Habilidad de piloto, " + str + " por " + auxPrecio + "t.";
            final long finalCosteReal = costeReal;
            new AlertDialog.Builder(context)
                    .setTitle("Mejorar " + str)
                    .setMessage(msg)
                    .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {
                            switch (hab) {
                                case 0:
                                    BDUtils.mejorarCurva(precios[1], precios[2], finalCosteReal);
                                    break;
                                case 1:
                                    BDUtils.mejorarFrenada(precios[1], precios[2], finalCosteReal);
                                    break;
                                case 2:
                                    BDUtils.mejorarVelocidad(precios[1], precios[2], finalCosteReal);
                                    break;
                                default:

                            }
                            TallerUtils.setMejorapiloto(true);
                            Intent i = new Intent(context, TallerActivity.class);
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
            buscarValorHabilidad(hab, Long.parseLong(preciosStr[1]));
        } else {
            Toast.makeText(context, "Tuercas insuficientes", Toast.LENGTH_LONG).show();
            v.setBackgroundColor(ImgUtils.colorDesPulsado);
        }
    }

    private void buscarValorHabilidad(int hab, long precio) {
        switch (hab) {
            case 0:
                BDUtils.actualizarCosteCurva(nuevoCoste(Integer.parseInt(BDUtils.getCurva()), precio));
                break;
            case 1:
                BDUtils.actualizarCosteFrenado(nuevoCoste(Integer.parseInt(BDUtils.getFrenado()), precio));
                break;
            case 2:
                BDUtils.actualizarCosteVelocidad(nuevoCoste(Integer.parseInt(BDUtils.getVelocidad()), precio));
        }
    }

    private long nuevoCoste(int valorHab, long precio) {
        if (valorHab >= 0 && valorHab < 15) {
            precio = precio / 44;
        } else if (valorHab >= 15 && valorHab < 30) {
            precio = precio / 66;
        } else {
            precio = precio / 88;
        }
        return precio;
    }

    private boolean esHabMax(int hab) {
        long aux;
        switch (hab) {
            case 0:
                aux = Integer.parseInt(BDUtils.getCurva());
                break;
            case 1:
                aux = Integer.parseInt(BDUtils.getFrenado());
                break;
            case 2:
                aux = Integer.parseInt(BDUtils.getVelocidad());
                break;
            default:
                return true;
        }
        if (aux >= 40) {
            return true;
        } else {
            return false;
        }
    }

}
