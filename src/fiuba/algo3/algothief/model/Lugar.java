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
public class Lugar {

    protected String nombre;
    protected int visitas;
    private final int HORAS_PRIMER_VISITA = 1;
    private final int HORAS_SEGUNDA_VISITA = 2;
    private final int HORAS_TRES_O_MAS_VISITAS = 3;

    private Indicio indicio;

    public Lugar(String nombre, int visitas, Indicio indicio) {
        this.indicio = indicio;
        this.nombre = nombre;
        this.visitas = visitas;
    }

    public Lugar(String nombre, Indicio indicio) {
        this(nombre, 0, indicio);
    }

    public String entrarLugar(Caso caso) {
        this.visitas++;
        String indicioCompleto;
        // Le descuento las horas por la visita teniendo en cuenta la cantidad de veces que visito el lugar
        switch (visitas) {
            case 1:
                caso.descontarHoras(HORAS_PRIMER_VISITA);
                break;
            case 2:
                caso.descontarHoras(HORAS_SEGUNDA_VISITA);
                break;
            default:
                caso.descontarHoras(HORAS_TRES_O_MAS_VISITAS);
                break;
        }
        
        indicioCompleto = indicio.darIndicio(caso.getDificultad()) + caso.darIndicioLadron();
        return indicioCompleto;
    }

    public String getNombre() {
        return nombre;
    }

    public static Lugar hidratar(Node nodoLugar) {
        if (nodoLugar.getNodeName().equals("LugarUltimoPais")) {
            return LugarUltimoPais.hidratar(nodoLugar);
        }
        Element elementoLugar = (Element) nodoLugar;
        Indicio indicioLugar = Indicio.hidratar(elementoLugar.getChildNodes().item(0));
        String nombreLugar = elementoLugar.getAttribute("Nombre");
        int visitasLugar = Integer.parseInt(elementoLugar.getAttribute("Visitas"));
        Lugar lugar = new Lugar(nombreLugar, visitasLugar, indicioLugar);
        return lugar;
    }

    public Element serializar(Document doc) {
        Element elementoLugar = doc.createElement("Lugar");
        elementoLugar.setAttribute("Nombre", nombre);
        elementoLugar.setAttribute("Visitas", String.valueOf(visitas));
        elementoLugar.appendChild(indicio.serializar(doc));
        return elementoLugar;
    }
}
