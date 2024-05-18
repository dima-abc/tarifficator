package com.test2.auth.config;

import org.springframework.core.convert.converter.Converter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.util.Assert;

import java.util.Collection;
import java.util.Collections;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

public class KeycloakJwtAuthoritiesConverter implements Converter<Jwt, Collection<GrantedAuthority>> {

    private String authorityPrefix = "";

    public KeycloakJwtAuthoritiesConverter setAuthorityPrefix(String authorityPrefix) {
        Assert.notNull(authorityPrefix, "authorityPrefix cannot be null");
        this.authorityPrefix = authorityPrefix;
        return this;
    }

    @Override
    public Collection<GrantedAuthority> convert(Jwt jwt) {
        Map<String, Object> realmAccess = jwt.getClaim("realm_access");
        if (Objects.isNull(realmAccess)) {
            return Collections.emptySet();
        }

        Object roles = realmAccess.get("roles");
        if (Objects.isNull(roles) || !(roles instanceof Collection<?>)) {
            return Collections.emptySet();
        }

        Collection<?> rolesCollection = (Collection<?>) roles;
        return rolesCollection.stream()
                .filter(String.class::isInstance)
                .map(role -> new SimpleGrantedAuthority(authorityPrefix + role))
                .collect(Collectors.toSet());
    }
}