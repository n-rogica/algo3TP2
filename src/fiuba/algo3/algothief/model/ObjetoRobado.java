package fiuba.algo3.algothief.model;

import fiuba.algo3.algothief.model.Juego.Dificultad;
import java.util.Random;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;

public class ObjetoRobado {

    public final int minimoValorObjetoRobado = 200;
    public final int maxValorObjetoFacil = 500;
    public final int maxValorObjetoNormal = 1000;
    public final int maxValorObjetoDificil = 3000;

    private final String descripcion;
    private final Dificultad dificultadObjeto;

    public ObjetoRobado(String descripcion, Dificultad dificultad) {

        this.descripcion = descripcion;
        this.dificultadObjeto = dificultad;

    }

    public String getDescripcion() {
        return descripcion;
    }

    public Dificultad getDificultadObjeto() {
        return dificultadObjeto;
    }
    
    private int generarValorObjetoRobado(int minValor, int maxValor) {

        Random numeroAleatorio = new Random();
        int valor = numeroAleatorio.nextInt(maxValor - minValor); //genera entero entre 0 y la diferencia
        valor = valor + minValor; //le sumo el minimo para que vuelva estar en el rango

        return valor;
    }

    public int getValor() {
        switch (this.dificultadObjeto) {
            case FACIL: {
                return generarValorObjetoRobado(minimoValorObjetoRobado, maxValorObjetoFacil);
            }
            case NORMAL: {
                return generarValorObjetoRobado(maxValorObjetoFacil, maxValorObjetoNormal);
            }
            case DIFICIL: {
                return generarValorObjetoRobado(maxValorObjetoNormal, maxValorObjetoDificil);
            }

            default:
                return minimoValorObjetoRobado;
        }
    }

    public Element serializar(Document doc) {
        Element elementoObjetoRobado = doc.createElement("ObjetoRobado");
        elementoObjetoRobado.setAttribute("Descripcion", descripcion);
        elementoObjetoRobado.setAttribute("Dificultad", String.valueOf(dificultadObjeto));
        return elementoObjetoRobado;
    }

    public static ObjetoRobado hidratar(Node nodoObjetoRobado) {
        Element elementoObjRob = (Element) nodoObjetoRobado;
        ObjetoRobado objRob = new ObjetoRobado(elementoObjRob.getAttribute("Descripcion"), Juego.Dificultad.valueOf(elementoObjRob.getAttribute("Dificultad")));
        return objRob;
    }

}
