/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package es.ujaen.meta;

/**
 *
 * @author Manuel
 */
public class random {

    private float MASK = 2147483647;
    private float PRIME = 65539;
    private float SCALE = (float) 0.4656612875e-9; //he tenido que hacer aquí un cast, pero igual se puede hacer de otra forma...

    private long Seed = 0L;

    /* Inicializa la semilla al valor 'x'.
    Solo debe llamarse a esta funcion una vez en todo el programa */
    public void setRandom(long x) {
        Seed = (long) x;
    }

    /* Devuelve el valor actual de la semilla */
    public long getRandom() {
        return Seed;
    }

    /* Genera un numero aleatorio real en el intervalo [0,1[
   (incluyendo el 0 pero sin incluir el 1) */
    public float Rand() {
        return ((Seed = (Float.floatToRawIntBits(MASK) & Float.floatToRawIntBits((PRIME * Seed)))) * SCALE); // hay aquí una operación AND y habría que usar un tipo de datos distinto que permita trabajar con los números bit a bit
    }

    /* Genera un numero aleatorio real en el intervalo [0,1[
       (incluyendo el 0 pero sin incluir el 1) */

 /* Genera un numero aleatorio entero en {low,...,high} */
    public int Randint(int low, int high) {
        return (int) (low + (high - (low) + 1) * Rand());
    }

    /* Genera un numero aleatorio real en el intervalo [low,...,high[
       (incluyendo 'low' pero sin incluir 'high') */
    public float Randfloat(float low, float high) {
        return (low + (high - (low)) * Rand());
    }
}
