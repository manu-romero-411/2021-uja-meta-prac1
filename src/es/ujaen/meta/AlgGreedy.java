/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package es.ujaen.meta;

import java.util.ArrayList;

/**
 *
 * @author admin
 */

public class AlgGreedy {

    private final ArrayList<ArrayList<Integer>> conjuntos;
    private final ArrayList<Integer> costeConjuntos;
    private ArrayList<ArchivoDatos> archivos;

    // CONSTRUCTOR POR DEFECTO
    public AlgGreedy() {
        this.conjuntos = new ArrayList<>();
        this.costeConjuntos = new ArrayList<>();
        this.archivos = new ArrayList<>();
    }

    // CONSTRUCTOR DADA UNA LISTA DE CONJUNTOS
    public AlgGreedy(ArrayList<ArrayList<Integer>> conjuntos) {
        this.conjuntos = conjuntos;
        this.costeConjuntos = new ArrayList<>();
    }

    public void insertaArchivos(ArrayList<ArchivoDatos> archivo) {
        this.archivos = archivo;
    }
    
    public void calculaGreedy() {
        System.out.println(archivos.size());
        for (int i = 0; i < archivos.size(); i++) {
            conjuntos.add(creaConjunto(archivos.get(i).getMatriz1(), archivos.get(i).getMatriz2()));
        }

        // POR CADA CONJUNTO GENERADO, SE CALCULARÁ EL COSTE Y SE MOSTRARÁ EN PANTALLA
        for (int i = 0; i < conjuntos.size(); i++) {
            costeConjuntos.add(calculaCosteConjunto(conjuntos.get(i), archivos.get(i).getMatriz1(), archivos.get(i).getMatriz2()));
            System.out.println("El conjunto " + i + " tiene un coste de " + costeConjuntos.get(i) + " y es el siguiente: ");
            FuncionesAuxiliares.muestraListaInt(conjuntos.get(i));
            System.out.println();
        }
        
    }

    private static ArrayList<Integer> creaConjunto(int matrizFlujo[][], int matrizDistancia[][]) {
        
        // ESTA FUNCIÓN SUMARÁ TODO LO DE CADA FILA DE LAS MATRICES DE FLUJOS Y 
        // DISTANCIAS, Y EMPAREJARÁ AMBOS RESULTADOS (QUE ESTARÁN EN 2 LISTAS),
        // DE TAL FORMA QUE A LA LOCALIZACIÓN CON EL FLUJO MÁS ALTO (REPRESENTADA
        // CON EL ÍNDICE DE ACCESO A LA LISTA) LE CORRESPONDERÁ EL MENOR VALOR 
        // DE DISTANCIA, Y DEVOLVERÁ UN ARRAYLIST QUE SERÁ EL CONJUNTO CALCULADO
        // EN BASE A LA MATRIZ DE FLUJOS Y DISTANCIAS DADA
        
        ArrayList<Integer> terminado = new ArrayList<>();
        ArrayList<Integer> auxF;
        ArrayList<Integer> auxD;

        for (int i = 0; i < matrizFlujo.length; i++) {
            terminado.add(0);
        }

        auxF = sumaFilas(matrizFlujo);
        auxD = sumaFilas(matrizDistancia);

        for (int j = 0; j < auxF.size(); j++) {
            int mayorF = Integer.MIN_VALUE;
            int menorD = Integer.MAX_VALUE;
            int posF = 0, posD = 0;
            for (int i = 0; i < auxF.size(); i++) {
                if (auxF.get(i) > mayorF) {
                    mayorF = auxF.get(i);
                    posF = i;
                }

                if (auxD.get(i) < menorD) {
                    menorD = auxD.get(i);
                    posD = i;
                }
            }
            terminado.set(posF, posD);
            auxF.set(posF, Integer.MIN_VALUE);
            auxD.set(posD, Integer.MAX_VALUE);
        }

        return terminado;
    }
    
    private int calculaCosteConjunto(ArrayList<Integer> conjunto, int matrizFlujo[][], int matrizDistancia[][]) {
        
        // EL COSTE DE CADA CONJUNTO SE CALCULA ACUMULANDO LOS PRODUCTOS DE 
        // UN FLUJO DADO ENTRE DOS LOCALIZACIONES Y LA DISTANCIA ENTRE DICHAS
        // LOCALIZACIONES
        
        int coste = 0;
        for (int i = 0; i < conjunto.size(); i++) {
            for (int j = 0; j < conjunto.size(); j++) {
                coste += matrizFlujo[i][j] * matrizDistancia[conjunto.get(i)][conjunto.get(j)];
            }
        }
        return coste;
    }

    private static ArrayList<Integer> sumaFilas(int matriz[][]) {
        ArrayList<Integer> listaSuma = new ArrayList<>();
        int sumador = 0;
        for (int i = 0; i < matriz.length; i++) {
            for (int j = 0; j < matriz.length; j++) {
                sumador += matriz[i][j];
            }
            listaSuma.add(sumador);
            sumador = 0;
        }
        return listaSuma;
    }

    public ArrayList<ArrayList<Integer>> getConjuntos() {
        return conjuntos;
    }

    public ArrayList<Integer> getCosteConjuntos() {
        return costeConjuntos;
    }

}
