package com.bta.web;

import javax.crypto.spec.SecretKeySpec;

import javax.ws.rs.container.ContainerRequestContext;
import javax.ws.rs.core.Cookie;
import javax.xml.bind.DatatypeConverter;
import java.security.Key;

import io.jsonwebtoken.*;

import java.util.Date;
import java.util.Map;


/**
 * This class manages all the jwt methods.
 */
public class JWTManager {

    // The secret key. This should be in a property file NOT under source
    // control and not hard coded in real life. We're putting it here for
    // simplicity.
    private static String SECRET_KEY = "oeRaYY7Wo24sDqKSX3IM9ASGndGPmfTd9jo1QTy4b7P9Ze5_9hKolVX8xNrQDcNRfVEdTZNOuOyqEGhXEbdJI-ZQ19k_o9MI0y3eZN2lp9jow55FfXMiINEdt1XR85VipRLSOkT6kSpzs2x-jbLDiz9iFVzkd81YKxMgPA7VfZeQUm4n-mOmnWMaVX30zGFU4L3oPBctYKkl4dYfqYWqRNfrgPJVi5DGFjywgxx0ASEiJHtV72paI3fDR2XwlSkyhhmY-ICjCRmsJN4fX1pdoL8a18-aQrvyu4j0Os6dVPYIoPvvY0SAZtWYKHfM15g7A3HD4cVREf9cUsprCRK93w";


    /**
     * Sample method to construct a JWT.
     *
     * @param id        of the token.
     * @param issuer    name of the issuer.
     * @param subject   of the token.
     * @param ttlMillis validity duration is ms.
     * @return the jwt token as a string.
     */
    public static String createJWT(String id, String issuer, String subject, long ttlMillis) {

        //The JWT signature algorithm we will be using to sign the token
        SignatureAlgorithm signatureAlgorithm = SignatureAlgorithm.HS256;

        long nowMillis = System.currentTimeMillis();
        Date now = new Date(nowMillis);

        //We will sign our JWT with our ApiKey secret
        byte[] apiKeySecretBytes = DatatypeConverter.parseBase64Binary(SECRET_KEY);
        Key signingKey = new SecretKeySpec(apiKeySecretBytes, signatureAlgorithm.getJcaName());

        //Let's set the JWT Claims
        JwtBuilder builder = Jwts.builder().setId(id)
                .setIssuedAt(now)
                .setSubject(subject)
                .setIssuer(issuer)
                .signWith(signingKey, signatureAlgorithm);

        //if it has been specified, let's add the expiration
        if (ttlMillis >= 0) {
            long expMillis = nowMillis + ttlMillis;
            Date exp = new Date(expMillis);
            builder.setExpiration(exp);
        }

        //Builds the JWT and serializes it to a compact, URL-safe string
        return builder.compact();
    }


    /**
     * A method for checking the validity of a jwt token.
     *
     * @param jwt token to determine the validity.
     * @return true if the token id valid, false otherwise.
     */
    public static boolean isJWTValid(String jwt) {
        //the jws object to store the claims

        try {
            //This line will throw an exception if it is not a signed JWS (as expected)
            Jwts.parserBuilder()
                    .setSigningKey(DatatypeConverter.parseBase64Binary(SECRET_KEY)).build()
                    .parseClaimsJws(jwt);
            //can use the claims for further checking, for basic implementation its ok
            return true;
        } catch (JwtException ex) {
            //Can not trust the jwt
            System.out.println("Invalid JWT Token");
        }
        return false;
    }


    /**
     * A method of returning the cookie containing the jwt.
     *
     * @param req the container request context.
     * @return the cookie with the jwt.
     */
    public static Cookie getTokenCookie(ContainerRequestContext req) {
        Map<String, Cookie> cookies = req.getCookies();

        return cookies.get("token");
    }


    /**
     * A emthod fo checking the jwt token inside a cookie.
     *
     * @param req the container request context.
     * @return true if the token is valid, false otherwise.
     */
    public static boolean checkTokenCookie(ContainerRequestContext req) {
        Cookie cookie = getTokenCookie(req);
        return cookie != null && isJWTValid(cookie.getValue());
    }
}
