package org.example.model.entities;

public class Jefe {

    private String nomJefe;
    private double vida;
    private boolean dificultat;
    private int numAtacs;
    private String localitzacio;

    public int getNumAtacs() {
        return numAtacs;
    }

    public Jefe() {
    }

    public Jefe(String nomJefe, double vida, boolean dificultat, int numAtacs, String localitzacio) {
        this.nomJefe = nomJefe;
        this.vida = vida;
        this.dificultat = dificultat;
        this.numAtacs = numAtacs;
        this.localitzacio = localitzacio;
    }



    public String getNomJefe() {
        return nomJefe;
    }


    public double getVida() {
        return vida;
    }


    public boolean isDificultat() {
        return dificultat;
    }

    public String getLocalitzacio() {
        return localitzacio;
    }



    public enum Locacions {
        L1("Acantilados aulladores"), L2("Bocasucia"), L3("Canales reales"), L4("Cañon nublado"),
        L5("Ciudad de las lagrimas"), L6("Coliseo de los insensatos"), L7("Cruces olvidados"), L8("Cuenca antigua"),
        L9("Cumbre de cristal"), L10("Hogar de dioses"), L11("Jardines de la reina"), L12("La colmena"),
        L13("Límites del reino"), L14("Nido profundo"), L15("Palacio blanco"), L16("Páramos fungicos"),
        L17("Sendero verde"), L18("Tierras del reposo");
        private final String nom;

        Locacions(String nom) {
            this.nom = nom;
        }

        @Override
        public String toString() {
            return this.nom;
        }

    }

}