package org.example.model.exceptions;

import java.util.HashMap;
import java.util.Map;

public class DAOException extends Exception {

    private static final Map<Integer, String> missatges = new HashMap<>();

    //num i retorna string, el map
    static {
        missatges.put(0, "Error al connectar a la BD!!");
        missatges.put(1, "Restricció d'integritat violada - clau primària duplicada");
        missatges.put(2, "El nom ha de complir certa estructura. exemple: jefe, titol");
        missatges.put(3, "La vida i el numero d'atacs han de ser un nombre positiu");
        missatges.put(4, "El nom del jefe ha de ser unic");
        missatges.put(5, "Falta omplir alguna dada");
        missatges.put(6, "Per a modificar un jefe has de cambiar alguna dada");
        missatges.put(7, "Per a modificar has de seleccionar un jefe");
        missatges.put(8, "Per a borrar has de seleccionar un jefe");
        missatges.put(904, "Nom de columna no vàlid");
        missatges.put(936, "Falta expressió en l'ordre SQL");
        missatges.put(942, "La taula o la vista no existeix");
        missatges.put(1000, "S'ha superat el nombre màxim de cursors oberts");
        missatges.put(1400, "Inserció de valor nul en una columna que no permet nuls");
        missatges.put(1403, "No s'ha trobat cap dada");
        missatges.put(1722, "Ha fallat la conversió d'una cadena de caràcters a un número");
        missatges.put(1747, "El nombre de columnes de la vista no coincideix amb el nombre de columnes de les taules subjacents");
        missatges.put(4091, "Modificació d'un procediment o funció en execució actualment");
        missatges.put(6502, "Error numèric o de valor durant l'execució del programa");
        missatges.put(12154, "No s'ha pogut resoldre el nom del servei de la base de dades Oracle o l'identificador de connexió");
        missatges.put(2292, "S'ha violat la restricció d'integritat -  s'ha trobat un registre fill");
    }

    //atribut
    private final int tipo;

    //constructor al q pasem tipo
    public DAOException(int tipo) {
        this.tipo = tipo;
    }

    //sobreescrivim el get message
    @Override
    public String getMessage() {
        return missatges.get(this.tipo); //el missatge del tipo
    }

    public int getTipo() {
        return tipo;
    }
}
