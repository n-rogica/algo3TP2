package fiuba.algo3.algothief.vistas;

import java.io.IOException;

import fiuba.algo3.algothief.model.Caso;

public class VistaCaso {
	public VistaCaso(Caso caso) throws IOException {
		System.out.println("Se le ha asignado un caso... ");
		System.out.println("Un objeto valioso a sido robado en " + caso.getPaisActual().getNombre());
		System.out.println("El objeto es " + caso.getObjetoRobado().getDescripcion());
		System.out.println("El ladron " + caso.getSexoDelLadron());
		System.out.println("");
		System.out.println("Enter para seguir...");
		System.in.read();
	}

}
