package org.cms.data.app.security;


import com.jayway.restassured.RestAssured;
import com.jayway.restassured.response.Response;
import org.cms.data.app.Application;
import org.cms.data.domain.User;
import org.cms.data.repository.UserRepository;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.IntegrationTest;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;

import static com.jayway.restassured.RestAssured.given;
import static com.jayway.restassured.RestAssured.when;
import static org.apache.http.HttpStatus.SC_FORBIDDEN;
import static org.apache.http.HttpStatus.SC_OK;
import static org.hamcrest.Matchers.isEmptyOrNullString;
import static org.hamcrest.Matchers.not;


@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = Application.class)
@WebAppConfiguration
@IntegrationTest("server.port:0")
public class SecurityTest {

    private static final String USERNAME = "aUsername";
    private static final String PASSWORD = "aPassword";

    @Autowired
    private UserRepository userRepository;

    @Value("${token.header.name}")
    private String header;
    @Value("${local.server.port}")
    private int port;

    @Before
    public void setUp() {
        RestAssured.port = port;
        User user = new User(USERNAME, encrypt(PASSWORD));
        user.setAccountEnabled(true);
        user.setAccountExpired(false);
        user.setAccountLocked(false);
        userRepository.save(user);
    }

    @After
    public void tearDown() {
        userRepository.deleteAll();
    }

    @Test
    public void testShouldSetTokenOnLogin() {
        login()
                .then()
                .log().all()
                .statusCode(SC_OK)
                .header(header, not(isEmptyOrNullString()));

    }

    @Test
    public void testShouldAnswerForbiddenForProtectedResource() {
        when()
                .get("/yards")
                .then()
                .log().all()
                .statusCode(SC_FORBIDDEN);
    }

    @Test
    public void testShouldReturnOkForProtectedResourceWhenTokenSent() {

    }

    private Response login() {
        UserDto userDto = new UserDto();
        userDto.username = USERNAME;
        userDto.password = PASSWORD;
        return login(userDto);
    }

    private Response login(UserDto userDto) {
        return given()
                .log().all()
                .body(userDto)
                .when()
                .post("/login");
    }

    private String encrypt(String value) {
        return new BCryptPasswordEncoder().encode(value);
    }

    static class UserDto {
        String username;
        String password;

        public String getUsername() {
            return username;
        }

        public String getPassword() {
            return password;
        }
    }
}
