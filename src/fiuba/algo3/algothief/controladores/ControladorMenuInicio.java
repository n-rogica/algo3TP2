package fiuba.algo3.algothief.controladores;

import java.io.IOException;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.TransformerException;

import org.xml.sax.SAXException;

import fiuba.algo3.algothief.model.Juego;
import fiuba.algo3.algothief.vistas.Vista;

public class ControladorMenuInicio {	
	
	public ControladorMenuInicio (){			
	}	
	
	public void juegoNuevo(){				
		Juego juego;
		
		try {
			juego = new Juego ();			
			ControladorJuego controladorJuego = new ControladorJuego(juego);
			controladorJuego.jugar();
		} catch (ParserConfigurationException e) {
			Vista.errorEnLosArchivos();
		} catch (TransformerException e) {
			Vista.errorEnLosArchivos();
		} catch (IOException e) {
			Vista.errorEnLosArchivos();
		} catch (SAXException e) {
			Vista.errorEnLosArchivos();
		}
	}
}
