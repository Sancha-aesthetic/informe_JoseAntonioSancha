package es.etg.dam.informe;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;

public class Comando {

    private static final String ERRORINFORME = "Error al crear o guardar el informe.";
    public static final String MENSAJE_ERROR = "Fallo al ejecutar el comando: ";

    public String ejecutarComando(String orden) {
        StringBuilder salida = new StringBuilder();

        try {
            Process p = Runtime.getRuntime().exec(orden);
            BufferedReader br = new BufferedReader(new InputStreamReader(p.getInputStream()));

            String linea;
            while ((linea = br.readLine()) != null) {
                salida.append(linea).append("\n");
            }

            br.close();
            p.waitFor();

        } catch (IOException | InterruptedException e) {
            salida.append(MENSAJE_ERROR).append(orden).append("\n");
        }

        return salida.toString();
    }

    public void escribirInforme(String texto, String ruta) {

        BufferedWriter bw = null;

        try {
            bw = new BufferedWriter(new FileWriter(ruta));
            bw.write(texto);
            bw.newLine();
            bw.close();
        } catch (IOException e) {
            System.out.println(ERRORINFORME);
        }
    }
}
