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
1. Recorre la lista de comandos.

1. Ejecuta cada comando con la clase Comando.

1. Guarda la salida formateada en un informe usando GeneradorMD.

1. Al final, guarda el archivo y muestra su ruta.
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
- **ejecutarComando**
1. Usa Runtime.getRuntime().exec(orden) para lanzar el comando del sistema.

1. Crea un *BufferedReader* para leer la salida estándar del comando.

1. Guarda cada línea leída en un *StringBuilder*.

1. Espera a que el proceso termine.

1. Si hay un **error** devuelve un **mensaje** indicando el fallo.

1. Devuelve la salida del comando como un String.

- **escribirInforme**
1. Crea un archivo en la ruta indicada.

1. Escribe el texto recibido como parámetro.

1. Cierra el archivo.

1. Si ocurre un **error** al guardar *muestra el mensaje*

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
1. Crea y guarda un archivo Markdown .

1. Va acumulando texto en un **StringBuilder**ss.

1. Uso *agregarContenido()* para añadir información.

1. Con *guardarArchivo()* escribe todo en el archivo.

1. Si hay un error al guardar muestra un mensaje.

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