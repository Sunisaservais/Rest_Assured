package com.cydeo.day08;

import com.cydeo.utilities.SpartanAuthTestBase;
import io.restassured.http.ContentType;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.is;

public class P01_SpartanAuthTest extends SpartanAuthTestBase {

    @DisplayName("GET /api/v2/spartans as GUEST user --> EXPECT 401")
    @Test
    public void test1() {

        given()
                .accept(ContentType.JSON)
                .when()
                .get("/api/v2/spartans")
                .then()
                .statusCode(401)
                .body("httpStatus", is("UNAUTHORIZED"))
                .log().body();
    }

    @DisplayName("GET /api/v2/spartans as USER --> EXPECT 200")
    @Test
    public void test2() {

        given()
                .log().headers()
                .accept(ContentType.JSON)
                .auth().preemptive().basic("User", "User")
                .when()
                .get("/api/v2/spartans")
                .then()
                .statusCode(200)
                .log().body();

        /*
        without preemptive():

        1. GET /api/v2/spartans (without credentials) --> 401 (with a "WWW-Authenticate" header in the response)
        The server will respond saying "I need authentication"
        2- GET /api/v2/spartans (with the credentials - Authorization header) --> 200 OK

        But if the server doesn't send this authentication challenge correctly, our credentials might not be sent at all and
        the request might fail.

        with preemptive():

        1. GET /api/v2/spartans (with the credentials - Authorization header) --> 200 OK

        In this case, the client will not wait for the error response, it will directly send the authorization header
        in the initial request
         */
    }

    @DisplayName("DELETE /api/v2/spartans/{id} as EDITOR --> EXPECT 403")
    @Test
    public void test3() {

        given()
                .pathParam("id", 100)
                .auth().preemptive().basic("Editor", "Editor")
                .when()
                .delete("/api/v2/spartans/{id}")
                .then()
                .statusCode(403)
                .body("error", is("Forbidden"))
                .log().body();
    }

    @DisplayName("DELETE /api/v2/spartans/{id} as ADMIN --> EXPECT 200")
    @Test
    public void test4() {

        given()
                .pathParam("id", 100)
                .auth().preemptive().basic("Admin", "Admin")
                .when()
                .delete("/api/v2/spartans/{id}")
                .then()
                .statusCode(200)
                .log().body();
    }
}
