/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package es.ujaen.meta;

//import com.sun.tools.javac.util.Pair;
import java.util.ArrayList;
import java.util.Random;
import javafx.util.Pair;

/**
 *
 * @author admin
 */
public class AlgMA_Clase3_Grupo9 {

    private final long inicio;
    private final ArrayList<Pair<Integer, Integer>> listaTabu;
    private final ArrayList<ArrayList<Integer>> memLargoPlazo;
    private long fin;
    private ArrayList<Integer> conjunto;
    private int coste;
    private final Archivodedatos archivo;
    private final int iteraciones;
    private final int candidatosGreedy;
    private final int tamLista;
    private final float probOscilacion;
    private final float iteracionesOscilacion;
    private final Random random;
    private final ArrayList<Pair<Integer, Integer>> LRC;
    private final ArrayList<Integer> mayorFlujo;
    private final ArrayList<Integer> mayorDistancia;
    private final ArrayList<Boolean> dlb;
    private final int longitudLRC;
    private boolean flagMejora;

    //Constructor de la clase
    public AlgMA_Clase3_Grupo9(Archivodedatos archivo, int iteraciones, int longitudLRC, int mejoresUnidades, int tamLista, float probOscilacion, float iteracionesOscilacion, Random random) {
        this.archivo = archivo;
        this.iteraciones = iteraciones;
        this.longitudLRC = longitudLRC;
        this.candidatosGreedy = mejoresUnidades;
        this.iteracionesOscilacion = iteracionesOscilacion;
        this.probOscilacion = probOscilacion;
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

    //Calcula el multiarranque
    public void calculaMultiarranque() {
        creaLRC();
        iniciaLargoPlazo();
        iniciaDLB();
        ArrayList<Integer> costes = new ArrayList<>();
        int posCoste = 0;
        ArrayList<ArrayList<Integer>> auxiliar = new ArrayList<>();
        for (int i = 0; i < longitudLRC; i++) {
            Pair<Integer, Integer> aux = LRC.get(i);
            conjunto = hazMultiArranque(aux);
            costes.add(calculaCosteConjunto(conjunto));
            auxiliar.add(conjunto);
        }
        int costeMejor = Integer.MAX_VALUE;
        for (int i = 0; i < longitudLRC; i++) {
            if (costes.get(i) < costeMejor) {
                costeMejor = costes.get(i);
                posCoste = i;
            }
        }
        conjunto = auxiliar.get(posCoste);
        coste = calculaCosteConjunto(conjunto);
    }

    //Inicia el DLB a falso
    private void iniciaDLB() {
        for (int i = 0; i < conjunto.size(); i++) {
            dlb.add(false);
        }
    }

    //Inicia la memoria largo plazo
    private void iniciaLargoPlazo() {
        ArrayList<Integer> aux = new ArrayList<>();
        for (int i = 0; i < conjunto.size(); i++) {
            aux.add(0);
        }
        for (int i = 0; i < conjunto.size(); i++) {
            memLargoPlazo.add(aux);
        }
    }

    //Crea la lista restringida de cantidados con el GRASP
    private void creaLRC() {
        AlgGRE_Clase3_Grupo9 greedy = new AlgGRE_Clase3_Grupo9(archivo);
        for (int i = 0; i < longitudLRC; i++) {
            greedy.calculaGreedy();
            conjunto = greedy.getConjunto();
            coste = greedy.getCosteConjunto();
            ArrayList<Integer> arrayAuxFlujos = greedy.sumaFilas(archivo.getMatriz1());
            arrayAuxFlujos.sort((o1, o2) -> o1.compareTo(o2));
            ArrayList<Integer> arrayAuxDist = greedy.sumaFilas(archivo.getMatriz2());
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

    //Inicia el multiarranque con el elemento dado de la lista restringida de candidatos
    private ArrayList<Integer> hazMultiArranque(Pair<Integer, Integer> par) {
        ArrayList<Integer> auxConjunto = new ArrayList<>(conjunto);
        ArrayList<Integer> mejorPeor = new ArrayList<>(conjunto);
        int costeMejorPeor = calculaCosteConjunto(mejorPeor);
        cambiaConjunto(par.getKey(), par.getValue(), auxConjunto);

        mejora(auxConjunto, mejorPeor, costeMejorPeor);
        return auxConjunto;
    }

    //Realiza la operacion de mejora del conjunto dado
    private void mejora(ArrayList<Integer> auxConjunto, ArrayList<Integer> mejorPeor, int costeMejorPeor) {
        int ultMov = 0, k = 0, sinCambiosIt = 0;
        while (k < iteraciones) {
            int i = ultMov;
            if (dlb.get(i % dlb.size()) == false) {
                flagMejora = false;
                int contJ = dlb.size() - 1;
                for (int j = ((i + 1) % dlb.size()); contJ > 0 && flagMejora == false; j++) {
                    if (i % dlb.size() != j % dlb.size()) {
                        if (factorizacion(i % dlb.size(), j % dlb.size(), auxConjunto, mejorPeor, costeMejorPeor) && !estaTabu(i % dlb.size(), j % dlb.size())) {
                            cambiaConjunto(i % dlb.size(), j % dlb.size(), auxConjunto);
                            dlb.set(i % dlb.size(), false);
                            dlb.set(j % dlb.size(), false);
                            flagMejora = true;
                            ultMov = conjunto.get(j % dlb.size());
                            sinCambiosIt = 0;
                        } else {
                            sinCambiosIt++;
                            if (sinCambiosIt == (int) iteraciones * iteracionesOscilacion) {
                                oscilacionEstrategica(auxConjunto, sinCambiosIt);
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
                for (int j = 0; j < mejorPeor.size(); j++) {
                    auxConjunto.set(j, mejorPeor.get(j));
                }
                resetDLB();
            }
            ultMov = (ultMov + 1) % conjunto.size();
            k++;
        }
    }

    //Cambia el conjunto dado y añade el cambio a la lista tabu y a la memoria largo plazo
    private void cambiaConjunto(int r, int s, ArrayList<Integer> conjuntoAux) {
        int aux = conjuntoAux.get(r);
        conjuntoAux.set(r, conjuntoAux.get(s));
        conjuntoAux.set(s, aux);
        Pair<Integer, Integer> par = new Pair<>(r, s);
        anadirElementoTabu(par);
        incrementaLargoPlazo(par);
    }

    //Funcion de factorizacion, devuelve true si la suma es menor a cero, si fuese mayor o igual comprobaria si es el mejor peor coste
    //en caso de que esto ocurra, este se cambiaria, tanto el vector como el coste mejor peor
    private boolean factorizacion(int r, int s, ArrayList<Integer> conjuntoAux, ArrayList<Integer> mejorPeor, int costeMejorPeor) {
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
        sum += (matrizF[r][r] * (matrizD[conjuntoAux.get(s)][conjuntoAux.get(s)] - matrizD[conjuntoAux.get(r)][conjuntoAux.get(r)]))
                + (matrizF[s][s] * (matrizD[conjuntoAux.get(r)][conjuntoAux.get(r)] - matrizD[conjuntoAux.get(s)][conjuntoAux.get(s)]))
                + (matrizF[r][s] * (matrizD[conjuntoAux.get(s)][conjuntoAux.get(r)] - matrizD[conjuntoAux.get(s)][conjuntoAux.get(r)]))
                + (matrizF[s][r] * (matrizD[conjuntoAux.get(r)][conjuntoAux.get(s)] - matrizD[conjuntoAux.get(s)][conjuntoAux.get(r)]));
        if (sum < 0) {
            return true;
        } else {
            ArrayList<Integer> auxMejorPeor = new ArrayList<>(mejorPeor);

            int aux = auxMejorPeor.get(r);
            auxMejorPeor.set(r, auxMejorPeor.get(s));
            auxMejorPeor.set(s, aux);

            int costeAux = calculaCosteConjunto(auxMejorPeor);
            if (costeMejorPeor > costeAux) {
                for (int i = 0; i < mejorPeor.size(); i++) {
                    mejorPeor.set(i, auxMejorPeor.get(i));
                }
                costeMejorPeor = costeAux;
            }
        }
        return false;

    }

    //Calcula el coste del conjunto dado
    private int calculaCosteConjunto(ArrayList<Integer> conjunto) {
        int coste = 0;
        for (int i = 0; i < conjunto.size(); i++) {
            for (int j = 0; j < conjunto.size(); j++) {
                coste += archivo.getMatriz1()[i][j] * archivo.getMatriz2()[conjunto.get(i)][conjunto.get(j)];
            }
        }
        return coste;
    }

    //Comprueba si el par con las posiciones dadas esta en la lista tabu
    private boolean estaTabu(int r, int s) {
        Pair<Integer, Integer> aux = new Pair<>(r, s);
        return listaTabu.contains(aux);
    }

    //Añade un par a la lista tabu, siendo el maximo de esta tres, asi que en caso de que entre otro, el mas antiguo se borra de esta lista
    private void anadirElementoTabu(Pair<Integer, Integer> elemento) {
        if (listaTabu.size() < tamLista) {
            listaTabu.add(elemento);
        } else {
            for (int i = tamLista - 1; i > 0; i--) {
                listaTabu.set(i, listaTabu.get(i - 1));
            }
            listaTabu.set(0, elemento);
        }
    }

    //Incrementa la memoria largo plazo del par dado
    private void incrementaLargoPlazo(Pair<Integer, Integer> elemento) {
        int aux = memLargoPlazo.get(elemento.getKey()).get(elemento.getValue());
        aux++;
        memLargoPlazo.get(elemento.getKey()).set(elemento.getValue(), aux);
    }

    //Realiza la oscilacion estrategica con la lista tabu, cogiendo un valor
    private void oscilacionEstrategica(ArrayList<Integer> conjuntoAux, int sinCambiosIt) {
        if (probOscilacion * 100 > random.nextInt(101)) {
            int aleatorio = random.nextInt(tamLista);
            Pair<Integer, Integer> aux = listaTabu.get(aleatorio);
            cambiaConjunto(aux.getKey(), aux.getValue(), conjuntoAux);
            sinCambiosIt = 0;
        }
    }

    //Comprueba si el DLB esta completo
    private boolean compruebaDLB() {
        for (int i = 0; i < dlb.size(); i++) {
            if (!dlb.get(i)) {
                return false;
            }
        }
        return true;
    }

    //Resetea el DLB a falso
    private void resetDLB() {
        for (int i = 0; i < conjunto.size(); i++) {
            dlb.set(i, false);
        }
    }

    //Funcion para añadir la informacion a los logs
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

    //Getters y Setters
    public ArrayList<Integer> getConjunto() {
        return conjunto;
    }

}
