package tk.slaaavyn.slavikserver.security.jwt;

import javax.naming.AuthenticationException;

public class JwtAuthenticationException extends AuthenticationException {
    public JwtAuthenticationException(String e) {
        super(e);
    }
}