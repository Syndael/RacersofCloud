package utils;

/**
 * Created by cloud on 24/05/2016.
 */
public class TallerUtils {
    private static boolean mejoraPiloto;
    private static boolean mejoraCoche;

    public static boolean isMejoraPiloto() {
        return mejoraPiloto;
    }

    public static boolean isMejoraCoche() {
        return mejoraCoche;
    }

    public static void setMejorapiloto(boolean mejoraPiloto) {
        TallerUtils.mejoraPiloto = mejoraPiloto;
    }

    public static void setMejoraCoche(boolean mejoraCoche) {
        TallerUtils.mejoraCoche = mejoraCoche;
    }
}
