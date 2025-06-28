package com.cydeo.day03;

import com.cydeo.utilities.SpartanTestBase;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import org.apache.http.HttpStatus;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.Map;

import static io.restassured.RestAssured.given;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class P01_SpartanWithParameters extends SpartanTestBase {

    /*
     * Given Accept type is Json
     * And "id" parameter value is 24
     * When user sends GET request to /api/v2/spartans/{id}
     * Then response status code should be 200
     * And response Content-Type should be application/json
     * And "Julio" should be in response payload(body)
     */

    @DisplayName("GET Spartan /api/v2/spartans/{id} with valid ID")
    @Test
    public void test1() {

        Response response = given()
                .accept(ContentType.JSON)
                .and()
                .pathParam("id", 24)
                .when()
                .get("/api/v2/spartans/{id}"); //request will be sent to /api/v2/spartans/24

        response.prettyPrint();

        //Then response status code should be 200
        assertEquals(200, response.statusCode());

        //And response Content-Type should be application/json
        assertEquals("application/json", response.contentType());

        //And "Julio" should be in response payload(body)
        assertTrue(response.body().asString().contains("Julio"));

    }

    /*
     * Given Accept type is Json
     * And "id" parameter value is 500
     * When user sends GET request to /api/v2/spartans/{id}
     * Then response status code should be 404
     * And response Content-Type should be application/json
     * And "NOT_FOUND" message should be in response payload
     */

    @DisplayName("GET Spartan /api/v2/spartans/{id} with invalid ID")
    @Test
    public void test2() {

        Response response = given()
                .accept(ContentType.JSON)
                .and()
                .pathParam("id", 500)
                .when()
                .get("/api/v2/spartans/{id}");

        response.prettyPrint();

        //Then response status code should be 404
        assertEquals(404, response.statusCode());
        //same as the above line
        assertEquals(HttpStatus.SC_NOT_FOUND, response.statusCode());

        //And response Content-Type should be application/json
        assertEquals("application/json", response.contentType());

        //And "NOT_FOUND" message should be in response payload
        assertTrue(response.body().asString().contains("NOT_FOUND"));

    }

    /*
     * Given Accept type is Json
     * And query parameter values are:
     *     gender|Female
     *     nameContains|e
     * When user sends GET request to /api/v2/spartans/search
     * Then response status code should be 200
     * And response Content-Type should be application/json
     * And "Female" should be in response payload
     * And "Janette" should be in response payload
     */

    @DisplayName("GET Request to /api/v2/spartans/search with Query Params")
    @Test
    public void test3() {

        Response response = given()
                .accept(ContentType.JSON)
                .and()
                .queryParam("gender", "Female")
                .and()
                .queryParam("nameContains", "e")
                .get("/api/v2/spartans/search");// /api/v2/spartans/search?gender=Female&nameContains=e

        response.prettyPrint();

        //Then response status code should be 200
        assertEquals(HttpStatus.SC_OK, response.statusCode());

        //And response Content-Type should be application/json
        assertEquals("application/json", response.contentType());

        //And "Female" should be in response payload
        assertTrue(response.body().asString().contains("Female"));

        //And "Janette" should be in response payload
        assertTrue(response.body().asString().contains("Janette"));

        /*
        converting the body to a string and using contains() method is not the proper way to verify that
        all genders are Female etc. We will learn how to get the specific data out of the response soon
         */

    }

    @DisplayName("GET Request to /api/v2/spartans/search with Query Params - second way")
    @Test
    public void test4() {

        Map<String, Object> queryMap = new HashMap<>();

        queryMap.put("gender", "Female");
        queryMap.put("nameContains", "e");

        Response response = given()
                .accept(ContentType.JSON)
                .and()
                .queryParams(queryMap)
                .get("/api/v2/spartans/search");// /api/v2/spartans/search?gender=Female&nameContains=e

        response.prettyPrint();

        //Then response status code should be 200
        assertEquals(HttpStatus.SC_OK, response.statusCode());

        //And response Content-Type should be application/json
        assertEquals("application/json", response.contentType());

        //And "Female" should be in response payload
        assertTrue(response.body().asString().contains("Female"));

        //And "Janette" should be in response payload
        assertTrue(response.body().asString().contains("Janette"));

        /*
        converting the body to a string and using contains() method is not the proper way to verify that
        all genders are Female etc. We will learn how to get the specific data out of the response soon
         */

    }
}
