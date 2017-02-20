package fiuba.algo3.algothief.vistas;

public class VistaMenuInicio extends Vista {
	
	public VistaMenuInicio() {
		System.out.println("**********ALGOTHIEF**********");
		System.out.println("");
		System.out.println(" 1 ) Juego Nuevo ");
		System.out.println(" 2 ) Salir ");
		System.out.println("");
		System.out.println(" Elija una accion: ");
	}

	public void mensajeSalida() {
		System.out.println("Saliendo del juego");
	}
	
}
