package com.cydeo.day04;

import com.cydeo.utilities.CydeoTestBase;

import io.restassured.http.ContentType;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.List;

import static io.restassured.RestAssured.*;
import static org.junit.jupiter.api.Assertions.*;

public class P03_CydeoTrainingWithJsonPath extends CydeoTestBase {

    /*
    Given Accepts type is "application/json"
    And path param "id" is 2
    When user sends a GET request to /student/{id}
    Then status code should be 200
    And "Content-Type" header should be "application/json;charset=UTF-8"
    And "Date" header must exist
    And "server" header should be "envoy"
    And verify the following:
            firstName: "Mark"
            batch: 13
            major: "math"
            emailAddress: "mark@email.com"
            companyName: "Cydeo"
            street: "777 5th Ave"
            zipCode: 33222
    */
    @DisplayName("GET /student/{id}")
    @Test
    public void test1() {
        Response response = given()
                .accept(ContentType.JSON)
                .and()
                .pathParam("id", 2)
                .when()
                .get("/student/{id}");

        //response.prettyPrint();

        //verify status code
        assertEquals(200, response.getStatusCode());

        //verify content type header
        assertEquals("application/json;charset=UTF-8", response.getContentType());

        //verify date header exists
        assertTrue(response.getHeaders().hasHeaderWithName("Date"));

        //verify server header
        assertEquals("envoy", response.getHeader("server"));

        //firstName: "Mark"               --> "students[0].firstName"
        //batch: 13                       --> "students[0].batch"
        //major: "math"                   --> "students[0].major"
        //emailAddress: "mark@email.com"  --> "students[0].contact.emailAddress"
        //companyName: "Cydeo"            --> "students[0].company.companyName"
        //street: "777 5th Ave"           --> "students[0].company.address.street"
        //zipCode: 33222                  --> "students[0].company.address.zipCode"

        JsonPath jsonPath = response.jsonPath();

        assertEquals("Mark", jsonPath.getString("students[0].firstName"));

        assertEquals(13, jsonPath.getInt("students[0].batch"));

        assertEquals("math", jsonPath.getString("students[0].major"));

        assertEquals("mark@email.com", jsonPath.getString("students[0].contact.emailAddress"));

        assertEquals("Cydeo", jsonPath.getString("students[0].company.companyName"));

        assertEquals("777 5th Ave", jsonPath.getString("students[0].company.address.street"));

        assertEquals(33222, jsonPath.getInt("students[0].company.address.zipCode"));
    }

    /*
    TASK:
    Given Accept type is "application/json"
    And path param "batch" is 22
    When user sends a GET request to "/student/batch/{batch}"
    Then status code should be 200
    And Content-Type header should be "application/json;charset=UTF-8"
    And Date header must exist
    And server header should be "envoy"
    And verify that all the batch numbers are 22
    */

    @DisplayName("GET /student/batch/{batch}")
    @Test
    public void test2() {
        Response response = given()
                .accept(ContentType.JSON)
                .and()
                .pathParam("batch", 22)
                .when()
                .get("/student/batch/{batch}");

        //response.prettyPrint();

        assertEquals(200, response.getStatusCode());
        assertEquals("application/json;charset=UTF-8", response.getContentType());
        assertTrue(response.getHeaders().hasHeaderWithName("Date"));
        assertEquals("envoy", response.getHeader("server"));

        JsonPath jsonPath = response.jsonPath();
        List<Integer> allBatchNumbers = jsonPath.getList("students.batch");
        for (Integer batchNumber : allBatchNumbers) {
            assertEquals(22, batchNumber);
        }
    }
}


