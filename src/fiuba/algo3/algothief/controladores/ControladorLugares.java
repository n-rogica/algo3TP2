package fiuba.algo3.algothief.controladores;

import java.io.IOException;
import java.util.List;

import fiuba.algo3.algothief.model.Caso;
import fiuba.algo3.algothief.model.Lugar;
import fiuba.algo3.algothief.vistas.VistaIndicio;

public class ControladorLugares {
	
	private List<Lugar> lugares;
	private Caso caso;
	
	public ControladorLugares(List<Lugar> lugares, Caso caso) {
		this.lugares = lugares;
		this.caso = caso;
	}

	public void ingresarALugar(int opcionIngresada) throws IOException {
		opcionIngresada--;
		VistaIndicio vi = new VistaIndicio(lugares.get(opcionIngresada),caso);
		vi.mostrarIndicio();		
	}

}

