/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package es.ujaen.meta;

import javafx.util.Pair;
import java.util.ArrayList;
import java.util.Random;

/**
 *
 * @author admin
 */
public class AlgMA_Clase3_Grupo9 {

    private ArrayList<Integer> conjunto;
    private int costeGreedy;
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
        this.costeGreedy = 0;
        this.conjunto = new ArrayList<>();
        this.LRC = new ArrayList<>();
        this.mayorDistancia = new ArrayList<>();
        this.mayorFlujo = new ArrayList<>();
        this.listaTabu = new ArrayList<>();
    }

    // Calcula el multiarranque
    public void calculaMultiarranque() {
        AlgGRE_Clase3_Grupo9 greedy = new AlgGRE_Clase3_Grupo9(archivo);

        // PRIMERO GENERAMOS LAS N SOLUCIONES GREEDY (SE LO DAREMOS POR PARÁMETRO)
        for (int i = 0; i < longitudLRC; i++) {
            greedy.calculaGreedy();
            conjunto = greedy.getConjunto();
            costeGreedy = greedy.getCosteConjunto();
            ArrayList<Integer> arrayAuxFlujos = AlgGRE_Clase3_Grupo9.sumaFilas(archivo.getMatriz1());
            arrayAuxFlujos.sort((o1, o2) -> o1.compareTo(o2));
            ArrayList<Integer> arrayAuxDist = AlgGRE_Clase3_Grupo9.sumaFilas(archivo.getMatriz2());
            arrayAuxDist.sort((o2, o1) -> o2.compareTo(o1));
            for (int j = 0; j < mejoresUnidades; j++) {
                mayorFlujo.add(arrayAuxFlujos.get(j));
                mayorDistancia.add(arrayAuxDist.get(j));
            }
            for (int j = 0; j < mejoresUnidades; j++) {
                Pair<Integer, Integer> aux = new Pair<>(mayorDistancia.get(j), mayorFlujo.get(j));
                anadirElementoLRC(aux);
            }
        }
        
        for (int i = 0; i < longitudLRC; i++) {
            Pair<Integer, Integer> aux = LRC.get(i);
        }
        
    }

    // Muestra los datos (futuro log)
    private void muestraDatos() {
        System.out.println("El conjunto de archivos de datos " + archivo.getNombre() + " tiene un coste de " + costeGreedy + " y es el siguiente: ");
        for (int i = 0; i < conjunto.size(); i++) {
            System.out.print(conjunto.get(i) + "  ");
        }
        System.out.println();
    }

    private void anadirElementoLRC(Pair<Integer, Integer> elemento) {
        if (LRC.size() - 1 == longitudLRC) {
            for (int i = 0; i < longitudLRC - 1; i++) {
                LRC.set(i, LRC.get(i + 1));
            }
            LRC.set(longitudLRC, elemento);
        } else {
            LRC.add(elemento);
        }
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

}
