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
 * @author Manuel
 */
public class prac1 {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        Configurador config = new Configurador(args[0]);
        ArrayList<Archivodedatos> arrayA = new ArrayList<>();
        System.out.println(config.getArchivos());

        //Añade a la lista de archivos los diferentes archivos de datos
        for (int i = 0; i < config.getArchivos().size(); i++) {
            Archivodedatos archivo = new Archivodedatos(config.getArchivos().get(i));
            arrayA.add(archivo);
        }

        //Se repite por cada semilla dada en el archivo config
        for (int j = 0; j < config.getSemillas().size(); j++) {

            //Creacion del log y su nombre
            Log log = new Log(config.getSalidaLog()+"_semilla_"+config.getSemillas().get(j));
            Random random = new Random(config.getSemillas().get(j));

            System.out.println("SEMILLA: "+ config.getSemillas().get(j));
            
            //Greedy
            for (int i = 0; i < arrayA.size(); i++) {
                AlgGRE_Clase3_Grupo9 greedy = new AlgGRE_Clase3_Grupo9(arrayA.get(i));
                greedy.calculaGreedy();
                System.out.print(greedy.muestraDatos());
                log.addTexto(greedy.muestraDatos());
            }

            //Primer Mejor Iterativo
            log.addTexto("\n");
            for (int i = 0; i < arrayA.size(); i++) {
                AlgPMDLBit_Clase3_Grupo9 primero = new AlgPMDLBit_Clase3_Grupo9(arrayA.get(i), config.getIteraciones());
                primero.calculaPrimerMejorIterativo();
                System.out.print(primero.muestraDatos());
                log.addTexto(primero.muestraDatos());
            }

            //Primer Mejor Aleatorio
            log.addTexto("\n");
            for (int i = 0; i < arrayA.size(); i++) {
                AlgPMDLBrandom_Clase3_Grupo9 primeroAle = new AlgPMDLBrandom_Clase3_Grupo9(arrayA.get(i), config.getIteraciones(), random);
                primeroAle.calculaPrimerMejorAleatorio();
                System.out.print(primeroAle.muestraDatos());
                log.addTexto(primeroAle.muestraDatos());
            }

            //Multiarranque
            log.addTexto("\n");
            for (int i = 0; i < arrayA.size(); i++) {
                AlgMA_Clase3_Grupo9 multiA = new AlgMA_Clase3_Grupo9(arrayA.get(i), config.getIteraciones(),
                        config.getLonguitudLRC(), config.getCandidatosGreedy(), config.getTamLista(), config.getIteracionesEstrategica(), random);
                multiA.calculaMultiarranque();
                System.out.print(multiA.muestraDatos());
                log.addTexto(multiA.muestraDatos());
            }

            //Guardado del log
            log.guardaLog();
        }
    }
}
