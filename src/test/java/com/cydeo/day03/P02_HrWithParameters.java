package com.cydeo.day03;

import com.cydeo.utilities.HrTestBase;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.given;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class P02_HrWithParameters extends HrTestBase {


    /*
     * Given Accept type is Json
     * And query parameters: region_id=20
     * When users sends a GET request to "/countries"
     * Then status code should be 200
     * And response Content-Type should be application/json
     * And Payload should contain "United States of America"
     */

    @DisplayName("GET request to /countries with Region ID")
    @Test
    public void test1() {

        Response response = given()
                .accept(ContentType.JSON)
                .queryParam("region_id", "20")
                .when()
                .get("/countries");

        response.prettyPrint();

        //Then status code should be 200
        assertEquals(200, response.statusCode());

        //And response Content-Type should be application/json
        assertEquals("application/json", response.contentType());

        //And Payload should contain "United States of America"
        assertTrue(response.body().asString().contains("United States of America"));

    }
}
