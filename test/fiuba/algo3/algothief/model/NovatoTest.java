/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package fiuba.algo3.algothief.model;

import org.junit.Assert;
import org.junit.Test;

/**
 *
 * @author Ramiro
 */
public class NovatoTest {
    
    public NovatoTest() {
    }
        @Test
    public void testNombreGrado() {
        Grado grado = new Novato();
        Assert.assertEquals("Novato", grado.nombreGrado());
    }

    @Test
    public void testVelocidadViaje() {
        Grado grado = new Novato();
        Assert.assertEquals(900, grado.velocidadViaje());
    }
}
