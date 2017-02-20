package fiuba.algo3.algothief.model;

import java.io.File;
import java.io.IOException;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.TransformerException;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import org.w3c.dom.Document;
import org.xml.sax.SAXException;
import fiuba.algo3.algothief.model.Caracteristica.Tipo;
import junit.framework.Assert;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.w3c.dom.Element;

public class CaracteristicaTest {

	public CaracteristicaTest (){
	}	
		private String nombreArchivo;   
		
		@Test
		public void testCaracteristicaSeCreaConContenidoYDescripcion(){
			Caracteristica caracteristica = new Caracteristica (Tipo.VEHICULO,"Limusina","Llego en una limusina");
			Assert.assertEquals ("Limusina",caracteristica.getContenido());
			Assert.assertEquals("Llego en una limusina", caracteristica.getDescripcion());
		}
		
		@Test
		public void testCaracteristicaDevuelveTipoCorrespondiente(){			
			Caracteristica caracteristica = new Caracteristica (Tipo.HOBBY,"tenis", null);
			
			Assert.assertEquals (Tipo.HOBBY,caracteristica.getTipo());
		}
		
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
			
			Caracteristica caracteristica = new Caracteristica (Tipo.CABELLO,"negro","tenia cabello color negro");
			Element caracteristicaSerializada = caracteristica.serializar(doc);
			Assert.assertNotNull(caracteristicaSerializada);			
			
			doc.appendChild(caracteristicaSerializada);
			TransformerFactory transformerFactory = TransformerFactory.newInstance();
			Transformer transformer = transformerFactory.newTransformer();
			DOMSource source = new DOMSource(doc);
			File archivoDestino = new File(this.nombreArchivo);
			StreamResult result = new StreamResult(archivoDestino);
			transformer.transform(source, result);
			
			File archivo = new File(this.nombreArchivo);
			Assert.assertTrue(archivo.exists());			

			DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
			DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
			doc = dBuilder.parse(archivo);
			doc.getDocumentElement().normalize();
			
			Caracteristica caracteristicaHidratada = Caracteristica.hidratar(doc.getFirstChild());
			Assert.assertNotNull(caracteristicaHidratada);
			Assert.assertEquals(caracteristicaHidratada.getTipo(), caracteristica.getTipo());
			Assert.assertEquals(caracteristicaHidratada.getContenido(), caracteristica.getContenido());
			Assert.assertEquals(caracteristicaHidratada.getDescripcion(), caracteristica.getDescripcion());			
		}
}