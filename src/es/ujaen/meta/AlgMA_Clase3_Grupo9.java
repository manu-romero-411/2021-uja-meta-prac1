/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package es.ujaen.meta;

import java.util.ArrayList;
import java.util.Random;
import javafx.util.Pair;

/**
 *
 * @author admin
 */
public class AlgMA_Clase3_Grupo9 {

    private ArrayList<Integer> conjunto;
    private int coste;
    private final Archivodedatos archivo;
    private final int iteraciones;
    private final int candidatosGreedy;
    private final int tamLista;
    private Random random;
    private ArrayList<Pair<Integer, Integer>> LRC;
    private ArrayList<Pair<Integer, Integer>> listaTabu;
    private ArrayList<ArrayList<Integer>> memLargoPlazo;
    private ArrayList<Integer> mayorFlujo;
    private ArrayList<Integer> mayorDistancia;
    private ArrayList<Boolean> dlb;
    private int longitudLRC;
    private boolean flagMejora;

    public AlgMA_Clase3_Grupo9(Archivodedatos archivo, int iteraciones, int longitudLRC, int mejoresUnidades, int tamLista, Random random) {
        this.archivo = archivo;
        this.iteraciones = iteraciones;
        this.longitudLRC = longitudLRC;
        this.candidatosGreedy = mejoresUnidades;
        this.tamLista = tamLista;
        this.random = random;
        this.coste = 0;
        this.conjunto = new ArrayList<>();
        this.LRC = new ArrayList<>();
        this.memLargoPlazo = new ArrayList<>();
        this.mayorDistancia = new ArrayList<>();
        this.mayorFlujo = new ArrayList<>();
        this.listaTabu = new ArrayList<>();
        this.dlb = new ArrayList<>();
        this.flagMejora = true;
    }

    // Calcula el multiarranque
    public void calculaMultiarranque() {
        creaLRC();

        for (int i = 0; i < longitudLRC; i++) {
            Pair<Integer, Integer> aux = LRC.get(i);
            hazMultiArranque(aux);
        }

    }

    private void hazMultiArranque(Pair<Integer, Integer> par) {
        ArrayList<Integer> auxConjunto = conjunto;
        cambiaConjunto(par, auxConjunto);

        boolean dlbCompleto = false;
        int ultMov = 0;
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
            if (compruebaDLB()) {
                dlbCompleto = true;
            }
            ultMov = (ultMov + 1) % conjunto.size();
            k++;
        }
        System.out.println("ITERACIONES BUENAS: " + cam);

    }
    
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
    
    
    private boolean compruebaDLB() {
        for (int i = 0; i < dlb.size(); i++) {
            if (!dlb.get(i)) {
                return false;
            }
        }
        return true;
    }

    private void cambiaConjunto(Pair<Integer, Integer> par, ArrayList<Integer> conjuntoAux) {
        int vAux = conjuntoAux.get(par.getKey());
        conjuntoAux.set(par.getKey(), par.getValue());
        conjuntoAux.set(par.getValue(), vAux);
        anadirElementoTabu(par);
        incrementaLargoPlazo(par);
    }

    private void creaLRC() {
        AlgGRE_Clase3_Grupo9 greedy = new AlgGRE_Clase3_Grupo9(archivo);
        for (int i = 0; i < longitudLRC; i++) {
            greedy.calculaGreedy();
            conjunto = greedy.getConjunto();
            coste = greedy.getCosteConjunto();
            ArrayList<Integer> arrayAuxFlujos = AlgGRE_Clase3_Grupo9.sumaFilas(archivo.getMatriz1());
            arrayAuxFlujos.sort((o1, o2) -> o1.compareTo(o2));
            ArrayList<Integer> arrayAuxDist = AlgGRE_Clase3_Grupo9.sumaFilas(archivo.getMatriz2());
            arrayAuxDist.sort((o2, o1) -> o2.compareTo(o1));
            for (int j = 0; j < candidatosGreedy; j++) {
                mayorFlujo.add(arrayAuxFlujos.get(j));
                mayorDistancia.add(arrayAuxDist.get(j));
            }
            int flujo = random.nextInt(candidatosGreedy);
            int distancia = random.nextInt(candidatosGreedy);
            LRC.add(new Pair<>(mayorDistancia.get(flujo), mayorFlujo.get(distancia)));
        }
    }

    // Muestra los datos (futuro log)
    private void muestraDatos() {
        System.out.println("El conjunto de archivos de datos " + archivo.getNombre() + " tiene un coste de " + coste + " y es el siguiente: ");
        for (int i = 0; i < conjunto.size(); i++) {
            System.out.print(conjunto.get(i) + "  ");
        }
        System.out.println();
    }

    private boolean factorizacion(int r, int s) {
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

    private void anadirElementoTabu(Pair<Integer, Integer> elemento) {
        if (listaTabu.size() - 1 == tamLista) {
            for (int i = 0; i < tamLista - 1; i++) {
                listaTabu.set(i, listaTabu.get(i + 1));
            }
            listaTabu.set(tamLista, elemento);
        } else {
            listaTabu.add(elemento);
        }
    }

    private void incrementaLargoPlazo(Pair<Integer, Integer> elemento) {
        int aux = memLargoPlazo.get(elemento.getKey()).get(elemento.getValue());
        aux++;
        memLargoPlazo.get(elemento.getKey()).set(elemento.getValue(), aux);
    }

    public ArrayList<Integer> getMayorDistancia() {
        return mayorDistancia;
    }

    public ArrayList<Integer> getConjuntos() {
        return conjunto;
    }

    public ArrayList<Pair<Integer, Integer>> getLRC() {
        return LRC;
    }

}
