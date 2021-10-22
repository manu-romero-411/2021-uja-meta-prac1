/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package es.ujaen.meta;

import com.sun.tools.javac.util.Pair;
import java.util.ArrayList;
import java.util.Random;
//import javafx.util.Pair;

/**
 *
 * @author admin
 */
public class AlgMA_Clase3_Grupo9 {

    private final long inicio;
    private long fin;
    private ArrayList<Integer> conjunto;
    private int coste;
    private final Archivodedatos archivo;
    private final int iteraciones;
    private final int candidatosGreedy;
    private final int tamLista;
    private final float iteracionesOscilacion;
    private Random random;
    private ArrayList<Pair<Integer, Integer>> LRC;
    private ArrayList<Pair<Integer, Integer>> listaTabu;
    private ArrayList<ArrayList<Integer>> memLargoPlazo;
    private ArrayList<Integer> mayorFlujo;
    private ArrayList<Integer> mayorDistancia;
    private ArrayList<Boolean> dlb;
    private int longitudLRC;
    private boolean flagMejora;

    public AlgMA_Clase3_Grupo9(Archivodedatos archivo, int iteraciones, int longitudLRC, int mejoresUnidades, int tamLista, float iteracionesOscilacion, Random random) {
        this.archivo = archivo;
        this.iteraciones = iteraciones;
        this.longitudLRC = longitudLRC;
        this.candidatosGreedy = mejoresUnidades;
        this.iteracionesOscilacion = iteracionesOscilacion;
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
        this.inicio = System.currentTimeMillis();
    }

    // Calcula el multiarranque
    public void calculaMultiarranque() {
        creaLRC();
        iniciaLargoPlazo();
        iniciaDLB();
        for (int i = 0; i < longitudLRC; i++) {
            Pair<Integer, Integer> aux = LRC.get(i);
            System.out.println("Vez: " + i);
            hazMultiArranque(aux);
        }

    }

    private void hazMultiArranque(Pair<Integer, Integer> par) {
        ArrayList<Integer> auxConjunto = conjunto;
        cambiaConjunto(par, auxConjunto);

        boolean dlbCompleto = false;
        int ultMov = 0, cam = 0, k = 0, sinCambiosIt = 0;
        while (k < iteraciones && !dlbCompleto) {
            int i = ultMov;
            if (dlb.get(i % dlb.size()) == false) {
                flagMejora = false;
                int contJ = dlb.size() - 1;
                for (int j = ((i + 1) % dlb.size()); contJ > 0 && flagMejora == false; j++) {
                    if (i % dlb.size() != j % dlb.size()) {
                        if (factorizacion(i % dlb.size(), j % dlb.size(), auxConjunto) && !estaTabu(i % dlb.size(), j % dlb.size(), auxConjunto)) {
                            Pair<Integer, Integer> aux = new Pair<>(i % dlb.size(), j % dlb.size());
                            cambiaConjunto(aux, auxConjunto);
                            dlb.set(i % dlb.size(), false);
                            dlb.set(j % dlb.size(), false);
                            flagMejora = true;
                            ultMov = conjunto.get(j % dlb.size());
                            cam++;
                            sinCambiosIt = 0;
                        } else {
                            sinCambiosIt++;
                            if (sinCambiosIt == (int) iteraciones * iteracionesOscilacion) {
                                oscilacionEstrategica(auxConjunto);
                                System.out.println("K: " + k);
                            }
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
                resetDLB();
                System.out.println("K2: " + k);
            }
            ultMov = (ultMov + 1) % conjunto.size();
            k++;
        }
    }

    private void iniciaDLB() {
        for (int i = 0; i < conjunto.size(); i++) {
            dlb.add(false);
        }
    }

    private void resetDLB() {
        for (int i = 0; i < conjunto.size(); i++) {
            dlb.set(i, false);
        }
    }

    private void oscilacionEstrategica(ArrayList<Integer> conjuntoAux) {
        int aleatorio = random.nextInt(3);
        Pair<Integer, Integer> aux = listaTabu.get(aleatorio);
        int auxC = conjuntoAux.get(aux.fst);
        conjuntoAux.set(aux.fst, aux.snd);
        conjuntoAux.set(aux.snd, auxC);
    }

    private void iniciaLargoPlazo() {
        ArrayList<Integer> aux = new ArrayList<>();
        for (int i = 0; i < conjunto.size(); i++) {
            for (int j = 0; j < conjunto.size(); j++) {
                aux.add(0);
            }
            memLargoPlazo.add(aux);
        }
    }

    private void resetLargoPlazo() {
        ArrayList<Integer> aux = new ArrayList<>();
        for (int i = 0; i < conjunto.size(); i++) {
            for (int j = 0; j < conjunto.size(); j++) {
                aux.set(j, 0);
            }
            memLargoPlazo.set(i, aux);
        }
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
        int vAux = conjuntoAux.get(par.fst/*par.getKey()*/);
        conjuntoAux.set(par.fst/*par.getKey()*/, par.snd /*par.getValue()*/);
        conjuntoAux.set(par.snd/*par.getValue()*/, vAux);
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
            LRC.add(new Pair<>(flujo, distancia));
        }
    }

    private boolean estaTabu(int r, int s, ArrayList<Integer> conjuntoAux) {
        for (int i = 0; i < listaTabu.size(); i++) {
            if (listaTabu.get(i).fst/*listaTabu.get(i).getKey()*/ == r && listaTabu.get(i).snd/*listaTabu.get(i).getValue()*/ == s) {
                return true;
            }
        }
        return false;
    }

    // Muestra los datos (futuro log)
    public String muestraDatos() {
        fin = System.currentTimeMillis();
        String aux = new String();
        for (int i = 0; i < conjunto.size(); i++) {
            aux += conjunto.get(i) + "  ";
        }
        System.out.println();
        return "MULTIARRANQUE \nEl conjunto de archivos de datos " + archivo.getNombre() + " tiene un coste de " + coste
                + " con un tiempo de ejecucion de: " + (fin - inicio) + " milisegundos y es el siguiente: \n" + aux + "\n";
    }

    private boolean factorizacion(int r, int s, ArrayList<Integer> conjuntoAux) {
        int matrizF[][] = archivo.getMatriz1();
        int matrizD[][] = archivo.getMatriz2();
        int sum = 0;

        for (int k = 0; k < matrizF.length; k++) {
            if (k != r && k != s) {
                sum += ((matrizF[s][k] * (matrizD[conjuntoAux.get(r)][conjuntoAux.get(k)] - matrizD[conjuntoAux.get(s)][conjuntoAux.get(k)]))
                        + (matrizF[r][k] * (matrizD[conjuntoAux.get(s)][conjuntoAux.get(k)] - matrizD[conjuntoAux.get(r)][conjuntoAux.get(k)]))
                        + (matrizF[k][s] * (matrizD[conjuntoAux.get(k)][conjuntoAux.get(r)] - matrizD[conjuntoAux.get(k)][conjuntoAux.get(s)]))
                        + (matrizF[k][r] * (matrizD[conjuntoAux.get(k)][conjuntoAux.get(s)] - matrizD[conjuntoAux.get(k)][conjuntoAux.get(r)])));
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
        int aux = memLargoPlazo.get(elemento.fst/*elemento.getKey()*/).get(elemento.snd/*elemento.getValue()*/);
        aux++;
        memLargoPlazo.get(elemento.fst/*elemento.getKey()*/).set(elemento.snd/*elemento.getValue()*/, aux);
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
