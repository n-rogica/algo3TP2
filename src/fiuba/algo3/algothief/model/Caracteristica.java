package fiuba.algo3.algothief.model;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;

public class Caracteristica {
	
	public enum Tipo{
		SEXO,HOBBY,CABELLO,SENIA,VEHICULO
	}
	
	private Tipo tipo;
 	private String contenido; 	
 	private String descripcion;
	
	public Caracteristica (Tipo tipo, String contenido, String descripcion){
		this.tipo = tipo;
		this.contenido = contenido;
		this.descripcion = descripcion;
	}	
	
	public String getDescripcion(){
		return descripcion;
	}
	
	public String getContenido (){
		return contenido;
	}
	
	public Tipo getTipo(){
		return tipo;
	}
	
	
	public Element serializar (Document doc){
		
		Element elementoCaracteristica = doc.createElement("Caracteristica");
		Integer num = tipo.ordinal();
		elementoCaracteristica.setAttribute ("Tipo",num.toString());
		elementoCaracteristica.setAttribute("Contenido",contenido);
		elementoCaracteristica.setAttribute("Descripcion",descripcion);
		
		return elementoCaracteristica;		
	}
	
	public static Caracteristica hidratar (Node nodoCaracteristica){
		
		Element elementoCaracteristica = (Element) nodoCaracteristica;		
		
		int tipo = Integer.parseInt(elementoCaracteristica.getAttribute("Tipo"));		
		String contenido = elementoCaracteristica.getAttribute("Contenido");
		String descripcion = elementoCaracteristica.getAttribute("Descripcion");
		Caracteristica caracteristica = new Caracteristica (Tipo.values()[tipo],contenido,descripcion);
		
		return caracteristica;		
	}
}
	
	