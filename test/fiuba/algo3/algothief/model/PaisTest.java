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
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.xml.sax.SAXException;

/**
 *
 * @author Ramiro
 */
public class PaisTest {

    public PaisTest() {
    }

    @Test
    public void testDistanciaDePaisesSegunCoordenadas() {

        Latitud latitudArg = new Latitud("S", 34, 35, 59);
        Longitud longitudArg = new Longitud("W", 58, 22, 54);
        Coordenadas coordenadasArg = new Coordenadas(latitudArg, longitudArg);

        Latitud latitudBrasil = new Latitud("S", 15, 47, 56);
        Longitud longitudBrasil = new Longitud("W", 47, 52, 00);
        Coordenadas coordenadasBrasil = new Coordenadas(latitudBrasil, longitudBrasil);

        Pais argentina = new Pais(coordenadasArg, "Argentina");
        Pais brasil = new Pais(coordenadasBrasil, "Brasil");
        Assert.assertEquals(2393, argentina.distanciaPaises(brasil));
    }

    @Test
    public void testPaisYStringNoSonIguales() {

        Assert.assertFalse((new Pais(mock(Coordenadas.class), "Argentina").equals(new String("Argentina"))));
    }

    @Test
    public void testPaisDeMismoNombreSonIguales() {
        Assert.assertTrue(new Pais(new Coordenadas(mock(Latitud.class), mock(Longitud.class)), "Argentina").equals(new Pais(new Coordenadas(mock(Latitud.class), mock(Longitud.class)), "Argentina")));
    }

    @Test
    public void testPaisDeDistintoNombreNoSonIguales() {
        Assert.assertFalse(new Pais(mock(Coordenadas.class), "Argentina").equals(new Pais(mock(Coordenadas.class), "Brasil")));
    }

    @Test
    public void testPaisDeMismoNombreTienenMismoHash() {
        Assert.assertTrue(new Pais(new Coordenadas(mock(Latitud.class), mock(Longitud.class)), "Argentina").hashCode() == new Pais(new Coordenadas(mock(Latitud.class), mock(Longitud.class)), "Argentina").hashCode());
    }

    @Test
    public void testPaisDeDistintoNombreNoTienenMismoHash() {
        Assert.assertFalse(new Pais(mock(Coordenadas.class), "Argentina").hashCode() == new Pais(mock(Coordenadas.class), "Brasil").hashCode());
    }

    @Test
    public void testPaiesSinNombreTienenMismoHash() {
        Assert.assertTrue(new Pais(mock(Coordenadas.class), null).hashCode() == new Pais(mock(Coordenadas.class), null).hashCode());
    }

    @Test
    public void testDistanciaAMismoPaisEsCero() {
        Latitud latitudArg = new Latitud("S", 34, 35, 59);
        Longitud longitudArg = new Longitud("W", 58, 22, 54);
        Coordenadas coordenadasArg = new Coordenadas(latitudArg, longitudArg);
        Pais argentina = new Pais(coordenadasArg, "Argentina");

        Assert.assertEquals(0, argentina.distanciaPaises(argentina));
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
        DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
        DocumentBuilder db = dbf.newDocumentBuilder();
        Document doc = db.newDocument();

        Pais pais = new Pais(new Coordenadas(new Latitud("S", 34, 35, 59), new Longitud("W", 58, 22, 54)), "Argentina");
        Element elementoPais = pais.serializar(doc);
        Assert.assertNotNull(elementoPais);

        // Hasta crea el doc y aca serializa el indicio
        doc.appendChild(elementoPais);
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

        Pais paisHidratado = Pais.hidratar(doc.getElementsByTagName("Pais").item(0));
        Assert.assertNotNull(paisHidratado);
        Assert.assertEquals("Argentina", paisHidratado.getNombre());
    }
}
