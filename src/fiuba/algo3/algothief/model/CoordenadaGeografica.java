package fiuba.algo3.algothief.model;

public abstract class CoordenadaGeografica {

    private final double circunferenciaDeLaTierra = 40000; // kilometros	
    private double grados;
    private double minutos;
    private double segundos;

    public CoordenadaGeografica(double grados, double minutos, double segundos) {

        this.grados = grados;
        this.minutos = minutos;
        this.segundos = segundos;
    }    

    public double convertirAKilometros(double coordenadaEnGrados) {

        return (Math.round(coordenadaEnGrados * circunferenciaDeLaTierra) / 360);
    }

    public double exPresarEnGrados() {

        double enGrados = grados + (minutos / 60) + (segundos / (3600));
        return enGrados;
    }

    public double getGrados() {
        return grados;
    }

    public double getMinutos() {
        return minutos;
    }

    public double getSegundos() {
        return segundos;
    }

}
