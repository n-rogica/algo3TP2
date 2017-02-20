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
import org.junit.Test;
import org.junit.Before;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.xml.sax.SAXException;

/**
 *
 * @author Ramiro
 */
public class CoordenadasTest {

    public CoordenadasTest() {
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

        Coordenadas coordenadas = new Coordenadas (new Latitud ("S",34,35,59),new Longitud ("W",58,22,54));
        Element elementoCoordenadas = coordenadas.serializar(doc);
        Assert.assertNotNull(elementoCoordenadas);

        // Hasta crea el doc y aca serializa las coordenadas
        doc.appendChild(elementoCoordenadas);
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

        Coordenadas coordenadasHidratadas = Coordenadas.hidratar(doc.getElementsByTagName("Coordenadas").item(0));
        Assert.assertNotNull(coordenadasHidratadas);
        Assert.assertEquals(-3844.0, coordenadas.getLatitud());
        Assert.assertEquals(6486.0, coordenadas.getLongitud());
    }
}
