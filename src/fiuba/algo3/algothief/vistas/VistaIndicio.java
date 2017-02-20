package fiuba.algo3.algothief.vistas;

import java.io.IOException;

import fiuba.algo3.algothief.model.Caso;
import fiuba.algo3.algothief.model.Lugar;

public class VistaIndicio {
	Lugar lugar;
	Caso caso;
	
	public VistaIndicio(Lugar lugar, Caso caso) {
		this.lugar = lugar;
		this.caso = caso;
	}
	
	public void mostrarIndicio() throws IOException {
		System.out.println(lugar.entrarLugar(caso));
		System.out.println("Enter para seguir...");
		System.in.read();
	}

}
