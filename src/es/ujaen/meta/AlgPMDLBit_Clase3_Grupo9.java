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
public class AlgPMDLBit_Clase3_Grupo9 {

    private ArrayList<Integer> conjunto;
    private int mejorCoste;
    private final Archivodedatos archivo;
    private final int iteraciones;

    public AlgPMDLBit_Clase3_Grupo9(Archivodedatos archivo, int iteraciones) {
        this.conjunto = new ArrayList<>();
        this.mejorCoste = 0;
        this.archivo = archivo;
        this.iteraciones = iteraciones;
    }

    // Calcula el primero el mejor iterativo
    public void calculaPrimeroElMejor() {
        AlgGRE_Clase3_Grupo9 greedyA = new AlgGRE_Clase3_Grupo9(archivo);
        greedyA.calculaGreedy();
        this.conjunto = greedyA.getConjunto();
        this.mejorCoste = greedyA.getCosteConjunto();
        System.out.println("El mejor coste de " + archivo.getNombre() + " es: " + mejorCoste);
        boolean improve_flag = true;
        ArrayList<Boolean> dlb = new ArrayList<>();
        for (int i = 0; i < conjunto.size(); i++) {
            dlb.add(false);
        }
        boolean dlbCompleto = false;
        int fin = 0;
        int cam = 0;
        int k = 0;
        while (k < iteraciones && !dlbCompleto) {
            int i = fin;
            if (dlb.get(i % dlb.size()) == false) {
                improve_flag = false;
                int contJ = dlb.size() - 1;
                for (int j = i + 1 % dlb.size(); contJ > 0 && improve_flag == false; j++) {
                    if (i % dlb.size() != j) {
                        if (checkMove(i % dlb.size(), j % dlb.size())) {
                            applyMove(i % dlb.size(), j % dlb.size(), greedyA);
                            cam++;
                            dlb.set(i % dlb.size(), false);
                            dlb.set(j % dlb.size(), false);
                            improve_flag = true;
                            fin = conjunto.get(j % dlb.size());
                        }
                    }
                    if (compruebaDLB(dlb)) {
                        dlbCompleto = true;
                    }
                    contJ--;
                }
                if (improve_flag == false) {
                    dlb.set(i % dlb.size(), true);
                }

            } else {
                fin++;
                if (fin >= 20) {
                    fin = 0;
                }
            }
            k++;
        }
        System.out.println("es.ujaen.meta.AlgPMDLBit_Clase3_Grupo9.calculaPrimeroElMejor(): " + cam);
        mejorCoste = greedyA.calculaCosteConjunto(conjunto, archivo.getMatriz1(), archivo.getMatriz2());
        muestraDatos();
    }

    private boolean compruebaDLB(ArrayList<Boolean> dlb) {
        for (int i = 0; i < dlb.size(); i++) {
            if (!dlb.get(i)) {
                return false;
            }
        }
        return true;
    }

    // Muestra los datos (futuro log)
    private void muestraDatos() {
        System.out.println("El conjunto de archivos de datos " + archivo.getNombre() + " tiene un coste de " + mejorCoste + " y es el siguiente: ");
        for (int i = 0; i < conjunto.size(); i++) {
            System.out.print(conjunto.get(i) + "  ");
        }
        System.out.println();
    }

    // Comprueba si el movimiento mejora
    private boolean checkMove(int posI, int posJ) {

        int sum = 0;
        int posR = conjunto.get(posI);
        int posS = conjunto.get(posJ);
        
        int matrizF[][] = archivo.getMatriz1();
        int matrizD[][] = archivo.getMatriz2();

        for (int k = 0; k < conjunto.size(); k++) {
            if (k != posR && k != posS) {
                sum +=   (matrizF[posJ][k] * (matrizD[conjunto.get(posI)][conjunto.get(k)] - matrizD[conjunto.get(posJ)][conjunto.get(k)])
                        + matrizF[posI][k] * (matrizD[conjunto.get(posJ)][conjunto.get(k)] - matrizD[conjunto.get(posI)][conjunto.get(k)])
                        + matrizF[k][posJ] * (matrizD[conjunto.get(k)][conjunto.get(posI)] - matrizD[conjunto.get(k)][conjunto.get(posJ)])
                        + matrizF[k][posI] * (matrizD[conjunto.get(k)][conjunto.get(posJ)] - matrizD[conjunto.get(k)][conjunto.get(posI)]));
            }
        }
//        System.out.println("es.ujaen.meta.AlgPMDLBit_Clase3_Grupo9.checkMove() " + sum + " posI: " + posI + " posJ: " + posJ);
        if (sum < 0) {
//            System.out.println("Coste conjunto: " + costeConjuntoActual + " Coste auxConjunto: " + costeConjuntoMod);
            return true;
        } else {
//            System.out.println("es.ujaen.meta.AlgPMDLBit_Clase3_Grupo9.checkMove(): " + sum);
            return false;
        }
    }

    // Aplica el movimiento de mejora
    private void applyMove(int posI, int posJ, AlgGRE_Clase3_Grupo9 greedy) {
        int aux = conjunto.get(posI);
        conjunto.set(posI, conjunto.get(posJ));
        conjunto.set(posJ, aux);
        mejorCoste = greedy.calculaCosteConjunto(conjunto, archivo.getMatriz1(), archivo.getMatriz2());
    }

    public ArrayList<Integer> getConjuntos() {
        return conjunto;
    }

}
