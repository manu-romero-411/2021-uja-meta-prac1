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
public class AlgMA_Clase3_Grupo9 {

    private ArrayList<Integer> conjunto;
    private int costeGreedy;
    private final Archivodedatos archivo;
    private Random random;

    public AlgMA_Clase3_Grupo9(Archivodedatos archivo, Random random) {
        this.conjunto = new ArrayList<>();
        this.costeGreedy = 0;
        this.archivo = archivo;
        this.random = random;
    }

    // Calcula el multiarranque
    public void calculaMultiarranque() {
        AlgGRE_Clase3_Grupo9 greedy = new AlgGRE_Clase3_Grupo9(archivo);
        greedy.calculaGreedy();
        conjunto = greedy.getConjunto();
        costeGreedy = greedy.getCosteConjunto();
        
        
    }
    
//    private void mayorFlujo(){
//        archivo.getMatriz1();
//    }

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
