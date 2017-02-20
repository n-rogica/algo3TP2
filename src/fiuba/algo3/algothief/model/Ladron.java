package fiuba.algo3.algothief.model;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Random;


import fiuba.algo3.algothief.model.Caracteristica.Tipo;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;

public class Ladron {

    private final int MaxCantDeCaracteristicas = 5;
    private final String nombre;
    private Map<Tipo, Caracteristica> caracteristicasDelLadron;
    private List<Tipo> claves;

    public Ladron(String nombreLadron, Map<Tipo, Caracteristica> caracteristicasDelLadron) {
        this.nombre = nombreLadron;
        this.caracteristicasDelLadron = caracteristicasDelLadron;
        this.claves = new LinkedList<Tipo>(this.caracteristicasDelLadron.keySet());
    }

    //constructor de prueba
    public Ladron(String nombreLadron) {

        Map<Tipo, Caracteristica> caracteristicas = new HashMap<Tipo, Caracteristica>();

        //Estos datos se levantarian del xml despues		
        Caracteristica caracteristica1 = new Caracteristica(Tipo.SEXO, "Masculino", "sexo del ladron");
        Caracteristica caracteristica2 = new Caracteristica(Tipo.VEHICULO, "Deportivo", "Manejaba un deportivo");
        Caracteristica caracteristica3 = new Caracteristica(Tipo.CABELLO, "Negro", "Tenia cabello de color negro");
        Caracteristica caracteristica4 = new Caracteristica(Tipo.HOBBY, "Tenis", "Queria participar en un campeonato de tenis");
        Caracteristica caracteristica5 = new Caracteristica(Tipo.SENIA, "Tatuaje", "Tenia un tatuaje en el brazo");

        caracteristicas.put(Tipo.SEXO, caracteristica1);
        caracteristicas.put(Tipo.VEHICULO, caracteristica2);
        caracteristicas.put(Tipo.CABELLO, caracteristica3);
        caracteristicas.put(Tipo.HOBBY, caracteristica4);
        caracteristicas.put(Tipo.SENIA, caracteristica5);

        this.nombre = nombreLadron;
        this.caracteristicasDelLadron = caracteristicas;
        this.claves = new LinkedList<Tipo>(this.caracteristicasDelLadron.keySet());

    }

    public String getNombre() {
        return nombre;
    }

    public String obtenerCaracteristica(Tipo tipoDeCaracteristica) {

        return ((caracteristicasDelLadron.get(tipoDeCaracteristica)).getContenido());
    }
    
    public String obtenerDescripcion(Tipo tipo) {
    	return ((caracteristicasDelLadron.get(tipo)).getDescripcion());
    }

    public String obtenerDescripcionDelLadron() {
    //Devuelve en forma aleatoria una de las caracteristicas del ladron		
		
		int num;
		Random random;
		Tipo clave;
		
		random = new Random();		  
		num = random.nextInt(MaxCantDeCaracteristicas); //num vale algo entre 0 y MaxCantidadDeCaracteristicas
		clave = (Tipo)claves.get(num);		
		while (clave == Tipo.SEXO){ //Si la clave corresponde al sexo del ladron, busco otra clave para darle al jugador
			num = random.nextInt(MaxCantDeCaracteristicas);
			clave = (Tipo)claves.get(num);
		}	
		return (caracteristicasDelLadron.get(clave).getDescripcion());			
	}

	public Element serializar(Document doc) {
		
		Element elementoLadron = doc.createElement("Ladron");
		elementoLadron.setAttribute("nombre",this.nombre);
		for (Tipo clave: this.claves){ //serializo las caracteristicas
			elementoLadron.appendChild(caracteristicasDelLadron.get(clave).serializar(doc));
		}
		return elementoLadron;		
	}


	public static Ladron hidratar(Node nodoLadron) {
            Element elementoLadron = (Element) nodoLadron;
            String nombre = elementoLadron.getAttribute("nombre");
            Map<Tipo, Caracteristica> caracteristicas = new HashMap<Tipo, Caracteristica>();
            for (int i = 0; i < elementoLadron.getChildNodes().getLength(); i++) {
                Caracteristica caracteristica = Caracteristica.hidratar(elementoLadron.getChildNodes().item(i));
                caracteristicas.put(caracteristica.getTipo(), caracteristica);
            }

            Ladron ladron = new Ladron(nombre, caracteristicas);
            return ladron;
	}		
}	
	
	
	
