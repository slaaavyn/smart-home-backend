package tk.slaaavyn.slavikhomebackend.security;

public class SecurityConstants {
    public static final String TOKEN_PREFIX = "Bearer_";
    public static final String HEADER_STRING = "Authorization";
    public static final long TOKEN_VALIDITY_TIME = 86400000L; // 24H
    public static final long REFRESH_TOKEN_VALIDITY_TIME = 604800000L; // 7D
}