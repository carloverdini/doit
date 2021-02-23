package it.unicam.doit.security;

import it.unicam.doit.model.Utente;
import it.unicam.doit.security.dto.JwtUser;

public final class JwtUserFactory {

    private JwtUserFactory() {
    }

    public static JwtUser create(Utente utente) {
        return new JwtUser(
                utente.getUsername(),
                utente.getPassword(),
                utente.getEnabled()
        );
    }
}