package com.cydeo.day04;

import com.cydeo.utilities.HrTestBase;
import io.restassured.http.ContentType;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.List;

import static io.restassured.RestAssured.get;
import static io.restassured.RestAssured.given;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class P02_HrWithJsonPath extends HrTestBase {

    /*
    When user sends a GET request to "/countries"
    Then status code should be 200
    And user should see ...
    */

    @DisplayName("GET All Countries with JSON Path")
    @Test
    public void test1() {

        Response response = get("/countries");

//        response.prettyPrint();

        //verify status code
        assertEquals(200, response.statusCode());

        //create JsonPath object
        JsonPath jsonPath = response.jsonPath();

        //get me the name of 3rd country
        System.out.println("jsonPath.getString(\"items[2].country_name\") = " + jsonPath.getString("items[2].country_name"));

        //get me the names of 3rd and 4th countries
        System.out.println("jsonPath.getList(\"items[2,3].country_name\") = " + jsonPath.getList("items[2,3].country_name"));

        //get me all country names where region_id is 20
        List<String> countryNameList = jsonPath.getList("items.findAll {it.region_id==20}.country_name");
        System.out.println("countryNameList = " + countryNameList);
    }

     /*
     Given Accept type is "application/json"
     And query param "limit" is 200
     When user sends a GET request to "/employees"
     Then status code should be 200
     And user should see ...
     */

    @DisplayName("GET All /employees?limit=200 with JSON Path")
    @Test
    public void test2() {

        Response response = given()
                .accept("application/json")
                .and()
                .queryParam("limit", 200)
                .when()
                .get("/employees");

//        response.prettyPrint();

        //verify status code
        assertEquals(200, response.statusCode());

        //create JsonPath object
        JsonPath jsonPath = response.jsonPath();

        //get all emails from response
        List<String> allEmails = jsonPath.getList("items.email");
        System.out.println("allEmails = " + allEmails);

        //get all emails of individuals working as 'IT_PROG'
        List<String> emailsIT = jsonPath.getList("items.findAll {it.job_id=='IT_PROG'}.email");
        System.out.println("emailsIT = " + emailsIT);

        //get the first names of all employees whose salary is greater than 10000
        List<String> allEmpNamesSalaryMoreThan10K = jsonPath.getList("items.findAll {it.salary>10000}.first_name");
        System.out.println("allEmpNamesSalaryMoreThan10K = " + allEmpNamesSalaryMoreThan10K);

        //get all information of the employee with the highest/max salary
        System.out.println("jsonPath.getString(\"items.max {it.salary}\") = " + jsonPath.getString("items.max {it.salary}"));

        //get me the first name of the employee with the highest/max salary
        System.out.println("jsonPath.getString(\"items.max {it.salary.first_name}\") = " + jsonPath.getString("items.max {it.salary}.first_name"));

        //get me the first name of the employee with the lowest/min salary
        System.out.println("jsonPath.getString(\"items.min {it.salary.first_name}\") = " + jsonPath.getString("items.min {it.salary}.first_name"));
    }

     /*
     TASK:

     Given Accept type is "application/json"
     When user sends a GET request to "/locations"
     Then response status code should be 200
     And response Content-Type should be "application/json"

     Using JsonPath:
     get the second city
     get the last city
     get all the country IDs
     get all the cities where their country ID is 'US'

     */


    //SOLUTION:

    @DisplayName("GET All Locations with JSON Path")
    @Test
    public void test3() {
        Response response = given()
                .accept(ContentType.JSON)
                .when()
                .get("/locations");

        assertEquals(200, response.statusCode());

        JsonPath jsonPath = response.jsonPath();

        System.out.println(jsonPath.getString("items[1].city"));
        System.out.println(jsonPath.getString("items[-1].city"));
        System.out.println(jsonPath.getString("items.country_id"));
        System.out.println(jsonPath.getString("items.findAll {it.country_id=='US'}.city"));
    }
}
