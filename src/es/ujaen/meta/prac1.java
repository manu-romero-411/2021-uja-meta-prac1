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
import java.util.Random;
import javafx.util.Pair;

/**
 *
 * @author Manuel
 */
public class prac1 {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        Configurador config = new Configurador(args[0]);
        ArrayList<Archivodedatos> arrayA = new ArrayList<>();
        Log log = new Log(config.getGuardar());
        Random random = new Random(config.getSemillas().get(0));
        System.out.println(config.getArchivos());

        //Añade a la lista de archivos los diferentes archivos de datos
        for (int i = 0; i < config.getArchivos().size(); i++) {
            Archivodedatos archivo = new Archivodedatos(config.getArchivos().get(i));
            arrayA.add(archivo);
        }

        System.out.println("GREEDY");
        for (int i = 0; i < arrayA.size(); i++) {
            AlgGRE_Clase3_Grupo9 greedy = new AlgGRE_Clase3_Grupo9(arrayA.get(i));
            greedy.calculaGreedy();
            log.addTexto(greedy.muestraDatos());
        }

        System.out.println("");
        System.out.println("PRIMERO EL MEJOR IT");
        for (int i = 0; i < arrayA.size(); i++) {
            AlgPMDLBit_Clase3_Grupo9 primero = new AlgPMDLBit_Clase3_Grupo9(arrayA.get(i), config.getIteraciones());
            primero.calculaPrimeroElMejor();
        }

        System.out.println("");
        System.out.println("PRIMERO EL MEJOR RAN");
        for (int i = 0; i < arrayA.size(); i++) {
            AlgPMDLBrandom_Clase3_Grupo9 primeroAle = new AlgPMDLBrandom_Clase3_Grupo9(arrayA.get(i), config.getIteraciones(), random);
            primeroAle.calculaPrimeroElMejor();
        }

        System.out.println("");
        System.out.println("MULTIARRANQUE");
        for (int i = 0; i < arrayA.size(); i++) {
            AlgMA_Clase3_Grupo9 multiA = new AlgMA_Clase3_Grupo9(arrayA.get(i), config.getMaLonguitudLRC(), config.getMaMejoresUnidades(), config.getTamLista(), random);
            multiA.calculaMultiarranque();
            ArrayList<Pair<Integer, Integer>> aux= multiA.getLRC();
            for (int j = 0; j < aux.size(); j++) {
                System.out.println("A: " + aux.get(i).getKey()+ " B: " + aux.get(i).getValue());
            }
        }

        log.guardaLog();
    }

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

    public static void muestraMatriz(int matriz[][]) {
        for (int i = 0; i < matriz.length; i++) {
            for (int j = 0; j < matriz.length; j++) {
                System.out.printf(matriz[i][j] + " ");
            }
            System.out.println("");
        }
    }

}
