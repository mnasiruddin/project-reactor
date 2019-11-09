package com.projectreactor.coding.search.configuration;

import lombok.Data;
import org.springframework.beans.factory.annotation.Value;

import java.io.Serializable;

@Data
public class JwtConfig implements Serializable {

    private static final long serialVersionUID = 1L;

    @Value("${security.jwt.uri:/api/v1/auth/login/**}")
    private String uri;

    @Value("${security.jwt.header:Authorization}")
    private String header;

    @Value("${security.jwt.prefix:Bearer }")
    private String prefix;

    @Value("${security.jwt.expiration:#{3600000}}")
    private int expiration;

    @Value("${security.jwt.secret:secret}")
    private String secret;

    @Value("${security.jwt.identifier:IDENTIFIER}")
    private String identifier;
}
