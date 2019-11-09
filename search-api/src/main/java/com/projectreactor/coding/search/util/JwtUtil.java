package com.projectreactor.coding.search.util;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.Claim;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.projectreactor.coding.search.account.domain.Account;
import com.projectreactor.coding.search.configuration.JwtConfig;
import lombok.NonNull;
import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

import java.io.Serializable;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.util.Date;
import java.util.Map;
import java.util.UUID;

@Component
public class JwtUtil implements Serializable {

  private static final long serialVersionUID = 1L;

  private JwtConfig jwtConfig;

  @Autowired
  public void setJwtConfig(JwtConfig jwtConfig) {
    this.jwtConfig = jwtConfig;
  }

  public JwtConfig getJwtConfig() {
    return jwtConfig;
  }

  @Bean
  JwtConfig jwtConfig() {
    return new JwtConfig();
  }

  public Map<String, Claim> getAllClaimsFromToken(String token) {
    return getDecodedJwt(token).getClaims();
  }

  public String getFromHeader(@NonNull Map<String, String> headers) {
    return headers.getOrDefault(jwtConfig.getHeader(), "No token found")
        .replace(jwtConfig.getPrefix(), "");
  }

  public String getUsernameFromToken(String token) {
      return getDecodedJwt(token).getSubject();
  }

  public DecodedJWT getDecodedJwt(String token) {
    return JWT.require(getAlgorithm())
        .build()
        .verify(token.replace(jwtConfig.getPrefix(), ""));
  }

  public Date getExpirationDateFromToken(String token) {
    return getDecodedJwt(token).getExpiresAt();
  }

  private Boolean isTokenExpired(String token) {
    final Date expiration = getExpirationDateFromToken(token);
    return expiration.before(new Date());
  }

  public String generateTokenWithPrefix(Account account) {
    return jwtConfig.getPrefix() + generateToken(account);
  }

  private String generateToken(Account account) {
    return JWT.create().withArrayClaim("role",
        account.getRoles().stream().map(Enum::name).toArray(String[]::new))
        .withSubject(account.getUsername())
        .withExpiresAt(new Date(System.currentTimeMillis() + jwtConfig.getExpiration()))
        .withClaim(jwtConfig.getIdentifier(), uuid())
        .sign(getAlgorithm());
  }

  private Algorithm getAlgorithm() {
    return Algorithm.HMAC256(jwtConfig.getSecret());
  }

  public Boolean validateToken(String token) {
    return !isTokenExpired(token);
  }

  @SneakyThrows
  public String uuid() {
    MessageDigest salt = MessageDigest.getInstance("SHA-256");
    salt.update(UUID.randomUUID().toString().getBytes(StandardCharsets.UTF_8));
    return bytesToHex(salt.digest());
  }

  private static String bytesToHex(byte[] hashInBytes) {

    StringBuilder sb = new StringBuilder();
    for (byte b : hashInBytes) {
      sb.append(String.format("%02x", b));
    }
    return sb.toString();

  }

}
