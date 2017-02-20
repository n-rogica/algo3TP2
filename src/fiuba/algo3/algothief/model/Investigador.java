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
class Investigador implements Grado {

    private final int velocidadViaje = 1300;

    public Investigador() {
    }

    @Override
    public int velocidadViaje() {
        return velocidadViaje;
    }

    @Override
    public Dificultad dificultadGrado() {
        return Dificultad.NORMAL;
    }

    @Override
    public String nombreGrado() {
        return "Investigador";
    }

}
