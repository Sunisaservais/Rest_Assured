package com.cydeo.day02;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.*;
import static org.junit.jupiter.api.Assertions.*;

public class P02_NegativeSpartansTests {

    @BeforeAll
    public static void init() {
        //RestAssured.baseURI = "http://cydeo.onthewifi.com:8000";

        RestAssured.baseURI = "http://cydeo.onthewifi.com"; //base Url, including the protocol and domain /ip, we can include the port here as well
        RestAssured.port = 8000;  //port - only needed if not included in the baseURI
        //RestAssured.basePath = "/api/v2"; //prefix that applies to all the endpoints
    }

    /*
     * Given Accepts content type is application/json
     * When user sends GET request to /api/v2/spartans endpoint
     * Then status code should be 200
     */

    @DisplayName("GET - ALL SPARTANS")
    @Test
    public void getAllSpartans() {
        Response response = given() // with static imports, instead of doing RestAssured.given(), we can simply call these static method
                .accept("application/json")
                .when()
                .get("/api/v2/spartans");

        assertEquals(200, response.getStatusCode());
    }

    /*
     * Given Accepts type application/xml
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

        response.prettyPrint();

        //Verify that the status code is 406
        assertEquals(406, response.getStatusCode());

        //Verify Content-Type is application/json
        assertEquals(ContentType.JSON.toString(), response.contentType()); //OPT1
        //assertEquals("application/json", response.header("Content-Type")); //OPT2
    }


}
