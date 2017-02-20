package fiuba.algo3.algothief.controladores;

import java.io.IOException;

import fiuba.algo3.algothief.vistas.VistaMenuInicio;


public class Principal {

	public static void main(String[] args) throws IOException {
		
		int opcion = 0;
		ControladorMenuInicio controlador = new ControladorMenuInicio ();
		
		do{
			VistaMenuInicio vmi= new VistaMenuInicio();
			opcion = vmi.obtenerOpcionDelUsuario();
			switch (opcion){			
				case 1:
					controlador.juegoNuevo();
					break;							
				case 2:
					vmi.mensajeSalida();
					break;					
				default: 	
					vmi.valorInvalido();
					break;							
			}	
		}while (opcion != 2);
	}
}
