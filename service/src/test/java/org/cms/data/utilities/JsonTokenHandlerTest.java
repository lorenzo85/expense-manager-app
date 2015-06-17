package org.cms.data.utilities;

import org.junit.Before;
import org.junit.Test;

import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.Random;

import static org.junit.Assert.assertEquals;

public class JsonTokenHandlerTest {

    JsonTokenHandler handler;
    UserDto userDto;

    @Before
    public void setUp() throws InvalidKeyException, NoSuchAlgorithmException {
        handler = new JsonTokenHandler("secretKey");
        userDto = new UserDto();
        userDto.username = "aUsername";
        userDto.password = "aPassword";
    }

    @Test
    public void shouldCreateAndValidateTokenCorrectly() throws JsonTokenHandler.TokenProcessingException, JsonTokenHandler.InvalidTokenException {
        // Given
        UserDto currentUser = userDto;

        // When
        String token = handler.create(currentUser);
        UserDto parsedUserDto = handler.parse(token, UserDto.class);

        // Then
        assertEquals(currentUser.username, parsedUserDto.username);
        assertEquals(currentUser.password, parsedUserDto.password);
    }

    @Test(expected = JsonTokenHandler.InvalidTokenException.class)
    public void shouldCreateAndThrowInvalidTokenWhenTokenTampered() throws JsonTokenHandler.TokenProcessingException, JsonTokenHandler.InvalidTokenException {
        // Given
        UserDto currentUser = userDto;
        String token = handler.create(currentUser);
        String tamperedToken = tamper(token);
        assertEquals(token.length(), tamperedToken.length());

        // Expect
        handler.parse(tamperedToken, UserDto.class);
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

    private static class UserDto {
        String username;
        String password;

        public UserDto() {
        }

        public String getUsername() {
            return username;
        }

        public void setUsername(String username) {
            this.username = username;
        }

        public String getPassword() {
            return password;
        }

        public void setPassword(String password) {
            this.password = password;
        }
    }
}
