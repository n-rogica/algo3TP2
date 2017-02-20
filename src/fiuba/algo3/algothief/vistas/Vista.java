package fiuba.algo3.algothief.vistas;

import java.io.IOException;
import java.util.Scanner;

public abstract class Vista {
	
	private Scanner input = new Scanner (System.in);
	
	public String leerCadenaPorConsola () throws IOException{
		return input.nextLine();
	}
	
	private int leerOpcionPorConsola() throws NumberFormatException, IOException {	
		int numero = Integer.parseInt(input.nextLine());
		return numero;
	}	
	
	public int obtenerOpcionDelUsuario() throws IOException {
		int valor = 0;
		boolean hayError;
		do{
			try{
				valor = this.leerOpcionPorConsola();
				hayError = false;
			}catch (NumberFormatException e){
				this.valorInvalido();
				hayError = true;
			}
		}while (hayError);
		return valor;
	}
	
	public void valorInvalido() {
		System.out.println("Valor ingresado invalido");
	}
	
	public static void errorEnLosArchivos(){
		System.out.println("Se produjo un error al intentar abrir los archivos de configuracion");
	}	
}
