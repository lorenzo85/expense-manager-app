package org.cms.rest;


import com.jayway.restassured.RestAssured;
import com.jayway.restassured.http.ContentType;
import com.jayway.restassured.response.Header;
import com.jayway.restassured.response.Response;
import org.cms.service.user.UserDto;
import org.cms.service.user.UserService;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.IntegrationTest;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;

import static com.jayway.restassured.RestAssured.given;
import static com.jayway.restassured.RestAssured.when;
import static com.jayway.restassured.http.ContentType.JSON;
import static org.apache.http.HttpStatus.*;
import static org.cms.service.user.Role.ROLE_ADMIN;
import static org.cms.service.user.Role.ROLE_USER;
import static org.hamcrest.Matchers.*;


@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = Application.class)
@WebAppConfiguration
@TestPropertySource("classpath:/test.properties")
@IntegrationTest("server.port:0")
public class SecurityTest {

    private static final String USERNAME = "aUsername";
    private static final String PASSWORD = "aPassword";

    @Value("${token.header.name}")
    private String tokenHeaderName;
    @Value("${local.server.port}")
    private int port;

    @Autowired
    private UserService userService;

    private UserDto user;

    @Before
    public void setUp() {
        RestAssured.port = port;
        user = new UserDto.Builder(USERNAME)
                .password(encrypt(PASSWORD))
                .accountEnabled(true)
                .accountExpired(false)
                .accountLocked(false)
                .build();
        user.getRoles().add(ROLE_ADMIN);
        user.getRoles().add(ROLE_USER);
        user = userService.save(user);
    }

    @After
    public void tearDown() {
        userService.delete(user.getId());
    }

    @Test
    public void testShouldSetTokenOnLogin() {
        login()
                .then()
                .log().all()
                .statusCode(SC_OK)
                .header(tokenHeaderName, not(isEmptyOrNullString()));
    }

    @Test
    public void testShouldAnswerUnauthorizedWhenInvalidUsername() {
        LoginRequestBody loginRequest = new LoginRequestBody();
        loginRequest.username = "anUnexistentUsername";
        loginRequest.password = "anUnexistentPassword";

        login(loginRequest)
                .then()
                .log().all()
                .statusCode(SC_UNAUTHORIZED);
    }

    @Test
    public void testShouldAnswerUnauthorizedWhenWrongPassword() {
        LoginRequestBody loginRequest = new LoginRequestBody();
        loginRequest.username = USERNAME;
        loginRequest.password = "aWrongPassword!";

        login(loginRequest)
                .then()
                .log()
                .all()
                .statusCode(SC_UNAUTHORIZED);
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
        String token = token();

        given()
                .header(new Header(tokenHeaderName, token))
                .when()
                .get("/yards")
                .then()
                .log().all()
                .statusCode(SC_OK);
    }

    @Test
    public void testShouldReturnTheCurrentLoggedUser() {
        String token = token();

        given()
                .header(new Header(tokenHeaderName, token))
                .when()
                .get("/auth/user")
                .then()
                .log().all()
                .statusCode(SC_OK)
                .assertThat()
                .contentType(JSON)
                .body("username", equalTo(USERNAME))
                .body("roles", hasItems(ROLE_ADMIN.toString(), ROLE_USER.toString()));
    }

    @Test
    public void testShouldReturnAnonymousUser() {
        given()
                .when()
                .get("/auth/user")
                .then()
                .log().all()
                .statusCode(SC_OK)
                .assertThat()
                .contentType(JSON)
                .body("username", equalTo("anonymousUser"));
    }

    private Response login() {
        LoginRequestBody loginRequestBody = new LoginRequestBody();
        loginRequestBody.username = USERNAME;
        loginRequestBody.password = PASSWORD;
        return login(loginRequestBody);
    }

    private String token() {
        Response response = login();
        return response.getHeader(tokenHeaderName);
    }

    private Response login(LoginRequestBody loginRequestBody) {
        return given()
                .log().all()
                .body(loginRequestBody)
                .contentType(ContentType.JSON)
                .when()
                .post("/auth/login");
    }

    private String encrypt(String value) {
        return new BCryptPasswordEncoder().encode(value);
    }

    static class LoginRequestBody {
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
