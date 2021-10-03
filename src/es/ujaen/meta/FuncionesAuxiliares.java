/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package es.ujaen.meta;

import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;

/**
 *
 * @author admin
 */
public class FuncionesAuxiliares {
    public static void guardarArchivo(String ruta, String texto) {
        FileWriter fichero = null;
        PrintWriter pw = null;
        try {
            fichero = new FileWriter(ruta);
            pw = new PrintWriter(fichero);
            pw.print(texto);
        } catch (IOException e) {

        } finally {
            try {
                if (null != fichero) {
                    fichero.close();
                }
            } catch (IOException e2) {
            }
        }
    }

    public static void muestraArray(int array[]) {
        for (int i = 0; i < array.length; i++) {
            System.out.printf(array[i] + " ");
        }
    }
    
    public static <T extends Object> void muestraLista(ArrayList<T> lista) {
        for (int i = 0; i < lista.size(); i++) {
            System.out.printf(lista.get(i).toString() + " | ");
        }
    }
    
    public static void muestraListaInt(ArrayList<Integer> lista) {
        for (int i = 0; i < lista.size(); i++) {
            System.out.printf(lista.get(i) + "  ");
        }
    }


    public static void muestraMatriz(int matriz[][]) {
        for (int i = 0; i < matriz.length; i++) {
            for (int j = 0; j < matriz.length; j++) {
                System.out.printf(matriz[i][j] + " ");
            }
            System.out.println("");
        }
    }
}
