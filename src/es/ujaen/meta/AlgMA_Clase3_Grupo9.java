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
    private int mejoresUnidades;
    private int tamLista;
    private Random random;
    private ArrayList<Pair<Integer, Integer>> LRC;
    private ArrayList<Pair<Integer, Integer>> listaTabu;
    private ArrayList<Integer> mayorFlujo;
    private ArrayList<Integer> mayorDistancia;
    private int longitudLRC;

    public AlgMA_Clase3_Grupo9(Archivodedatos archivo, int longitudLRC, int mejoresUnidades, int tamLista, Random random) {
        this.archivo = archivo;
        this.longitudLRC = longitudLRC;
        this.mejoresUnidades = mejoresUnidades;
        this.tamLista = tamLista;
        this.random = random;
        this.coste = 0;
        this.conjunto = new ArrayList<>();
        this.LRC = new ArrayList<>();
        this.mayorDistancia = new ArrayList<>();
        this.mayorFlujo = new ArrayList<>();
        this.listaTabu = new ArrayList<>();
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
            for (int j = 0; j < mejoresUnidades; j++) {
                mayorFlujo.add(arrayAuxFlujos.get(j));
                mayorDistancia.add(arrayAuxDist.get(j));
            }
            int flujo = random.nextInt(mejoresUnidades);
            int distancia = random.nextInt(mejoresUnidades);
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
