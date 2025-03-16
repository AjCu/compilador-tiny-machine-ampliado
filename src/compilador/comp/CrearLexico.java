package comp;

import java.io.File;

public class CrearLexico {

    public static void main(String[] args) {
        String rutaFlexFile = "C:/Development/Personal AJCU/UNET/compilador-tiny-machine-ampliado/src/especificacion/lexico.flex";
        generarLexer(rutaFlexFile);

    }

    public static void generarLexer(String ruta) {
        File file = new File(ruta);
        JFlex.Main.generate(file);
    }
}