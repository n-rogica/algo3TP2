package fiuba.algo3.algothief.model;

import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;

import fiuba.algo3.algothief.model.Caracteristica.Tipo;

public class OrdenDeArresto {

    private boolean emitida;
    private String nombreDelSospechoso;
    private Map<Tipo,String> campos;

    public OrdenDeArresto() {
        this.emitida = false;
        this.campos = new HashMap<Tipo,String>();
        this.nombreDelSospechoso = null;

    }   

	public boolean isEmitida() {
        return emitida;
    }

    public void emitirOrdenDeArresto(String nombre) {
        this.nombreDelSospechoso = nombre;
        this.emitida = true;
    }
    
    public void desemitir() {
		this.emitida = false;		
	}

    public void agregarCaracteristica(Caracteristica caracteristica) {
    	campos.put(caracteristica.getTipo(),caracteristica.getContenido());
    }
    
    public String obtenerCaracteristica (Tipo tipo){
    	return campos.get(tipo);
    }
    
    public String obtenerNombreDelSospechoso(){
    	return nombreDelSospechoso;
    }
    
    public Iterator<Tipo> obtenerClaves(){
    	Iterator<Tipo> iterador = campos.keySet().iterator();
		return iterador;    	
    }
    
    public Element serializar(Document doc) {
        Element elementoOrdArresto = doc.createElement("OrdenDeArresto");
        elementoOrdArresto.setAttribute("Emitida", String.valueOf(emitida));        
        elementoOrdArresto.setAttribute("NombreSospechoso", nombreDelSospechoso);
        
        List<Tipo> claves = new LinkedList<Tipo> (campos.keySet());
        Element camposOrdArresto = doc.createElement("Campos");
        
        for (Tipo clave: claves){
        	Element elemCampo = doc.createElement("Campo");
        	Integer num = clave.ordinal();
        	elemCampo.setAttribute("Tipo",num.toString());
        	elemCampo.setAttribute("Valor",campos.get(clave));
        	camposOrdArresto.appendChild(elemCampo);       	        	
        }       
        elementoOrdArresto.appendChild(camposOrdArresto);
        return elementoOrdArresto;
    }

    public static OrdenDeArresto hidratar(Node nodoOrdenDeArresto) {
        Element elementoOrdArr = (Element) nodoOrdenDeArresto;
        OrdenDeArresto ordenDeArrestoHidratada = new OrdenDeArresto();

        ordenDeArrestoHidratada.emitida = Boolean.parseBoolean(elementoOrdArr.getAttribute("Emitida"));        
        ordenDeArrestoHidratada.nombreDelSospechoso = elementoOrdArr.getAttribute("NombreSospechoso");
        
        Element elemCampos = (Element) elementoOrdArr.getElementsByTagName("Campos").item(0);
        
        for (int i = 0; i < elemCampos.getChildNodes().getLength() ; i++){        	
        	Element elemCampo = (Element) elemCampos.getChildNodes().item(i);
        	int tipo = Integer.parseInt(elemCampo.getAttribute("Tipo"));
        	ordenDeArrestoHidratada.campos.put(Tipo.values()[tipo],elemCampo.getAttribute("Valor"));        	
        }
        return ordenDeArrestoHidratada;
    }

	
}
