package utils;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.provider.Settings;
import android.text.Editable;
import android.widget.Toast;

import com.pacheco.saul.racersofcloud.MainActivity;
import com.pacheco.saul.racersofcloud.R;

import java.io.IOException;
import java.sql.Date;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;

import cz.msebera.android.httpclient.NameValuePair;
import cz.msebera.android.httpclient.message.BasicNameValuePair;
import online.CrearPilotosAsyncTask;

public class BDUtils {
    static SQLiteDatabase db;

    public static final String ID = "id";
    public static final String PILOTO = "piloto";
    public static final String TUERCAS = "tuercas";
    public static final String TROFEOS = "trofeos";
    public static final String EQUIPO = "equipo";
    public static final String NOMBRE = "nombre";
    public static final String IDENTIFICADOR = "identificador";
    public static final String PILOTO_STR = NOMBRE + ", " + EQUIPO + ", " + TROFEOS + ", " + TUERCAS;

    public static final String HABILIDADES = "habilidades";
    public static final String CURVA = "curva";
    public static final String FRENADO = "frenado";
    public static final String VELOCIDAD = "velocidad";
    public static final String ID_PILOTO = "id_piloto";
    public static final String HABILIDADES_STR = ID_PILOTO + ", " + CURVA + ", " + FRENADO + ", " + VELOCIDAD;

    public static final String COCHE = "coche";
    public static final String ALERON = "aleron";
    public static final String FRENOS = "frenos";
    public static final String NEUMATICOS = "neumaticos";
    public static final String MORRO = "morro";
    public static final String MOTOR = "motor";
    public static final String VOLANTE = "volante";
    public static final String CHOCHE_STR = ID_PILOTO + ", " + NOMBRE + ", " + ALERON + ", " + FRENOS + ", " + NEUMATICOS + ", " + MORRO + ", " + MOTOR + ", " + VOLANTE;

    public static final String PRECIOS = "precios";
    public static final String PRECIO_ANTERIOR_C = "precio_anterior_c";
    public static final String PRECIO_ACTUAL_C = "precio_actual_c";
    public static final String PRECIO_SIGUIENTE_C = "precio_siguiente_c";
    public static final String PRECIO_ANTERIOR_F = "precio_anterior_f";
    public static final String PRECIO_ACTUAL_F = "precio_actual_f";
    public static final String PRECIO_SIGUIENTE_F = "precio_siguiente_f";
    public static final String PRECIO_ANTERIOR_V = "precio_anterior_v";
    public static final String PRECIO_ACTUAL_V = "precio_actual_v";
    public static final String PRECIO_SIGUIENTE_V = "precio_siguiente_v";
    public static final String COSTE_C = "coste_c";
    public static final String COSTE_F = "coste_f";
    public static final String COSTE_V = "coste_v";
    public static final String PRECIOS_STR = ID_PILOTO + ", " + PRECIO_ANTERIOR_C + ", " + PRECIO_ACTUAL_C + ", " + PRECIO_SIGUIENTE_C + ", " + PRECIO_ANTERIOR_F + ", " + PRECIO_ACTUAL_F + ", " + PRECIO_SIGUIENTE_F + ", " + PRECIO_ANTERIOR_V + ", " + PRECIO_ACTUAL_V + ", " + PRECIO_SIGUIENTE_V + ", " + COSTE_C + ", " + COSTE_F + ", " + COSTE_V;

    public static final String CARRERAS = "carreras";
    public static final String IDIMG = "idimg";
    public static final String IMPORTANCIACURVA = "importanciacurva";
    public static final String IMPORTANCIAFRENADO = "importanciafrenado";
    public static final String IMPORTANCIAVELOCIDAD = "importanciavelocidad";
    public static final String CARRERAS_STR = IDIMG + ", " + NOMBRE + ", " + IMPORTANCIACURVA + ", " + IMPORTANCIAFRENADO + ", " + IMPORTANCIAVELOCIDAD;

    public static final String PIEZAS = "piezas";
    public static final String TIPO = "tipo";
    public static final String DIA = "dia";
    public static final String PRECIO = "precio";
    public static final String PIEZAS_STR = TIPO + ", " + NOMBRE + ", " + CURVA + ", " + FRENADO + ", " + VELOCIDAD + ", " + PRECIO + ", " + DIA;

    public static final String CLAN = "clan";
    public static final String CODIGO = "codigo";
    public static final String CLAN_STR = ID_PILOTO + ", " + CODIGO;

    public static final String IMAGEN = "imagen";
    public static final String RUTA = "ruta";

    public static final String STRING_TYPE = "text";
    public static final String INT_TYPE = "integer";
    public static final String BIGINT_TYPE = "bigint";
    public static final String DATE_TYPE = "date";

    public static String tablaPiloto() {
        return "CREATE TABLE IF NOT EXISTS " + PILOTO + "(" +
                ID + " " + INT_TYPE + " primary key autoincrement," +
                NOMBRE + " " + STRING_TYPE + " not null," +
                EQUIPO + " " + STRING_TYPE + " not null," +
                TROFEOS + " " + INT_TYPE + " not null," +
                TUERCAS + " " + BIGINT_TYPE + " not null," +
                IDENTIFICADOR + " " + STRING_TYPE + " not null)";
    }

    public static String tablaHabilidadesPiloto() {
        return "CREATE TABLE IF NOT EXISTS " + HABILIDADES + "(" +
                ID + " " + INT_TYPE + " primary key autoincrement," +
                ID_PILOTO + " " + INT_TYPE + " not null," +
                CURVA + " " + INT_TYPE + " not null," +
                FRENADO + " " + INT_TYPE + " not null," +
                VELOCIDAD + " " + INT_TYPE + " not null," +
                IDENTIFICADOR + " " + STRING_TYPE + " not null," +
                " FOREIGN KEY(" + ID_PILOTO + ") REFERENCES " + PILOTO + "(" + ID + "))";
    }

    public static String tablaImagenPiloto() {
        return "CREATE TABLE IF NOT EXISTS " + IMAGEN + "(" +
                ID + " " + INT_TYPE + " primary key autoincrement," +
                ID_PILOTO + " " + INT_TYPE + " not null," +
                RUTA + " " + STRING_TYPE + " not null," +
                " FOREIGN KEY(" + ID_PILOTO + ") REFERENCES " + PILOTO + "(" + ID + "))";
    }

    public static String tablaCoche() {
        return "CREATE TABLE IF NOT EXISTS " + COCHE + "(" +
                ID + " " + INT_TYPE + " primary key autoincrement," +
                ID_PILOTO + " " + INT_TYPE + " not null," +
                NOMBRE + " " + STRING_TYPE + " not null," +
                ALERON + " " + STRING_TYPE + " not null," +
                FRENOS + " " + STRING_TYPE + " not null," +
                NEUMATICOS + " " + STRING_TYPE + " not null," +
                MORRO + " " + STRING_TYPE + " not null," +
                MOTOR + " " + STRING_TYPE + " not null," +
                VOLANTE + " " + STRING_TYPE + " not null," +
                " FOREIGN KEY(" + ID_PILOTO + ") REFERENCES " + PILOTO + "(" + ID + "))";
    }

    public static String tablaPrecios() {
        return "CREATE TABLE IF NOT EXISTS " + PRECIOS + "(" +
                ID + " " + INT_TYPE + " primary key autoincrement," +
                ID_PILOTO + " " + INT_TYPE + " not null," +
                PRECIO_ANTERIOR_C + " " + BIGINT_TYPE + " not null," +
                PRECIO_ACTUAL_C + " " + BIGINT_TYPE + " not null," +
                PRECIO_SIGUIENTE_C + " " + BIGINT_TYPE + " not null," +
                PRECIO_ANTERIOR_F + " " + BIGINT_TYPE + " not null," +
                PRECIO_ACTUAL_F + " " + BIGINT_TYPE + " not null," +
                PRECIO_SIGUIENTE_F + " " + BIGINT_TYPE + " not null," +
                PRECIO_ANTERIOR_V + " " + BIGINT_TYPE + " not null," +
                PRECIO_ACTUAL_V + " " + BIGINT_TYPE + " not null," +
                PRECIO_SIGUIENTE_V + " " + BIGINT_TYPE + " not null," +
                COSTE_C + " " + BIGINT_TYPE + " not null," +
                COSTE_F + " " + BIGINT_TYPE + " not null," +
                COSTE_V + " " + BIGINT_TYPE + " not null," +
                " FOREIGN KEY(" + ID_PILOTO + ") REFERENCES " + PILOTO + "(" + ID + "))";
    }

    public static String tablaCarreras() {
        return "CREATE TABLE IF NOT EXISTS " + CARRERAS + "(" +
                ID + " " + INT_TYPE + " primary key autoincrement," +
                NOMBRE + " " + STRING_TYPE + " not null," +
                IDIMG + " " + INT_TYPE + " not null," +
                IMPORTANCIACURVA + " " + INT_TYPE + " not null," +
                IMPORTANCIAFRENADO + " " + INT_TYPE + " not null," +
                IMPORTANCIAVELOCIDAD + " " + INT_TYPE + " not null)";
    }

    public static String tablaPiezas() {
        return "CREATE TABLE IF NOT EXISTS " + PIEZAS + "(" +
                ID + " " + INT_TYPE + " primary key autoincrement," +
                TIPO + " " + INT_TYPE + " not null," +
                NOMBRE + " " + STRING_TYPE + " not null," +
                CURVA + " " + INT_TYPE + " not null," +
                FRENADO + " " + INT_TYPE + " not null," +
                VELOCIDAD + " " + INT_TYPE + " not null," +
                PRECIO + " " + INT_TYPE + " not null," +
                DIA + " " + DATE_TYPE + " not null)";
    }

    public static String tablaClan() {
        return "CREATE TABLE IF NOT EXISTS " + CLAN + "(" +
                ID + " " + INT_TYPE + " primary key autoincrement," +
                ID_PILOTO + " " + INT_TYPE + " not null," +
                CODIGO + " " + DATE_TYPE + " not null," +
                " FOREIGN KEY(" + ID_PILOTO + ") REFERENCES " + PILOTO + "(" + ID + "))";
    }

    public static boolean hayRegistros() {
        Cursor c = getCursor();
        return c.moveToNext();
    }

    public static void crearPiloto(SQLiteDatabase db, Context c) {
        try {
            String identificador = Settings.Secure.getString(c.getContentResolver(), Settings.Secure.ANDROID_ID);
            String str = "Estándar:0:0:0";
            String prec_an = "34", prec_ac = "55", prec_si = "89";
            String prec_aux = prec_an + "," + prec_ac + "," + prec_si;
            db.execSQL("INSERT INTO " + PILOTO + "(" + PILOTO_STR + "," + IDENTIFICADOR + ") VALUES ('Piloto','RoC F1 Team',0,5,'" + identificador + "');");
            db.execSQL("INSERT INTO " + HABILIDADES + "(" + HABILIDADES_STR + "," + IDENTIFICADOR + ") VALUES (" + getId() + ",0,0,0,'" + identificador + "');");
            db.execSQL("INSERT INTO " + COCHE + "(" + CHOCHE_STR + ") VALUES (" + getId() + ",'RoCar','" + str + "','" + str + "','" + str + "','" + str + "','" + str + "','" + str + "');");
            db.execSQL("INSERT INTO " + PRECIOS + "(" + PRECIOS_STR + ") VALUES (" + getId() + "," + prec_aux + "," + prec_aux + "," + prec_aux + ",0,0,0);");
        } catch (Exception ex) {
            System.out.println(ex);
        }
    }

    public static void crearCircuitos(SQLiteDatabase db) {
        try {
            db.execSQL("DELETE FROM " + CARRERAS);
            db.execSQL("INSERT INTO " + CARRERAS + "(id," + CARRERAS_STR + ") VALUES (1," + R.drawable.nuerburgring + ",'Nuerburgring',14,25,18);");
            db.execSQL("INSERT INTO " + CARRERAS + "(id," + CARRERAS_STR + ") VALUES (2," + R.drawable.indianapolisspeedway + ",'Indianapolis Speedway',18,18,25);");
            db.execSQL("INSERT INTO " + CARRERAS + "(id," + CARRERAS_STR + ") VALUES (3," + R.drawable.race + ",'RoCloway',25,14,14);");
        } catch (Exception ex) {
            System.out.println(ex);
        }
    }

    public static String getNombrePiloto() {
        Cursor c = getCursor();
        c.moveToNext();
        return c.getString(c.getColumnIndex(NOMBRE));
    }

    public static String getIdentificadorPiloto() {
        Cursor c = getCursor();
        c.moveToNext();
        return c.getString(c.getColumnIndex(IDENTIFICADOR));
    }

    public static String getNombreEquipo() {
        Cursor c = getCursor();
        c.moveToNext();
        return c.getString(c.getColumnIndex(EQUIPO));
    }

    public static int getTrofeos() {
        Cursor c = getCursor();
        c.moveToNext();
        return Integer.parseInt(c.getString(c.getColumnIndex(TROFEOS)));
    }

    public static long getTuercas() {
        Cursor c = getCursor();
        c.moveToNext();
        return Long.parseLong(c.getString(c.getColumnIndex(TUERCAS)));
    }

    public static String getHabilidades() {
        String aux;
        Cursor c = getCursorHabs();
        c.moveToNext();
        aux = c.getString(c.getColumnIndex(CURVA));
        aux += ";" + c.getString(c.getColumnIndex(FRENADO));
        aux += ";" + c.getString(c.getColumnIndex(VELOCIDAD));
        return aux;
    }

    public static String getId() {
        Cursor c = getCursor();
        c.moveToNext();
        return c.getString(c.getColumnIndex(ID));
    }

    private static Cursor getCursor() {
        db = MainActivity.getDb();
        Cursor c;
        c = db.rawQuery("select * from " + PILOTO, null);
        return c;
    }

    private static Cursor getCursorImagen() {
        db = MainActivity.getDb();
        Cursor c;
        c = db.rawQuery("select * from " + IMAGEN, null);
        return c;
    }

    private static Cursor getCursorHabs() {
        db = MainActivity.getDb();
        Cursor c;
        c = db.rawQuery("select * from " + HABILIDADES, null);
        return c;
    }

    private static Cursor getCursorCoche() {
        db = MainActivity.getDb();
        Cursor c;
        c = db.rawQuery("select * from " + COCHE, null);
        return c;
    }

    private static Cursor getCursorCarreras(int indexCircuito) {
        db = MainActivity.getDb();
        Cursor c;
        c = db.rawQuery("select * from " + CARRERAS + " where id=" + indexCircuito, null);
        return c;
    }

    public static int getCountCircuitos() {
        db = MainActivity.getDb();
        Cursor c;
        c = db.rawQuery("select count(*) from " + CARRERAS, null);
        c.moveToNext();
        return c.getInt(0);
    }

    private static Cursor getCursorPrecios() {
        db = MainActivity.getDb();
        Cursor c;
        c = db.rawQuery("select * from " + PRECIOS, null);
        return c;
    }

    private static Cursor getCursorPiezas() {
        db = MainActivity.getDb();
        Cursor c;
        c = db.rawQuery("select * from " + PIEZAS, null);
        return c;
    }

    public static int getCountPiezas() {
        db = MainActivity.getDb();
        Cursor c;
        c = db.rawQuery("select count(*) from " + PIEZAS, null);
        c.moveToNext();
        return c.getInt(0);
    }

    private static Cursor getCursorClan() {
        db = MainActivity.getDb();
        Cursor c;
        c = db.rawQuery("select * from " + CLAN, null);
        return c;
    }

    public static void insertar(double premio, boolean gana) {
        int trfAux;
        long trAux;
        trfAux = getTrofeos() + 1;

        trAux = getTuercas() + ((long) premio);
        if (trAux >= 1000000000) {
            trAux = 1000000000;
        } else if (trAux < 0) {
            trAux = trfAux * -1;
        }

        String aux = "UPDATE " + BDUtils.PILOTO + " SET ";
        aux += BDUtils.TUERCAS + "=" + trAux;
        if (gana) {
            aux += ", " + BDUtils.TROFEOS + "=" + (trfAux);
        }
        aux += " WHERE " + BDUtils.ID + "=" + getId();
        db.execSQL(aux);
    }

    public static void mejorarCurva(long precioAct, long precioSig, long costeReal) {
        long intAux;
        intAux = getTuercas();
        String aux = "UPDATE " + BDUtils.PILOTO + " SET ";
        aux += BDUtils.TUERCAS + "=" + (intAux - costeReal);
        aux += " WHERE " + BDUtils.ID + "=" + getId();
        db.execSQL(aux);
        intAux = Integer.parseInt(getHabilidades().split(";")[0]);
        aux = "UPDATE " + BDUtils.HABILIDADES + " SET ";
        aux += BDUtils.CURVA + "=" + (intAux + 1);
        aux += " WHERE " + BDUtils.ID + "=" + getId();
        db.execSQL(aux);
        aux = "UPDATE " + BDUtils.PRECIOS + " SET ";
        aux += BDUtils.PRECIO_ANTERIOR_C + "=" + precioAct;
        aux += ", " + BDUtils.PRECIO_ACTUAL_C + "=" + precioSig;
        aux += ", " + BDUtils.PRECIO_SIGUIENTE_C + "=" + (precioAct + precioSig);
        aux += " WHERE " + BDUtils.ID + "=" + getId();
        db.execSQL(aux);

    }

    public static void mejorarFrenada(long precioAct, long precioSig, long costeReal) {
        long intAux;
        intAux = getTuercas();
        String aux = "UPDATE " + BDUtils.PILOTO + " SET ";
        aux += BDUtils.TUERCAS + "=" + (intAux - costeReal);
        aux += " WHERE " + BDUtils.ID + "=" + getId();
        db.execSQL(aux);
        intAux = Integer.parseInt(getHabilidades().split(";")[1]);
        aux = "UPDATE " + BDUtils.HABILIDADES + " SET ";
        aux += BDUtils.FRENADO + "=" + (intAux + 1);
        aux += " WHERE " + BDUtils.ID + "=" + getId();
        db.execSQL(aux);
        aux = "UPDATE " + BDUtils.PRECIOS + " SET ";
        aux += BDUtils.PRECIO_ANTERIOR_F + "=" + precioAct;
        aux += ", " + BDUtils.PRECIO_ACTUAL_F + "=" + precioSig;
        aux += ", " + BDUtils.PRECIO_SIGUIENTE_F + "=" + (precioAct + precioSig);
        aux += " WHERE " + BDUtils.ID + "=" + getId();
        db.execSQL(aux);
    }

    public static void mejorarVelocidad(long precioAct, long precioSig, long costeReal) {
        long intAux;
        intAux = getTuercas();
        String aux = "UPDATE " + BDUtils.PILOTO + " SET ";
        aux += BDUtils.TUERCAS + "=" + (intAux - costeReal);
        aux += " WHERE " + BDUtils.ID + "=" + getId();
        db.execSQL(aux);
        intAux = Integer.parseInt(getHabilidades().split(";")[2]);
        aux = "UPDATE " + BDUtils.HABILIDADES + " SET ";
        aux += BDUtils.VELOCIDAD + "=" + (intAux + 1);
        aux += " WHERE " + BDUtils.ID + "=" + getId();
        db.execSQL(aux);
        aux = "UPDATE " + BDUtils.PRECIOS + " SET ";
        aux += BDUtils.PRECIO_ANTERIOR_V + "=" + precioAct;
        aux += ", " + BDUtils.PRECIO_ACTUAL_V + "=" + precioSig;
        aux += ", " + BDUtils.PRECIO_SIGUIENTE_V + "=" + (precioAct + precioSig);
        aux += " WHERE " + BDUtils.ID + "=" + getId();
        db.execSQL(aux);
    }

    public static void insertarImagen(String filePath) {
        db.execSQL("INSERT INTO " + IMAGEN + "(" + ID_PILOTO + ", " + RUTA + ") VALUES (" + getId() + ",'" + filePath + "');");
    }

    public static boolean existeEscudo() {
        Cursor c = getCursorImagen();
        return c.moveToNext();
    }

    public static String getEscudoImagen() {
        String aux;
        Cursor c = getCursorImagen();
        c.moveToNext();
        aux = c.getString(c.getColumnIndex(RUTA));
        return aux;
    }

    public static void borrarEscudo() {
        db.execSQL("DELETE FROM " + IMAGEN + " WHERE " + RUTA + "='" + getEscudoImagen() + "';");
    }

    public static String getNombreCoche() {
        Cursor c = getCursorCoche();
        c.moveToNext();
        return c.getString(c.getColumnIndex(NOMBRE));
    }

    public static String getCurva() {
        Cursor c = getCursorHabs();
        c.moveToNext();
        return c.getString(c.getColumnIndex(CURVA));
    }

    public static String getFrenado() {
        Cursor c = getCursorHabs();
        c.moveToNext();
        return c.getString(c.getColumnIndex(FRENADO));
    }

    public static String getVelocidad() {
        Cursor c = getCursorHabs();
        c.moveToNext();
        return c.getString(c.getColumnIndex(VELOCIDAD));
    }

    public static String getAleron() {
        Cursor c = getCursorCoche();
        c.moveToNext();
        return c.getString(c.getColumnIndex(ALERON));
    }

    public static String getFrenos() {
        Cursor c = getCursorCoche();
        c.moveToNext();
        return c.getString(c.getColumnIndex(FRENOS));
    }

    public static String getNeumaticos() {
        Cursor c = getCursorCoche();
        c.moveToNext();
        return c.getString(c.getColumnIndex(NEUMATICOS));
    }

    public static String getMorro() {
        Cursor c = getCursorCoche();
        c.moveToNext();
        return c.getString(c.getColumnIndex(MORRO));
    }

    public static String getMotor() {
        Cursor c = getCursorCoche();
        c.moveToNext();
        return c.getString(c.getColumnIndex(MOTOR));
    }

    public static String getVolante() {
        Cursor c = getCursorCoche();
        c.moveToNext();
        return c.getString(c.getColumnIndex(VOLANTE));
    }

    public static void setAleron(String concatenado, int trMenos) {
        String aux = "UPDATE " + BDUtils.COCHE + " SET ";
        aux += BDUtils.ALERON + "='" + concatenado + "'";
        aux += " WHERE " + BDUtils.ID + "=" + getId();
        db.execSQL(aux);
        restarTuercas(trMenos);
    }

    public static void setFrenos(String concatenado, int trMenos) {
        String aux = "UPDATE " + BDUtils.COCHE + " SET ";
        aux += BDUtils.FRENOS + "='" + concatenado + "'";
        aux += " WHERE " + BDUtils.ID + "=" + getId();
        db.execSQL(aux);
        restarTuercas(trMenos);
    }

    public static void setNeumaticos(String concatenado, int trMenos) {
        String aux = "UPDATE " + BDUtils.COCHE + " SET ";
        aux += BDUtils.NEUMATICOS + "='" + concatenado + "'";
        aux += " WHERE " + BDUtils.ID + "=" + getId();
        db.execSQL(aux);
        restarTuercas(trMenos);
    }

    public static void setMorro(String concatenado, int trMenos) {
        String aux = "UPDATE " + BDUtils.COCHE + " SET ";
        aux += BDUtils.MORRO + "='" + concatenado + "'";
        aux += " WHERE " + BDUtils.ID + "=" + getId();
        db.execSQL(aux);
        restarTuercas(trMenos);
    }

    public static void setMotor(String concatenado, int trMenos) {
        String aux = "UPDATE " + BDUtils.COCHE + " SET ";
        aux += BDUtils.MOTOR + "='" + concatenado + "'";
        aux += " WHERE " + BDUtils.ID + "=" + getId();
        db.execSQL(aux);
        restarTuercas(trMenos);
    }

    public static void setVolante(String concatenado, int trMenos) {
        String aux = "UPDATE " + BDUtils.COCHE + " SET ";
        aux += BDUtils.VOLANTE + "='" + concatenado + "'";
        aux += " WHERE " + BDUtils.ID + "=" + getId();
        db.execSQL(aux);
        restarTuercas(trMenos);
    }

    private static void restarTuercas(int trMenos) {
        long trAux = getTuercas();
        String aux = "UPDATE " + BDUtils.PILOTO + " SET ";
        aux += BDUtils.TUERCAS + "=" + (trAux - trMenos);
        aux += " WHERE " + BDUtils.ID + "=" + getId();
        db.execSQL(aux);
    }

    public static boolean executeCommand(Context c) {
        Runtime runtime = Runtime.getRuntime();
        try {
            Process mIpAddrProcess = runtime.exec("/system/bin/ping -c 1 roc.servegame.com");
            int mExitValue = mIpAddrProcess.waitFor();
            if (mExitValue == 0) {
                crearPilotoOnline(c);
                return true;
            } else {
                return false;
            }
        } catch (InterruptedException ignore) {
            ignore.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return false;
    }

    public static boolean crearPilotoOnline(Context c) {
        List<NameValuePair> parametros = new ArrayList<>();
        parametros.add(new BasicNameValuePair("id", BDUtils.getId()));
        parametros.add(new BasicNameValuePair("nombre", BDUtils.getNombrePiloto()));
        parametros.add(new BasicNameValuePair("equipo", BDUtils.getNombreEquipo()));
        parametros.add(new BasicNameValuePair("trofeos", Integer.toString(BDUtils.getTrofeos())));
        parametros.add(new BasicNameValuePair("identificador", BDUtils.getIdentificadorPiloto()));
        parametros.add(new BasicNameValuePair("curva", BDUtils.getCurva()));
        parametros.add(new BasicNameValuePair("frenado", BDUtils.getFrenado()));
        parametros.add(new BasicNameValuePair("velocidad", BDUtils.getVelocidad()));
        parametros.add(new BasicNameValuePair("aleron", BDUtils.getAleron()));
        parametros.add(new BasicNameValuePair("frenos", BDUtils.getFrenos()));
        parametros.add(new BasicNameValuePair("neumaticos", BDUtils.getNeumaticos()));
        parametros.add(new BasicNameValuePair("morro", BDUtils.getMorro()));
        parametros.add(new BasicNameValuePair("motor", BDUtils.getMotor()));
        parametros.add(new BasicNameValuePair("volante", BDUtils.getVolante()));
        if (BDUtils.existeClan()) {
            parametros.add(new BasicNameValuePair("codigoclan", BDUtils.getClan()));
        } else {
            parametros.add(new BasicNameValuePair("codigoclan", "sinclan"));
        }
        try {
            String str = new CrearPilotosAsyncTask().execute(parametros).get();
            if (str == null || str.equals("")) {
                Toast.makeText(c, "No se ha podido realizar la conexion con el servidor.", Toast.LENGTH_LONG).show();
                return false;
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (NullPointerException e) {
            e.printStackTrace();
        }
        return true;
    }

    public static int getImgCircuito(int indexCircuito) {
        Cursor c = getCursorCarreras(indexCircuito);
        c.moveToNext();
        return c.getInt(c.getColumnIndex(IDIMG));
    }

    public static int getImportanciaCurvas(int indexCircuito) {
        Cursor c = getCursorCarreras(indexCircuito);
        c.moveToNext();
        return c.getInt(c.getColumnIndex(IMPORTANCIACURVA));
    }

    public static int getImportanciaFrenado(int indexCircuito) {
        Cursor c = getCursorCarreras(indexCircuito);
        c.moveToNext();
        return c.getInt(c.getColumnIndex(IMPORTANCIAFRENADO));
    }

    public static int getImportanciaVelocidad(int indexCircuito) {
        Cursor c = getCursorCarreras(indexCircuito);
        c.moveToNext();
        return c.getInt(c.getColumnIndex(IMPORTANCIAVELOCIDAD));
    }

    public static String getNombreCircuito(int indexCircuito) {
        Cursor c = getCursorCarreras(indexCircuito);
        c.moveToNext();
        return c.getString(c.getColumnIndex(NOMBRE));
    }

    public static String getPrecioCurva() {
        Cursor c = getCursorPrecios();
        c.moveToNext();
        return c.getString(c.getColumnIndex(PRECIO_ANTERIOR_C)) + ";" + c.getString(c.getColumnIndex(PRECIO_ACTUAL_C)) + ";" + c.getString(c.getColumnIndex(PRECIO_SIGUIENTE_C));
    }

    public static String getPrecioFreando() {
        Cursor c = getCursorPrecios();
        c.moveToNext();
        return c.getString(c.getColumnIndex(PRECIO_ANTERIOR_F)) + ";" + c.getString(c.getColumnIndex(PRECIO_ACTUAL_F)) + ";" + c.getString(c.getColumnIndex(PRECIO_SIGUIENTE_F));
    }

    public static String getPrecioVelocidad() {
        Cursor c = getCursorPrecios();
        c.moveToNext();
        return c.getString(c.getColumnIndex(PRECIO_ANTERIOR_V)) + ";" + c.getString(c.getColumnIndex(PRECIO_ACTUAL_V)) + ";" + c.getString(c.getColumnIndex(PRECIO_SIGUIENTE_V));
    }

    public static long getCosteCurva() {
        Cursor c = getCursorPrecios();
        c.moveToNext();
        return c.getInt(c.getColumnIndex(COSTE_C));
    }

    public static long getCosteFrenado() {
        Cursor c = getCursorPrecios();
        c.moveToNext();
        return c.getInt(c.getColumnIndex(COSTE_F));
    }

    public static long getCosteVelocidad() {
        Cursor c = getCursorPrecios();
        c.moveToNext();
        return c.getInt(c.getColumnIndex(COSTE_V));
    }

    public static void actualizarCosteCurva(long costeNuevo) {
        String aux = "UPDATE " + BDUtils.PRECIOS + " SET ";
        aux += BDUtils.COSTE_C + "=" + costeNuevo;
        aux += " WHERE " + BDUtils.ID + "=" + getId();
        db.execSQL(aux);
    }

    public static void actualizarCosteFrenado(long costeNuevo) {
        String aux = "UPDATE " + BDUtils.PRECIOS + " SET ";
        aux += BDUtils.COSTE_F + "=" + costeNuevo;
        aux += " WHERE " + BDUtils.ID + "=" + getId();
        db.execSQL(aux);
    }

    public static void actualizarCosteVelocidad(long costeNuevo) {
        String aux = "UPDATE " + BDUtils.PRECIOS + " SET ";
        aux += BDUtils.COSTE_V + "=" + costeNuevo;
        aux += " WHERE " + BDUtils.ID + "=" + getId();
        db.execSQL(aux);
    }

    public static boolean crearPiezas() {
        if (getCountPiezas() < 5) {
            inserPiezas();
            return true;
        } else if (getCountPiezas() >= 5) {
            Cursor c = getCursorPiezas();
            c.moveToNext();
            java.util.Date hoy = new java.util.Date();
            String dia = c.getString(c.getColumnIndex(DIA));
            if (!hoy.toString().substring(0, 10).equals(dia.substring(0, 10))) {
                limpiarPiezas();
                inserPiezas();
                return true;
            }
        }
        return false;
    }

    private static void limpiarPiezas() {
        db.execSQL("DELETE FROM " + BDUtils.PIEZAS);
    }

    private static void inserPiezas() {
        java.util.Date hoy = new java.util.Date();
        for (int i = 0; i < 5; i++) {
            int habCrv = 0, habFr = 0, habVl = 0, precio = 0;
            while (precio < 10000) {
                habCrv = CocheUtils.randomNeg(10);
                habFr = CocheUtils.randomNeg(10);
                habVl = CocheUtils.randomNeg(10);
                precio = (habCrv + habFr + habVl) * 15000000;
            }
            String aux = "INSERT INTO " + BDUtils.PIEZAS + " (" + PIEZAS_STR + ") VALUES (" + CocheUtils.random(5) + ", '" + CocheUtils.getNombre() + "', " + habCrv + ", " + habFr + ", " + habVl + ", " + precio + ", '" + hoy + "');";
            db.execSQL(aux);
        }
    }

    public static List<String> piezasDisponibles() {
        List<String> aux = new ArrayList<>();
        String pieza;
        Cursor c = getCursorPiezas();
        while (c.moveToNext()) {
            int tipo = c.getInt(c.getColumnIndex(TIPO));
            switch (tipo) {
                case 1:
                    pieza = "Alerón";
                    break;
                case 2:
                    pieza = "Frenos";
                    break;
                case 3:
                    pieza = "Neumáticos";
                    break;
                case 4:
                    pieza = "Morro";
                    break;
                case 5:
                    pieza = "Motor";
                    break;
                default:
                    pieza = "Volante";
            }
            pieza += ":" + c.getString(c.getColumnIndex(NOMBRE)) + ":" + c.getString(c.getColumnIndex(CURVA)) + ":" + c.getString(c.getColumnIndex(FRENADO)) + ":" + c.getString(c.getColumnIndex(VELOCIDAD)) + ":" + c.getInt(c.getColumnIndex(PRECIO));
            aux.add(pieza);
        }
        return aux;
    }

    public static boolean existeClan() {
        Cursor c = getCursorClan();
        return c.moveToNext();
    }

    public static String getClan() {
        Cursor c = getCursorClan();
        c.moveToNext();
        return c.getString(c.getColumnIndex(CODIGO));
    }

    public static void setClan(String text) {
        if (existeClan()) {
            String aux = "UPDATE " + BDUtils.CLAN + " SET ";
            aux += BDUtils.CODIGO + "='" + text + "'";
            aux += " WHERE " + BDUtils.ID + "=" + getId();
            db.execSQL(aux);
        } else {
            String aux = "INSERT INTO " + BDUtils.CLAN + " (" + CLAN_STR + ") VALUES (" + getId() + ", '" + text + "');";
            db.execSQL(aux);
        }
    }
}
