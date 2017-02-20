/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fiuba.algo3.algothief.model;

import static fiuba.algo3.algothief.model.Coordenadas.distancia;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;

/**
 *
 * @author Ramiro
 */
public class Pais {

    private final Coordenadas coordenadas;
    private final String nombre;

    public Pais(Coordenadas coordenadas, String nombre) {
        this.coordenadas = coordenadas;
        this.nombre = nombre;
    }

    public int distanciaPaises(Pais destino) {
        // Redondeo para arriba
        Long distancia = Math.round(distancia(getCoordenadas(), destino.getCoordenadas()));
        return distancia.intValue();
    }

    public Coordenadas getCoordenadas() {
        return coordenadas;
    }

    public String getNombre() {
        return nombre;
    }

    @Override
    public int hashCode() {
        int hash = 3;
        hash = 37 * hash + (this.nombre != null ? this.nombre.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final Pais other = (Pais) obj;
        if ((this.nombre == null) ? (other.nombre != null) : !this.nombre.equals(other.nombre)) {
            return false;
        }
        return true;
    }

    public static Pais hidratar(Node nodoPais) {
        Element elementoPais = (Element) nodoPais;
        Coordenadas coordenadasPais = Coordenadas.hidratar(elementoPais.getChildNodes().item(0));
        String nombrePais = elementoPais.getAttribute("Nombre");
        Pais pais = new Pais(coordenadasPais, nombrePais);
        return pais;
    }

    public Element serializar(Document doc) {
        Element elementoPais = doc.createElement("Pais");
        elementoPais.setAttribute("Nombre", nombre);
        elementoPais.appendChild(coordenadas.serializar(doc));
        return elementoPais;
    }
}
