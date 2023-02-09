package uz.dam.userservice.security;

/**
 * Constants for Spring Security authorities.
 */
public final class AuthoritiesConstants {

    // platforma uchun super admin
    public static final String SUPER_ADMIN = "ROLE_SUPER_ADMIN";
    // Role platformadagi campany egasi uchun beriladi
    public static final String OWNER = "ROLE_OWNER";

    // Admin owner ni qolida ishlaydigan ishchi/
    public static final String ADMIN = "ROLE_ADMIN";

    // Saytda mehmon
    public static final String ANONYMOUS = "ROLE_ANONYMOUS";

    private AuthoritiesConstants() {
    }
}
