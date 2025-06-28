package com.cydeo.day08;

import com.cydeo.utilities.BookItTestBase;
import com.cydeo.utilities.BookItUtilities;
import io.restassured.http.ContentType;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.given;

public class P02_BookItTest extends BookItTestBase {

    String email = "lfinnisz@yolasite.com"; // in configuration.properties file you would have TEAM_LEADER, TEAM_MEMBER, TEACHER
    String password = "lissiefinnis";
    String accessToken = BookItUtilities.getToken(email, password);

    @DisplayName("GET /api/campuses")
    @Test
    public void test1() {

        given()
                .log().headers()
                .accept(ContentType.JSON)
                .header("Authorization", accessToken)
                .when()
                .get("/api/campuses")
                .then()
                .statusCode(200)
                .log().body();
    }

    //create a new utility class that will generate the token based on the given email and password

    @DisplayName("GET /api/users/me")
    @Test
    public void test2() {

        given()
                .log().headers()
                .accept(ContentType.JSON)
                .header("Authorization",  BookItUtilities.getToken(email, password))
                .when()
                .get("/api/users/me")
                .then()
                .statusCode(200)
                .log().body();
    }

    @DisplayName("GET /api/users/me")
    @Test
    public void test3() {

        given()
                .log().headers()
                .accept(ContentType.JSON)
                .auth().oauth2(accessToken)
                .when()
                .get("/api/users/me")
                .then()
                .statusCode(200)
                .log().body();
    }
}
