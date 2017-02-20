package fiuba.algo3.algothief.vistas;

import fiuba.algo3.algothief.model.Caso;

public class VistaHoras {
	
	public VistaHoras(Caso caso){
		System.out.println("Horas restantes: " + caso.getTiempoResolucion());
	}

}
