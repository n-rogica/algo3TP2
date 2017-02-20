package fiuba.algo3.algothief.model;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;

public class Latitud extends CoordenadaGeografica  {

	private String sentido;	
	public Latitud (String sentido, double grados, double minutos, double segundos){
		
		super (grados,minutos,segundos);
		this.sentido = sentido;
	}	
	
	public double expresarEnKilometros (){
		
		double grados = this.exPresarEnGrados();
		
		if (sentido == "S"){
			grados = grados *(-1);
		}		
		return this.convertirAKilometros(grados);
	}	

    public Element serializar(Document doc) {
        Element elementoLatitud = doc.createElement("Latitud");
        elementoLatitud.setAttribute("Sentido", sentido);
        elementoLatitud.setAttribute("Grados", String.valueOf(getGrados()));
        elementoLatitud.setAttribute("Minutos", String.valueOf(getMinutos()));
        elementoLatitud.setAttribute("Segundos", String.valueOf(getSegundos()));
        return elementoLatitud;
    }

    public static Latitud hidratar(Node nodoLatitud) {
        Element elementoLatitud = (Element) nodoLatitud;
        String sentidoHidratado = elementoLatitud.getAttribute("Sentido");
        double gradosHidratados = Double.parseDouble(elementoLatitud.getAttribute("Grados"));
        double minutosHidratados = Double.parseDouble(elementoLatitud.getAttribute("Grados"));
        double segundosHidratados = Double.parseDouble(elementoLatitud.getAttribute("Grados"));
        Latitud latitud = new Latitud(sentidoHidratado, gradosHidratados, minutosHidratados, segundosHidratados);
        return latitud;
    }
  }
