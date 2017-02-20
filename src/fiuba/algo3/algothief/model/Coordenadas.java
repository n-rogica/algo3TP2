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
public class Coordenadas {

    private Latitud latitud;
    private Longitud longitud;

    public Coordenadas(Latitud latitud, Longitud longitud) {
        this.latitud = latitud;
        this.longitud = longitud;
    }
  
    public double getLatitud() {
        return latitud.expresarEnKilometros();
    }

    public double getLongitud() {
        return longitud.expresarEnKilometros();
    }

    public static double distancia(Coordenadas origen, Coordenadas destino) {
        return Math.hypot((destino.getLongitud() - origen.getLongitud()), (destino.getLatitud() - origen.getLatitud()));
    }

    public static Coordenadas hidratar(Node nodoCoordenadas) {
        Element elementoCoordenadas = (Element) nodoCoordenadas;
        Latitud latitudCoordenadas = Latitud.hidratar(elementoCoordenadas.getChildNodes().item(0));
        Longitud longitudCoordenadas = Longitud.hidratar(elementoCoordenadas.getChildNodes().item(1));
        Coordenadas coordenadas = new Coordenadas(latitudCoordenadas, longitudCoordenadas);
        return coordenadas;
    }

    public Element serializar(Document doc) {
        Element elementoCoordenadas = doc.createElement("Coordenadas");
        elementoCoordenadas.appendChild(latitud.serializar(doc));
        elementoCoordenadas.appendChild(longitud.serializar(doc));
        return elementoCoordenadas;
    }
}
