/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fiuba.algo3.algothief.model;

import fiuba.algo3.algothief.model.Caracteristica.Tipo;
import fiuba.algo3.algothief.model.Juego.Dificultad;

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
public class CasoTest {

    public CasoTest() {
    }

    @Test
    public void testCrearCasoConArgunementoFacilTieneDificultadFacil() {
        Ladron mockLadron = mock(Ladron.class);
        Caso caso = new Caso(Dificultad.FACIL, mock(Pais.class), mock(ObjetoRobado.class), mockLadron, null);
        Assert.assertEquals(caso.getDificultad(), Dificultad.FACIL);
    }

    @Test
    public void testCasoPendienteNoEstaFallidoNiResuelto() {
        Ladron mockLadron = mock(Ladron.class);
        Caso caso = new Caso(Dificultad.FACIL, mock(Pais.class), mock(ObjetoRobado.class), mockLadron, null);
        Assert.assertFalse(caso.isFallido());
        Assert.assertFalse(caso.isResuelto());
        Assert.assertTrue(caso.isPendiente());
    }

    @Test
    public void testCasoFallidoNoEstaPendienteNiResuelto() {
        Ladron mockLadron = mock(Ladron.class);
        Caso caso = new Caso(Dificultad.FACIL, mock(Pais.class), mock(ObjetoRobado.class), mockLadron, null);
        caso.descontarHoras(155);

        Assert.assertTrue(caso.isFallido());
        Assert.assertFalse(caso.isResuelto());
        Assert.assertFalse(caso.isPendiente());
    }

    @Test
    public void testCasoResueltoNoEstaPendienteNiFallido() {
                
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
        
        caso.setResuelto(true);
        
        Assert.assertFalse(caso.isFallido());
        Assert.assertTrue(caso.isResuelto());
        Assert.assertFalse(caso.isPendiente());
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
    
    @Test
    public void testCasoNuevoDebeEstarPendiente() {
        Ladron mockLadron = mock(Ladron.class);
        Caso caso = new Caso(Dificultad.DIFICIL, mock(Pais.class), mock(ObjetoRobado.class), mockLadron, null);
        Assert.assertTrue(caso.isPendiente());
    }

    @Test
    public void testCasoConTiempoAgotadoDebeEstarFallido() {
        Caso caso = new Caso(Dificultad.DIFICIL, mock(Pais.class), mock(ObjetoRobado.class), null, null);

        caso.descontarHoras(155);

        Assert.assertTrue(caso.isFallido());
    }

    @Test
    public void testCrearCasoConObjetoRobado() {

        Ladron mockLadron = mock(Ladron.class);
        ObjetoRobado objetoRobado = new ObjetoRobado("Joyas de la corona", Dificultad.FACIL);

        Caso caso = new Caso(Dificultad.FACIL, mock(Pais.class), objetoRobado, mockLadron, null);
        Assert.assertEquals(objetoRobado, caso.getObjetoRobado());
    }

    @Test
    public void testSiCasoFacilSeObtieneIndicioFacil() {
        Ladron mockLadron = mock(Ladron.class);
        Caso caso = new Caso(Dificultad.FACIL, mock(Pais.class), mock(ObjetoRobado.class), mockLadron, null);
        Indicio indicio = new Indicio("Este es el indicio Facil",
                "Este es el indicio Normal",
                "Este es el indicio Dificil");
        Lugar lugar = new Lugar("Banco", indicio);

        Assert.assertTrue(lugar.entrarLugar(caso).contains("Este es el indicio Facil"));
    }

    @Test
    public void testSiCasoNormalSeObtieneIndicioNormal() {
        Ladron mockLadron = mock(Ladron.class);
        Caso caso = new Caso(Dificultad.NORMAL, mock(Pais.class), mock(ObjetoRobado.class), mockLadron, null);
        Indicio indicio = new Indicio("Este es el indicio Facil",
                "Este es el indicio Normal",
                "Este es el indicio Dificil");
        Lugar lugar = new Lugar("Banco", indicio);

        Assert.assertTrue(lugar.entrarLugar(caso).contains("Este es el indicio Normal"));
    }

    @Test
    public void testSiCasoDificilSeObtieneIndicioDificil() {
        Ladron mockLadron = mock(Ladron.class);
        Caso caso = new Caso(Dificultad.DIFICIL, mock(Pais.class), mock(ObjetoRobado.class), mockLadron, null);
        Indicio indicio = new Indicio("Este es el indicio Facil",
                "Este es el indicio Normal",
                "Este es el indicio Dificil");
        Lugar lugar = new Lugar("Banco", indicio);
        
        Assert.assertTrue(lugar.entrarLugar(caso).contains("Este es el indicio Dificil"));
    }

    @Test
    public void testCasoDevuelvePistaCorrectaSiJugadorEnPaisCorrespondiente() {
        Ladron mockLadron = mock(Ladron.class);
        Pais mockPais = mock(Pais.class);
        Pista mockPista = mock(Pista.class);
        Set<Pista> pistas = new HashSet<Pista>();
        pistas.add(mockPista);

        Caso caso = new Caso(Dificultad.DIFICIL, mockPais, mock(ObjetoRobado.class), mockLadron, null);
        caso.setPistas(pistas);

        Assert.assertSame(caso.getPista(), mockPista);
    }

    @Test
    public void testCasoDevuelvePistaFalsaSiJugadorEnPaisErrado() {
        Ladron mockLadron = mock(Ladron.class);
        Pais mockPais = mock(Pais.class);
        Pais mockOtroPais = mock(Pais.class);

        Pista mockPista = mock(Pista.class);
        Set<Pista> pistas = new HashSet<Pista>();
        pistas.add(mockPista);

        Caso caso = new Caso(Dificultad.DIFICIL, mockPais, mock(ObjetoRobado.class), mockLadron, null);
        caso.setPistas(pistas);

        caso.llevarCasoEn(mockOtroPais, 100);

        Lugar lugarEnPaisIncorrecto = caso.getPista().getLugares().get(0);

        Assert.assertNotSame("Definitivamente esa persona no paso por aqui", lugarEnPaisIncorrecto.entrarLugar(caso));
    }
    
    @Test
    public void testCasoDevuelveUnCampoDeLaOrdenDeArresto (){
    	Caso caso = new Caso (Dificultad.DIFICIL,mock(Pais.class), mock(ObjetoRobado.class), mock (Ladron.class), null);
    	Caracteristica caracteristica = new Caracteristica (Tipo.VEHICULO,"Moto",null);    	
    	caso.agregarCaracteristaALaOrden(caracteristica);
    	
    	Assert.assertEquals("Moto",caso.obtenerCampoOrdenDeArresto(Tipo.VEHICULO));    
    }
    
    @Test
    public void testCasoDevuelveElSexoDelLadron(){
    	Ladron mockLadron = mock(Ladron.class);
    	when(mockLadron.obtenerDescripcion(Tipo.SEXO)).thenReturn("Era una mujer");
    	Caso caso = new Caso (Dificultad.DIFICIL,mock(Pais.class), mock(ObjetoRobado.class), mockLadron, null);
    	
    	Assert.assertEquals("Era una mujer", caso.getSexoDelLadron());
    	
    }
    

    @Test
    public void testCasoDevuelveIndicioDelLadron() {
        Pais mockPais = mock(Pais.class);
        Pista mockPista = mock(Pista.class);
        Set<Pista> pistas = new HashSet<Pista>();
        pistas.add(mockPista);

        Ladron ladron = new Ladron("pp");

        Caso caso = new Caso(Dificultad.DIFICIL, mockPais, mock(ObjetoRobado.class), ladron, null);
        caso.setPistas(pistas);

        Assert.assertNotNull(caso.darIndicioLadron());
    }

    @Test
    public void testCasoDescuentaOchoHorasCuandoSonLasDoce() {
        Ladron mockLadron = mock(Ladron.class);
        Caso caso = new Caso(null, null, null, mockLadron, null);
        //el caso se crea con 154 horas que representan las 7am del lunes

        caso.descontarHoras(17);
        //le descuento 17 horas para que sean las 24, y me devuelve las 8 am.

        Assert.assertEquals(129, caso.getTiempoResolucion());

        caso.descontarHoras(16);
        //le descuento 16 horas para que sean las 24 y me devuelve las 8 am del miercoles

        Assert.assertEquals(105, caso.getTiempoResolucion());
    }

    @Test
    public void testSeVaADormirOchoHorasTodasLasNoches() {
        Ladron mockLadron = mock(Ladron.class);
        Caso caso = new Caso(null, null, null, mockLadron, null);

        caso.descontarHoras(16);
        //son las 23hs del lunes
        Assert.assertEquals(138, caso.getTiempoResolucion());

        caso.descontarHoras(3);
        // a las 23hs le descuento 3hs y como pasa por las 24hs se va a dormir en el medio
        Assert.assertEquals(127, caso.getTiempoResolucion());
    }

    @Test
    public void testCasoSeCreaConConjuntoDeSospechosos() {
        Pais mockPais = mock(Pais.class);
        ObjetoRobado mockObjetoRobado = mock(ObjetoRobado.class);
        Ladron mockLadron = mock(Ladron.class);
        Ladron mockSospechoso1 = mock(Ladron.class);
        Ladron mockSospechoso2 = mock(Ladron.class);
        List<Ladron> sospechosos = new LinkedList<Ladron>();

        sospechosos.add(mockSospechoso1);
        sospechosos.add(mockSospechoso2);

        Caso caso = new Caso(Dificultad.FACIL, mockPais, mockObjetoRobado, mockLadron, sospechosos);

        Assert.assertNotSame(0, caso.obtenerCantidadDeSospechosos());
    }

    @Test
    public void testSiLasCaracteristicasIngresadasNoSonSuficientesParaIdentificarAUnUnicoSospechosoNoSeEmiteOrdenDeArresto() {

        Pais mockPais = mock(Pais.class);
        ObjetoRobado mockObjetoRobado = mock(ObjetoRobado.class);
        Ladron mockLadron = mock(Ladron.class);
        Ladron mockSospechoso1 = mock(Ladron.class);
        Ladron mockSospechoso2 = mock(Ladron.class);
        when(mockSospechoso1.obtenerCaracteristica(Tipo.HOBBY)).thenReturn("Tenis");
        when(mockSospechoso2.obtenerCaracteristica(Tipo.HOBBY)).thenReturn("Tenis");
        List<Ladron> sospechosos = new LinkedList<Ladron>();
        sospechosos.add(mockSospechoso1);
        sospechosos.add(mockSospechoso2);

        Caracteristica caracteristicaIngresada = new Caracteristica(Tipo.HOBBY, "Tenis", "Queria asistir a un torneo de tenis");
        

        Caso caso = new Caso(Dificultad.DIFICIL, mockPais, mockObjetoRobado, mockLadron, sospechosos);
        Assert.assertFalse(caso.ordenDeArrestoEmitida());

        caso.agregarCaracteristaALaOrden(caracteristicaIngresada);
        Assert.assertTrue(caso.buscarSospechosos().size() == 2);
        Assert.assertFalse(caso.ordenDeArrestoEmitida());
    }

    @Test
    public void testSiLaCaracteristicaIngresadaAlcanzaParaIdentificarAUnUnicoSospechosoSeEmiteOrdenDeArrestoYSeDescuentan3Horas() {

        Pais mockPais = mock(Pais.class);
        ObjetoRobado mockObjetoRobado = mock(ObjetoRobado.class);
        Ladron mockLadron = mock(Ladron.class);
        Ladron mockSospechoso1 = mock(Ladron.class);
        Ladron mockSospechoso2 = mock(Ladron.class);
        when(mockSospechoso1.obtenerCaracteristica(Tipo.VEHICULO)).thenReturn("Convertible");
        when(mockSospechoso2.obtenerCaracteristica(Tipo.VEHICULO)).thenReturn("Moto");
        when(mockSospechoso2.getNombre()).thenReturn("un nombre");

        List<Ladron> sospechosos = new LinkedList<Ladron>();
        sospechosos.add(mockSospechoso1);
        sospechosos.add(mockSospechoso2);

        Caracteristica caracteristicaIngresada = new Caracteristica(Tipo.VEHICULO, "Moto", "Viajaba en moto");        

        Caso caso = new Caso(Dificultad.DIFICIL, mockPais, mockObjetoRobado, mockLadron, sospechosos);
        Assert.assertEquals(154, caso.getTiempoResolucion());
        Assert.assertFalse(caso.ordenDeArrestoEmitida());

        caso.agregarCaracteristaALaOrden(caracteristicaIngresada);
        Assert.assertTrue(caso.buscarSospechosos().size() == 1);
        Assert.assertTrue(caso.ordenDeArrestoEmitida());
        Assert.assertEquals(151, caso.getTiempoResolucion());
    }
    
    @Test
    public void testOrdenDeArrestoDejaDeEstarEmitidaSiAgregoCaracteristicasQueIdentifiquenAOtroSospechoso(){
        Pais mockPais = mock(Pais.class);
        ObjetoRobado mockObjetoRobado = mock(ObjetoRobado.class);
        Ladron mockLadron = mock(Ladron.class);
        Ladron mockSospechoso1 = mock(Ladron.class);
        Ladron mockSospechoso2 = mock(Ladron.class);
        when(mockSospechoso1.obtenerCaracteristica(Tipo.VEHICULO)).thenReturn("Convertible");
        when(mockSospechoso2.obtenerCaracteristica(Tipo.VEHICULO)).thenReturn("Moto");
        when(mockSospechoso1.getNombre()).thenReturn("otro nombre");
        when(mockSospechoso2.getNombre()).thenReturn("un nombre");

        List<Ladron> sospechosos = new LinkedList<Ladron>();
        sospechosos.add(mockSospechoso1);
        sospechosos.add(mockSospechoso2);

        Caracteristica caracteristicaIngresada = new Caracteristica(Tipo.VEHICULO, "Moto", "Viajaba en moto");        

        Caso caso = new Caso(Dificultad.DIFICIL, mockPais, mockObjetoRobado, mockLadron, sospechosos);
        Assert.assertEquals(154, caso.getTiempoResolucion());
        Assert.assertFalse(caso.ordenDeArrestoEmitida());

        caso.agregarCaracteristaALaOrden(caracteristicaIngresada);
        Assert.assertTrue(caso.buscarSospechosos().size() == 1);
        Assert.assertTrue(caso.ordenDeArrestoEmitida());
        Assert.assertEquals(151, caso.getTiempoResolucion());
        
        //ahora cambio la caracteristica del vehiculo de moto a deportivo para que no coincida con ninguno
        Caracteristica otraCaracteristicaIngresada = new Caracteristica(Tipo.VEHICULO, "Deportivo", "Viajaba en un auto deportivo");
        caso.agregarCaracteristaALaOrden(otraCaracteristicaIngresada);
        
        Assert.assertTrue(caso.buscarSospechosos().size() == 0);
        Assert.assertFalse(caso.ordenDeArrestoEmitida());
        Assert.assertEquals(151, caso.getTiempoResolucion());    	
    }
    
    @Test
    public void testOrdenDeArrestoEmitidaSeVuelveAEmitirContraOtroSospechosoSiSeIngresanDiferentesCaracteristicas(){
        Pais mockPais = mock(Pais.class);
        ObjetoRobado mockObjetoRobado = mock(ObjetoRobado.class);
        Ladron mockLadron = mock(Ladron.class);
        Ladron mockSospechoso1 = mock(Ladron.class);
        Ladron mockSospechoso2 = mock(Ladron.class);
        when(mockSospechoso1.obtenerCaracteristica(Tipo.VEHICULO)).thenReturn("Convertible");
        when(mockSospechoso2.obtenerCaracteristica(Tipo.VEHICULO)).thenReturn("Moto");
        when(mockSospechoso1.getNombre()).thenReturn("otro nombre");
        when(mockSospechoso2.getNombre()).thenReturn("un nombre");

        List<Ladron> sospechosos = new LinkedList<Ladron>();
        sospechosos.add(mockSospechoso1);
        sospechosos.add(mockSospechoso2);

        Caracteristica caracteristicaIngresada = new Caracteristica(Tipo.VEHICULO, "Moto", "Viajaba en moto");        

        Caso caso = new Caso(Dificultad.DIFICIL, mockPais, mockObjetoRobado, mockLadron, sospechosos);
        Assert.assertEquals(154, caso.getTiempoResolucion());
        Assert.assertFalse(caso.ordenDeArrestoEmitida());

        caso.agregarCaracteristaALaOrden(caracteristicaIngresada);
        Assert.assertTrue(caso.buscarSospechosos().size() == 1);
        Assert.assertTrue(caso.ordenDeArrestoEmitida());
        Assert.assertEquals(151, caso.getTiempoResolucion());
        
        //ahora cambio la caracteristica del vehiculo de moto a convertible para que coincida con el otro sospechoso
        Caracteristica otraCaracteristicaIngresada = new Caracteristica(Tipo.VEHICULO, "Convertible", "Viajaba en un convertible");
        caso.agregarCaracteristaALaOrden(otraCaracteristicaIngresada);
        
        Assert.assertTrue(caso.buscarSospechosos().size() == 1);
        Assert.assertTrue(caso.ordenDeArrestoEmitida());
        //verifico que se descontaron las horas correspondientes a la segunda emision
        Assert.assertEquals(148, caso.getTiempoResolucion());    	
    }
    

    @Test
    public void testLlevarCasoAlMismoPaisNoRestaHoras() {
        Ladron mockLadron = mock(Ladron.class);
        Pais argentina = new Pais(new Coordenadas(new Latitud("S", 34, 35, 59), new Longitud("W", 58, 22, 54)), "Argentina");
        Caso caso = new Caso(Dificultad.DIFICIL, argentina, mock(ObjetoRobado.class), mockLadron, null);

        caso.llevarCasoEn(argentina, 100);
        Assert.assertEquals(154, caso.getTiempoResolucion());
    }

    private String nombreArchivo;

    @Before
    public void before() {
        this.nombreArchivo = "Jugadores.xml";
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

        Caso caso = armarCasoCompleto();
        Element elementoCaso = caso.serializar(doc);
        junit.framework.Assert.assertNotNull(elementoCaso);

        // Hasta crea el doc y aca serializa el indicio
        doc.appendChild(elementoCaso);
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

        Caso casoHidratado = Caso.hidratar(doc.getElementsByTagName("Caso").item(0));
        Assert.assertNotNull(casoHidratado);
        Assert.assertEquals(154, casoHidratado.getTiempoResolucion());
        Assert.assertNotNull(casoHidratado.getObjetoRobado());
        Assert.assertNotNull(casoHidratado.getPista());
        Assert.assertNotNull(casoHidratado.getDificultad());
    }

    private Caso armarCasoCompleto() throws ParserConfigurationException, TransformerException, SAXException, IOException {
        Juego juego = new Juego();
        Jugador jugador = juego.crearJugador("Timy Timon");
        juego.asignarCasoNuevo(jugador);

        return jugador.getCaso();
    }
}
