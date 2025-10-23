# Índice

- [Índice](#índice)
- [Informe](#informe)
  - [EXPLICACION PROGRAMA](#explicacion-programa)
  - [CODIGO ARCHIVOS](#codigo-archivos)
    - [Main](#main)
    - [Comando](#comando)
    - [GeneradorMD](#generadormd)
    - [CreadorInforme](#creadorinforme)

# Informe


## EXPLICACION PROGRAMA

- En este programa lo que hemos hecho ha sido crear 4 archivos distintos para que en conjunto hagan que ustilizando:
  - **free**
  - **ps**
  - **df**
- Creen un fichero con extension **MD(Mark Down)** en el que se guarden **todos los datos** que den los comandos
## CODIGO ARCHIVOS
---
### Main
```java 
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
```
---

### Comando
``` java
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
```
---
### GeneradorMD
```java
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
```
---
### CreadorInforme

```java 
    public interface CreadorInforme {

        public void escribirInforme(String texto);
    }
```