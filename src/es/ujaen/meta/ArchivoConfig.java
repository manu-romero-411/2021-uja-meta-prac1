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
 * @author Usuario
 */
public class ArchivoConfig {

    ArrayList<String> archivos;
    ArrayList<String> algoritmos;
    ArrayList<Long> semillas;

    Integer parametroExtra;

    public ArchivoConfig(String ruta) {
        archivos = new ArrayList<>();
        algoritmos = new ArrayList<>();
        semillas = new ArrayList<>();

        String linea;
        FileReader f = null;
        try {
            f = new FileReader(ruta);
            BufferedReader b = new BufferedReader(f);
            while ((linea = b.readLine()) != null) {
                String[] split = linea.split("=");
                switch (split[0]) {
                    case "Archivos":
                        //archivos.add(split[1]);
                        String[] v = split[1].split(" ");
                        for (int i = 0; i < v.length; i++) {
                            archivos.add(v[i]);
                        }
                        break;

                    case "Semillas":
                        //archivos.add(split[1]);
                        String[] vsemillas = split[1].split(" ");
                        for (int i = 0; i < vsemillas.length; i++) {
                            semillas.add(Long.parseLong(vsemillas[i]));
                        }
                        break;

                    case "Algoritmos":
                        //archivos.add(split[1]);
                        String[] valgoritmos = split[1].split(" ");
                        for (int i = 0; i < valgoritmos.length; i++) {
                            algoritmos.add(valgoritmos[i]);
                        }
                        break;

                    case "Extra":
                        parametroExtra = Integer.parseInt(split[1]);
                        break;
                }
            }

        } catch (IOException e) {
            System.out.println(e);
        }
    }

    public ArrayList<String> getArchivos() {
        return archivos;
    }

    public ArrayList<String> getAlgoritmos() {
        return algoritmos;
    }

    public ArrayList<Long> getSemillas() {
        return semillas;
    }

    public Integer getParametroExtra() {
        return parametroExtra;
    }

}
