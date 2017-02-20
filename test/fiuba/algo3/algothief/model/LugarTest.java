/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fiuba.algo3.algothief.model;

import java.io.File;
import java.io.IOException;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import junit.framework.Assert;
import org.junit.After;
import org.junit.Before;

import org.junit.Test;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.xml.sax.SAXException;

/**
 *
 * @author Ramiro
 */
public class LugarTest {

    public LugarTest() {
    }

    @Test
    public void testEntrarPorPrimeraVezDescuenta1HoraDelCaso() {
        Indicio indicio = new Indicio("Facil", "Normal", "Dificil");
        Caso caso = new Caso(Juego.Dificultad.FACIL, mock(Pais.class), mock(ObjetoRobado.class), mock(Ladron.class), null);
        Lugar banco = new Lugar("Banco Frances", indicio);

        banco.entrarLugar(caso);

        Assert.assertEquals(153, caso.getTiempoResolucion());
    }

    @Test
    public void testEntrarPorSegundaVezDescuenta2HorasDelCaso() {
        Indicio indicio = new Indicio("Facil", "Normal", "Dificil");
        Caso caso = new Caso(Juego.Dificultad.FACIL, mock(Pais.class), mock(ObjetoRobado.class), mock(Ladron.class), null);
        Lugar banco = new Lugar("Banco Frances", 1, indicio);

        banco.entrarLugar(caso);

        Assert.assertEquals(152, caso.getTiempoResolucion());
    }

    @Test
    public void testEntrarPorTerceraVezDescuenta3HorasDelCaso() {
        Indicio indicio = new Indicio("Facil", "Normal", "Dificil");
        Caso caso = new Caso(Juego.Dificultad.FACIL, mock(Pais.class), mock(ObjetoRobado.class), mock(Ladron.class), null);
        Lugar banco = new Lugar("Banco Frances", 2, indicio);

        banco.entrarLugar(caso);

        Assert.assertEquals(151, caso.getTiempoResolucion());
    }

    @Test
    public void testEntrarPorCuartaVezDescuenta3HorasDelCaso() {
        Indicio indicio = new Indicio("Facil", "Normal", "Dificil");
        Caso caso = new Caso(Juego.Dificultad.FACIL, mock(Pais.class), mock(ObjetoRobado.class), mock(Ladron.class), null);
        Lugar banco = new Lugar("Banco Frances", 3, indicio);

        banco.entrarLugar(caso);

        Assert.assertEquals(151, caso.getTiempoResolucion());
    }

    @Test
    public void testEntrarPorPrimeraVezDevuelveElIndicio() {
        Indicio indicio = new Indicio("Indicio Facil", "Indicio Normal", " Indicio Dificil");
        Caso caso = new Caso(Juego.Dificultad.NORMAL, mock(Pais.class), mock(ObjetoRobado.class), mock(Ladron.class), null);
        Lugar banco = new Lugar("Banco Frances", indicio);

        Assert.assertTrue(banco.entrarLugar(caso).contains("Indicio Normal"));
    }

    private String nombreArchivo;

    @Before
    public void before() {
        this.nombreArchivo = java.util.UUID.randomUUID().toString() + ".xml";
    }

    @After
    public void after() {
        File archivo = new File(this.nombreArchivo);
        if (archivo.exists()) {
            archivo.delete();
        }
    }

    @Test
    public void guardarYRecuperar() throws ParserConfigurationException, TransformerException, SAXException, IOException {
        Indicio indicio = new Indicio("Indicio Facil", "Indicio Normal", " Indicio Dificil");
        Caso caso = mock(Caso.class);
        when(caso.getDificultad()).thenReturn(Juego.Dificultad.FACIL);
        when(caso.darIndicioLadron()).thenReturn("");

        DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
        DocumentBuilder db = dbf.newDocumentBuilder();
        Document doc = db.newDocument();

        Lugar lugar = new Lugar("Banco", indicio);
        Element elementoLugar = lugar.serializar(doc);
        Assert.assertNotNull(elementoLugar);

        // Hasta crea el doc y aca serializa las coordenadas
        doc.appendChild(elementoLugar);
        TransformerFactory transformerFactory = TransformerFactory.newInstance();
        Transformer transformer = transformerFactory.newTransformer();
        DOMSource source = new DOMSource(doc);
        File archivoDestino = new File(this.nombreArchivo);
        StreamResult result = new StreamResult(archivoDestino);
        transformer.transform(source, result);

        File archivo = new File(this.nombreArchivo);
        Assert.assertTrue(archivo.exists());

        // ahora hacemos lo inveso, levantamos el archivo de disco y 
        //  y verificamos que los objetos se hidratan correctamente
        DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
        DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
        doc = dBuilder.parse(archivo);
        doc.getDocumentElement().normalize();

        Lugar lugarHidratado = Lugar.hidratar(doc.getElementsByTagName("Lugar").item(0));
        Assert.assertNotNull(lugarHidratado);
        Assert.assertEquals("Banco", lugarHidratado.getNombre());
        Assert.assertEquals("Indicio Facil", lugarHidratado.entrarLugar(caso));
    }
}
