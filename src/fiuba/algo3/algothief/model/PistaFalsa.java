package fiuba.algo3.algothief.model;

import java.util.ArrayList;

public class PistaFalsa extends Pista {

    public PistaFalsa() {
        super(new ArrayList<Lugar>(), null);

        Indicio indiciosIncorrectos = new Indicio("Definitivamente esa persona no paso por aqui",
                "No recuerdo a esa persona, por aqui pasa mucha gente",
                "Podria ser que lo haya visto. Pruebe preguntando en otro lado");
        this.lugares.add(new Lugar("Banco", indiciosIncorrectos));
        this.lugares.add(new Lugar("Aeropuerto", indiciosIncorrectos));
        this.lugares.add(new Lugar("Biblioteca", indiciosIncorrectos));

    }

}
