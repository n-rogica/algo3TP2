package fiuba.algo3.algothief.model;

import static org.mockito.Mockito.mock;

import java.util.LinkedList;
import java.util.List;


import org.junit.Assert;
import org.junit.Test;

import fiuba.algo3.algothief.model.Juego.Dificultad;

import java.util.HashSet;
import java.util.Set;

public class IntegracionTest {

    private final String INDICIO_FACIL = "Indicio Facil";
    private final String INDICIO_NORMAL = "Indicio Normal";
    private final String INDICIO_DIFICIL = "Indicio Dificil";

    private Pais argentina = new Pais(new Coordenadas(new Latitud ("S",34,35,59), new Longitud ("W",58,22,54)), "Argentina");
    private Pais brasil = new Pais(new Coordenadas(new Latitud ("S",15,47,56), new Longitud ("W",47,52,00)), "Brasil");
    
    public IntegracionTest() {
    }

    @Test
    public void testCrearCasoCompleto() {
    	Ladron mockLadron = mock(Ladron.class);
        ObjetoRobado objeto = new ObjetoRobado("Cuadro de Museo", Dificultad.FACIL);
        Caso caso = new Caso(Dificultad.FACIL, argentina, objeto, mockLadron, null);

        Indicio indicio1 = new Indicio("Indicio Facil Biblio", "Indicio Normal Biblio", "Indicio Dificil Biblio");
        Indicio indicio2 = new Indicio("Indicio Facil Aero", "Indicio Normal Aero", "Indicio Dificil Aero");
        Indicio indicio3 = new Indicio("Indicio Facil Bolsa", "Indicio Normal Bolsa", "Indicio Dificil Bolsa");

        Lugar lugar1 = new Lugar("Biblioteca", 0, indicio1);
        Lugar lugar2 = new Lugar("Aeropuerto", 0, indicio2);
        Lugar lugar3 = new Lugar("Bolsa", 0, indicio3);
        List<Lugar> lugares = new LinkedList<Lugar>();
        lugares.add(lugar1);
        lugares.add(lugar2);
        lugares.add(lugar3);

        Pista pista1 = new Pista(lugares, brasil);

        Set<Pista> pistas = new HashSet<Pista>();
        pistas.add(pista1);
        caso.setPistas(pistas);

        Assert.assertEquals(Dificultad.FACIL, caso.getDificultad());
        Assert.assertEquals("Argentina", caso.getPaisActual().getNombre());
        Assert.assertEquals("Cuadro de Museo",caso.getObjetoRobado().getDescripcion());
        Assert.assertEquals("Biblioteca", caso.getPista().getLugares().get(0).getNombre());        
        Assert.assertTrue(caso.getPista().getLugares().get(1).entrarLugar(caso).contains("Indicio Facil Aero"));
        
    }

    @Test
    public void testJugadorEntraAUnLugarEnPaisCorrectoObtienePista() {
        // Se crea un jugador Novato
        Jugador jugador = new Jugador("TimyTimon");

        // Le asigno el caso al jugador y como el caso comienza en Argentina
        // El jugador se trasladara alli para comenzar a investigar
        jugador.asignarCaso(armarCasoFacilConUnaPista());

        String indicioObtenido = jugador.lugaresParaEntrar().get(0).entrarLugar(jugador.getCaso());        
        Assert.assertTrue(indicioObtenido.contains(INDICIO_FACIL));
    }

    @Test
    public void testJugadorEntraAUnLugarEnPaisIncorrectoNoObtienePista() {
        // Se crea un jugador Novato
        Jugador jugador = new Jugador("TimyTimon");

        // Le asigno el caso al jugador y como el caso comienza en Argentina
        // El jugador se trasladara alli para comenzar a investigar
        jugador.asignarCaso(armarCasoFacilConUnaPista());

        // Jugador pasa a pais incorrecto
        jugador.realizarViaje(brasil);

        String indicioObtenido = jugador.lugaresParaEntrar().get(0).entrarLugar(jugador.getCaso());        
        Assert.assertNotSame(INDICIO_FACIL, indicioObtenido);
    }

    private Caso armarCasoFacilConUnaPista() {
        ObjetoRobado objeto = new ObjetoRobado("Cuadro de Museo", Dificultad.FACIL);

        // El caso Facil comienza en Argentina
        Caso caso = new Caso(Dificultad.FACIL, argentina, objeto, mock(Ladron.class), null);

        // Indicio que sera devuelto en el Banco si el jugador se encuentra en Argentina
        Indicio indicioBanco = new Indicio(INDICIO_FACIL, INDICIO_NORMAL, INDICIO_DIFICIL);

        // Preparo la Pista que el jugador recibe en Argentina
        Lugar banco = new Lugar("Banco", indicioBanco);
        List<Lugar> lugares = new LinkedList<Lugar>();
        lugares.add(banco);

        // La pista trata sobre Brasil
        Pista pistaQueLlevaABrasil = new Pista(lugares, brasil);

        // Agrego la pista al caso
        Set<Pista> pistas = new HashSet<Pista>();
        pistas.add(pistaQueLlevaABrasil);
        caso.setPistas(pistas);

        return caso;
    }
}
