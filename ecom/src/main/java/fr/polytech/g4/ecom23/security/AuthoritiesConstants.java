package fr.polytech.g4.ecom23.security;

/**
 * Constants for Spring Security authorities.
 */
public final class AuthoritiesConstants {

    public static final String ADMIN = "ROLE_ADMIN";

    public static final String USER = "ROLE_USER";

    public static final String ANONYMOUS = "ROLE_ANONYMOUS";

    public static final String MEDECIN = "ROLE_MEDECIN";

    public static final String SOIGNANT = "ROLE_SOIGNANT";

    private AuthoritiesConstants() {}
}
