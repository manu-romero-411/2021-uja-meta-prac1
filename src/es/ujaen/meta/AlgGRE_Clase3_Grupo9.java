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
public class AlgGRE_Clase3_Grupo9 {

    private final long inicio;
    private long fin;
    private ArrayList<Integer> conjunto;
    private int costeConjunto;
    private final Archivodedatos archivo;

    //Constructor de la clase
    public AlgGRE_Clase3_Grupo9(Archivodedatos archivo) {
        inicio = System.currentTimeMillis();
        this.conjunto = new ArrayList<>();
        this.costeConjunto = 0;
        this.archivo = archivo;
    }

    //Calcula el greedy y su coste
    public void calculaGreedy() {

        creaConjunto(archivo.getMatriz1(), archivo.getMatriz2());
        costeConjunto = calculaCosteConjunto(conjunto);
    }

    //Calcula el coste de un conjunto dado
    private int calculaCosteConjunto(ArrayList<Integer> conjunto) {
        int coste = 0;
        for (int i = 0; i < conjunto.size(); i++) {
            for (int j = 0; j < conjunto.size(); j++) {
                coste += archivo.getMatriz1()[i][j] * archivo.getMatriz2()[conjunto.get(i)][conjunto.get(j)];
            }
        }
        return coste;
    }

    //Crea el conjunto del greedy
    private void creaConjunto(int matrizFlujo[][], int matrizDistancia[][]) {
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
        this.conjunto = terminado;
    }

    //Función auxiliar que suma las filas de las matrices
    public ArrayList<Integer> sumaFilas(int matriz[][]) {
        ArrayList<Integer> arrayS = new ArrayList<>();
        int sumador = 0;
        for (int i = 0; i < matriz.length; i++) {
            for (int j = 0; j < matriz.length; j++) {
                sumador += matriz[i][j];
            }
            arrayS.add(sumador);
            sumador = 0;
        }
        return arrayS;
    }
    
    //Funcion para añadir la informacion a los logs
    public String muestraDatos() {
        fin = System.currentTimeMillis();
        String aux = new String();
        for (int i = 0; i < conjunto.size(); i++) {
            aux += conjunto.get(i) + "  ";
        }
        System.out.println();
        return "GREEDY \nEl conjunto de archivos de datos " + archivo.getNombre() + " tiene un coste de " + costeConjunto
                + " con un tiempo de ejecucion de: " + (fin - inicio) + " milisegundos y es el siguiente: \n" + aux + "\n";
    }

    //Getters y Setters
    
    public ArrayList<Integer> getConjunto() {
        return conjunto;
    }

    public int getCosteConjunto() {
        return costeConjunto;
    }

}
