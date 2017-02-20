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
public class SargentoTest {

    public SargentoTest() {
    }

    @Test
    public void testNombreGrado() {
        Grado grado = new Sargento();
        Assert.assertEquals("Sargento", grado.nombreGrado());
    }

    @Test
    public void testVelocidadViaje() {
        Grado grado = new Sargento();
        Assert.assertEquals(1500, grado.velocidadViaje());
    }
}
