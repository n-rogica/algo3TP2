/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fiuba.algo3.algothief.model;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Random;
import java.util.Set;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerConfigurationException;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

/**
 *
 * @author Ramiro
 */
public class Juego {

    private final String NOMBRE_ARCHIVO_JUGADORES = "Jugadores.xml";
    private final String NOMBRE_ARCHIVO_PISTAS = "Pistas.xml";
    private final String NOMBRE_ARCHIVO_OBJROB = "ObjetosRobados.xml";
    private final String NOMBRE_ARCHIVO_LADRON = "Ladrones.xml";

    private File archivoJugadores;
    private File archivoPistas;
    private File archivoObjetosRobados;
    private File archivoLadrones;

    public Juego() throws ParserConfigurationException, TransformerException, FileNotFoundException {
        initArchivos();
    }

    public enum Dificultad {

        FACIL, NORMAL, DIFICIL
    }

    private void initArchivos() throws ParserConfigurationException, TransformerConfigurationException, TransformerException, FileNotFoundException {
        archivoJugadores = new File(NOMBRE_ARCHIVO_JUGADORES);
        archivoPistas = new File(NOMBRE_ARCHIVO_PISTAS);
        archivoObjetosRobados = new File(NOMBRE_ARCHIVO_OBJROB);
        archivoLadrones = new File(NOMBRE_ARCHIVO_LADRON);

        if (!archivoPistas.exists()) {
            throw new FileNotFoundException();        	
        }
        if (!archivoJugadores.exists()) {
            DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
            DocumentBuilder db = dbf.newDocumentBuilder();
            Document doc = db.newDocument();

            Element elementoJugadores = doc.createElement("Jugadores");;

            doc.appendChild(elementoJugadores);
            TransformerFactory transformerFactory = TransformerFactory.newInstance();
            Transformer transformer = transformerFactory.newTransformer();
            DOMSource source = new DOMSource(doc);
            StreamResult result = new StreamResult(archivoJugadores);
            transformer.transform(source, result);
        }
    }

    public void asignarCasoNuevo(Jugador jugador) throws ParserConfigurationException, SAXException, IOException {
        jugador.asignarCaso(generarCaso(jugador.getGrado()));
    }

    private Caso generarCaso(Grado gradoJugador) throws ParserConfigurationException, SAXException, IOException {
        Iterator<Pista> pistasCargadas = cargarPistas();
        Set<Pista> pistasCaso = new HashSet<Pista>();
        int cantidadPistas = 0;
        switch (gradoJugador.dificultadGrado()) {
            case FACIL:
                cantidadPistas = 4;
                break;
            case NORMAL:
                cantidadPistas = 5;
                break;
            case DIFICIL:
                cantidadPistas = 7;
                break;
        }

        for (int i = 0; i < cantidadPistas; i++) {
            pistasCaso.add(pistasCargadas.next());
        }
        List<Ladron> listaSospechosos = obtenerListaDeSospechosos();
        Ladron ladron = obtenerLadronDelCaso(listaSospechosos);

        Caso nuevoCaso = new Caso(gradoJugador.dificultadGrado(), pistasCargadas.next().getPaisDestino(), obtenerObjetoRobado(gradoJugador.dificultadGrado()), ladron, listaSospechosos);
        nuevoCaso.setPistas(pistasCaso);
        return nuevoCaso;
    }

    public Jugador crearJugador(String nombreJugador) throws ParserConfigurationException, SAXException, IOException {
        Jugador jugadorCargado = cargarJugador(nombreJugador);
        if (jugadorCargado != null) {
            return jugadorCargado;
        }
        return crearNuevoJugador(nombreJugador);
    }

    private Jugador crearNuevoJugador(String nombreJugador) {
        return new Jugador(nombreJugador);
    }

    private Jugador cargarJugador(String nombreJugador) throws ParserConfigurationException, SAXException, IOException {
        NodeList jugadores = cargarJugadores();
        for (int i = 0; i < jugadores.getLength(); i++) {
            Jugador jugadorCargado = Jugador.hidratar(jugadores.item(i));
            if (jugadorCargado.getNombre().equals(nombreJugador)) {
                return jugadorCargado;
            }
        }
        return null;
    }

    private NodeList cargarJugadores() throws ParserConfigurationException, SAXException, IOException {
        DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
        DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
        Document doc = dBuilder.parse(archivoJugadores);
        doc.getDocumentElement().normalize();

        NodeList jugadores = doc.getElementsByTagName("Jugadores");

        return jugadores.item(0).getChildNodes();
    }

    private Iterator<Pista> cargarPistas() throws ParserConfigurationException, SAXException, IOException {
        DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
        DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
        Document doc = dBuilder.parse(archivoPistas);
        doc.getDocumentElement().normalize();

        NodeList nodosPista = doc.getElementsByTagName("Pista");

        Set<Pista> pistas = new HashSet<Pista>();

        for (int i = 0; i < nodosPista.getLength(); i++) {
            Pista pistaHidratada = Pista.hidratar(nodosPista.item(i));
            pistas.add(pistaHidratada);
        }
        return pistas.iterator();
    }
    
    public List<Pais> obtenerListaDePaises() throws ParserConfigurationException, SAXException, IOException {
		List<Pais> paises = new ArrayList<Pais>();
		Iterator<Pista> pistas = cargarPistas();
		while (pistas.hasNext()) {
			paises.add(pistas.next().getPaisDestino());
		}
    	return paises;
    }

    private ObjetoRobado obtenerObjetoRobado(Dificultad dificultad) throws ParserConfigurationException, SAXException, IOException {
        Iterator<ObjetoRobado> objetosRobadosCargados = cargarObjetosRobados();
        ObjetoRobado objetoRobado = objetosRobadosCargados.next();
        while (objetoRobado.getDificultadObjeto() == dificultad) {
            objetoRobado = objetosRobadosCargados.next();
        }
        return objetoRobado;
    }

    private Iterator<ObjetoRobado> cargarObjetosRobados() throws ParserConfigurationException, SAXException, IOException {
        DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
        DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
        Document doc = dBuilder.parse(archivoObjetosRobados);
        doc.getDocumentElement().normalize();

        NodeList nodosObjetoRobado = doc.getElementsByTagName("ObjetoRobado");

        Set<ObjetoRobado> objetos = new HashSet<ObjetoRobado>();

        for (int i = 0; i < nodosObjetoRobado.getLength(); i++) {
            ObjetoRobado objRobHidratado = ObjetoRobado.hidratar(nodosObjetoRobado.item(i));
            objetos.add(objRobHidratado);
        }
        return objetos.iterator();
    }

    private List<Ladron> obtenerListaDeSospechosos() throws ParserConfigurationException, SAXException, IOException {

        DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
        DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
        Document doc = dBuilder.parse(archivoLadrones);
        doc.getDocumentElement().normalize();

        List<Ladron> listaDeSospechosos = new LinkedList<Ladron>();
        NodeList ladrones = doc.getElementsByTagName("Ladron");

        for (int i = 0; i < ladrones.getLength(); i++) {
            Ladron ladronHidratado = Ladron.hidratar(ladrones.item(i));
            listaDeSospechosos.add(ladronHidratado);
        }

        return listaDeSospechosos;
    }

    private Ladron obtenerLadronDelCaso(List<Ladron> listaSospechosos) {

        int num;
        Random random = new Random();

        num = random.nextInt(listaSospechosos.size());
        return listaSospechosos.get(num);

    }

    public void guardarJuego(Jugador jugador) throws ParserConfigurationException, SAXException, IOException, TransformerConfigurationException, TransformerException {
        DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
        DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
        Document doc = dBuilder.parse(archivoJugadores);
        doc.getDocumentElement().normalize();

        Element jugadoresElement = (Element) doc.getElementsByTagName("Jugadores").item(0);
        NodeList jugadores = jugadoresElement.getChildNodes();
        
        for (int i = 0; i < jugadores.getLength(); i++) {
            Jugador jugadorCargado = Jugador.hidratar(jugadores.item(i));
            if (jugadorCargado.getNombre().equals(jugador.getNombre())) {
                jugadoresElement.removeChild(jugadores.item(i));
                break;
            }
        }

        jugadoresElement.appendChild(jugador.serializar(doc));

        TransformerFactory transformerFactory = TransformerFactory.newInstance();
        Transformer transformer = transformerFactory.newTransformer();
        DOMSource source = new DOMSource(doc);
        StreamResult result = new StreamResult(archivoJugadores);
        transformer.transform(source, result);
    }
}
