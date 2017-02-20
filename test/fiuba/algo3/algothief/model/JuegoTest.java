/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fiuba.algo3.algothief.model;

import fiuba.algo3.algothief.model.Juego.Dificultad;

import java.io.File;
import java.io.IOException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.TransformerException;

import junit.framework.Assert;

import org.junit.Before;
import org.junit.Test;

import static org.mockito.Mockito.spy;
import static org.mockito.Mockito.when;

import org.w3c.dom.Document;
import org.xml.sax.SAXException;

/**
 *
 * @author Ramiro
 */
public class JuegoTest {

    public JuegoTest() {
    }

    @Before
    public void eliminarArchivoJugadores() {
        File archivoJugadores = new File("Jugadores.xml");
        if (archivoJugadores.exists()) {
            archivoJugadores.delete();
        }
    }

    @Test
    public void testSeAsignaCasoFacilAJugadorNovato() throws ParserConfigurationException, TransformerException, SAXException, IOException {
        Juego juego = new Juego();
        Jugador jugador = new Jugador("TimyTimon");

        juego.asignarCasoNuevo(jugador);

        Assert.assertEquals(jugador.getCaso().getDificultad(), Dificultad.FACIL);
    }

    @Test
    public void testSeAsignaCasoFacilAJugadorDetective() throws ParserConfigurationException, TransformerException, SAXException, IOException {
        Juego juego = new Juego();
        Jugador jugador = new Jugador("TimyTimon");

        Jugador spyJugador = spy(jugador);
        when(spyJugador.getGrado()).thenReturn(new Detective());

        juego.asignarCasoNuevo(spyJugador);

        Assert.assertEquals(spyJugador.getCaso().getDificultad(), Dificultad.FACIL);
    }

    @Test
    public void testSeAsignaCasoNormalAJugadorInvestigador() throws ParserConfigurationException, TransformerException, SAXException, IOException {
        Juego juego = new Juego();
        Jugador jugador = new Jugador("TimyTimon");

        Jugador spyJugador = spy(jugador);
        when(spyJugador.getGrado()).thenReturn(new Investigador());

        juego.asignarCasoNuevo(spyJugador);

        Assert.assertEquals(spyJugador.getCaso().getDificultad(), Dificultad.NORMAL);
    }

    @Test
    public void testSeAsignaCasoDificilAJugadorSargento() throws ParserConfigurationException, TransformerException, SAXException, IOException {
        Juego juego = new Juego();
        Jugador jugador = new Jugador("TimyTimon");

        Jugador spyJugador = spy(jugador);
        when(spyJugador.getGrado()).thenReturn(new Sargento());

        juego.asignarCasoNuevo(spyJugador);

        Assert.assertEquals(spyJugador.getCaso().getDificultad(), Dificultad.DIFICIL);
    }

    @Test
    public void testJuegoCreaArchivoDeJugadores() throws ParserConfigurationException, TransformerException, SAXException, IOException {
        @SuppressWarnings("unused")
		Juego juego = new Juego();
        Assert.assertTrue(new File("Jugadores.xml").exists());
    }

    @Test
    public void testJuegoCrearJugadorVerificaSiYaExisteYLoCarga() throws ParserConfigurationException, TransformerException, SAXException, IOException {
        
        Juego juego = new Juego();
        Jugador jugador = juego.crearJugador("Timy Timon");        
        
        //verifico que el jugador creado no tenga asignado ningun caso        
        Assert.assertNull(jugador.getCaso());  
        
        //asigno caso y guardo la partida
        juego.asignarCasoNuevo(jugador);
        juego.guardarJuego(jugador);
        
        //creo un jugador con el mismo nombre para que cargue los datos
        Jugador otroJugador = juego.crearJugador("Timy Timon");
        
        Assert.assertEquals(jugador.getNombre(),otroJugador.getNombre());
        Assert.assertEquals(jugador.getCasosResueltos(), otroJugador.getCasosResueltos());
        Assert.assertEquals(jugador.getGrado().nombreGrado(),otroJugador.getGrado().nombreGrado());        
        Assert.assertEquals(jugador.getCaso().getPaisActual().getNombre(), otroJugador.getCaso().getPaisActual().getNombre());
        Assert.assertEquals(jugador.getCaso().getTiempoResolucion(),otroJugador.getCaso().getTiempoResolucion());
        Assert.assertEquals(jugador.getCaso().getObjetoRobado().getDescripcion(), otroJugador.getCaso().getObjetoRobado().getDescripcion());
    }

    @Test
    public void guardarYRecuperar() throws ParserConfigurationException, TransformerException, SAXException, IOException {

        Juego juego = new Juego();
        Jugador jugador = juego.crearJugador("Timmy Timon");
        juego.asignarCasoNuevo(jugador);
        juego.guardarJuego(jugador);

        File archivoJugadores = new File("Jugadores.xml");

        Document doc;

        DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
        DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
        doc = dBuilder.parse(archivoJugadores);
        doc.getDocumentElement().normalize();

        Jugador jugadorHidratado = Jugador.hidratar(doc.getElementsByTagName("Jugador").item(0));
        Assert.assertNotNull(jugadorHidratado);
        Assert.assertEquals("Timmy Timon", jugadorHidratado.getNombre());
        Assert.assertEquals(true, jugadorHidratado.getCaso().isPendiente());
    }

    @Test
    public void guardarYRecuperarExistente() throws ParserConfigurationException, TransformerException, SAXException, IOException {

        Juego juego = new Juego();
        Jugador jugador = juego.crearJugador("Timmy Timon");
        juego.asignarCasoNuevo(jugador);
        juego.guardarJuego(jugador);

        File archivoJugadores = new File("Jugadores.xml");

        Document doc;

        DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
        DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
        doc = dBuilder.parse(archivoJugadores);
        doc.getDocumentElement().normalize();

        Jugador jugadorHidratado = Jugador.hidratar(doc.getElementsByTagName("Jugador").item(0));
        Assert.assertNotNull(jugadorHidratado);
        Assert.assertEquals("Timmy Timon", jugadorHidratado.getNombre());
        Assert.assertEquals(true, jugadorHidratado.getCaso().isPendiente());

        Juego juegoNuevo = new Juego();
        Jugador jugadorNuevo = juegoNuevo.crearJugador("Timmy Timon");
        Assert.assertEquals("Timmy Timon", jugadorNuevo.getNombre());
        Assert.assertEquals(true, jugadorNuevo.getCaso().isPendiente());
        juegoNuevo.guardarJuego(jugadorNuevo);

    }
}
