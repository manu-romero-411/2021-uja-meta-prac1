/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package es.ujaen.meta;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

/**
 *
 * @author Manuel
 */
public class ArchivoDatos {
    // Lo hacemos así para distinguir cada archivo de datos de los demás
    private String fichero;
    private int matriz1[][];
    private int matriz2[][];
    
    public ArchivoDatos(String path){
        String linea;
        fichero = path.split("\\.")[0];
        FileReader f=null;
        try {
            // LEYENDO ARCHIVO Y DECLARANDO Y DETERMINANDO DIMENSIONES DE LAS MATRICES
            f = new FileReader(path);
            BufferedReader buffer = new BufferedReader(f);
            linea = buffer.readLine();
            String[] splitNumero = linea.split(" ");
            int dimMatrix1 = Integer.parseInt(splitNumero[1]); // readLine() lee directamente la primera línea del archivo, el cual hemos metido en un buffer con BufferedReader()
            matriz1 = new int[dimMatrix1][dimMatrix1];
            matriz2 = new int[dimMatrix1][dimMatrix1];
            
            // PRIMERA MATRIZ DEL ARCHIVO
            linea = buffer.readLine();
            for (int i = 0; i < dimMatrix1; ++i){
                linea = buffer.readLine();
                String[] split = linea.split(" ");
                int errores = 0;
                for (int j = 0; j < split.length; ++j){
                    try {
                        matriz1[i][j - errores] = Integer.parseInt(split[j]);
                    } catch (NumberFormatException ex) {
                        ++errores; 
                    }
                }
            }
            
            // SEGUNDA MATRIZ DEL ARCHIVO
            linea = buffer.readLine();
            for (int i = 0; i < dimMatrix1; ++i){
                linea = buffer.readLine();
                String[] split = linea.split(" ");
                int errores = 0;
                for (int j = 0; j < split.length; ++j){
                    try {
                        matriz2[i][j - errores] = Integer.parseInt(split[j]);
                    } catch (NumberFormatException ex) {
                        ++errores; 
                    }
                }
            }
        } catch(IOException e) {
            System.out.println(e);
        }
    }

    /**
     * @return the fichero
     */
    public String getFichero() {
        return fichero;
    }

    /**
     * @param fichero the fichero to set
     */
    public void setFichero(String fichero) {
        this.fichero = fichero;
    }

    /**
     * @return the matriz1
     */
    public int[][] getMatriz1() {
        return matriz1;
    }

    /**
     * @param matriz1 the matriz1 to set
     */
    public void setMatriz1(int[][] matriz1) {
        this.matriz1 = matriz1;
    }

    /**
     * @return the matriz2
     */
    public int[][] getMatriz2() {
        return matriz2;
    }

    /**
     * @param matriz2 the matriz2 to set
     */
    public void setMatriz2(int[][] matriz2) {
        this.matriz2 = matriz2;
    }
    
}
