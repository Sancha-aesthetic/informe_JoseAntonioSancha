package es.etg.dam.informe;

public class Main {

    private static final String[] LISTA_COMANDOS = {"ps", "df", "free"};
    private static final String LAZNZARC = "Lanzando comando";
    private static final String SALIDAC = "Salida del comando '%s':\n\n%s\n\n";


    public static void main(String[] args) {

        Comando comando = new Comando();
        GeneradorMD creadorInforme = new GeneradorMD();

        for (String cmd : LISTA_COMANDOS) {
            System.out.println(LAZNZARC + cmd);

            String resultado = comando.ejecutarComando(cmd);
            System.out.println(resultado);

            String textoInforme = String.format(SALIDAC , cmd, resultado);
            creadorInforme.agregarContenido(textoInforme);
        }

        creadorInforme.guardarArchivo();
        System.out.println( GeneradorMD.RUTA_INFORME);
    }
}
