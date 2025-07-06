package com.cydeo.day03;

import com.cydeo.utilities.HrTestBase;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import org.apache.http.HttpStatus;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.*;
import static org.junit.jupiter.api.Assertions.*;

public class P02HRWithParameters extends HrTestBase {

    /*
     * Given Accepts type is Json
     * And query parameters: region_id=20
     * When users sends a GET request to "/countries"
     * Then status code should be 200
     * And response Content-Type should be application/json
     * And Payload should contain "United States of America"
     */

    @DisplayName("GET request to /countries with Region ID")
    @Test
    public void test1(){
        Response response = given()
                .accept(ContentType.JSON)
                .and()
                .queryParam("region_id", "20")
                .when()
                .get("/countries");

        response.prettyPrint();

        //Then status code should be 200
        assertEquals(HttpStatus.SC_OK,response.getStatusCode());

        //And response Content-Type should be application/json
        assertEquals(ContentType.JSON.toString(),response.getContentType());

        //And Payload should contain "United States of America"
        assertTrue(response.getBody().asString().contains("United States of America"));
    }
}
