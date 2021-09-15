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
import java.util.Arrays;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 *
 * @author Manuel
 */
public class prac1 {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) throws InterruptedException {
        Config config = new Config(args[0]);
        System.out.println(config.getAlgoritmos());
        
        // CREAMOS UN ARRAYLIST DE ARCHIVOS
        ArrayList<ArchivoDatos> archivos = new ArrayList<>();
        for (int i = 0; i < config.getArchivos().size(); ++i){
            archivos.add(new ArchivoDatos(config.getArchivos().get(i)));
        }
        
   
        ExecutorService ejecutor = Executors.newCachedThreadPool();
        
        for (int i = 0; i < config.getAlgoritmos().size(); ++i){
            for (int j = 0; j < archivos.size(); ++j){
                    CountDownLatch countdownlatch = new CountDownLatch(config.getSeeds().size());
                    switch(config.getAlgoritmos().get(i)){
                        case("PrimerMejor"):
                            ArrayList<MHEjemplo> mh = new ArrayList();
                            for (int k = 0; k < config.getSeeds().size(); ++k){
                                MHEjemplo meta= new MHEjemplo(archivos.get(j), countdownlatch, config.getSeeds().get(k));
                                mh.add(meta);
                                ejecutor.execute(meta);
                            }
                            countdownlatch.await();
                            for (int k = 0; k < mh.size(); ++k){
                                guardarArchivo("log/" + config.getAlgoritmos().get(i) +" " + archivos.get(j).getFichero() + config.getSeeds().get(k) + ".txt", mh.get(k).getLog());
                            }
                            break;
                    }
            }
        }
    }
    public static void guardarArchivo(String path, String texto){
        FileWriter archivo = null;
        PrintWriter pw = null;
        try {
            archivo = new FileWriter (path);
            pw = new PrintWriter(archivo);
            pw.print(texto);
        } catch (IOException e) {
        } finally {
            try {
                if(archivo != null){
                    archivo.close();
                }
            } catch (IOException e2) {
            }
        }
    }
}

