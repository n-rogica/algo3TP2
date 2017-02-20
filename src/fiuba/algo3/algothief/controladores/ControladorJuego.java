package fiuba.algo3.algothief.controladores;

import java.io.IOException;
import java.util.LinkedList;
import java.util.List;
import java.util.Observable;
import java.util.Observer;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.TransformerConfigurationException;
import javax.xml.transform.TransformerException;

import org.xml.sax.SAXException;

import fiuba.algo3.algothief.model.Caso;
import fiuba.algo3.algothief.model.Caso.Flag;
import fiuba.algo3.algothief.model.Juego;
import fiuba.algo3.algothief.model.Jugador;
import fiuba.algo3.algothief.model.Pais;
import fiuba.algo3.algothief.vistas.VistaCaso;
import fiuba.algo3.algothief.vistas.VistaGrado;
import fiuba.algo3.algothief.vistas.VistaHoras;
import fiuba.algo3.algothief.vistas.VistaLugares;
import fiuba.algo3.algothief.vistas.VistaMenuCargarJugador;
import fiuba.algo3.algothief.vistas.VistaMenuPrincipal;
import fiuba.algo3.algothief.vistas.VistaOrdenDeArresto;
import fiuba.algo3.algothief.vistas.VistaPais;

public class ControladorJuego implements Observer {

    private Juego juego;
    private Jugador jugador;
    private Caso caso;
    private boolean salir;
    private LinkedList<?> actualizaciones;
    private int opcionIngresadaPrincipal;
    private int opcionIngresadaLugares;
    private int opcionIngresadaPais;
    private int opcionIngresadaOrdenDA;
    private final int VOLVER_ATRAS = 7;
    private final int MAX_LUGARES = 3;
    private List<Pais> paises;

    public ControladorJuego(Juego juego) {
        this.juego = juego;
        this.jugador = null;
        this.caso = null;
        this.actualizaciones = null;
        this.salir = false;
    }

    public void jugar() throws IOException, ParserConfigurationException, SAXException, TransformerConfigurationException, TransformerException {

        this.menuCargaJugadores();
        do {
            this.mostrarMenuPrincipal();
            switch (opcionIngresadaPrincipal) {
                case 1:
                    do {
                        this.menuPaises();
                        if (this.esValida(opcionIngresadaPais, (paises.size()))) {
                            ControladorPaises controladorPaises = new ControladorPaises(paises, jugador);
                            controladorPaises.viajarAPais(this.opcionIngresadaPais);
                            this.verificarActualizaciones();
                        }
                    } while (!this.esValida(opcionIngresadaPais, (paises.size() + 1)));
                    break;
                case 2:
                    do {
                        this.menuLugares();
                        if (this.esValida(opcionIngresadaLugares, MAX_LUGARES)) {
                            ControladorLugares controladorLug = new ControladorLugares(jugador.lugaresParaEntrar(), caso);
                            controladorLug.ingresarALugar(this.opcionIngresadaLugares);
                            this.verificarActualizaciones();
                        }
                    } while (opcionIngresadaLugares != 4 && !salir);
                    break;
                case 3:
                    do {
                        this.menuOrdenDeArresto();
                        ControladorOrdenDA coa = new ControladorOrdenDA(this.opcionIngresadaOrdenDA, caso);
                        coa.ingresarCaracteristica();
                        this.verificarActualizaciones();
                    } while (opcionIngresadaOrdenDA != VOLVER_ATRAS && !salir);
                    break;
                case 4:
                    break;
                default:
                    VistaMenuPrincipal.mensajeOpcionInvalida();
            }
        } while (opcionIngresadaPrincipal != 4 && !salir);
        this.verificarEstado();
        juego.guardarJuego(jugador);
        VistaMenuPrincipal.mensajeTerminandoPartida();
    }

    private void verificarEstado() {
        if (caso.isResuelto()) {
            jugador.casoResuelto();
            new VistaGrado(jugador.getGrado());
        }
    }

    private void verificarActualizaciones() {

        if (actualizaciones != null) {
            for (Object flag : actualizaciones) {
                if (flag == Flag.DURMIENDO) {
                    System.out.println("Durmiendo");
                }
                if (flag == Flag.DESCUENTO_HORAS) {
                    new VistaHoras(caso);
                }
                if (flag == Flag.CAMBIO_ESTADO_CASO) {
                    salir = true;
                }
            }
            actualizaciones.clear();
        }
    }

    private boolean esValida(int opcionIngresada, int max) {
        for (int i = 1; i <= max; i++) {
            if (opcionIngresada == i) {
                return true;
            }
        }
        return false;
    }

    private void menuCargaJugadores() throws ParserConfigurationException, SAXException, IOException {

        String nombreIngresado;
        VistaMenuCargarJugador menuCargarJugadores = new VistaMenuCargarJugador();

        menuCargarJugadores.mostrarMenu();
        nombreIngresado = menuCargarJugadores.obtenerNombrDeUsuario();
        this.jugador = juego.crearJugador(nombreIngresado);

        if (jugador.getCaso() != null) { //existe jugador y tiene un caso que todavia no termino
            this.continuar(menuCargarJugadores);
        } else {
            juego.asignarCasoNuevo(jugador);
            this.caso = jugador.getCaso();
            this.caso.addObserver(this);
            this.mostrarDatosDelCaso();
        }
    }

    private void continuar(VistaMenuCargarJugador menuCargarJugadores) throws IOException, ParserConfigurationException, SAXException {
        int opcion;
        menuCargarJugadores.mostrarMenuContinuarPartida();
        do {
            opcion = menuCargarJugadores.obtenerOpcionDelUsuario();
            switch (opcion) {
                case 1: //continua con la partida que tenia guardada
                    this.caso = jugador.getCaso();
                    this.caso.addObserver(this);
                    break;
                case 2: //comienza una partida nueva
                    juego.asignarCasoNuevo(jugador);
                    this.caso = jugador.getCaso();
                    this.caso.addObserver(this);
                    this.mostrarDatosDelCaso();
                    break;
                default:
                    VistaMenuPrincipal.mensajeOpcionInvalida();
            }
        } while (opcion != 1 & opcion != 2);
    }

    private void mostrarDatosDelCaso() throws IOException {

        new VistaGrado(jugador.getGrado());
        new VistaHoras(caso);
        new VistaCaso(caso);
    }

    private void mostrarMenuPrincipal() throws NumberFormatException, IOException {
        VistaMenuPrincipal vmp = new VistaMenuPrincipal();
        this.opcionIngresadaPrincipal = vmp.obtenerOpcionDelUsuario();
    }

    private void menuPaises() throws ParserConfigurationException, SAXException, IOException {
        this.paises = juego.obtenerListaDePaises();
        VistaPais vp = new VistaPais(caso.getPaisActual(), paises);
        this.opcionIngresadaPais = vp.obtenerOpcionDelUsuario();

    }

    private void menuLugares() throws IOException {
        VistaLugares vl = new VistaLugares(jugador.lugaresParaEntrar());
        this.opcionIngresadaLugares = vl.obtenerOpcionDelUsuario();
    }

    private void menuOrdenDeArresto() throws IOException {
        VistaOrdenDeArresto voa = new VistaOrdenDeArresto(caso);
        this.opcionIngresadaOrdenDA = voa.obtenerOpcionDelUsuario();
    }

    @Override
    public void update(Observable objObservable, Object actualizaciones) {

        this.actualizaciones = (LinkedList<?>) actualizaciones;

    }
}
