package org.cms.rest;


import org.cms.rest.config.security.CurrentUserDetails;
import org.cms.rest.config.security.UserTokenHandler;
import org.junit.Before;
import org.junit.Test;

import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.Random;

import static org.junit.Assert.assertEquals;

public class UserTokenHandlerTest {

    UserTokenHandler handler;
    CurrentUserDetails userDetails;

    @Before
    public void setUp() throws InvalidKeyException, NoSuchAlgorithmException {
        handler = new UserTokenHandler("secretKey");
        userDetails = new CurrentUserDetails.Builder()
                .username("aUsername")
                .password("aPassword")
                .build();
    }

    @Test
    public void shouldCreateAndValidateTokenCorrectly() throws UserTokenHandler.TokenProcessingException, UserTokenHandler.InvalidTokenException {
        // When
        String token = handler.create(userDetails);
        CurrentUserDetails parsedUser = handler.parse(token, CurrentUserDetails.class);

        // Then
        assertEquals(parsedUser.getUsername(), userDetails.getUsername());
        assertEquals(parsedUser.getPassword(), userDetails.getPassword());
    }

    @Test(expected = UserTokenHandler.InvalidTokenException.class)
    public void shouldCreateAndThrowInvalidTokenWhenTokenTampered() throws UserTokenHandler.TokenProcessingException, UserTokenHandler.InvalidTokenException {
        // Given
        String token = handler.create(userDetails);
        String tamperedToken = tamper(token);
        assertEquals(token.length(), tamperedToken.length());

        // Expect
        handler.parse(tamperedToken, CurrentUserDetails.class);
    }

    private String tamper(String token) {
        int numCharsToChange = 10;
        int start = 2;
        int end = start + numCharsToChange;
        return token.substring(0, start) + randomStringOf(numCharsToChange) + token.substring(end, token.length());
    }

    private String randomStringOf(int numCharacters) {
        char[] allowedChars = new char[] {'a', 'b', 'c','g', 'h', 'K', 'L'};
        Random random = new Random();
        StringBuilder builder = new StringBuilder();
        for (int i = 0; i < numCharacters; i++) {
            builder.append(allowedChars[random.nextInt(7)]);
        }
        return builder.toString();
    }

}
