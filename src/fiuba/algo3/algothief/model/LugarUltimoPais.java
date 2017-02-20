package fiuba.algo3.algothief.model;

import java.util.List;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;

public class LugarUltimoPais extends Lugar {

    private final Boolean estaLadron;

    public LugarUltimoPais(String nombre, Boolean estaLadron) {
        super(nombre, 0, null);

        this.estaLadron = estaLadron;
    }

    @Override
    public Element serializar(Document doc) {
        Element elementoLugarUltimoPais = doc.createElement("LugarUltimoPais");
        elementoLugarUltimoPais.setAttribute("nombre", this.nombre);
        elementoLugarUltimoPais.setAttribute("estaLadron", estaLadron.toString());
        return elementoLugarUltimoPais;
    }

    public static LugarUltimoPais hidratar(Node nodoLugar) {
        Element elementoLugarUltimoPais = (Element) nodoLugar;
        String nombre = elementoLugarUltimoPais.getAttribute("nombre");
        boolean estaLadron = Boolean.parseBoolean(elementoLugarUltimoPais.getAttribute("estaLadron"));
        LugarUltimoPais nuevoLugarUltimoPais = new LugarUltimoPais(nombre, estaLadron);
        return nuevoLugarUltimoPais;
    }

    public int getVisitas() {
        return visitas;
    }

    @Override
    public String entrarLugar(Caso caso) {
        int cantidadDeLugaresUltimoPaisVisitados = 0;
        visitas++;
        List<Lugar> lugares = caso.getPista().getLugares();

        caso.descontarHoras(visitas);

        if (estaLadron) {
            if (caso.ordenDeArrestoEmitida() && (caso.ordenEmitidaContraElLadronDelCaso())) {
                caso.setResuelto(caso.isPendiente());
                return "Encontraste al ladron y lo has capturado! Felicitaciones!";
            }

            caso.setFallido(caso.isPendiente());
            return "Lamentablemente no has podido atrapar al ladron";
        }

        // Como aqui no esta el ladron
        // Chequeo cuantos lugares del ultimo pais fueron visitados antes
        // Para ver que le corresponde recibir al jugador
        for (LugarUltimoPais lugar : (List<LugarUltimoPais>) (List<?>) lugares) {
            if (lugar.getVisitas() != 0) {
                cantidadDeLugaresUltimoPaisVisitados++;
            }
        }

        switch (cantidadDeLugaresUltimoPaisVisitados) {
            case 1:
                caso.jugadorEsAcuchillado();
                return "Vas por buen camino! Cuidado con los cuchillos!";
            case 2:
                caso.jugadorEsHeridoDeBala();
                return "Te estas acercando demasiado! Cuidado con los disparos!";
            default:
                return "si llegaste hasta aca algo anduvo mal";
        }
    }

}
