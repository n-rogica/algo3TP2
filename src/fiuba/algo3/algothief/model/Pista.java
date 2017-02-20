/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fiuba.algo3.algothief.model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;

/**
 *
 * @author Ramiro
 */
public class Pista {

    private final Pais paisDestino;
    protected final List<Lugar> lugares;

    public Pista(List<Lugar> lugares, Pais paisDestino) {
        this.lugares = lugares;
        this.paisDestino = paisDestino;
    }

    public static Pista pistaUltimoPais() {
        boolean estaLadronEnBanco = false;
        boolean estaLadronEnAeropuerto = false;
        boolean estaLadronEnBiblioteca = false;

        Random numeroAleatorio = new Random();

        // Genero un numero aleatorio entre 1 y 3 para elegir en que lugar estara el ladron
        switch (numeroAleatorio.nextInt(3) + 1) {
            case 1:
                estaLadronEnBanco = true;
                break;
            case 2:
                estaLadronEnAeropuerto = true;
                break;
            case 3:
                estaLadronEnBiblioteca = true;
                break;
        }

        LugarUltimoPais banco = new LugarUltimoPais("Banco", estaLadronEnBanco);
        LugarUltimoPais aeropuerto = new LugarUltimoPais("Aeropuerto", estaLadronEnAeropuerto);
        LugarUltimoPais biblioteca = new LugarUltimoPais("Biblioteca", estaLadronEnBiblioteca);

        List<Lugar> lugares = new ArrayList<Lugar>();
        lugares.add(banco);
        lugares.add(aeropuerto);
        lugares.add(biblioteca);
        Pista pistaSerial = new Pista(lugares, new Pais(new Coordenadas(new Latitud("N", 0, 0, 0), new Longitud("W", 0, 0, 0)), "Fin Del Juego"));
        return pistaSerial;
    }

    public List<Lugar> getLugares() {
        return Collections.unmodifiableList(lugares);
    }

    public Pais getPaisDestino() {
        return paisDestino;
    }

    public Element serializar(Document doc) {
        Element elementoPista = doc.createElement("Pista");
        elementoPista.appendChild(paisDestino.serializar(doc));
        for (Lugar lugar : lugares) {
            elementoPista.appendChild(lugar.serializar(doc));
        }
        return elementoPista;
    }

    public static Pista hidratar(Node nodoPista) {
        Element elementoPista = (Element) nodoPista;
        List<Lugar> lugares = new ArrayList<Lugar>();
        Pais paisDestino = Pais.hidratar(elementoPista.getChildNodes().item(0));
        for (int i = 1; i < elementoPista.getChildNodes().getLength(); i++) {
            Element child = (Element) elementoPista.getChildNodes().item(i);
            lugares.add(Lugar.hidratar(child));
        }
        Pista pista = new Pista(lugares, paisDestino);
        return pista;
    }
}
