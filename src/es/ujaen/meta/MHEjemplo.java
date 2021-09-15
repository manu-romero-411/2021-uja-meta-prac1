/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package es.ujaen.meta;

import java.util.Random;
import java.util.concurrent.CountDownLatch;
/**
 *
 * @author Manuel
 */
public class MHEjemplo implements Runnable {

    private Random aleatorio;
    private ArchivoDatos archivo;
    private StringBuilder log;
    private CountDownLatch countdownlutch;
    
    /*
    CONSTRUCTOR DE LA CLASE
    */
    
    public MHEjemplo(ArchivoDatos archivo, CountDownLatch countdownlutch, long seed){
        this.archivo = archivo;
        this.countdownlutch = countdownlutch;
        aleatorio = new Random(seed);
        log = new StringBuilder();
    }
    
    @Override
    public void run() {
        //throw new UnsupportedOperationException("Not supported yet.");
        // Inicialización aleatoria de la primera solución (se necesitará en todas las prácticas)
        log.append("El coste de la solución inicial es x \n");
        long tiempoInicio = System.currentTimeMillis();

        // Ejecución de la metaheurística
        // se recomienda ir diciendo en el log lo que está pasando en cada ejecución
        log.append("Iteración Y \n Coste mejor: X \n Se acepta solución generada con coste XXX..... \n");
        
        long tiempoFin = System.currentTimeMillis();
        
        // Finalización de la metaheurística (escribir en el log el costo final y la duración
        log.append("El coste final es X \n Duración: \n" + (tiempoFin - tiempoInicio));
        countdownlutch.countDown();
    }
    public String getLog(){
        return log.toString();
    }
}
