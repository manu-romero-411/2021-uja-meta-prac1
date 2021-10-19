/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package es.ujaen.meta;

import static es.ujaen.meta.prac1.calculaCosteConjunto;
import static es.ujaen.meta.prac1.creaConjunto;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import static java.util.Comparator.comparingInt;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Random;
import java.util.concurrent.CountDownLatch;
import static java.util.stream.Collectors.toMap;

/**
 *
 * @author Manuel
 */
public class Voraz implements Runnable {
    private String archivo;
    private int[] permutacion;
    private Random aleatorio;
    private ArrayList<ArchivoDatos> archivosMatrices;
    private StringBuilder log;
    private CountDownLatch countdownlutch;
    private int coste;
    private ArchivoDatos matrices;

    
    public Voraz(ArchivoDatos matriz, String sln, CountDownLatch countdownlutch, long seed){
        this.matrices = matriz;
        this.archivo = sln.split(".sln")[0];
        String linea;
        coste=0;
        FileReader f = null;
        try {
            f = new FileReader(sln);
            BufferedReader lectorLinea = new BufferedReader(f);
            linea = lectorLinea.readLine();
            int errores=0;
            
            String[] splitNumero = linea.split(" ");
            for (int j = 1; j < splitNumero.length; ++j){
                try {
                    coste = Integer.parseInt(splitNumero[j]);
                } catch (NumberFormatException ex) {
                    ++errores; 
                }
            }
                        
            int numeroPermutaciones = Integer.parseInt(linea.split(" ")[1]);
            permutacion = new int[numeroPermutaciones];
            linea = lectorLinea.readLine();
            String[] split = linea.split(" ");
            errores = 0;
            for (int j = 0; j <= numeroPermutaciones; ++j){
                try {
                    permutacion[j - errores] = Integer.parseInt(split[j]);
                } catch (NumberFormatException ex) {
                    ++errores; 
                }
            }
            
            for (int i = 0; i < permutacion.length; ++i){
                System.out.print(permutacion[i]+" ");
            }
            System.out.println("");
            
        } catch (IOException e){
            System.out.println(e);
        }
    }

    /**
     * @return the archivo
     */
    public String getArchivo() {
        return archivo;
    }

    /**
     * @param archivo the archivo to set
     */
    public void setArchivo(String archivo) {
        this.archivo = archivo;
    }

    /**
     * @return the permutacion
     */
    public int[] getPermutacion() {
        return permutacion;
    }

    /**
     * @param permutacion the permutacion to set
     */
    public void setPermutacion(int[] permutacion) {
        this.permutacion = permutacion;
    }
    
      
    @Override
    public void run() {
        //throw new UnsupportedOperationException("Not supported yet.");
        // Inicialización aleatoria de la primera solución (se necesitará en todas las prácticas)
        long tiempoInicio = System.currentTimeMillis();

        // Ejecución de la metaheurística
        // se recomienda ir diciendo en el log lo que está pasando en cada ejecución
        //int miCoste = calculaCosteConjunto();
        
        log.append("Iteración Y \n Coste mejor: " + coste + "\n Se acepta solución generada con coste XXX..... \n");
        
        long tiempoFin = System.currentTimeMillis();
        
        // Finalización de la metaheurística (escribir en el log el costo final y la duración
        //log.append("El coste final es " + miCoste +" \n Duración: \n" + (tiempoFin - tiempoInicio));
        countdownlutch.countDown();
    }
    public String getLog(){
        return log.toString();
    }
    
    /* public static void generarConjunto(){
        ArrayList<ArrayList<Integer>> conjunto = new ArrayList<>();

        for (int i = 0; i < arrayA.size(); i++) {
            conjunto.add(creaConjunto(flujosO.get(i), distanciasO.get(i)));
        }

        for (int i = 0; i < conjunto.size(); i++) {
            int costeConjunto = calculaCosteConjunto(conjunto.get(i), arrayA.get(i).getMatriz1(), arrayA.get(i).getMatriz2());
            System.out.println("El conjunto " + i + " tiene un coste de " + costeConjunto + " y es el siguiente: ");
            for (int j = 0; j < conjunto.get(i).size(); j++) {
                System.out.print(conjunto.get(i).get(j) + "  ");
            }
            System.out.println();

        }

    }
    
    public static int calculaCosteConjunto(ArrayList<Integer> conjunto, int matrizFlujo[][], int matrizDistancia[][]) {
        int costeConjunto = 0;
        for (int i = 0; i < conjunto.size(); i++) {
            for (int j = 0; j < conjunto.size(); j++) {
                costeConjunto += matrizFlujo[i][j] * matrizDistancia[conjunto.get(i)][conjunto.get(j)];
            }
        }
        return costeConjunto;
    }
    
    public static ArrayList<Integer> creaConjunto(Map<Integer, Integer> flujos, Map<Integer, Integer> distancia) {
        ArrayList<Integer> auxf = new ArrayList<>();
        flujos.forEach((k, v) -> auxf.add(k));
        ArrayList<Integer> auxd = new ArrayList<>();
        distancia.forEach((k, v) -> auxd.add(k));
        ArrayList<Integer> terminado = new ArrayList<>();
        for (int i = 0; i < flujos.size(); i++) {
            terminado.add(0);
        }
        for (int i = 0, j = flujos.size() - 1; i < flujos.size(); i++, j--) {
            terminado.set(auxf.get(i), auxd.get(j));
        }
        return terminado;
    }
    
    public static Map<Integer, Integer> ordenaValor(int matriz[][]) {
        int sumador = 0;
        Map<Integer, Integer> mapaA = new HashMap<>();
        for (int i = 0; i < matriz.length; i++) {
            for (int j = 0; j < matriz.length; j++) {
                sumador += matriz[i][j];
            }
            mapaA.put(i, sumador);
            sumador = 0;
        }

        Map<Integer, Integer> sortedMap = mapaA.entrySet().stream()
                .sorted(comparingInt(e -> -1 * e.getValue()))
                .collect(toMap(
                        Map.Entry::getKey,
                        Map.Entry::getValue,
                        (a, b) -> {
                            throw new AssertionError();
                        },
                        LinkedHashMap::new
                ));
        return sortedMap;
    }*/
}

