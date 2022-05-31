package com.north.poc.service;

import com.north.poc.utility.RSACryptoUtil;
import com.north.poc.utility.SecurityConstants;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;
import java.security.PrivateKey;
import java.security.spec.InvalidKeySpecException;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Collection;
import java.util.Date;

@Service
@AllArgsConstructor
public class JwtService {

    final RSACryptoUtil rsaCryptoUtil;

    public String signingByJWT(String code, long userId, String username, Collection<String> rolesName, long minuteTimeOut) {
        String ret = Jwts.builder()
                .setHeaderParam("typ", "JWT")
                .claim("I_USER", userId)
                .claim("N_LOGIN", username)
                .claim("ROLES", rolesName)
                .claim("CODE", code)
                .claim("TIMESTAMP", new Date())
                .setIssuer("ABC")
                .setIssuedAt(new Date())
                .setExpiration(Date.from(LocalDateTime.now().plusMinutes(minuteTimeOut).atZone(ZoneId.systemDefault()).toInstant()))
                .compact();
        return ret;
    }
}
