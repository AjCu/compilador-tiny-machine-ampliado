package comp;

import java.io.IOException;

import java_cup.internal_error;

public class CrearCup {
    public static void main(String[] args) throws internal_error, IOException, Exception {
        generarCup();

    }



    public static void generarCup() throws internal_error, IOException, Exception {
        String[] rutaCup = {"-parser","parser","C:/Development/Personal AJCU/UNET/compilador-tiny-machine-ampliado/src/especificacion/sintactico.cup"};
        java_cup.Main.main(rutaCup);
    }
}