/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package es.ujaen.meta;

import java.util.ArrayList;
import java.util.Random;

/**
 *
 * @author admin
 */
public class AlgPMDLBrandom_Clase3_Grupo9 {

    private ArrayList<Integer> conjunto;
    private int mejorCoste;
    private final Archivodedatos archivo;
    private final int iteraciones;
    private final Random random;
    private int aleatorio;

    public AlgPMDLBrandom_Clase3_Grupo9(Archivodedatos archivo, int iteraciones, Random random) {
        this.conjunto = new ArrayList<>();
        this.mejorCoste = 0;
        this.archivo = archivo;
        this.iteraciones = iteraciones;
        this.random = random;
    }

    // Calcula el primero el mejor random
    public void calculaPrimeroElMejor() {
        AlgGRE_Clase3_Grupo9 greedyA = new AlgGRE_Clase3_Grupo9(archivo);
        greedyA.calculaGreedy();
        this.conjunto = greedyA.getConjunto();
        this.mejorCoste = greedyA.getCosteConjunto();
        System.out.println("El mejor coste de " + archivo.getNombre() + " es: " + mejorCoste);
        boolean improve_flag = true;
        ArrayList<Boolean> dlb = new ArrayList<>();
        for (int i = 0; i < conjunto.size(); i++) {
            dlb.add(Boolean.FALSE);
        }
        int fin = aleatorio = random.nextInt(dlb.size());
        System.out.println("El valor de inicio es: " + fin);
        boolean dlbCompleto = false;
        int k = 0;
        int cam=0;
        while (k < iteraciones && !dlbCompleto) {
            int i = fin;
            if (dlb.get(i % dlb.size()) == Boolean.FALSE) {
                improve_flag = false;
                int contJ = dlb.size();
                for (int j = i % dlb.size(); contJ > 0 && improve_flag == false; j++) {
                    if (i % dlb.size() != j) {
                        if (checkMove(i % dlb.size(), j % dlb.size())) {
                            applyMove(i % dlb.size(), j % dlb.size(), greedyA);
                            cam++;
                            dlb.set(i % dlb.size(), Boolean.FALSE);
                            dlb.set(j % dlb.size(), Boolean.FALSE);
                            improve_flag = true;
                            fin = conjunto.get(i);
                            if (compruebaDLB(dlb)) {
                                dlbCompleto = true;
                            }
                        }
                    }
                    contJ--;
                }
                if (improve_flag == false) {
                    dlb.set(i, true);
                }

            }
            k++;
        }
        System.out.println("es.ujaen.meta.AlgPMDLBrandom_Clase3_Grupo9.calculaPrimeroElMejor(): "+ cam);
        mejorCoste = greedyA.calculaCosteConjunto(conjunto, archivo.getMatriz1(), archivo.getMatriz2());
        muestraDatos();
    }

    // Muestra los datos (futuro log)
    private void muestraDatos() {
        System.out.println("El conjunto de archivos de datos " + archivo.getNombre() + " con semilla: " + " tiene un coste de " + mejorCoste + " y es el siguiente: ");
        for (int i = 0; i < conjunto.size(); i++) {
            System.out.print(conjunto.get(i) + "  ");
        }
        System.out.println();
    }

    // Comprueba si el movimiento mejora
    private boolean checkMove(int posI, int posJ) {
        ArrayList<Integer> auxConjunto = new ArrayList<>();
        for (int i = 0; i < conjunto.size(); i++) {
            auxConjunto.add(conjunto.get(i));
        }
        int sum = 0;
        int posAI = conjunto.get(posI);
        int posAJ = conjunto.get(posJ);
        auxConjunto.set(posI, posAJ);
        auxConjunto.set(posJ, posAI);
        int matrizF[][] = archivo.getMatriz1();
        int matrizD[][] = archivo.getMatriz2();

        for (int k = 0; k < auxConjunto.size(); k++) {
            if (k != posI && k != posJ) {
                sum += (matrizF[posI][k] * (matrizD[auxConjunto.get(posJ)][auxConjunto.get(k)] - matrizD[conjunto.get(posI)][conjunto.get(k)])
                        + matrizF[posJ][k] * (matrizD[auxConjunto.get(posI)][auxConjunto.get(k)] - matrizD[conjunto.get(posJ)][conjunto.get(k)])
                        + matrizF[k][posI] * (matrizD[auxConjunto.get(k)][auxConjunto.get(posJ)] - matrizD[conjunto.get(k)][conjunto.get(posI)])
                        + matrizF[k][posI] * (matrizD[auxConjunto.get(k)][auxConjunto.get(posI)] - matrizD[conjunto.get(k)][conjunto.get(posJ)]));
            }
        }
//        System.out.println("es.ujaen.meta.AlgPMDLBit_Clase3_Grupo9.checkMove() " + sum + " posI: " + posI + " posJ: " + posJ);
        if (sum > 0) {
            return false;
        } else {
            return true;
        }
    }

    // Aplica el movimiento de mejora
    private void applyMove(int posI, int posJ, AlgGRE_Clase3_Grupo9 greedy) {
        int posAI = conjunto.get(posI);
        int posAJ = conjunto.get(posJ);
        conjunto.set(posI, posAJ);
        conjunto.set(posJ, posAI);
//        System.out.println("El coste de " + posI + " y " + posJ + " es:" + greedy.calculaCosteConjunto(conjunto, archivo.getMatriz1(), archivo.getMatriz2()));
        mejorCoste = greedy.calculaCosteConjunto(conjunto, archivo.getMatriz1(), archivo.getMatriz2());
    }

    private boolean compruebaDLB(ArrayList<Boolean> dlb) {
        for (int i = 0; i < dlb.size(); i++) {
            if (!dlb.get(i)) {
                return false;
            }
        }
        return true;
    }

    public ArrayList<Integer> getConjuntos() {
        return conjunto;
    }

}
