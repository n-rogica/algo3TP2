package fiuba.algo3.algothief.vistas;

import java.io.IOException;

public class VistaMenuCargarJugador extends Vista {
	
	public VistaMenuCargarJugador (){		
	}

	public void mostrarMenu() {
		System.out.println ("Ingrese un nombre de usuario: ");		
	}

	public void mostrarMenuContinuarPartida() {
		System.out.println("Tiene una partida en curso");
		System.out.println("");
		System.out.println(" 1 ) Continuar ");
		System.out.println(" 2 ) Comenzar una partida nueva ");		
		System.out.println("");
		System.out.println(" Elija una accion: ");
		
	}
	
	public String obtenerNombrDeUsuario() throws IOException{
		
		String cadenaIngresada;
		boolean hayError;
		
		do{
			cadenaIngresada = this.leerCadenaPorConsola();
			hayError = false;
			if (cadenaIngresada.equals("")){
				System.out.println("Error, ingrese un nombre: ");
				hayError = true;
			}
		}while (hayError);
		
		return cadenaIngresada;

	}	
}
