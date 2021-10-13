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
public class AlgMA_Clase3_Grupo9 {

    private ArrayList<Integer> conjunto;
    private int costeGreedy;
    private final Archivodedatos archivo;
    private Random random;
    private ArrayList<ArrayList<Integer>> LRC;
    private ArrayList<Integer> mayorFlujo;
    private ArrayList<Integer> menorDistancia;
    private int longitudLRC;
    private int mejoresUnidades;
    private int tamLista;
    private ArrayList<Integer> listaTabu;

    public AlgMA_Clase3_Grupo9(Archivodedatos archivo, int longitudLRC, int mejoresUnidades, int tamLista, Random random) {
        this.conjunto = new ArrayList<>();
        this.costeGreedy = 0;
        this.archivo = archivo;
        this.random = random;
        this.LRC = new ArrayList<>();
        this.longitudLRC = longitudLRC;
        this.mayorFlujo = new ArrayList<>();
        this.menorDistancia = new ArrayList<>();
        this.tamLista = tamLista;
        this.listaTabu = new ArrayList();
    }

    // Calcula el multiarranque
    public void calculaMultiarranque() {
        AlgGRE_Clase3_Grupo9 greedy = new AlgGRE_Clase3_Grupo9(archivo);

        // PRIMERO GENERAMOS LAS N SOLUCIONES GREEDY (SE LO DAREMOS POR PARÁMETRO)
        for (int i = 0; i < longitudLRC; i++) {
            greedy.calculaGreedy();
            conjunto = greedy.getConjunto();
            costeGreedy = greedy.getCosteConjunto();
            
            ArrayList<Integer> arrayAuxFlujos = greedy.sumaFilas(archivo.getMatriz1());
            arrayAuxFlujos.sort((o1, o2) -> o1.compareTo(o2));
            ArrayList<Integer> arrayAuxDist = greedy.sumaFilas(archivo.getMatriz2());
            arrayAuxDist.sort((o1, o2) -> o2.compareTo(o1));
            for (int j = 0; j < mejoresUnidades; j++) {
                mayorFlujo.add(arrayAuxFlujos.get(i));
            }
            for (int j = 0; j < mejoresUnidades; j++) {
                menorDistancia.add(arrayAuxDist.get(i));
            }
            
            int aleat1 = random.nextInt(mejoresUnidades);
            int aleat2 = random.nextInt(mejoresUnidades);
            
            conjunto.set(aleat1, aleat2);
            LRC.add(conjunto);
        }
        
    }
    
    public void seleccionarSolucion(){
        
    }

    
    void insertarEnListaIntercambio(int elemento) {
        if (listaTabu.size() - 1 == tamLista) {
            for (int i = 0; i < tamLista - 1; i++) {
                listaTabu.set(i, listaTabu.get(i + 1));
            }
            listaTabu.set(tamLista, elemento);
        } else {
            listaTabu.add(elemento);
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

    public ArrayList<Integer> getConjuntos() {
        return conjunto;
    }

}
