package fiuba.algo3.algothief.vistas;

import java.io.IOException;
import java.util.Iterator;
import java.util.List;

import fiuba.algo3.algothief.model.Ladron;

public class VistaCaracteristicas extends Vista {
	
	public VistaCaracteristicas(int tipo) {
		switch(tipo){
			case 1:
				System.out.println("**********SEXO**********");
				System.out.println("");
				System.out.println(" 1 ) Femenino ");
				System.out.println(" 2 ) Masculino ");
				break;
			case 2:
				System.out.println("**********HOBBY**********");
				System.out.println("");
				System.out.println(" 1 ) Tenis ");
				System.out.println(" 2 ) Musica ");
				System.out.println(" 3 ) Alpinismo ");
				System.out.println(" 4 ) Paracaidismo ");
				System.out.println(" 5 ) Natacion ");
				System.out.println(" 6 ) Croquet ");
				break;
			case 3:
				System.out.println("**********CABELLO**********");
				System.out.println("");
				System.out.println(" 1 ) Castanio ");
				System.out.println(" 2 ) Rubio ");
				System.out.println(" 3 ) Rojo ");
				System.out.println(" 4 ) Negro ");
				break;
			case 4:
				System.out.println("**********SENIA**********");
				System.out.println("");
				System.out.println(" 1 ) Anillo ");
				System.out.println(" 2 ) Tatuaje ");
				System.out.println(" 3 ) Cicatriz ");
				System.out.println(" 4 ) Joyas ");
				break;
			case 5:
				System.out.println("**********VEHICULO**********");
				System.out.println("");
				System.out.println(" 1 ) Descapotable ");
				System.out.println(" 2 ) Limusina ");
				System.out.println(" 3 ) Deportivo ");
				System.out.println(" 4 ) Moto ");
				break;
			case 6:
				break;
			case 7:
				break;
			default:
				this.valorInvalido();
				break;

		}
	}
	
	public void volverPantallaAnterior() {
		System.out.println("Volviendo a la pantalla anterior...");
	}
	
	public void buscarSospechosos() {
		System.out.println("Buscando sospechosos...");
	}

	public void mostrarOrdenEmitida(List<Ladron> sospechosos) {
		System.out.println("Se ha emitido una orden de arresto contra: " + sospechosos.get(0).getNombre());		
	}

	public void mostrarSospechosos(List<Ladron> sospechosos) throws IOException {
		
		
		Iterator<Ladron> iterador = sospechosos.iterator();
		
		System.out.println("Sospechosos que cumplen con la descripcion: ");
		while (iterador.hasNext()){
			System.out.println("	-" + iterador.next().getNombre());
		}
		System.out.println("Enter para seguir...");
		System.in.read();
		
	}
	
	

	

}
