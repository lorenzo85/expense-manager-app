package org.cms.rest.config.security;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.tomcat.util.codec.binary.Base64;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;
import java.io.IOException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.Arrays;

import static java.lang.String.format;
import static org.apache.commons.codec.binary.Base64.decodeBase64;


public class UserTokenHandler {

    private static final String HMAC_ALGO = "HmacSHA256";
    private static final String SEPARATOR_TEMPLATE = "%s.%s";
    private final Mac sha256Hmac;

    public UserTokenHandler(String secret) throws NoSuchAlgorithmException, InvalidKeyException {
        SecretKeySpec secretKeySpec = new SecretKeySpec(secret.getBytes(), HMAC_ALGO);
        sha256Hmac = Mac.getInstance(HMAC_ALGO);
        sha256Hmac.init(secretKeySpec);
    }

    public String create(Object payload) throws TokenProcessingException {
        String encodedHeader = encode(toJson(new Header()));
        String encodedPayload = encode(toJson(payload));
        String encodedHeaderAndPayload = format(SEPARATOR_TEMPLATE, encodedHeader, encodedPayload);
        String encodedSignature = encode(sign(encodedHeaderAndPayload));
        return format(SEPARATOR_TEMPLATE, encodedHeaderAndPayload, encodedSignature);
    }

    public <T> T parse(String token, Class<T> clazz) throws InvalidTokenException, TokenProcessingException {
        final String[] parts = token.split("\\.");
        String tokenHeader = parts[0];
        String tokenPayload = parts[1];
        String tokenHeaderAndPayload = format(SEPARATOR_TEMPLATE, tokenHeader, tokenPayload);

        byte[] tokenSignature = decode(parts[2]);
        byte[] signedTokenHeaderAndPayload = sign(tokenHeaderAndPayload);
        boolean validSignature = Arrays.equals(tokenSignature, signedTokenHeaderAndPayload);
        if (!validSignature) {
            throw new InvalidTokenException();
        }

        return fromJson(decode(parts[1]), clazz);
    }

    private synchronized byte[] sign(String stringToSign) {
        return sha256Hmac.doFinal(stringToSign.getBytes());
    }

    public <T> T fromJson(byte[] jsonAsBytes, Class<T> clazz) throws TokenProcessingException {
        try {
            return new ObjectMapper().readValue(jsonAsBytes, clazz);
        } catch (IOException e) {
            throw new TokenProcessingException(e);
        }

    }

    private byte[] decode(String base64String) {
        return decodeBase64(base64String.getBytes());
    }

    private String encode(byte[] bytes) {
        return Base64.encodeBase64String(bytes);
    }

    private byte[] toJson(Object object) throws TokenProcessingException {
        try {
            return new ObjectMapper()
                    .writeValueAsString(object)
                    .getBytes();
        } catch (JsonProcessingException e) {
            throw new TokenProcessingException(e);
        }
    }

    static class Header {
        String typ = "JWT";
        String alg = "HS256";

        public String getTyp() {
            return typ;
        }

        public String getAlg() {
            return alg;
        }
    }

    public class TokenProcessingException extends Exception {
        public TokenProcessingException(Throwable e) {
            super("An error has occurred while processing the token", e);
        }
    }

    public class InvalidTokenException extends Exception {
        public InvalidTokenException() {
            super("Token is tampered");
        }
    }
}