package com.test2.auth.config;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationConverter;

import java.util.Collection;

public class KeycloakJwtAuthenticationConverter extends JwtAuthenticationConverter {

    private String clientId;
    private String authorityPrefix;

    public void setClientId(String clientId) {
        this.clientId = clientId;
    }

    public void setAuthorityPrefix(String authorityPrefix) {
        this.authorityPrefix = authorityPrefix;
    }

    public KeycloakJwtAuthenticationConverter() {
        KeycloakJwtAuthoritiesConverter authoritiesConverter = new KeycloakJwtAuthoritiesConverter();
        setJwtGrantedAuthoritiesConverter(authoritiesConverter);
    }

    protected Collection<GrantedAuthority> extractAuthorities(Jwt jwt) {
        KeycloakJwtAuthoritiesConverter converter = new KeycloakJwtAuthoritiesConverter();
        converter.setAuthorityPrefix(authorityPrefix);
        return converter.convert(jwt);
    }
}
