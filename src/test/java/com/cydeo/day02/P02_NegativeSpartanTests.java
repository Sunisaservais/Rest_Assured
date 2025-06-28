package com.cydeo.day02;

import io.restassured.http.ContentType;
import io.restassured.response.Response;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.baseURI;
import static io.restassured.RestAssured.given;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class P02_NegativeSpartanTests {

    @BeforeAll
    public static void init() {

//        RestAssured.baseURI = "http://cydeo.onthewifi.com:8000";
        baseURI = "http://cydeo.onthewifi.com:8000";

//        RestAssured.baseURI = "http://cydeo.onthewifi.com"; //base url, including the protocol and the domain/ip, we can include the port here as well
//        RestAssured.port = 8000; //port - only needed if not included in the baseURI
//        RestAssured.basePath = "/api/v2"; //prefix that applies to all the endpoints

    }

    /*
     * Given Accept content type is application/json
     * When user sends GET request to /api/v2/spartans endpoint
     * Then status code should be 200
     */


    @DisplayName("GET - ALL SPARTANS")
    @Test
    public void getAllSpartans() {

        Response response = given() //with static imports, instead of doing RestAssured.given(), we can simply call these static methods by themselves
                .accept("application/json")
                .when()
                .get("/api/v2/spartans"); //http://cydeo.onthewifi.com:8000/api/v2/spartans

        assertEquals(200, response.statusCode());

    }

    /*
     * Given Accept type application/xml
     * When user send GET request to /api/v2/spartans/10 endpoint
     * Then status code must be 406
     * And response header for Content-Type must be application/json
     */

    @DisplayName("GET - One Spartan - Accept application/xml - 406")
    @Test
    public void xmlTest() {

        Response response = given()
                .accept(ContentType.XML)
                .when()
                .get("/api/v2/spartans/10");

        //verify that the status code is 406
        assertEquals(406, response.statusCode());

        //verify Content-Type is application/json
        assertEquals("application/json", response.header("Content-Type"));
//        assertEquals("application/json", response.contentType());

    }
}
