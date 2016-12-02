package carreras;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.pacheco.saul.racersofcloud.R;

import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.concurrent.ExecutionException;

import activity.CarreraActivity;
import activity.TallerCocheActivity;
import cz.msebera.android.httpclient.NameValuePair;
import cz.msebera.android.httpclient.message.BasicNameValuePair;
import online.ObtenerPilotosAsyncTask;
import online.ObtenerPilotosClanAsyncTask;
import utils.BDUtils;
import utils.CocheUtils;
import utils.ImgUtils;
import utils.PilotosUtils;

public class ModuloCarreras extends AppCompatActivity {
    int estado, dificultad, curvas, importanciaFrenado, importanciaVelocidad, puntosAux;
    double tiempoAux, tiempoAuxAnterior;
    List<String> pilotos;
    ImageView img_circuito;
    ListView lv_pilotos;
    TextView tv_circuito, tv_vuelta;
    ImageButton bt_aceptar, bt_saltar;
    boolean carreraActiva;

    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_modulo_carreras);
        setCircuito();
        estado = 0;
        tiempoAuxAnterior = 0;
        dificultad = getIntent().getExtras().getInt("dificultad");
        switch (dificultad) {
            case 1:
                tiempoAux = Math.round(Math.random() * 35) + 220;
                break;
            case 2:
                tiempoAux = Math.round(Math.random() * 30) + 200;
                break;
            default:
                tiempoAux = Math.round(Math.random() * 20) + 130;
        }
        new ImgUtils().fondo(getApplicationContext(), R.drawable.herbmos, (RelativeLayout) findViewById(R.id.rl_moduloCarreras));
        bt_aceptar = (ImageButton) findViewById(R.id.bt_aceptar);
        bt_aceptar.setImageBitmap(ImgUtils.decodeSampledBitmapFromResource(getResources(), R.drawable.siguiente, 35, 35));
        bt_saltar = (ImageButton) findViewById(R.id.bt_saltar);
        bt_saltar.setImageBitmap(ImgUtils.decodeSampledBitmapFromResource(getResources(), R.drawable.saltar, 35, 35));
        lv_pilotos = (ListView) findViewById(R.id.lv_pilotos);
        tv_vuelta = (TextView) findViewById(R.id.tv_vueltas);
        pilotos = cargarPilotosRandom();
        pilotos.add(R.drawable.coche + ";" + BDUtils.getNombrePiloto() + ";1;" + calcularCochePiloto() + ";" + BDUtils.getNombreEquipo() + ";0;--:--,---");
        tv_vuelta.setText("Clasificación");
        carreraActiva = false;
        bt_saltar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (carreraActiva) {
                    popUpSaltar();
                }
            }
        });
        bt_aceptar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (estado == 0) {
                    simularClasi();
                    lv_pilotos = (ListView) findViewById(R.id.lv_pilotos);
                    lv_pilotos.setAdapter(new PilotosAdapter(getApplicationContext(), pilotos));
                    tv_vuelta.setText("Vuelta " + (estado + 1) + "/5");
                    estado = 1;
                    carreraActiva = true;
                } else if (estado == 1 || estado == 2 || estado == 3 || estado == 4 || estado == 5) {
                    tiempoAuxAnterior = 0;
                    if ((int) Math.round(Math.random() * 10) == 1 && estado != 5) {
                        popUpFrenar();
                    }
                    simularVuelta();
                    tv_vuelta.setText("Vuelta " + (estado + 1) + "/5");
                    if (estado == 5) {
                        tv_vuelta.setText("Resultado");
                    }
                    lv_pilotos.setAdapter(new PilotosAdapter(getApplicationContext(), pilotos));
                    estado++;
                } else {
                    carreraActiva = false;
                    boolean ganador = false;
                    double premio;
                    premio = BDUtils.getCosteCurva() + BDUtils.getCosteFrenado() + BDUtils.getCosteVelocidad();
                    premio = nuevoPremio(BDUtils.getHabilidades().split(";"), premio);
                    switch (getResultadoCarrera()) {
                        case 1:
                            premio += premio * 0.02;
                            ganador = true;
                            break;
                        case 2:
                            premio += premio * 0.016;
                            break;
                        case 3:
                            premio += premio * 0.013;
                            break;
                        case 4:
                            premio += premio * 0.01;
                            break;
                        case 5:
                            premio += premio * 0.008;
                            break;
                        case 6:
                            premio += premio * 0.006;
                            break;
                        case 7:
                            premio += premio * 0.004;
                            break;
                        case 8:
                            premio += premio * 0.003;
                            break;
                        case 9:
                            premio += premio * 0.002;
                    }
                    switch (dificultad) {
                        case 2:
                            premio += premio * 0.2;
                            break;
                        case 3:
                            premio += premio * 0.5;
                            break;
                        case 4:
                            premio += premio * 0.6;
                            break;
                        case 5:
                            premio += premio * 0.4;
                            break;
                        default:
                            premio += 1;
                    }
                    DecimalFormat formateador = (DecimalFormat) NumberFormat.getInstance(Locale.GERMAN);
                    String auxPremio = formateador.format((int) premio);
                    Toast.makeText(getApplicationContext(), "Has quedado " + getResultadoCarrera() + " y has ganado " + auxPremio + " tuercas.", Toast.LENGTH_SHORT).show();
                    BDUtils.insertar(premio, ganador);
                    finCarrera();
                }
            }
        });
    }

    private double nuevoPremio(String[] habs, double nuevoPremio) {
        int totalHab = Integer.parseInt(habs[0]) + Integer.parseInt(habs[1]) + Integer.parseInt(habs[2]);
        if (totalHab >= 0 && totalHab < 45) {
            nuevoPremio = nuevoPremio / 5;
        } else if (totalHab >= 45 && totalHab < 90) {
            nuevoPremio = nuevoPremio / 20;
        } else {
            nuevoPremio = nuevoPremio / 40;
        }
        return nuevoPremio;
    }


    private int getResultadoCarrera() {
        int posicion = 0, contador = 0;
        boolean encontrado = false;
        do {
            String[] aux = pilotos.get(contador).split(";");
            if (aux[2].equals("1")) {
                encontrado = true;
                posicion = contador + 1;
            }
            contador++;
        } while (!encontrado || contador < pilotos.size());
        return posicion;
    }

    private void setCircuito() {
        img_circuito = (ImageView) findViewById(R.id.img_circuito);
        tv_circuito = (TextView) findViewById(R.id.tv_circuito);

        int countCircuitos = BDUtils.getCountCircuitos();
        countCircuitos--;
        int indexCircuito = (int) Math.round((Math.random() * countCircuitos) + 1);
        int idImgCircuito = BDUtils.getImgCircuito(indexCircuito);
        img_circuito.setImageBitmap(ImgUtils.decodeSampledBitmapFromResource(getResources(), idImgCircuito, 250, 250));
        tv_circuito.setText(BDUtils.getNombreCircuito(indexCircuito));
        setImportanciaCurvas(BDUtils.getImportanciaCurvas(indexCircuito));
        setImportanciaFrenado(BDUtils.getImportanciaFrenado(indexCircuito));
        setImportanciaVelocidad(BDUtils.getImportanciaVelocidad(indexCircuito));
    }

    private void simularClasi() {
        for (int i = 0; i < pilotos.size(); i++) {
            simularHabilidadPiloto(i, true);
        }
        ordenaLista();
        for (int i = 0; i < pilotos.size(); i++) {
            String[] auxHabs = pilotos.get(i).split(";");
            String tiempo = generarTiempo(Integer.parseInt(pilotos.get(i).split(";")[7]));
            pilotos.set(i, auxHabs[0] + ";" + auxHabs[1] + ";" + auxHabs[2] + ";" + auxHabs[3] + ";" + auxHabs[4] + ";" + auxHabs[5] + ";" + auxHabs[6] + ";" + auxHabs[7] + ";" + tiempo);
        }
    }

    private void simularVuelta() {
        for (int i = 0; i < pilotos.size(); i++) {
            simularHabilidadPiloto(i, false);
        }
        ordenaLista();
        for (int i = 0; i < pilotos.size(); i++) {
            String[] auxHabs = pilotos.get(i).split(";");
            String tiempo = generarTiempo(Integer.parseInt(pilotos.get(i).split(";")[7]));
            pilotos.set(i, auxHabs[0] + ";" + auxHabs[1] + ";" + auxHabs[2] + ";" + auxHabs[3] + ";" + auxHabs[4] + ";" + auxHabs[5] + ";" + auxHabs[6] + ";" + auxHabs[7] + ";" + tiempo);
        }
    }

    private void simularHabilidadPiloto(int posicion, boolean clasi) {
        String[] auxHabs = pilotos.get(posicion).split(";");
        int auxPuntuacion = Integer.parseInt(auxHabs[7]);
        auxPuntuacion += (CocheUtils.random(Integer.parseInt(auxHabs[3]) - 2, Integer.parseInt(auxHabs[3]) + 2) * getImportanciaCurvas());
        auxPuntuacion += (CocheUtils.random(Integer.parseInt(auxHabs[4]) - 2, Integer.parseInt(auxHabs[4]) + 2) * getImportanciaFrenado());
        auxPuntuacion += (CocheUtils.random(Integer.parseInt(auxHabs[5]) - 2, Integer.parseInt(auxHabs[5]) + 2) * getImportanciaVelocidad());
        if (clasi) {
            int superVuelta = (int) Math.round(Math.random() * 75);
            if (superVuelta >= 75) {
                auxPuntuacion = ((Integer.parseInt(auxHabs[3]) + 5) * getImportanciaCurvas());
                auxPuntuacion += ((Integer.parseInt(auxHabs[4]) + 5) * getImportanciaFrenado());
                auxPuntuacion += ((Integer.parseInt(auxHabs[5]) + 5) * getImportanciaVelocidad());
            }
        }
        pilotos.set(posicion, auxHabs[0] + ";" + auxHabs[1] + ";" + auxHabs[2] + ";" + auxHabs[3] + ";" + auxHabs[4] + ";" + auxHabs[5] + ";" + auxHabs[6] + ";" + auxPuntuacion + ";--:--,---");
        puntosAux = auxPuntuacion / 10;
    }

    private String generarTiempo(int auxPuntuacion) {
        double auxDouble;
        String aux;
        if (tiempoAuxAnterior != 0) {
            auxDouble = tiempoAuxAnterior * 1000 + (CocheUtils.random(500, 10));
            aux = String.format(Locale.GERMAN, "%.3f", auxDouble / 1000);
            aux = "+" + aux.substring(2);
        } else {
            auxDouble = tiempoAux * 1000 - (auxPuntuacion);
            aux = String.format(Locale.GERMAN, "%.3f", auxDouble / 1000);
            aux = aux.substring(0, 1) + ":" + aux.substring(1);
        }
        tiempoAuxAnterior = auxDouble / 1000;
        return aux;
    }

    private void ordenaLista() {
        int cuentaintercambios = 0;
        for (boolean ordenado = false; !ordenado; ) {
            for (int i = 0; i < pilotos.size() - 1; i++) {
                if (Integer.parseInt(pilotos.get(i).split(";")[7]) > Integer.parseInt(pilotos.get(i + 1).split(";")[7])) {
                    String variableauxiliar = pilotos.get(i);
                    pilotos.set(i, pilotos.get(i + 1));
                    pilotos.set((i + 1), variableauxiliar);
                    cuentaintercambios++;
                }
            }
            if (cuentaintercambios == 0) {
                ordenado = true;
            }
            cuentaintercambios = 0;
        }
        reverse((ArrayList<String>) pilotos);
    }

    public ArrayList<String> reverse(ArrayList<String> list) {
        if (list.size() > 1) {
            String value = list.remove(0);
            reverse(list);
            list.add(value);
        }
        return list;
    }

    private ArrayList<String> cargarPilotosRandom() {
        ArrayList<String> aux = new ArrayList<>();
        PilotosUtils nu = new PilotosUtils();
        if (dificultad == 4) {
            List<String> pilotosOnline = null;
            List<NameValuePair> parametros = new ArrayList<>();
            parametros.add(new BasicNameValuePair("trofeos", Integer.toString(BDUtils.getTrofeos())));
            parametros.add(new BasicNameValuePair("identificador", BDUtils.getIdentificadorPiloto()));
            try {
                pilotosOnline = new ObtenerPilotosAsyncTask().execute(parametros).get();
            } catch (InterruptedException e) {
                e.printStackTrace();
            } catch (ExecutionException e) {
                e.printStackTrace();
            }

            int contador = 0;
            for (String pilotoAux : pilotosOnline) {
                String[] pilotoAuxStr = pilotoAux.split(";");
                int crv = calcularCoche(pilotoAuxStr[4], pilotoAuxStr[7], pilotoAuxStr[8], pilotoAuxStr[9], pilotoAuxStr[10], pilotoAuxStr[11], pilotoAuxStr[12], 0);
                int frn = calcularCoche(pilotoAuxStr[4], pilotoAuxStr[7], pilotoAuxStr[8], pilotoAuxStr[9], pilotoAuxStr[10], pilotoAuxStr[11], pilotoAuxStr[12], 1);
                int vlc = calcularCoche(pilotoAuxStr[4], pilotoAuxStr[7], pilotoAuxStr[8], pilotoAuxStr[9], pilotoAuxStr[10], pilotoAuxStr[11], pilotoAuxStr[12], 2);

                aux.add(contador, R.drawable.coche + ";" + pilotoAuxStr[1] + ";0;" + crv + ";" + frn + ";" + vlc + ";" + pilotoAuxStr[2] + ";0;--:--,---");
                contador++;
            }
            for (int i = contador; i < 9; i++) {
                String auxHA = nu.getHabilidadesAleatorias(4);
                aux.add(R.drawable.coche + ";" + nu.getNombreAleatorio() + ";0;" + auxHA + ";l;0;--:--,---");
            }
        } else if (dificultad == 5) {
            List<String> pilotosOnline = null;
            List<NameValuePair> parametros = new ArrayList<>();
            parametros.add(new BasicNameValuePair("codigoclan", BDUtils.getClan()));
            parametros.add(new BasicNameValuePair("identificador", BDUtils.getIdentificadorPiloto()));
            try {
                pilotosOnline = new ObtenerPilotosClanAsyncTask().execute(parametros).get();
            } catch (InterruptedException e) {
                e.printStackTrace();
            } catch (ExecutionException e) {
                e.printStackTrace();
            }

            int contador = 0;
            for (String pilotoAux : pilotosOnline) {
                String[] pilotoAuxStr = pilotoAux.split(";");
                int crv = calcularCoche(pilotoAuxStr[4], pilotoAuxStr[7], pilotoAuxStr[8], pilotoAuxStr[9], pilotoAuxStr[10], pilotoAuxStr[11], pilotoAuxStr[12], 0);
                int frn = calcularCoche(pilotoAuxStr[4], pilotoAuxStr[7], pilotoAuxStr[8], pilotoAuxStr[9], pilotoAuxStr[10], pilotoAuxStr[11], pilotoAuxStr[12], 1);
                int vlc = calcularCoche(pilotoAuxStr[4], pilotoAuxStr[7], pilotoAuxStr[8], pilotoAuxStr[9], pilotoAuxStr[10], pilotoAuxStr[11], pilotoAuxStr[12], 2);

                aux.add(contador, R.drawable.coche + ";" + pilotoAuxStr[1] + ";0;" + crv + ";" + frn + ";" + vlc + ";" + pilotoAuxStr[2] + ";0;--:--,---");
                contador++;
            }
            for (int i = contador; i < 9; i++) {
                String auxHA = nu.getHabilidadesAleatorias(4);
                aux.add(R.drawable.coche + ";" + nu.getNombreAleatorio() + ";0;" + auxHA + ";l;0;--:--,---");
            }
        } else {
            for (int i = 0; i < 9; i++) {
                String auxHA = nu.getHabilidadesAleatorias(dificultad);
                aux.add(R.drawable.coche + ";" + nu.getNombreAleatorio() + ";0;" + auxHA + ";l;0;--:--,---");
            }
        }
        return aux;
    }


    private String calcularCochePiloto() {
        String habConPieza;
        habConPieza = calcularCoche(BDUtils.getCurva(), BDUtils.getAleron(), BDUtils.getFrenos(), BDUtils.getNeumaticos(), BDUtils.getMorro(), BDUtils.getMotor(), BDUtils.getVolante(), 0) + ";";
        habConPieza += calcularCoche(BDUtils.getFrenado(), BDUtils.getAleron(), BDUtils.getFrenos(), BDUtils.getNeumaticos(), BDUtils.getMorro(), BDUtils.getMotor(), BDUtils.getVolante(), 1) + ";";
        habConPieza += calcularCoche(BDUtils.getVelocidad(), BDUtils.getAleron(), BDUtils.getFrenos(), BDUtils.getNeumaticos(), BDUtils.getMorro(), BDUtils.getMotor(), BDUtils.getVolante(), 2) + "";
        return habConPieza;
    }

    private int calcularCoche(String hab, String aleron, String frenos, String neumaticos, String morro, String motor, String volante, int indiceHab) {
        int habConPieza = Integer.parseInt(hab);
        indiceHab++;
        try {
            habConPieza += sumarPieza(aleron, indiceHab);
            habConPieza += sumarPieza(frenos, indiceHab);
            habConPieza += sumarPieza(neumaticos, indiceHab);
            habConPieza += sumarPieza(morro, indiceHab);
            habConPieza += sumarPieza(motor, indiceHab);
            habConPieza += sumarPieza(volante, indiceHab);
        } catch (Exception ex) {
            System.out.println(ex);
        }
        return habConPieza;
    }

    private int sumarPieza(String pieza, int indiceHab) {
        String[] auxPieza = pieza.split(":");
        return Integer.parseInt(auxPieza[indiceHab]);
    }

    private void finCarrera() {
        Intent i = new Intent(getApplicationContext(), CarreraActivity.class);
        i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(i);
        this.overridePendingTransition(R.anim.fadein, R.anim.fadeout);
    }

    private void popUpSaltar() {
        new AlertDialog.Builder(ModuloCarreras.this)
                .setTitle("Simular Carrera")
                .setMessage("¿Simular el resto de carrera? Si lo haces, no serás tan competitivo durente el resto de carrera.")
                .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        for (int i = estado; i < 6; i++) {
                            carreraActiva = false;
                            tiempoAuxAnterior = 0;
                            simularVuelta();
                            tv_vuelta.setText("Vuelta " + (estado + 1) + "/5");
                            if (estado == 5) {
                                tv_vuelta.setText("Resultado");
                            }
                            lv_pilotos.setAdapter(new PilotosAdapter(getApplicationContext(), pilotos));
                            estado++;
                        }
                        for (int i = 0; i < pilotos.size(); i++) {
                            if (pilotos.get(i).split(";")[2].equals("1")) {
                                String[] aux = pilotos.get(i).split(";");
                                aux[7] = "" + (Integer.parseInt(aux[7]) - puntosAux);
                                pilotos.set(i, aux[0] + ";" + aux[1] + ";" + aux[2] + ";" + aux[3] + ";" + aux[4] + ";" + aux[5] + ";" + aux[6] + ";" + aux[7] + ";" + aux[8]);
                            }
                        }
                    }
                })
                .setNegativeButton(android.R.string.no, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {

                    }
                })
                .setIcon(android.R.drawable.ic_dialog_alert)
                .show();
    }

    private void popUpFrenar() {
        final int probabilidad = (int) Math.round(Math.random() * 100);
        new AlertDialog.Builder(ModuloCarreras.this)
                .setTitle("Safety Car")
                .setMessage("Un incidente en la pista obliga al Safety Car salir a pista. ¿Quieres cambiar neumáticos? " + probabilidad + "% de salir beneficiado.")
                .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        boolean beneficio = false;
                        if ((int) Math.round(Math.random() * 100) < probabilidad) {
                            beneficio = true;
                        }
                        for (int i = 0; i < pilotos.size(); i++) {
                            if (pilotos.get(i).split(";")[2].equals("1")) {
                                String[] aux = pilotos.get(i).split(";");
                                if (beneficio) {
                                    aux[7] = "" + (Integer.parseInt(aux[7]) + puntosAux);
                                } else {
                                    aux[7] = "" + (Integer.parseInt(aux[7]) - puntosAux / 3);
                                }
                                pilotos.set(i, aux[0] + ";" + aux[1] + ";" + aux[2] + ";" + aux[3] + ";" + aux[4] + ";" + aux[5] + ";" + aux[6] + ";" + aux[7] + ";" + aux[8]);
                            }
                        }
                    }
                })
                .setNegativeButton(android.R.string.no, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {

                    }
                })
                .setIcon(android.R.drawable.ic_dialog_alert)
                .show();
    }

    public int getImportanciaVelocidad() {
        return importanciaVelocidad;
    }

    public void setImportanciaVelocidad(int importanciaVelocidad) {
        this.importanciaVelocidad = importanciaVelocidad;
    }

    public int getImportanciaCurvas() {
        return curvas;
    }

    public void setImportanciaCurvas(int curvas) {
        this.curvas = curvas;
    }

    public int getImportanciaFrenado() {
        return importanciaFrenado;
    }

    public void setImportanciaFrenado(int importanciaFrenado) {
        this.importanciaFrenado = importanciaFrenado;
    }
}
