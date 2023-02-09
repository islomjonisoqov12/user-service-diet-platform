package uz.dam.userservice.config;


import uz.dam.userservice.security.AuthoritiesConstants;

/**
 * Application constants.
 */
public final class Constants {

    //Regex for acceptable logins

    public static final int PASSWORD_MIN_LENGTH = 5;

    public static final int PASSWORD_MAX_LENGTH = 100;

    public static final String SYSTEM = "system";

//    public static final String HAS_ROLE_ADMIN = "hasAuthority('" + AuthoritiesConstants.ADMIN + "')";
//    public static final String HAS_ROLE_ADMIN_OR_TEACHER = "hasAnyAuthority('" + AuthoritiesConstants.ADMIN + "', '"+ AuthoritiesConstants.TEACHER + "')";
//    public static final String HAS_ROLE_TEACHER = "hasAuthority('" + AuthoritiesConstants.TEACHER + "')";

//    public static final String MAIN_BADGE_PHOTO = "./src/main/resources/badge.pdf";

}
