package main.java.com.Proyecto.manejo;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

public class ManejoDatos {

    /**
     * Escribe una línea en un archivo de texto
     * @param nombreArchivo Nombre del archivo en el que se va a escribir
     * @param contenido Contenido a escribir
     * @throws IOException Si ocurre un error al escribir el archivo
     */
    public static void escribirArchivo(String nombreArchivo, String contenido) throws IOException {
        BufferedWriter bw = new BufferedWriter(new FileWriter(nombreArchivo, true)); // 'true' para habilitar el modo de
                                                                                     // adjuntar
        bw.write(contenido);
        bw.newLine(); // Agregar nueva linea despues del contenido
        bw.close();
    }

    /**
     * Lee un archivo de texto y devuelve su contenido
     * @param nombreArchivo Nombre del archivo a leer
     * @return Contenido del archivo
     * @throws IOException Si ocurre un error al leer el archivo
     */
    public static String leerArchivo(String nombreArchivo) throws IOException {
        StringBuilder contenido = new StringBuilder();
        try (BufferedReader br = new BufferedReader(new FileReader(nombreArchivo))) {
            String linea;
            while ((linea = br.readLine()) != null) {
                contenido.append(linea).append("\n");
            }
        }
        return contenido.toString();
    }

    /**
     * Busca la primera línea en un archivo de texto que contenga el parámetro ingresado
     * @param nombreArchivo Nombre del archivo en el que se va a buscar
     * @param busqueda Palabra o frase a buscar
     * @return La primera línea que contenga la palabra o frase buscada
     * @throws IOException Si ocurre un error al leer el archivo
     */
    public static String busquedaEnArchivo(String nombreArchivo, String busqueda) throws IOException {
        try (BufferedReader br = new BufferedReader(new FileReader(nombreArchivo))) {
            String linea;
            while ((linea = br.readLine()) != null) {
                if (linea.contains(busqueda)) {
                    return linea;
                }
            }
        }
        return null;
    }

    /**
     * Busca todas las línea en un archivo de texto que contengan el parámetro ingresado
     * @param nombreArchivo Nombre del archivo en el que se va a buscar
     * @param busqueda Palabra o frase a buscar
     * @return Un arreglo con todas las líneas que contengan la palabra o frase buscada
     * @throws IOException Si ocurre un error al leer el archivo
     */
    public static ArrayList<String> busquedaAvanzada(String nombreArchivo, String busqueda) throws IOException {
        ArrayList<String> lineas = new ArrayList<>();
        try (BufferedReader br = new BufferedReader(new FileReader(nombreArchivo))) {
            String linea;
            while ((linea = br.readLine()) != null) {
                if (linea.contains(busqueda)) {
                    lineas.add(linea);
                }
            }
        }
        return lineas;
    }

    /**
     * Modifica una línea en un archivo de texto
     * @param nombreArchivo Nombre del archivo en el que se va a modificar
     * @param lineaVieja Línea a modificar
     * @param lineaNueva Nueva línea
     * @throws IOException Si ocurre un error al modificar el archivo
     */
    public static void modificarLineaDeArchivo(String nombreArchivo, String lineaVieja, String lineaNueva) throws IOException {
        File archivoOriginal = new File(nombreArchivo);
        File archivoTemporal = new File(nombreArchivo + "(1).txt");

        try (BufferedReader br = new BufferedReader(new FileReader(archivoOriginal));
                BufferedWriter bw = new BufferedWriter(new FileWriter(archivoTemporal))) {

            String linea;
            while ((linea = br.readLine()) != null) {
                if (linea.equals(lineaVieja)) {
                    linea = lineaNueva;
                }
                bw.write(linea);
                bw.newLine();
            }
        }

        if (!archivoOriginal.delete()) {
            throw new IOException("Error al modificar el archivo: " + nombreArchivo);
        }
        if (!archivoTemporal.renameTo(archivoOriginal)) {
            throw new IOException("Error al modificar el archivo: " + nombreArchivo);
        }
    }
}