package fiuba.algo3.algothief.controladores;

import java.util.List;

import fiuba.algo3.algothief.model.Jugador;
import fiuba.algo3.algothief.model.Pais;
import fiuba.algo3.algothief.vistas.VistaViajar;

public class ControladorPaises {
	
	private List<Pais> paises;
	private Jugador jugador;

	public ControladorPaises(List<Pais> paises, Jugador jugador) {
		this.paises = paises;
		this.jugador = jugador;
	}
	
	public void viajarAPais(int opcionIngresada) {
		opcionIngresada--;
		jugador.realizarViaje(paises.get(opcionIngresada));
		new VistaViajar(paises.get(opcionIngresada).getNombre());
	}

}
