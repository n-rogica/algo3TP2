package fiuba.algo3.algothief.controladores;

import java.io.IOException;
import java.util.List;

import fiuba.algo3.algothief.model.Caracteristica;
import fiuba.algo3.algothief.model.Caracteristica.Tipo;
import fiuba.algo3.algothief.model.Caso;
import fiuba.algo3.algothief.model.Ladron;
import fiuba.algo3.algothief.vistas.VistaCaracteristicas;

public class ControladorOrdenDA {
	private int opcionIngresadaCarac;
	private int opcionOrden;
	private Caso caso;
	private final int VOLVER_ATRAS = 7;
	private final int EMITIR_ORDEN = 6;

	public ControladorOrdenDA(int opcionIngresadaOrdenDA, Caso caso) {
		this.opcionOrden = opcionIngresadaOrdenDA;
		this.caso = caso;
	}
	
	public void ingresarCaracteristica() throws NumberFormatException, IOException {
		VistaCaracteristicas vc = new VistaCaracteristicas(opcionOrden);
		if (opcionOrden == VOLVER_ATRAS) {
			vc.volverPantallaAnterior();
			return;
		}
		if (opcionOrden == EMITIR_ORDEN) {
			vc.buscarSospechosos();
			List<Ladron> sospechosos = caso.buscarSospechosos();
			if (sospechosos.size() == 1){
				vc.mostrarOrdenEmitida(sospechosos);				
			}else{
				vc.mostrarSospechosos(sospechosos);
			}
			
			return;
		}
		System.out.println("Eliga una opcion: ");
		this.opcionIngresadaCarac = vc.obtenerOpcionDelUsuario();
		Caracteristica caracteristica = null;
		switch(opcionOrden){
			case 1:
				switch(opcionIngresadaCarac){
					case 1:
						Caracteristica carac11 = new Caracteristica(Tipo.SEXO,"Femenino",null);
						caracteristica = carac11;
						break;
					case 2:
						Caracteristica carac12 = new Caracteristica(Tipo.SEXO,"Masculino",null);
						caracteristica = carac12;
						break;
					default:
						vc.valorInvalido();
						break;
				}
				break;
			case 2:
				switch(opcionIngresadaCarac){
					case 1:
						Caracteristica carac21 = new Caracteristica(Tipo.HOBBY,"Tenis",null);
						caracteristica = carac21;
						break;
					case 2:
						Caracteristica carac22 = new Caracteristica(Tipo.HOBBY,"Musica",null);
						caracteristica = carac22;
						break;
					case 3:
						Caracteristica carac23 = new Caracteristica(Tipo.HOBBY,"Alpinismo",null);
						caracteristica = carac23;
						break;
					case 4:
						Caracteristica carac24 = new Caracteristica(Tipo.HOBBY,"Paracaidismo",null);
						caracteristica = carac24;
						break;
					case 5:
						Caracteristica carac25 = new Caracteristica(Tipo.HOBBY,"Natacion",null);
						caracteristica = carac25;
						break;
					case 6:
						Caracteristica carac26 = new Caracteristica(Tipo.HOBBY,"Croquet",null);
						caracteristica = carac26;
						break;
					default:
						vc.valorInvalido();
						break;
				}
				break;
			case 3:
				switch(opcionIngresadaCarac){
					case 1:
						Caracteristica carac31 = new Caracteristica(Tipo.CABELLO,"Castanio",null);
						caracteristica = carac31;
						break;
					case 2:
						Caracteristica carac32 = new Caracteristica(Tipo.CABELLO,"Rubio",null);
						caracteristica = carac32;
						break;
					case 3:
						Caracteristica carac33 = new Caracteristica(Tipo.CABELLO,"Rojo",null);
						caracteristica = carac33;
						break;
					case 4:
						Caracteristica carac34 = new Caracteristica(Tipo.CABELLO,"Negro",null);
						caracteristica = carac34;
						break;
					default:
						vc.valorInvalido();
						break;
				}
				break;
			case 4:
				switch(opcionIngresadaCarac){
					case 1:
						Caracteristica carac41 = new Caracteristica(Tipo.SENIA,"Anillo",null);
						caracteristica = carac41;
						break;
					case 2:
						Caracteristica carac42 = new Caracteristica(Tipo.SENIA,"Tatuaje",null);
						caracteristica = carac42;
						break;
					case 3:
						Caracteristica carac43 = new Caracteristica(Tipo.SENIA,"Cicatriz",null);
						caracteristica = carac43;
						break;
					case 4:
						Caracteristica carac44 = new Caracteristica(Tipo.SENIA,"Joyas",null);
						caracteristica = carac44;
						break;
					default:
						vc.valorInvalido();
						break;
				}
				break;
			case 5:
				switch(opcionIngresadaCarac){
					case 1:
						Caracteristica carac51 = new Caracteristica(Tipo.VEHICULO,"Descapotable",null);
						caracteristica = carac51;
						break;
					case 2:
						Caracteristica carac52 = new Caracteristica(Tipo.VEHICULO,"Limusina",null);
						caracteristica = carac52;
						break;
					case 3:
						Caracteristica carac53 = new Caracteristica(Tipo.VEHICULO,"Deportivo",null);
						caracteristica = carac53;
						break;
					case 4:
						Caracteristica carac54 = new Caracteristica(Tipo.VEHICULO,"Moto",null);
						caracteristica = carac54;
						break;
					default:
						vc.valorInvalido();
						break;
				}
				break;
			default:
				vc.valorInvalido();
				break;
		}

		caso.agregarCaracteristaALaOrden(caracteristica);
	}



}
