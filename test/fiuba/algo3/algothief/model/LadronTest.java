package fiuba.algo3.algothief.model;

import fiuba.algo3.algothief.model.Caracteristica.Tipo;
import java.util.HashMap;
import java.util.Map;

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
import org.junit.Assert;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.xml.sax.SAXException;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class LadronTest {

    public LadronTest() {
    }

    @Test
    public void testLadronSeCreaConNombreYCaracteristicas() {

        Map<Tipo, Caracteristica> caracteristicas = new HashMap<Tipo, Caracteristica>();
        //Estos datos se levantarian del xml despues		
        Caracteristica caracteristica1 = new Caracteristica(Tipo.SEXO, "Masculino", "sexo del ladron");
        Caracteristica caracteristica2 = new Caracteristica(Tipo.VEHICULO, "Deportivo", "Manejaba un deportivo");
        Caracteristica caracteristica3 = new Caracteristica(Tipo.CABELLO, "Negro", "Tenia cabello de color negro");
        Caracteristica caracteristica4 = new Caracteristica(Tipo.HOBBY, "Tenis", "Queria participar en un campeonato de tenis");
        Caracteristica caracteristica5 = new Caracteristica(Tipo.SENIA, "Tatuaje", "Tenia un tatuaje en el brazo");

        caracteristicas.put(Tipo.SEXO, caracteristica1);
        caracteristicas.put(Tipo.VEHICULO, caracteristica2);
        caracteristicas.put(Tipo.CABELLO, caracteristica3);
        caracteristicas.put(Tipo.HOBBY, caracteristica4);
        caracteristicas.put(Tipo.SENIA, caracteristica5);

        Ladron ladron = new Ladron("pp", caracteristicas);

        Assert.assertEquals("pp", ladron.getNombre());
        Assert.assertEquals("Tenis", ladron.obtenerCaracteristica(Tipo.HOBBY));
        Assert.assertEquals("Negro", ladron.obtenerCaracteristica(Tipo.CABELLO));
        Assert.assertEquals("Masculino", ladron.obtenerCaracteristica(Tipo.SEXO));
        Assert.assertEquals("Tatuaje", ladron.obtenerCaracteristica(Tipo.SENIA));
        Assert.assertEquals("Deportivo", ladron.obtenerCaracteristica(Tipo.VEHICULO));
    }

    @Test
    public void testLadronDevuelveDescripcion() {

        Ladron ladron = new Ladron("pp");
        Assert.assertNotNull(ladron.obtenerDescripcionDelLadron());
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

        Map<Tipo, Caracteristica> caracteristicas = new HashMap<Tipo, Caracteristica>();
        //Estos datos se levantarian del xml despues		
        Caracteristica caracteristica1 = new Caracteristica(Tipo.SEXO, "Masculino", "sexo del ladron");
        Caracteristica caracteristica2 = new Caracteristica(Tipo.VEHICULO, "Deportivo", "Manejaba un deportivo");
        Caracteristica caracteristica3 = new Caracteristica(Tipo.CABELLO, "Negro", "Tenia cabello de color negro");
        Caracteristica caracteristica4 = new Caracteristica(Tipo.HOBBY, "Tenis", "Queria participar en un campeonato de tenis");
        Caracteristica caracteristica5 = new Caracteristica(Tipo.SENIA, "Tatuaje", "Tenia un tatuaje en el brazo");

        caracteristicas.put(Tipo.SEXO, caracteristica1);
        caracteristicas.put(Tipo.VEHICULO, caracteristica2);
        caracteristicas.put(Tipo.CABELLO, caracteristica3);
        caracteristicas.put(Tipo.HOBBY, caracteristica4);
        caracteristicas.put(Tipo.SENIA, caracteristica5);

        Ladron ladron = new Ladron("pp", caracteristicas);

        Element ladronSerializado = ladron.serializar(doc);
        Assert.assertNotNull(ladronSerializado);

		//ladron ya esta serializado
        doc.appendChild(ladronSerializado);
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

        Ladron ladronHidratado = Ladron.hidratar(doc.getFirstChild());
        Assert.assertNotNull(ladronHidratado);

        //verifico que todos los datos sean correctos
        Assert.assertEquals("pp", ladron.getNombre());
        Assert.assertEquals("Tenis", ladronHidratado.obtenerCaracteristica(Tipo.HOBBY));
        Assert.assertEquals("Negro", ladronHidratado.obtenerCaracteristica(Tipo.CABELLO));
        Assert.assertEquals("Masculino", ladronHidratado.obtenerCaracteristica(Tipo.SEXO));
        Assert.assertEquals("Tatuaje", ladronHidratado.obtenerCaracteristica(Tipo.SENIA));
        Assert.assertEquals("Deportivo", ladronHidratado.obtenerCaracteristica(Tipo.VEHICULO));
    }

}
