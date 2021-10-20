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
        boolean flagMejora = true;
        ArrayList<Boolean> dlb = new ArrayList<>();
        for (int i = 0; i < conjunto.size(); i++) {
            dlb.add(Boolean.FALSE);
        }
        int ultMov = random.nextInt(dlb.size());
        System.out.println("El valor de inicio es: " + ultMov);
        boolean dlbCompleto = false;
        int cam = 0;
        int k = 0;
        while (k < iteraciones && !dlbCompleto) {
            int i = ultMov;
            if (dlb.get(i % dlb.size()) == false) {
                flagMejora = false;
                int contJ = dlb.size() - 1;
                for (int j = ((i + 1) % dlb.size()); contJ > 0 && flagMejora == false; j++) {
                    if (i % dlb.size() != j) {
                        if (checkMove(i % dlb.size(), j % dlb.size())) {
                            applyMove(i % dlb.size(), j % dlb.size());
                            dlb.set(i % dlb.size(), false);
                            dlb.set(j % dlb.size(), false);
                            flagMejora = true;
                            ultMov = conjunto.get(j % dlb.size());
                            cam++;
                        }
                    }
                    contJ--;
                }

                if (flagMejora == false) {
                    dlb.set(i % dlb.size(), true);
                }
            }
            if (compruebaDLB(dlb)) {
                dlbCompleto = true;
            }
            ultMov = (ultMov + 1) % conjunto.size();
            k++;
        }
        System.out.println("es.ujaen.meta.AlgPMDLBrandom_Clase3_Grupo9.calculaPrimeroElMejor(): " + cam);
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
    private boolean checkMove(int r, int s) {
        int matrizF[][] = archivo.getMatriz1();
        int matrizD[][] = archivo.getMatriz2();
        int sum = 0;

        for (int k = 0; k < matrizF.length; k++) {
            if (k != r && k != s) {
                sum += ((matrizF[s][k] * (matrizD[conjunto.get(r)][conjunto.get(k)] - matrizD[conjunto.get(s)][conjunto.get(k)]))
                        + (matrizF[r][k] * (matrizD[conjunto.get(s)][conjunto.get(k)] - matrizD[conjunto.get(r)][conjunto.get(k)]))
                        + (matrizF[k][s] * (matrizD[conjunto.get(k)][conjunto.get(r)] - matrizD[conjunto.get(k)][conjunto.get(s)]))
                        + (matrizF[k][r] * (matrizD[conjunto.get(k)][conjunto.get(s)] - matrizD[conjunto.get(k)][conjunto.get(r)])));
            }
        }
        return (sum < 0);
    }

    // Aplica el movimiento de mejora
    private void applyMove(int r, int s) {
        int valorR = conjunto.get(r);
        conjunto.set(r, conjunto.get(s));
        conjunto.set(s, valorR);
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
