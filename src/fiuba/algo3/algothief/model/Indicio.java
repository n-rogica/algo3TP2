/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fiuba.algo3.algothief.model;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;

/**
 *
 * @author Ramiro
 */
public class Indicio {

    private final String indicioFacil;
    private final String indicioNormal;
    private final String indicioDificil;

    public Indicio(String indicioFacil, String indicioNormal, String indicioDificil) {
        this.indicioDificil = indicioDificil;
        this.indicioNormal = indicioNormal;
        this.indicioFacil = indicioFacil;
    }

    public String darIndicio(Juego.Dificultad dificultad) {
        switch (dificultad) {
            case FACIL:
                return indicioFacil;
            case NORMAL:
                return indicioNormal;
            case DIFICIL:
                return indicioDificil;
            default:
                return null;
        }
    }

    public static Indicio hidratar(Node nodoIndicio) {
        Element elementoIndicio = (Element) nodoIndicio;
        String indicioFacilHidratado = elementoIndicio.getAttribute("IndicioFacil");
        String indicioNormalHidratado = elementoIndicio.getAttribute("IndicioNormal");
        String indicioDificilHidratado = elementoIndicio.getAttribute("IndicioDificil");

        Indicio indicio = new Indicio(indicioFacilHidratado, indicioNormalHidratado, indicioDificilHidratado);
        return indicio;
    }

    public Element serializar(Document doc) {
        Element elementoIndicio = doc.createElement("Indicio");
        elementoIndicio.setAttribute("IndicioFacil", indicioFacil);
        elementoIndicio.setAttribute("IndicioNormal", indicioNormal);
        elementoIndicio.setAttribute("IndicioDificil", indicioDificil);

        return elementoIndicio;
    }
}
