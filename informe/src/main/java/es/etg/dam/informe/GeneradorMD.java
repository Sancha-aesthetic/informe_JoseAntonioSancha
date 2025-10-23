package es.etg.dam.informe;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;

public class GeneradorMD implements CreadorInforme {
    private static final String ERROR ="Error al guardar el informe";
    public static final String RUTA_INFORME = "informe.md";
    private StringBuilder contenido = new StringBuilder();

    @Override
    public void escribirInforme(String texto) {
        contenido.append(texto).append("\n");
    }

    public void agregarContenido(String texto) {
        escribirInforme(texto);
    }

    public void guardarArchivo() {
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(RUTA_INFORME))) {
            bw.write(contenido.toString());
        } catch (IOException e) {
            System.out.println(ERROR);
        }
    }
}
