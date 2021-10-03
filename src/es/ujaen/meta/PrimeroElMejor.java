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
public class PrimeroElMejor {

    private final ArrayList<ArrayList<Integer>> conjuntos;
    private ArrayList<Integer> mejorCoste;
    private final ArrayList<Archivodedatos> archivos;

    public PrimeroElMejor(ArrayList<Archivodedatos> archivos) {
        this.conjuntos = new ArrayList<>();
        this.mejorCoste = new ArrayList<>();
        this.archivos = archivos;
    }

    public void calculaPrimeroElMejor() {
        Greedy greedyA = new Greedy();
        greedyA.meteArchivos(archivos);
        ArrayList<ArrayList<Integer>> greedy = greedyA.getConjuntos();
        mejorCoste = greedyA.getCosteConjuntos();
        boolean improve_flag = true;
        ArrayList<Boolean> dlb = new ArrayList<>();
        for (int i = 0; i < greedy.size(); i++) {
            dlb.add(Boolean.FALSE);
        }
        for (int i = 0; i < greedy.size(); i++) {
            if (dlb.get(i) == Boolean.FALSE) {
                improve_flag = false;
                for (int j = 0; j < greedy.size(); j++) {
                    if (checkMove(i, j, mejorCoste.get(i))) {
                        applyMove(i, j);
                        dlb.set(i, Boolean.FALSE);
                        dlb.set(j, Boolean.FALSE);
                        improve_flag = true;
                    }
                }
                if (improve_flag == false) {
                    dlb.set(i, Boolean.TRUE);
                }
            }
        }
    }

    private boolean checkMove(int posI, int posJ, int costeMejor) {
        ArrayList<ArrayList<Integer>> auxConjuntos = this.conjuntos;

        Greedy greedyA = new Greedy(auxConjuntos);
        greedyA.calculaGreedy();
        return true;
    }

    private void applyMove(int posI, int posJ) {

    }

    public ArrayList<ArrayList<Integer>> getConjuntos() {
        return conjuntos;
    }
}
