package it.unibo.adozione_animali.model.api;

/**
 * Represents the Persona table in the database.
 */
public interface Persona {

    void insertPersona(String CF, String nome, String cognome, String email, String password, String telefono);

    void updateEmail(String CF, String email);

    void updateTelefono(String CF, String telefono);

    void updatePassword(String CF, String password);

    void deletePersona(String CF);
}
