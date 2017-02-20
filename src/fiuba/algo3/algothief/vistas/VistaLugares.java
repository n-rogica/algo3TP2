package fiuba.algo3.algothief.vistas;

import java.util.List;

import fiuba.algo3.algothief.model.Lugar;

public class VistaLugares extends Vista{
	
	public VistaLugares(List<Lugar> lugares)  {
		
		Lugar lugar;
		
		System.out.println("**********LUGARES**********");
		System.out.println("");
		for (int i=0; i<3; i++ ) {
			int j = i+1;
			System.out.print(" " + j + " ) ");
			lugar = lugares.get(i);
			System.out.println(lugar.getNombre());
		}
		System.out.println(" 4 ) Volver atras");
		System.out.println("");
		System.out.println(" Elija un lugar: ");
	}
	
	

}
