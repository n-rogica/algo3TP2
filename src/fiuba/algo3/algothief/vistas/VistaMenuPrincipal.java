package fiuba.algo3.algothief.vistas;

import java.io.IOException;

public class VistaMenuPrincipal extends Vista{
	
	public VistaMenuPrincipal(){
		System.out.println("**********ALGOTHIEF**********");
		System.out.println("");
		System.out.println(" 1 ) Viajar a otro pais ");
		System.out.println(" 2 ) Entrar a un lugar ");
		System.out.println(" 3 ) Ver orden de arresto ");
		System.out.println(" 4 ) Salir");
		System.out.println("");
		System.out.println(" Elija una accion: ");
	}

	public static void mensajeTerminandoPartida() throws IOException {
		System.out.println("Terminando partida. Enter para seguir...");	
		System.in.read();
	}

	public static void mensajeOpcionInvalida() {
		System.out.println("Valor ingresado invalido");		
	}
	
}
