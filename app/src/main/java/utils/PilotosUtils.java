package utils;

public class PilotosUtils {
    char[] con = {'b', 'c', 'd', 'f', 'g', 'h', 'j', 'k', 'l', 'm', 'n', 'p', 'q', 'r', 's', 't', 'v', 'w', 'x', 'y', 'z'};
    char[] con2 = {'c', 'd', 'f', 'k', 'l', 'm', 'n', 'p', 'r', 's', 't', 'v', 'y', 'z'};
    char[] voc = {'a', 'e', 'i', 'o', 'u'};

    private char letAleC() {
        char temp = 'a';
        int tempnu = (int) Math.round(Math.random() * 20);
        temp = con[tempnu];
        return temp;
    }

    private char letAleCF() {
        char temp = 'a';
        int tempnu = (int) Math.round(Math.random() * 13);
        temp = con2[tempnu];
        return temp;
    }

    private char letAleV() {
        char temp = 'a';
        int tempnu = (int) Math.round(Math.random() * 4);
        temp = voc[tempnu];
        return temp;
    }

    private String pal5() {
        String temp = "", tempM = "";
        tempM = "" + letAleC();
        temp = "" + tempM.toUpperCase() + letAleV() + letAleC() + letAleV() + letAleCF();
        return temp;
    }

    private String pal7() {
        String temp = "", tempM = "";
        tempM = "" + letAleC();
        temp = "" + tempM.toUpperCase() + letAleV() + letAleC() + letAleV() + letAleC() + letAleV();
        return temp;
    }

    private String pal81() {
        String temp = "", tempM = "";
        tempM = "" + letAleC();
        temp = "" + tempM.toUpperCase() + letAleV() + letAleC() + letAleV() + letAleC() + letAleC() + letAleV() + letAleCF();
        return temp;
    }

    private String pal82() {
        String temp = "", tempM = "";
        tempM = "" + letAleC();
        temp = "" + tempM.toUpperCase() + letAleV() + letAleC() + letAleV() + letAleV() + letAleC() + letAleV() + letAleCF();
        return temp;
    }

    public String getNombreAleatorio() {
        int temp = (int) Math.round(Math.random() * 3);
        if (temp == 0) {
            return pal5();
        } else if (temp == 1) {
            return pal7();
        } else if (temp == 2) {
            return pal81();
        } else {
            return pal82();
        }
    }

    public String getHabilidadesAleatorias(int dificultad) {
        return (getHabRandom(dificultad) + ";" + getHabRandom(dificultad) + ";" + getHabRandom(dificultad));
    }

    private int getHabRandom(int dificultad) {
        int habRandom = 0;
        switch (dificultad) {
            case 1:
                habRandom = ((int) Math.round(Math.random() * 14) + 1);
                break;
            case 2:
                habRandom = ((int) Math.round(Math.random() * 15) + 15);
                break;
            case 3:
                habRandom = ((int) Math.round(Math.random() * 15) + 25);
                break;
            case 4:
                habRandom = ((int) Math.round(Math.random() * 20) + 20);
                break;
        }
        return habRandom;
    }
}
