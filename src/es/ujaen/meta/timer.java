/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package es.ujaen.meta;

import java.time.Clock;

/**
 *
 * @author Manuel
 */

enum type_timer {REAL, VIRTUAL}; // No estoy muy convencido de dejar esta línea aquí :(
public class timer {
    private long start_time=System.nanoTime();
    private double elapsed;
    private Clock clock = Clock.systemDefaultZone();
    private int CLOCKS_PER_SEC = 1000000;

    /*    
      FUNCTION:       virtual and real time of day are computed and stored to 
                      allow at later time the computation of the elapsed time 
                      (virtual or real) 
      INPUT:          none
      OUTPUT:         none
      (SIDE)EFFECTS:  virtual and real time are computed   
    */
    public void start_timers(){
        start_time = clock.millis();
    }

    
    //TIMER_TYPE type; 
    /*    
          FUNCTION:       return the time used in seconds (virtual or real, depending on type) 
          INPUT:          none
          OUTPUT:         seconds since last call to start_timers
          (SIDE)EFFECTS:  none
    */
    public double elapsed_time(){
        elapsed = clock.millis() - (start_time/1000);
        return elapsed / CLOCKS_PER_SEC;
    }
}
