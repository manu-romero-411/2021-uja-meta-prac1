/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package es.ujaen.meta;

import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;

/**
 *
 * @author Manuel
 */
public class prac1 {

    /**
     * @param args the command line arguments
     */
    
    public static void main(String[] args) {
        ArrayList<ArchivoDatos> listaArchivos = new ArrayList<>();
        ArchivoConfig config = new ArchivoConfig(args[0]);
        System.out.println(config.getArchivos());

        //Añade a la lista de archivos los diferentes archivos de datos
        for (int i = 0; i < config.getArchivos().size(); i++) {
            ArchivoDatos archivo = new ArchivoDatos(config.getArchivos().get(i));
            listaArchivos.add(archivo);
        }

        AlgGreedy greedy = new AlgGreedy();
        greedy.insertaArchivos(listaArchivos);
        greedy.calculaGreedy();
        //FuncionesAuxiliares.muestraLista(listaArchivos);
    }
}