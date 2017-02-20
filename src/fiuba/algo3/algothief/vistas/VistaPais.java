package fiuba.algo3.algothief.vistas;

import java.util.List;

import fiuba.algo3.algothief.model.Pais;

public class VistaPais extends Vista {
	
	public VistaPais(Pais paisActual, List<Pais> paises) {
		System.out.println("**********PAISES**********");
		System.out.println("");
		System.out.println(" Pais actual: " + paisActual.getNombre());
		System.out.println("");
		System.out.println(" A donde queres viajar? ");
		System.out.println("");
		int i = 0;
		for (Pais pais : paises) {
			i++;
			System.out.println(i + " ) " + pais.getNombre());
		}
		i++;
		System.out.println(i + " ) Volver atras");
		System.out.println("");
		System.out.println(" Elija un pais: ");
	}
	
}
