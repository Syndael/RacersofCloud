package centros;

import android.app.Activity;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.os.Environment;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.pacheco.saul.racersofcloud.MainActivity;
import com.pacheco.saul.racersofcloud.R;

import java.io.File;
import java.io.FileOutputStream;

import activity.ConfActivity;
import utils.BDUtils;
import utils.ImgUtils;
import utils.PilotosUtils;

public class ConfFragment extends Fragment {
    private static final int SELECT_PHOTO = 100;
    Button btGuardar;
    EditText et_piloto, et_equipo, et_nombreCoche, et_codigoClan;
    ImageButton imgBt_selIcono, imgBt_remove, imtBtNombreAleatorio;

    SQLiteDatabase db;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_conf, container, false);
    }

    @Override
    public void onActivityCreated(Bundle state) {
        super.onActivityCreated(state);
        new ImgUtils().fondo(getView().getContext(), R.drawable.cuadros, (RelativeLayout) getView().findViewById(R.id.rl_frg_conf));
        et_piloto = (EditText) getView().findViewById(R.id.et_piloto);
        et_equipo = (EditText) getView().findViewById(R.id.et_equipo);
        et_nombreCoche = (EditText) getView().findViewById(R.id.et_nombreCoche);
        et_codigoClan = (EditText) getView().findViewById(R.id.et_codigoClan);
        et_piloto.setText(BDUtils.getNombrePiloto());
        et_equipo.setText(BDUtils.getNombreEquipo());
        et_nombreCoche.setText(BDUtils.getNombreCoche());
        if (BDUtils.existeClan()) {
            et_codigoClan.setText(BDUtils.getClan());
        }
        et_nombreCoche.setText(BDUtils.getNombreCoche());
        imgBt_selIcono = (ImageButton) getView().findViewById(R.id.imgBt_selIcono);
        imtBtNombreAleatorio = (ImageButton) getView().findViewById(R.id.imtBtNombreAleatorio);
        imtBtNombreAleatorio.setImageBitmap(ImgUtils.decodeSampledBitmapFromResource(getResources(), R.drawable.ic_dado, 50, 50));
        imtBtNombreAleatorio.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PilotosUtils pu = new PilotosUtils();
                et_piloto.setText(pu.getNombreAleatorio());
            }


        });
        imgBt_selIcono.setImageBitmap(ImgUtils.decodeSampledBitmapFromResource(getResources(), R.drawable.ic_action_lupa, 50, 50));
        imgBt_selIcono.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                pickImage();
            }


        });
        imgBt_remove = (ImageButton) getView().findViewById(R.id.imgBt_remove);
        imgBt_remove.setImageBitmap(ImgUtils.decodeSampledBitmapFromResource(getResources(), R.drawable.ic_action_name, 50, 50));
        imgBt_remove.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                removeImage();
            }


        });
        btGuardar = (Button) getView().findViewById(R.id.btGuardar);
        btGuardar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                boolean cambios = false, coche = false, piloto = false, codigo = false;
                String auxQuery = "UPDATE " + BDUtils.PILOTO + " SET";
                String auxQueryCoche = "UPDATE " + BDUtils.COCHE + " SET";
                if (!(et_piloto.getText().toString().equals("") || et_piloto.getText().toString().equals(BDUtils.getNombrePiloto()))) {
                    if (!cambios) {
                        cambios = true;
                    }
                    piloto = true;
                    auxQuery += " " + BDUtils.NOMBRE + "='" + et_piloto.getText().toString() + "'";
                }
                if (!(et_equipo.getText().toString().equals("") || et_equipo.getText().toString().equals(BDUtils.getNombreEquipo()))) {
                    if (!cambios) {
                        cambios = true;
                    } else {
                        auxQuery += ",";
                    }
                    piloto = true;
                    auxQuery += " " + BDUtils.EQUIPO + "='" + et_equipo.getText().toString() + "'";
                }
                if (!(et_nombreCoche.getText().toString().equals("") || et_nombreCoche.getText().toString().equals(BDUtils.getNombreCoche()))) {
                    if (!cambios) {
                        cambios = true;
                    }
                    coche = true;
                    auxQueryCoche += " " + BDUtils.NOMBRE + "='" + et_nombreCoche.getText().toString() + "'";
                }
                if (!(et_codigoClan.getText().toString().equals("") || (BDUtils.existeClan() && et_codigoClan.getText().toString().equals(BDUtils.getClan())))) {
                    if (!cambios) {
                        cambios = true;
                    }
                    codigo = true;
                }
                if (cambios) {
                    abrirConexion();
                    if (piloto) {
                        db.execSQL(auxQuery + " WHERE " + BDUtils.ID + "=" + BDUtils.getId());
                    }
                    if (coche) {
                        db.execSQL(auxQueryCoche + " WHERE " + BDUtils.ID + "=" + BDUtils.getId());
                    }
                    if (codigo) {
                        BDUtils.setClan(et_codigoClan.getText().toString());
                    }
                    refreshConf();
                }
            }
        });
    }

    private void refreshConf() {
        Intent i = new Intent(getContext(), ConfActivity.class);
        i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(i);
        ((Activity) getContext()).overridePendingTransition(R.anim.fadein, R.anim.fadeout);
    }


    public void abrirConexion() {
        db = MainActivity.getDb();
    }

    public void removeImage() {
        if (BDUtils.existeEscudo()) {
            BDUtils.borrarEscudo();
            refreshConf();
        }
    }

    public void pickImage() {
        Intent intent = new Intent(Intent.ACTION_PICK);
        intent.setType("image/*");
        intent.putExtra("crop", "true");
        intent.putExtra("scale", true);
        intent.putExtra("outputX", 256);
        intent.putExtra("outputY", 256);
        intent.putExtra("aspectX", 1);
        intent.putExtra("aspectY", 1);
        intent.putExtra("return-data", true);
        startActivityForResult(intent, SELECT_PHOTO);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent imageReturnedIntent) {
        super.onActivityResult(requestCode, resultCode, imageReturnedIntent);
        try {
            Bitmap imgEscudo = (Bitmap) imageReturnedIntent.getExtras().get("data");
            File folder = new File("/" + Environment.getExternalStorageDirectory() + "/Android/data/" + getContext().getPackageName());
            if (!folder.exists()) {
                folder.mkdir();
            }
            File file = new File(folder.getPath(), "user_escudo.png");
            if (file.exists()) {
                file.delete();
            }
            FileOutputStream out = new FileOutputStream(file);
            imgEscudo.compress(Bitmap.CompressFormat.PNG, 100, out);
            out.flush();
            out.close();
            BDUtils.insertarImagen(file.getPath());
            refreshConf();
        } catch (Exception e) {
            Toast.makeText(getContext(), "Error al guardar la imagen", Toast.LENGTH_LONG).show();
            System.out.println(e);
        }
    }
}
