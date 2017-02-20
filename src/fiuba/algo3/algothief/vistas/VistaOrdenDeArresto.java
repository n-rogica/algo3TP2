package fiuba.algo3.algothief.vistas;

import fiuba.algo3.algothief.model.Caracteristica.Tipo;
import fiuba.algo3.algothief.model.Caso;


public class VistaOrdenDeArresto extends Vista{
	
	public VistaOrdenDeArresto(Caso caso){

		System.out.println("**********ORDEN DE ARRESTO**********");
		System.out.println("");
		System.out.println(" 1 ) Sexo:     " + caso.obtenerCampoOrdenDeArresto(Tipo.SEXO));
		System.out.println(" 2 ) Hobby:    " + caso.obtenerCampoOrdenDeArresto(Tipo.HOBBY));
		System.out.println(" 3 ) Cabello:    " + caso.obtenerCampoOrdenDeArresto(Tipo.CABELLO));
		System.out.println(" 4 ) Senia:  " + caso.obtenerCampoOrdenDeArresto(Tipo.SENIA));
		System.out.println(" 5 ) Vehiculo: " + caso.obtenerCampoOrdenDeArresto(Tipo.VEHICULO));
		System.out.println(" 6 ) Buscar sospechoso");
		System.out.println(" 7 ) Volver atras");
		System.out.println("");
		System.out.println(" Elija la caracteristica que quiere agregar o modificar: ");
	}

}
