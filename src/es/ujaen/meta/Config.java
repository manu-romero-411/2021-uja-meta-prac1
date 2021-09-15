/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package es.ujaen.meta;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

/**
 *
 * @author Manuel
 */
public class Config {
    private ArrayList<String> archivos;
    private ArrayList<String> algoritmos;
    private ArrayList<Long> seeds;
    private Integer parametrosExtra;
    
    public Config(String path){
        archivos = new ArrayList<>();
        algoritmos = new ArrayList<>();
        seeds = new ArrayList<>();
        
        FileReader f=null;
        String linea;
        try {
            f = new FileReader(path);
            BufferedReader buffer = new BufferedReader(f);
            
            // POR CADA LÍNEA DEL ARCHIVO DE CONFIGURACIÓN, COMPROBAMOS UNO A UNO LOS PARÁMETROS
            while((linea = buffer.readLine()) != null){
                String[] split = linea.split("=");
                switch(split[0]){
                    case "Archivos":
                        //TENEMOS QUE TENER EN CUENTA QUE HABRÁ MÁS DE UN PARÁMETRO, SEPARADOS POR ESPACIOS, ASÍ QUE TENEMOS QUE METER UN SEGUNDO SPLIT
                        String[] vFile = split[1].split(" ");
                        for (int i = 0; i < vFile.length; ++i){
                            archivos.add(vFile[i]);
                        }
                        break;
                        
                    case "Semillas":
                        String[] vSeed = split[1].split(" ");
                        for (int i = 0; i < vSeed.length; ++i){
                            seeds.add(Long.parseLong(vSeed[i]));
                        }
                        break;
                        
                    case "Algoritmos":
                        String[] vAlg = split[1].split(" ");
                        for (int i = 0; i < vAlg.length; ++i){
                            algoritmos.add(vAlg[i]);
                        }
                        break;
                        
                    case "OtrosParametros1":
                        parametrosExtra = Integer.parseInt(split[1]);
                        break;
                        
                }
            }
            linea = buffer.readLine();
                        
        } catch(IOException e) {
            System.out.println(e);
        }

    }

    /**
     * @return the archivos
     */
    public ArrayList<String> getArchivos() {
        return archivos;
    }

    /**
     * @param archivos the archivos to set
     */
    public void setArchivos(ArrayList<String> archivos) {
        this.archivos = archivos;
    }

    /**
     * @return the algoritmos
     */
    public ArrayList<String> getAlgoritmos() {
        return algoritmos;
    }

    /**
     * @param algoritmos the algoritmos to set
     */
    public void setAlgoritmos(ArrayList<String> algoritmos) {
        this.algoritmos = algoritmos;
    }

    /**
     * @return the seeds
     */
    public ArrayList<Long> getSeeds() {
        return seeds;
    }

    /**
     * @param seeds the seeds to set
     */
    public void setSeeds(ArrayList<Long> seeds) {
        this.seeds = seeds;
    }

    /**
     * @return the parametrosExtra
     */
    public Integer getParametrosExtra() {
        return parametrosExtra;
    }

    /**
     * @param parametrosExtra the parametrosExtra to set
     */
    public void setParametrosExtra(Integer parametrosExtra) {
        this.parametrosExtra = parametrosExtra;
    }
}
