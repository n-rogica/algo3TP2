package fiuba.algo3.algothief.model;

import junit.framework.Assert;
import org.junit.Test;

import fiuba.algo3.algothief.model.Juego.Dificultad;
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
import org.junit.After;
import org.junit.Before;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.xml.sax.SAXException;

public class ObjetoRobadoTest {

    public ObjetoRobadoTest() {
    }

    @Test
    public void testSeCreaObjetoRobadoSeCreaConDescripcionYvalor() {
        ObjetoRobado objetoRobado = new ObjetoRobado("pp", Dificultad.FACIL);
        Assert.assertNotNull(objetoRobado.getDescripcion());
        Assert.assertNotNull(objetoRobado.getValor());
    }

    @Test
    public void testObjetoRobadoConDificultadFacilDevuelveValorDentroDelRango() {
        ObjetoRobado objetoRobado = new ObjetoRobado("pp", Dificultad.FACIL);

        Assert.assertTrue(objetoRobado.minimoValorObjetoRobado <= objetoRobado.getValor());
        Assert.assertTrue(objetoRobado.maxValorObjetoFacil >= objetoRobado.getValor());
    }

    @Test
    public void testObjetoRobadoConDificultadNormalDevuelveValorDentroDelRango() {
        ObjetoRobado objetoRobado = new ObjetoRobado("pp", Dificultad.NORMAL);

        Assert.assertTrue(objetoRobado.maxValorObjetoFacil <= objetoRobado.getValor());
        Assert.assertTrue(objetoRobado.maxValorObjetoNormal >= objetoRobado.getValor());
    }

    @Test
    public void testObjetoRobadoConDificultadDificilDevuelveValorDentroDelRango() {
        ObjetoRobado objetoRobado = new ObjetoRobado("pp", Dificultad.DIFICIL);

        Assert.assertTrue(objetoRobado.maxValorObjetoNormal <= objetoRobado.getValor());
        Assert.assertTrue(objetoRobado.maxValorObjetoDificil >= objetoRobado.getValor());
    }

    @Test
    public void testObjetoRobadoDevuelveDescripcion() {
        ObjetoRobado objetoRobado = new ObjetoRobado("pp", Dificultad.NORMAL);
        Assert.assertEquals("pp", objetoRobado.getDescripcion());

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

        ObjetoRobado objetoRobado = new ObjetoRobado("ObjetoRobado de Prueba", Dificultad.FACIL);
        Element elementoObjRob = objetoRobado.serializar(doc);
        Assert.assertNotNull(elementoObjRob);

        // Hasta crea el doc y aca serializa el indicio
        doc.appendChild(elementoObjRob);
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

        ObjetoRobado objRobHidratado = ObjetoRobado.hidratar(doc.getElementsByTagName("ObjetoRobado").item(0));
        Assert.assertNotNull(objRobHidratado);
        Assert.assertEquals("ObjetoRobado de Prueba", objRobHidratado.getDescripcion());
        Assert.assertEquals(Dificultad.FACIL, objRobHidratado.getDificultadObjeto());
    }
}
