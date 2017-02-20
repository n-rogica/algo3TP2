package fiuba.algo3.algothief.model;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.io.File;
import java.io.IOException;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

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
import org.junit.Test;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.xml.sax.SAXException;

import fiuba.algo3.algothief.model.Caracteristica.Tipo;
import junit.framework.Assert;

public class LugarUltimoPaisTest {

    private String nombreArchivo;

    @Test
    public void testEntrarAlPrimerLugarSinLadronDescuenta1HoraPorLaVisitaY2PorLosCuchillosDelCaso() {

        Pais brasil = new Pais(null, "Brasil");
        Ladron mockLadron = mock(Ladron.class);
        Caso caso = new Caso(Juego.Dificultad.FACIL, brasil, mock(ObjetoRobado.class), mockLadron, null);
        List<Lugar> lugares = generarLugares();

        Pista pista1 = new Pista(lugares, null);
        Set<Pista> pistas = new HashSet<Pista>();
        pistas.add(pista1);
        caso.setPistas(pistas);

        lugares.get(1).entrarLugar(caso);

        //descuenta 1 hs por la visita y 2hs por los cuchillos. Total 3 hs
        Assert.assertEquals(151, caso.getTiempoResolucion());

    }

    @Test
    public void testEntrarAlSegundoLugarSinLadronDescuenta2HoraPorLaVisitas2PorCuchilloY4PorLosDisparosDelCaso() {

        Ladron mockLadron = mock(Ladron.class);
        Pais brasil = new Pais(null, "Brasil");
        Caso caso = new Caso(Juego.Dificultad.FACIL, brasil, mock(ObjetoRobado.class), mockLadron, null);
        List<Lugar> lugares = generarLugares();

        Pista pista1 = new Pista(lugares, null);
        Set<Pista> pistas = new HashSet<Pista>();
        pistas.add(pista1);
        caso.setPistas(pistas);

        lugares.get(1).entrarLugar(caso);
        lugares.get(2).entrarLugar(caso);

        //descuenta 2 hs por la visita, 2 por el cuchillo y 4hs por el disparo. Total 8 hs
        Assert.assertEquals(146, caso.getTiempoResolucion());

    }

    @Test
    public void testEntrarAlLugarConLadronDescuenta1HoraPorLaVisitaDelCaso() {

        Ladron mockLadron = mock(Ladron.class);
        Pais brasil = new Pais(null, "Brasil");
        Caso caso = new Caso(Juego.Dificultad.FACIL, brasil, mock(ObjetoRobado.class), mockLadron, null);
        List<Lugar> lugares = generarLugares();

        Pista pista1 = new Pista(lugares, null);
        Set<Pista> pistas = new HashSet<Pista>();
        pistas.add(pista1);
        caso.setPistas(pistas);

        lugares.get(0).entrarLugar(caso);

        //descuenta 1 hs por la visita
        Assert.assertEquals(153, caso.getTiempoResolucion());

    }

    @Test
    public void testEntrarPorTerceraVezDescuenta3HorasDelCaso() {

        Ladron mockLadron = mock(Ladron.class);
        Indicio indicio = new Indicio("Facil", "Normal", "Dificil");
        Caso caso = new Caso(Juego.Dificultad.FACIL, mock(Pais.class), mock(ObjetoRobado.class), mockLadron, null);
        Lugar banco = new Lugar("Banco Francés", 2, indicio);

        banco.entrarLugar(caso);

        Assert.assertEquals(151, caso.getTiempoResolucion());
    }

    @Test
    public void testEntrarAlPrimerLugarSinLadronTiraCuchillos() {

        Ladron mockLadron = mock(Ladron.class);
        Pais brasil = new Pais(null, "Brasil");
        Caso caso = new Caso(Juego.Dificultad.FACIL, brasil, mock(ObjetoRobado.class), mockLadron, null);
        List<Lugar> lugares = generarLugares();

        Pista pista1 = new Pista(lugares, null);
        Set<Pista> pistas = new HashSet<Pista>();
        pistas.add(pista1);
        caso.setPistas(pistas);

        Assert.assertEquals("Vas por buen camino! Cuidado con los cuchillos!", lugares.get(1).entrarLugar(caso));

    }

    @Test
    public void testEntrarAlSegundoLugarSinLadronTiraDisparos() {

        Ladron mockLadron = mock(Ladron.class);
        Pais brasil = new Pais(null, "Brasil");
        Caso caso = new Caso(Juego.Dificultad.FACIL, brasil, mock(ObjetoRobado.class), mockLadron, null);
        List<Lugar> lugares = generarLugares();

        Pista pista1 = new Pista(lugares, null);
        Set<Pista> pistas = new HashSet<Pista>();
        pistas.add(pista1);
        caso.setPistas(pistas);

        lugares.get(1).entrarLugar(caso);

        Assert.assertEquals("Te estas acercando demasiado! Cuidado con los disparos!", lugares.get(2).entrarLugar(caso));

    }

    @Test
    public void testEntrarAlLugarConLadronYOrdenDeArrestoNoEmitidaSeteaElEstadoDelCasoEnFallido() {

        Ladron mockLadron = mock(Ladron.class);
        Pais brasil = new Pais(null, "Brasil");
        Caso caso = new Caso(Juego.Dificultad.FACIL, brasil, mock(ObjetoRobado.class), mockLadron, null);
        List<Lugar> lugares = generarLugares();

        Pista pista1 = new Pista(lugares, null);
        Set<Pista> pistas = new HashSet<Pista>();
        pistas.add(pista1);
        caso.setPistas(pistas);

        lugares.get(0).entrarLugar(caso);

        Assert.assertEquals(true, caso.isFallido());
    }
    
    @Test
    public void testEntrarAlLugarConLadronYOrdenDeArrestoEmitidaContraOtroSospechosoSeteaElEstadoDelCasoEnFallido() {

    	Ladron mockLadron = mock(Ladron.class);
    	Ladron mockOtroLadron = mock(Ladron.class);
    	when(mockLadron.getNombre()).thenReturn("pp");
    	when(mockOtroLadron.getNombre()).thenReturn("pipo");
    	when(mockOtroLadron.obtenerCaracteristica(Tipo.SENIA)).thenReturn("Tatuaje");
    	
    	List<Ladron> sospechosos = new LinkedList<Ladron>();
    	sospechosos.add(mockOtroLadron);
    	Caracteristica caracteristicaIngresada = new Caracteristica(Tipo.SENIA,"Tatuaje","Tenia un tatuaje en el brazo");
    	    	
    	
        Pais brasil = new Pais(null, "Brasil");
        Caso caso = new Caso(Juego.Dificultad.FACIL, brasil, mock(ObjetoRobado.class), mockLadron, sospechosos);
        List<Lugar> lugares = generarLugares();

        Pista pista1 = new Pista(lugares, null);
        Set<Pista> pistas = new HashSet<Pista>();
        pistas.add(pista1);
        caso.setPistas(pistas);
        
        caso.agregarCaracteristaALaOrden(caracteristicaIngresada);
        caso.buscarSospechosos();
       
        lugares.get(0).entrarLugar(caso);

        Assert.assertTrue(caso.isFallido());
    }

    @Test
    public void testEntrarAlLugarConLadronYOrdenDeArrestoEmitidaContraElLadronSeteaElEstadoDelCasoEnResuelto() {

    	Ladron mockLadron = mock(Ladron.class);
    	when(mockLadron.getNombre()).thenReturn("pp");
    	when(mockLadron.obtenerCaracteristica(Tipo.SENIA)).thenReturn("Tatuaje");
    	List<Ladron> sospechosos = new LinkedList<Ladron>();
    	sospechosos.add(mockLadron);
    	Caracteristica caracteristicaIngresada = new Caracteristica(Tipo.SENIA,"Tatuaje","Tenia un tatuaje en el brazo");
    	
    	
        Pais brasil = new Pais(null, "Brasil");
        Caso caso = new Caso(Juego.Dificultad.FACIL, brasil, mock(ObjetoRobado.class), mockLadron, sospechosos);
        List<Lugar> lugares = generarLugares();

        Pista pista1 = new Pista(lugares, null);
        Set<Pista> pistas = new HashSet<Pista>();
        pistas.add(pista1);
        caso.setPistas(pistas);
        
        caso.agregarCaracteristaALaOrden(caracteristicaIngresada);
        caso.buscarSospechosos();
       
        lugares.get(0).entrarLugar(caso);

        Assert.assertTrue(caso.isResuelto());
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

        LugarUltimoPais lugarUltimoPaisA = new LugarUltimoPais("LugarUltimoPais", true);
        Element lugarUltimoPaisSerializado = lugarUltimoPaisA.serializar(doc);
        Assert.assertNotNull(lugarUltimoPaisSerializado);

        // hasta aqui hemos serializado LugarUltimoPais
        // ahora tenemos que bajarlo a disco
        doc.appendChild(lugarUltimoPaisSerializado);
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

        LugarUltimoPais lugarUltimoPaisCargado = LugarUltimoPais.hidratar(doc.getFirstChild());
        Assert.assertNotNull(lugarUltimoPaisCargado);
        Assert.assertEquals(lugarUltimoPaisA.getNombre(), lugarUltimoPaisCargado.getNombre());
    }

    private List<Lugar> generarLugares() {
        List<Lugar> lugares = new LinkedList<Lugar>();
        LugarUltimoPais banco = new LugarUltimoPais("Banco Frances", true);
        LugarUltimoPais biblioteca = new LugarUltimoPais("Biblioteca", false);
        LugarUltimoPais puerto = new LugarUltimoPais("Puerto", false);

        lugares.add(banco);
        lugares.add(biblioteca);
        lugares.add(puerto);
        return lugares;
    }
}
