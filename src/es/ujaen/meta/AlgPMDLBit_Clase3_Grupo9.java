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
            dlb.add(Boolean.FALSE);
        }
        boolean dlbCompleto = false;
        int fin = 0;
        int k = 1;
        int contI = dlb.size();
        while (k < iteraciones && !dlbCompleto) {
            int i = fin;
            if (dlb.get(i % dlb.size()) == Boolean.FALSE) {
                improve_flag = false;
                int contJ = dlb.size();
                for (int j = i % dlb.size(); contJ > 0 && improve_flag == false; j++) {
                    if (i % dlb.size() != j) {
                        if (checkMove(i % dlb.size(), j % dlb.size(), greedyA)) {
                            applyMove(i % dlb.size(), j % dlb.size(), greedyA);
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
    private boolean checkMove(int posI, int posJ, AlgGRE_Clase3_Grupo9 greedy) {
        ArrayList<Integer> auxConjunto = new ArrayList<>();
        for (int i = 0; i < conjunto.size(); i++) {
            auxConjunto.add(conjunto.get(i));
        }
        int posAI = conjunto.get(posI);
        int posAJ = conjunto.get(posJ);
        auxConjunto.set(posI, posAJ);
        auxConjunto.set(posJ, posAI);

        return mejorCoste > greedy.calculaCosteConjunto(auxConjunto, archivo.getMatriz1(), archivo.getMatriz2());
    }

    // Aplica el movimiento de mejora
    private void applyMove(int posI, int posJ, AlgGRE_Clase3_Grupo9 greedy) {
        int posAI = conjunto.get(posI);
        int posAJ = conjunto.get(posJ);
        conjunto.set(posI, posAJ);
        conjunto.set(posJ, posAI);
        mejorCoste = greedy.calculaCosteConjunto(conjunto, archivo.getMatriz1(), archivo.getMatriz2());
    }

    public ArrayList<Integer> getConjuntos() {
        return conjunto;
    }

}