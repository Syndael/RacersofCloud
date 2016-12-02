package utils;

public class CocheUtils {

    static String[] marcas = {"SMB", "MR", "PW", "RCG", "MiKOS", "LR", "SuPR", "GaL", "VSS", "QF"};

    public static String getNombre() {
        switch (random(3)) {
            case 1:
                return marcas[random(marcas.length - 1)] + " - M" + random(35);
            case 2:
                return marcas[random(marcas.length - 1)] + " T" + random(40, 30);
            default:
                return marcas[random(marcas.length - 1)] + " X-" + random(200, 100);
        }

    }

    public static int random(int aux) {
        return (int) (Math.round(Math.random() * aux));
    }

    public static int random(int aux, int aux2) {
        return (int) (Math.round(Math.random() * aux + aux2));
    }

    public static int randomNeg(int aux) {
        aux += 5;
        int rndm = (int) (Math.round(Math.random() * aux));
        rndm -= 5;
        return rndm;
    }
}
