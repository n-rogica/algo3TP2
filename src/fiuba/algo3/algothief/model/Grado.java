/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fiuba.algo3.algothief.model;

import fiuba.algo3.algothief.model.Juego.Dificultad;

/**
 *
 * @author Ramiro
 */
public interface Grado {

    public abstract int velocidadViaje();

    public abstract String nombreGrado();
    
    public abstract Dificultad dificultadGrado();
}
