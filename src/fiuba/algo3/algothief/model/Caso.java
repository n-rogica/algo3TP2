/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fiuba.algo3.algothief.model;

import fiuba.algo3.algothief.model.Caracteristica.Tipo;
import fiuba.algo3.algothief.model.Juego.Dificultad;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Observable;
import java.util.Set;
import java.util.Iterator;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;

/**
 *
 * @author Ramiro
 */
public class Caso extends Observable {

    private enum EstadoCaso {

        RESUELTO, FALLIDO, PENDIENTE
    }
    
    public static enum Flag{
    	
    	DURMIENDO,DESCUENTO_HORAS,CAMBIO_ESTADO_CASO
    }

    private final Dificultad dificultad;
    private int tiempoResolucion;
    private EstadoCaso estado;
    private Pais paisActual;
    private boolean paisIncorrecto;
    private final ObjetoRobado objetoRobado;
    private Map<Pais, Pista> pistaPais;
    private final Ladron ladron;
    private OrdenDeArresto ordenDeArresto;
    private List<Ladron> sospechosos;
    private LinkedList<Flag> flags = new LinkedList<Flag>();
    private final int HORAS_DORMIR = 8;
    private final int HORAS_DISPARO = 4;
    private final int HORAS_CUCHILLO = 2;
    private final int TIEMPO_TOTAL = 154;
    private final int HORAS_ORDEN_DE_ARRESTO = 3;

    public Caso(Dificultad dificultad, Pais paisDeInicio, ObjetoRobado objetoRobado, Ladron ladron, List<Ladron> sospechosos) {
        this.dificultad = dificultad;
        this.tiempoResolucion = TIEMPO_TOTAL;
        this.estado = EstadoCaso.PENDIENTE;
        this.paisActual = paisDeInicio;
        this.objetoRobado = objetoRobado;
        this.ladron = ladron;
        this.ordenDeArresto = new OrdenDeArresto();
        this.sospechosos = sospechosos;
        this.paisIncorrecto = false;
    }

    public boolean isFallido() {
        return (estado == EstadoCaso.FALLIDO);
    }

    public boolean isPendiente() {
        return (estado == EstadoCaso.PENDIENTE);
    }

    public boolean isResuelto() {
        return (estado == EstadoCaso.RESUELTO);
    }

    public Dificultad getDificultad() {
        return dificultad;
    }

    public Pais getPaisActual() {
        return paisActual;
    }

    public boolean ordenDeArrestoEmitida() {
        return ordenDeArresto.isEmitida();
    }

    public void llevarCasoEn(Pais destino, int velocidadViajeKmph) {
        // Calculo el tiempo de viaje y le resto las horas al caso
        descontarHoras(paisActual.distanciaPaises(destino) / velocidadViajeKmph);
        paisActual = destino;
    }

    public void descontarHoras(int horas) {
    	
    	flags.clear();
    	
        for (int i = 1; i <= horas; i = i + 1) {
            tiempoResolucion--;
            if (this.esHoraDeDormir()) {
                tiempoResolucion -= HORAS_DORMIR;
                flags.add(Flag.DURMIENDO);
            }            
        }
    	flags.add(Flag.DESCUENTO_HORAS);

        // Verifico que el caso no esta perdido
        if (tiempoResolucion <= 0 && !this.isResuelto()) {
            setFallido(this.isPendiente());
            flags.add(Flag.CAMBIO_ESTADO_CASO);
        }        
        this.actualizarObservadores(flags);
    }

    private boolean esHoraDeDormir() {
        return (tiempoResolucion % 24 == 17);
    }

    public int getTiempoResolucion() {
        return tiempoResolucion;
    }

    public Pista getPista() {
        Pista pista = pistaPais.get(paisActual);
        if (pista == null) {
        	paisIncorrecto = true;
            PistaFalsa pistaFalsa = new PistaFalsa();
            return pistaFalsa;
        }
        paisIncorrecto = false;
        return pista;
    }    
    
    public void setPistas(Set<Pista> pistas) {
        // Asocio cada pista a un pais
        pistaPais = new HashMap<Pais, Pista>();

        Pais paisPistaAnterior = paisActual;
        for (Pista pista : pistas) {
            pistaPais.put(paisPistaAnterior, pista);
            paisPistaAnterior = pista.getPaisDestino();
        }
        pistaPais.put(paisPistaAnterior, Pista.pistaUltimoPais());
    }

    public String darIndicioLadron() {
    	if (paisIncorrecto){
    		return "";
    	}
    	return ladron.obtenerDescripcionDelLadron();
    }

    public ObjetoRobado getObjetoRobado() {
        return objetoRobado;
    }

    public int obtenerCantidadDeSospechosos() {
        return sospechosos.size();
    }
    
    public String obtenerCampoOrdenDeArresto(Tipo tipo){
    	return ordenDeArresto.obtenerCaracteristica(tipo);
    }

    public void agregarCaracteristaALaOrden (Caracteristica caracteristicaIngresada){
    	ordenDeArresto.agregarCaracteristica(caracteristicaIngresada);
    }    

	public List<Ladron> buscarSospechosos() {
		List<Ladron> posiblesLadrones = new LinkedList<Ladron> (sospechosos);
		this.verificarCaracteristicas(ordenDeArresto, posiblesLadrones);
			if (posiblesLadrones.size() == 1){
				ordenDeArresto.emitirOrdenDeArresto(posiblesLadrones.get(0).getNombre());
				this.descontarHoras(HORAS_ORDEN_DE_ARRESTO);
			}else {
				if (ordenDeArresto.isEmitida()){
					ordenDeArresto.desemitir();
				}
			}
		return posiblesLadrones;		
	}
	
    private void verificarCaracteristicas(OrdenDeArresto ordenDeArresto, List<Ladron> posiblesLadrones) {       
        Iterator<Tipo> iteradorClaves = ordenDeArresto.obtenerClaves();        
        while (iteradorClaves.hasNext()){        	
        	Tipo clave = iteradorClaves.next();
        	Iterator<Ladron> iteradorLadrones = posiblesLadrones.iterator();
        	while (iteradorLadrones.hasNext()){		
        		if (! iteradorLadrones.next().obtenerCaracteristica(clave).contentEquals(ordenDeArresto.obtenerCaracteristica(clave))){
        			iteradorLadrones.remove();
        		}
        	}
        }        
    }

    public void setResuelto(boolean resuelto) {
        if (resuelto) {
            setEstado(EstadoCaso.RESUELTO);
            flags.add(Flag.CAMBIO_ESTADO_CASO);
            actualizarObservadores(flags);
        }
    }

    public void setFallido(boolean fallido) {
        if (fallido) {
            setEstado(EstadoCaso.FALLIDO);
            flags.add(Flag.CAMBIO_ESTADO_CASO);
            actualizarObservadores(flags);
        }
    }

    private void setEstado(EstadoCaso estado) {
        this.estado = estado;		
    }

    public void jugadorEsAcuchillado() {
        descontarHoras(HORAS_CUCHILLO);
    }

    public void jugadorEsHeridoDeBala() {
        descontarHoras(HORAS_DISPARO);
    }

    public boolean ordenEmitidaContraElLadronDelCaso() {
        return (ordenDeArresto.obtenerNombreDelSospechoso().contentEquals(ladron.getNombre()));
    }
    
    public String getSexoDelLadron() {
    	return ladron.obtenerDescripcion(Tipo.SEXO);
    }
    
    private void actualizarObservadores(Object flags){
    	setChanged();
    	notifyObservers(flags);
    }

    public Element serializar(Document doc) {
        Element elementoCaso = doc.createElement("Caso");
        elementoCaso.setAttribute("Dificultad", String.valueOf(dificultad));
        elementoCaso.setAttribute("TiempoResolucion", String.valueOf(tiempoResolucion));
        elementoCaso.setAttribute("Estado", String.valueOf(estado));
        elementoCaso.appendChild(paisActual.serializar(doc));
        elementoCaso.appendChild(objetoRobado.serializar(doc));
        elementoCaso.appendChild(ordenDeArresto.serializar(doc));
        elementoCaso.appendChild(ladron.serializar(doc));

        Element listaSospechosos = doc.createElement("Sospechosos");

        for (Ladron sospechoso : sospechosos) {
            listaSospechosos.appendChild(sospechoso.serializar(doc));
        }

        elementoCaso.appendChild(listaSospechosos);

        // Serializo el Map<Pais,Pista>
        Element mapaPistas = doc.createElement("Mapa");
        for (Pais paisClave : pistaPais.keySet()) {

            Element clave = doc.createElement("Clave");
            clave.appendChild(paisClave.serializar(doc));

            Element valor = doc.createElement("Valor");
            valor.appendChild(pistaPais.get(paisClave).serializar(doc));

            mapaPistas.appendChild(clave);
            mapaPistas.appendChild(valor);
        }

        // Agrego el map al caso
        elementoCaso.appendChild(mapaPistas);

        return elementoCaso;
    }  

    public static Caso hidratar(Node nodoCaso) {
        Element elementoCaso = (Element) nodoCaso;        
        Dificultad dificultadHidrat = Dificultad.valueOf(elementoCaso.getAttribute("Dificultad"));
        int tiempoResolucionHidrat = Integer.parseInt(elementoCaso.getAttribute("TiempoResolucion"));
        EstadoCaso estadoHidrat = EstadoCaso.valueOf(elementoCaso.getAttribute("Estado"));

        Pais paisHidrat = Pais.hidratar(elementoCaso.getChildNodes().item(0));
        ObjetoRobado objRobHidrat = ObjetoRobado.hidratar(elementoCaso.getChildNodes().item(1));
        OrdenDeArresto ordArrHidrat = OrdenDeArresto.hidratar(elementoCaso.getChildNodes().item(2));
        Ladron ladronHidrat = Ladron.hidratar(elementoCaso.getChildNodes().item(3));


        // Hidrato sospechosos
        List<Ladron> sospechososHidrat = new ArrayList<Ladron>();

        Element elementoSospechosos = (Element) elementoCaso.getElementsByTagName("Sospechosos").item(0);
        for (int j = 0; j < elementoSospechosos.getChildNodes().getLength(); j++) {
            sospechososHidrat.add(Ladron.hidratar(elementoSospechosos.getChildNodes().item(j)));
        }

        // Hidrato el map
        Map<Pais, Pista> pistaPaisHidrat = new HashMap<Pais, Pista>();
        Element elementoMapa = (Element) elementoCaso.getElementsByTagName("Mapa").item(0);
        for (int j = 0; j < elementoMapa.getChildNodes().getLength() - 1; j += 2) {
            Pais paisClaveHidrat = Pais.hidratar(elementoMapa.getChildNodes().item(j).getChildNodes().item(0));
            Pista pistaValorHidrat = Pista.hidratar(elementoMapa.getChildNodes().item(j + 1).getChildNodes().item(0));
            pistaPaisHidrat.put(paisClaveHidrat, pistaValorHidrat);
        }

        Caso casoHidratado = new Caso(dificultadHidrat, paisHidrat, objRobHidrat, ladronHidrat, sospechososHidrat);
        casoHidratado.estado = estadoHidrat;
        casoHidratado.paisActual = paisHidrat;
        casoHidratado.ordenDeArresto = ordArrHidrat;
        casoHidratado.tiempoResolucion = tiempoResolucionHidrat;
        casoHidratado.pistaPais = pistaPaisHidrat;
        return casoHidratado;
    }

}
