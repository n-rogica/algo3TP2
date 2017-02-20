package fiuba.algo3.algothief.model;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;

public class Longitud extends CoordenadaGeografica {

    private String sentido;

    public Longitud(String sentido, double grados, double minutos, double segundos) {

        super(grados, minutos, segundos);
        this.sentido = sentido;

    }

    public double expresarEnKilometros() {

        double grados = this.exPresarEnGrados();

        if (sentido == "O") {
            grados = grados * (-1);
        }
        return this.convertirAKilometros(grados);
    }

    public Element serializar(Document doc) {
        Element elementoLongitud = doc.createElement("Longitud");
        elementoLongitud.setAttribute("Sentido", sentido);
        elementoLongitud.setAttribute("Grados", String.valueOf(getGrados()));
        elementoLongitud.setAttribute("Minutos", String.valueOf(getMinutos()));
        elementoLongitud.setAttribute("Segundos", String.valueOf(getSegundos()));
        return elementoLongitud;
    }

    public static Longitud hidratar(Node nodoLongitud) {
        Element elementoLongitud = (Element) nodoLongitud;
        String sentidoHidratado = elementoLongitud.getAttribute("Sentido");
        double gradosHidratados = Double.parseDouble(elementoLongitud.getAttribute("Grados"));
        double minutosHidratados = Double.parseDouble(elementoLongitud.getAttribute("Grados"));
        double segundosHidratados = Double.parseDouble(elementoLongitud.getAttribute("Grados"));
        Longitud longitud = new Longitud(sentidoHidratado, gradosHidratados, minutosHidratados, segundosHidratados);
        return longitud;
    }
}
