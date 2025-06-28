package com.cydeo.day05;

import com.cydeo.utilities.HrTestBase;
import io.restassured.http.ContentType;
import io.restassured.path.json.JsonPath;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.List;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.*;

public class P03_HamCrestHr extends HrTestBase {

        /*
       Given Accept type is "application/json"
       And query parameter: job_id = IT_PROG
       When user sends a GET request to "/employees"
       Then status code should be 200
       And Content-type should be "application/json"
       Verify that:
           - each employee has a manager
           - each employee is working as IT_PROG
           - each employee's salary is greater than 3000
           - the list of first names matches the expected list (in order):
             ["Alexander","Bruce","David","Valli","Diana"]
           - the list of emails contains all the expected values (order does not matter):
             ["DNGUYEN","DWILLIAMS","AJAMES","BMILLER","VJACKSON"]
    */

    @DisplayName("GET Employees IT_PROG with Hamcrest")
    @Test
    public void test1() {

        //expected first names
        List<String> names = Arrays.asList("Alexander","Bruce","David","Valli","Diana");

        given()
                .accept(ContentType.JSON)
                .queryParam("job_id", "IT_PROG")
        .when()
                .get("/employees")
        .then()
                .statusCode(200)
                .and()
                .contentType("application/json")
                .and()
                .body("items.manager_id", everyItem(notNullValue()))
                .body("items.job_id", everyItem(equalTo("IT_PROG")))
                .body("items.salary", everyItem(greaterThan(3000)))
                .body("items.first_name", equalTo(names))
                .body("items.email", containsInAnyOrder("DNGUYEN","DWILLIAMS","AJAMES","BMILLER","VJACKSON"));
    }

      /*
      Given:
               Accept type is "application/json"
      When:
               User sends a GET request to "/regions"
      Then:
               - Status code should be 200
               - Content-type should be "application/json"
               - Response header "Date" should not be null
               - The first region's name should be "Europe"
               - The first region's id should be 10
               - Total number of regions should be 5
               - Each region must have a non-null name
               - Region names should be in the following order:
                    "Europe", "Americas", "Asia", "Oceania", "Africa"
               - Region ids should be in the following order:
                     10, 20, 30, 40, 50

               - Print all the regions names
               ...
     */

    @DisplayName("GET /regions with Hamcrest")
    @Test
    public void test2() {

        JsonPath jsonPath = given()
                .log().ifValidationFails()
                .accept(ContentType.JSON)
        .when()
                .get("/regions")
        .then()
                .log().ifValidationFails()
                .assertThat()
                .statusCode(200)
                .and()
                .contentType("application/json")
                .and()
                .header("Date", notNullValue())
                .and()
                .body("items[0].region_name", is(equalTo("Europe")))
                .body("items[0].region_id", is(10))
                .body("items", hasSize(5))
                .body("items.region_name", everyItem(notNullValue()))
                .body("items.region_name", containsInRelativeOrder("Europe", "Americas", "Asia", "Oceania", "Africa"))
                .body("items.region_id", containsInRelativeOrder(10, 20, 30, 40, 50))
                .extract().jsonPath();

        List<String> allRegionNames = jsonPath.getList("items.region_name");
        System.out.println("allRegionNames = " + allRegionNames);

        //get regions names from ui or database and compare
    }
}
