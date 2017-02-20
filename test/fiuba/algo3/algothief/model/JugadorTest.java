/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fiuba.algo3.algothief.model;

import fiuba.algo3.algothief.model.Juego.Dificultad;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import junit.framework.Assert;

import org.junit.Test;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

/**
 *
 * @author Ramiro
 */
public class JugadorTest {

    public JugadorTest() {
    }

    @Test
    public void testNuevoJugadorSeLlamaTimon() {
        Jugador jugador = new Jugador("Timon");
        Assert.assertEquals("Timon", jugador.getNombre());
    }

    @Test
    public void testNuevoJugadorTieneRangoNovato() {
        Jugador jugador = new Jugador("Timon");
        Assert.assertEquals("Novato", jugador.getGrado().nombreGrado());
    }

    @Test
    public void testNuevoJugadorNoTieneCasoAsignado() {
        Jugador jugador = new Jugador("Timon");
        Assert.assertNull(jugador.getCaso());
    }

    @Test
    public void testJugadorPierdeCasoYNoTieneNingunoAsignado() {
        Jugador jugador = new Jugador("Timon");
        Caso caso = mock(Caso.class);
        when(caso.isFallido()).thenReturn(true);

        jugador.asignarCaso(caso);

        jugador.casoPerdido();
        Assert.assertNull(jugador.getCaso());
    }

    @Test
    public void testJugadorConCasoAsignadoNoPuedeAsignarNuevoCaso() {
        Jugador jugador = new Jugador("Timon");

        Caso caso = mock(Caso.class);
        Caso casoNuevo = mock(Caso.class);

        jugador.asignarCaso(caso);

        jugador.asignarCaso(casoNuevo);

        Assert.assertSame(caso, jugador.getCaso());
    }

    @Test
    public void testJugadorNovatoTardaMasEnViajeQueSargento() {

        Latitud latitudArg = new Latitud("S", 34, 35, 59);
        Longitud longitudArg = new Longitud("W", 58, 22, 54);
        Coordenadas coordenadasArg = new Coordenadas(latitudArg, longitudArg);

        Latitud latitudBrasil = new Latitud("S", 15, 47, 56);
        Longitud longitudBrasil = new Longitud("W", 47, 52, 00);
        Coordenadas coordenadasBrasil = new Coordenadas(latitudBrasil, longitudBrasil);

        Pais argentina = new Pais(coordenadasArg, "Argentina");
        Pais brasil = new Pais(coordenadasBrasil, "Brasil");

        Ladron mockLadron = mock(Ladron.class);

        Set<Pista> pistas = new HashSet<Pista>();
        pistas.add(new Pista(mock(List.class), brasil));

        Caso caso = new Caso(Dificultad.FACIL, argentina, mock(ObjetoRobado.class), mockLadron, null);
        caso.setPistas(pistas);

        Jugador jugadorNovato = new Jugador("TimyTimon");

        jugadorNovato.asignarCaso(caso);
        jugadorNovato.realizarViaje(brasil);
        Assert.assertEquals(152, caso.getTiempoResolucion());

        Jugador jugadorSargento = new Jugador("TimyTimonson", 20);

        Caso caso2 = new Caso(Dificultad.FACIL, argentina, mock(ObjetoRobado.class), mockLadron, null);
        caso2.setPistas(pistas);
        jugadorSargento.asignarCaso(caso2);
        jugadorSargento.realizarViaje(brasil);
        Assert.assertEquals(153, caso2.getTiempoResolucion());
    }

    @Test
    public void testJugadorCambiaGrado() {
        Jugador jugador = new Jugador("TimyTimon");
        Caso caso = mock(Caso.class);
        when(caso.isResuelto()).thenReturn(true);

        for (int i = 0; i <= 5; i++) {
            jugador.asignarCaso(caso);
            jugador.casoResuelto();
        }

        Assert.assertSame("Detective", jugador.getGrado().nombreGrado());
    }
    
        @Test
    public void testJugadorQueViajaAlPaisDondeEstaNoPierdeHorasEnElCaso() {

        Latitud latitudArg = new Latitud("S", 34, 35, 59);
        Longitud longitudArg = new Longitud("W", 58, 22, 54);
        Coordenadas coordenadasArg = new Coordenadas(latitudArg, longitudArg);

        Pais argentina = new Pais(coordenadasArg, "Argentina");

        Caso caso = new Caso(Dificultad.FACIL, argentina, mock(ObjetoRobado.class), mock(Ladron.class), null);

        // Jugador se encuentra en Argentina
        Jugador jugador = new Jugador("TimyTimon");

        jugador.asignarCaso(caso);
        
        jugador.realizarViaje(argentina);
        Assert.assertEquals(154, caso.getTiempoResolucion());
    }
}
