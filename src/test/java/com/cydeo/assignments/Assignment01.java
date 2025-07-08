package com.cydeo.assignments;

import com.cydeo.utilities.HrTestBase;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.*;
import static org.junit.jupiter.api.Assertions.*;


public class Assignment01 extends HrTestBase {

    /**
     * Task 1 :
     * - Given accept type is Json
     * - And base URI: http://cydeo.onthewifi.com:1000/ords/hr
     * - When users sends request to endpoints:/countries/US
     * - Then status code is 200
     * - And Content - Type is application/json
     * - And response contains United States of America
     */

    @DisplayName("GET United States of America")
    @Test
    public void test1() {
        Response response = given()
                .accept(ContentType.JSON)
                .and()
                .when()
                .get("/countries/US");

        response.prettyPrint();

        //Verify status code is 200
        assertEquals(200, response.getStatusCode());

        //Verify Content - Type is application/json
        assertEquals(ContentType.JSON.toString(), response.getContentType());

        //Verify response contains United States of America
        assertTrue(response.getBody().asString().contains("United States of America"));
    }

    /**
     * Task 2 : NEGATIVE TESTS
     * - Given accept type is Json
     * - And base URI: http://cydeo.onthewifi.com:1000/ords/hr
     * - When users sends request to endpoints: /employees/1
     * - Then status code is 404
     */

    @DisplayName("Negative Test")
    @Test
    public void test2() {
        Response response = given()
                .accept(ContentType.JSON)
                .and()
                .when()
                .get("/employees/1");

        response.prettyPrint();

        //Verify status code is 404
        assertEquals(404, response.getStatusCode());
    }

    /**
     * Task 3 :
     * - Given Accept type  is Json
     * - And base URI: http://cydeo.onthewifi.com:1000/ords/hr
     * - When users sends request to endpoints:/regions/10
     * - Then status code is 200
     * - And Content - Type is application/json
     * - And response contains Europe
     * - And header should contains Date
     * - And "Transfer-Encoding" should be "chunked"
     */

    @DisplayName("GET Europe")
    @Test
    public void test3() {
        Response response = given()
                .accept(ContentType.JSON)
                .and()
                .when()
                .get("/regions/10");

        response.prettyPrint();

        //Verify status code is 200
        assertEquals(200, response.getStatusCode());

        //Verify Content - Type is application/json
        assertEquals(ContentType.JSON.toString(), response.getContentType());

        //Verify response contains Europe
        assertTrue(response.getBody().asString().contains("Europe"));

        //Verify header should contains Date
        assertTrue(response.getHeaders().hasHeaderWithName("Date"));

        //Verify "Transfer-Encoding" should be "chunked"
        assertEquals("chunked", response.getHeader("Transfer-Encoding"));
    }
}
