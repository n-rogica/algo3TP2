package fiuba.algo3.algothief.model;

import org.junit.Test;
import fiuba.algo3.algothief.model.Caracteristica.Tipo;
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
import org.junit.Assert;
import org.junit.Before;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.xml.sax.SAXException;

public class OrdenDeArrestoTest {

	public OrdenDeArrestoTest() {		
	}
	
	@Test
	public void testSeCreaOrdenDeArrestoNoEmitida() {	
		OrdenDeArresto ordenDeArresto = new OrdenDeArresto();
	    Assert.assertEquals(false,ordenDeArresto.isEmitida());
	}
	
	@Test
	public void testEmitirOrdenDeArrestoModificaEstado(){
		OrdenDeArresto ordenDeArresto = new OrdenDeArresto();
		ordenDeArresto.emitirOrdenDeArresto("pp");		
		Assert.assertTrue(ordenDeArresto.isEmitida());		
	}	
	
	
	@Test
	public void testOrdenDeArrestoEmitidaAgregaElNombreDelSospechoso(){
		OrdenDeArresto ordenDeArresto = new OrdenDeArresto();
		
		Assert.assertNull(ordenDeArresto.obtenerNombreDelSospechoso());		
		ordenDeArresto.emitirOrdenDeArresto("pp");		
		Assert.assertEquals("pp",ordenDeArresto.obtenerNombreDelSospechoso());
	}
	
	@Test
	public void testOrdenDeArrestoPermiteAgregarDistintosTiposDeCaracteristicas(){
		
		Caracteristica sexoDelLadron = new Caracteristica (Tipo.SEXO,"sexo del ladron", null);
		Caracteristica vehiculoDelLadron = new Caracteristica (Tipo.VEHICULO,"vehiculo del ladron", null);
		Caracteristica cabelloDelLadron = new Caracteristica (Tipo.CABELLO,"color de cabello del ladron", null);
		Caracteristica hobbyDelLadron = new Caracteristica (Tipo.HOBBY,"hobby del ladron", null);
		Caracteristica seniaDelLadron = new Caracteristica (Tipo.SENIA,"senia del ladron", null);		
		OrdenDeArresto ordenDeArresto = new OrdenDeArresto();
		
		ordenDeArresto.agregarCaracteristica(sexoDelLadron);
		ordenDeArresto.agregarCaracteristica(vehiculoDelLadron);
		ordenDeArresto.agregarCaracteristica(cabelloDelLadron);
		ordenDeArresto.agregarCaracteristica(hobbyDelLadron);
		ordenDeArresto.agregarCaracteristica(seniaDelLadron);
		
		Assert.assertEquals(sexoDelLadron.getContenido(),ordenDeArresto.obtenerCaracteristica(Tipo.SEXO));
		Assert.assertEquals(vehiculoDelLadron.getContenido(),ordenDeArresto.obtenerCaracteristica(Tipo.VEHICULO));
		Assert.assertEquals(cabelloDelLadron.getContenido(),ordenDeArresto.obtenerCaracteristica(Tipo.CABELLO));
		Assert.assertEquals(hobbyDelLadron.getContenido(),ordenDeArresto.obtenerCaracteristica(Tipo.HOBBY));
		Assert.assertEquals(seniaDelLadron.getContenido(),ordenDeArresto.obtenerCaracteristica(Tipo.SENIA));		
	}
	
	@Test
	public void testObtenerCaracteristicaDevuelveNullSiNoSeAgregoNada(){
		OrdenDeArresto ordenDeArresto = new OrdenDeArresto();
		
		Assert.assertNull(ordenDeArresto.obtenerCaracteristica(Tipo.SEXO));
		Assert.assertNull(ordenDeArresto.obtenerCaracteristica(Tipo.VEHICULO));
		Assert.assertNull(ordenDeArresto.obtenerCaracteristica(Tipo.HOBBY));
		Assert.assertNull(ordenDeArresto.obtenerCaracteristica(Tipo.SENIA));
		Assert.assertNull(ordenDeArresto.obtenerCaracteristica(Tipo.CABELLO));
		
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
		
		Caracteristica sexoDelLadron = new Caracteristica (Tipo.SEXO,"sexo del ladron", null);
		Caracteristica vehiculoDelLadron = new Caracteristica (Tipo.VEHICULO,"vehiculo del ladron", null);
		Caracteristica cabelloDelLadron = new Caracteristica (Tipo.CABELLO,"color de cabello del ladron", null);
		Caracteristica hobbyDelLadron = new Caracteristica (Tipo.HOBBY,"hobby del ladron", null);
		Caracteristica seniaDelLadron = new Caracteristica (Tipo.SENIA,"senia del ladron", null);		
		OrdenDeArresto ordenDeArresto = new OrdenDeArresto();
		
		ordenDeArresto.agregarCaracteristica(sexoDelLadron);
		ordenDeArresto.agregarCaracteristica(vehiculoDelLadron);
		ordenDeArresto.agregarCaracteristica(cabelloDelLadron);
		ordenDeArresto.agregarCaracteristica(hobbyDelLadron);
		ordenDeArresto.agregarCaracteristica(seniaDelLadron);
		
		Element ordenDeArrestoSerializada = ordenDeArresto.serializar(doc);
		
		//la orden ya esta serializada
		
		doc.appendChild(ordenDeArrestoSerializada);
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
		
		OrdenDeArresto ordenDeArrestoHidratada = OrdenDeArresto.hidratar(doc.getFirstChild());		
		Assert.assertNotNull(ordenDeArrestoHidratada);		
		
		//verifico que no haya cambiado ningun dato 
		Assert.assertEquals(ordenDeArresto.obtenerCaracteristica(Tipo.SEXO), ordenDeArrestoHidratada.obtenerCaracteristica(Tipo.SEXO));
		Assert.assertEquals(ordenDeArresto.obtenerCaracteristica(Tipo.HOBBY), ordenDeArrestoHidratada.obtenerCaracteristica(Tipo.HOBBY));
		Assert.assertEquals(ordenDeArresto.obtenerCaracteristica(Tipo.SENIA), ordenDeArrestoHidratada.obtenerCaracteristica(Tipo.SENIA));
		Assert.assertEquals(ordenDeArresto.obtenerCaracteristica(Tipo.CABELLO), ordenDeArrestoHidratada.obtenerCaracteristica(Tipo.CABELLO));
		Assert.assertEquals(ordenDeArresto.obtenerCaracteristica(Tipo.VEHICULO), ordenDeArrestoHidratada.obtenerCaracteristica(Tipo.VEHICULO));
		Assert.assertEquals(ordenDeArresto.isEmitida(), ordenDeArrestoHidratada.isEmitida());
		Assert.assertTrue (ordenDeArrestoHidratada.obtenerNombreDelSospechoso().isEmpty());
		
    }
}
