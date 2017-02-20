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
public class Novato implements Grado {

    private final int velocidadViaje = 900;

    Novato() {
    }

    @Override
    public int velocidadViaje() {
        return this.velocidadViaje;
    }

    @Override
    public Dificultad dificultadGrado() {
        return Dificultad.FACIL;
    }

    @Override
    public String nombreGrado() {
        return "Novato";
    }
}
