 /*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fiuba.algo3.algothief.model;

import java.util.List;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;

/**
 *
 * @author Ramiro
 */
public class Jugador {

    private final String nombre;
    private Grado grado;
    private int casosResueltos;
    private Caso caso;
    private final int CasosDeNovato = 5;
    private final int CasosDeDetective = 10;
    private final int CasosDeInvestigador = 20;

    public Jugador(String nombre, int casosResueltos) {
        this.casosResueltos = casosResueltos;
        this.nombre = nombre;
        asignarGrado();
    }

    public Jugador(String nombre) {
        this(nombre, 0);
    }

    /*
     * Se asigna Grado al jugador segun la cantidad de casos resueltos que tenga
     * Menos de 5: Novato.
     * Entre 5 y 10: Detective.
     * Entre 10 y 20: Investigador.
     * Mas de 20: Sargento.
     */
    private void asignarGrado() {
        if (getCasosResueltos() < CasosDeNovato) {
            grado = new Novato();
        } else if (getCasosResueltos() < CasosDeDetective) {
            grado = new Detective();
        } else if (getCasosResueltos() < CasosDeInvestigador) {
            grado = new Investigador();
        } else {
            grado = new Sargento();
        }
    }

    /* 
     * Pre: El jugador debe tener un caso asignado
     * Post: El viaje resta tiempo al caso segun la distancia del mismo
     */
    public void realizarViaje(Pais destino) {

        // Actualizo la ubicacion del jugador
        caso.llevarCasoEn(destino, grado.velocidadViaje());
    }

    String getNombre() {
        return this.nombre;
    }

    int getCasosResueltos() {
        return this.casosResueltos;
    }

    public boolean casoResuelto() {
        if (!caso.isResuelto()) {
            return false;
        }

        this.casosResueltos++;

        this.caso = null;

        asignarGrado();

        return true;
    }

    public boolean casoPerdido() {
        if (!caso.isFallido()) {
            return false;
        }

        caso = null;
        return true;
    }

    public List<Lugar> lugaresParaEntrar() {
        return caso.getPista().getLugares();
    }

    public Caso getCaso() {
        return caso;
    }

    public Grado getGrado() {
        return grado;
    }

    /*
     * Asigna caso al jugador.
     * Si el jugador tiene un caso en cualquier estado no se le asignara el nuevo.
     */
    public void asignarCaso(Caso caso) {
        if (this.caso == null) {
            this.caso = caso;
        }
    }

    public static Jugador hidratar(Node nodoJugador) {
        Element elementoJugador = (Element) nodoJugador;
        Element elementoCaso = (Element) elementoJugador.getElementsByTagName("Caso").item(0);
        String nombreJugador = elementoJugador.getAttribute("Nombre");
        String casosResueltosJugador = elementoJugador.getAttribute("CasosResueltos");
        Jugador jugadorHidratado = new Jugador(nombreJugador, Integer.parseInt(casosResueltosJugador));
        if (elementoCaso != null) {
            Caso casoHidratado = Caso.hidratar(elementoCaso);
            jugadorHidratado.asignarCaso(casoHidratado);
        }
        return jugadorHidratado;
    }

    public Element serializar(Document doc) {
        Element elementoJugador = doc.createElement("Jugador");
        elementoJugador.setAttribute("Nombre", nombre);
        elementoJugador.setAttribute("CasosResueltos", String.valueOf(casosResueltos));
        if (caso != null) {
            elementoJugador.appendChild(caso.serializar(doc));
        }
        return elementoJugador;
    }
}
