/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fiuba.algo3.algothief.model;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import org.junit.After;
import org.junit.Assert;
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
public class PistaTest {

    public PistaTest() {
    }

    @Test
    public void testPistaSeCreaConPaisDeDestinoYListaDeLugares() {
        Pais paisDestino = mock(Pais.class);
        List<Lugar> lugares = mock(List.class);

        Pista pista = new Pista(lugares, paisDestino);

        Assert.assertSame(paisDestino, pista.getPaisDestino());
        Assert.assertNotNull(pista.getLugares());
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

        Pista pista = new Pista(generarLugares(), new Pais(new Coordenadas(new Latitud("S", 34, 35, 59), new Longitud("W", 58, 22, 54)), "Argentina"));
        Element elementoPista = pista.serializar(doc);
        junit.framework.Assert.assertNotNull(elementoPista);

        // Hasta crea el doc y aca serializa el indicio
        doc.appendChild(elementoPista);
        TransformerFactory transformerFactory = TransformerFactory.newInstance();
        Transformer transformer = transformerFactory.newTransformer();
        DOMSource source = new DOMSource(doc);
        File archivoDestino = new File(this.nombreArchivo);
        StreamResult result = new StreamResult(archivoDestino);
        transformer.transform(source, result);

        File archivo = new File(this.nombreArchivo);
        junit.framework.Assert.assertTrue(archivo.exists());

        // ahora hacemos lo inveso, levantamos el archivo de disco y 
        //  y verificamos que los objetos se hidratan correctamente
        DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
        DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
        doc = dBuilder.parse(archivo);
        doc.getDocumentElement().normalize();

        Pista pistaHidratada = Pista.hidratar(doc.getElementsByTagName("Pista").item(0));
        Assert.assertNotNull(pistaHidratada);
        Assert.assertEquals("Argentina", pistaHidratada.getPaisDestino().getNombre());
        Assert.assertEquals("Banco Frances", pistaHidratada.getLugares().get(0).getNombre());
        Assert.assertEquals("Biblioteca", pistaHidratada.getLugares().get(1).getNombre());

        // Pruebo con un LugarUltimoPais tambien
        Assert.assertEquals("Puerto", pistaHidratada.getLugares().get(2).getNombre());
    }

    private List<Lugar> generarLugares() {
        Indicio indicio = new Indicio("indicioFacil", "indicioNormal", "indicioDificil");

        List<Lugar> lugares = new LinkedList<Lugar>();
        Lugar banco = new Lugar("Banco Frances", indicio);
        Lugar biblioteca = new Lugar("Biblioteca", indicio);
        Lugar puerto = new LugarUltimoPais("Puerto", false);

        lugares.add(banco);
        lugares.add(biblioteca);
        lugares.add(puerto);
        return lugares;
    }

    @Test
    public void testNoSePuedeModificarListaDeLugares() {
        List<Lugar> lugares = new ArrayList<Lugar>();
        Pista pista = new Pista(lugares, mock(Pais.class));

        try {
            pista.getLugares().add(mock(Lugar.class));
            Assert.fail();
        } catch (UnsupportedOperationException ex) {
            Assert.assertTrue(true);
        }
    }

    @Test
    public void testPistaUltimoPaisTieneLadronEnUnLugarAleatorio() {
        Pista pista = Pista.pistaUltimoPais();
        Caso caso = mock(Caso.class);
        when(caso.ordenDeArrestoEmitida()).thenReturn(true);
        when(caso.ordenEmitidaContraElLadronDelCaso()).thenReturn(true);
        when(caso.getPista()).thenReturn(pista);


        boolean primero = false;
        boolean segundo = false;
        boolean tercero = false;

        if (pista.getLugares().get(0).entrarLugar(caso).equals("Encontraste al ladron y lo has capturado! Felicitaciones!")) {
            primero = true;
        }

        if (pista.getLugares().get(1).entrarLugar(caso).equals("Encontraste al ladron y lo has capturado! Felicitaciones!")) {
            segundo = true;
        }
        if (pista.getLugares().get(2).entrarLugar(caso).equals("Encontraste al ladron y lo has capturado! Felicitaciones!")) {
            tercero = true;
        }
        Assert.assertTrue(primero ^ segundo ^ tercero);
    }
}
