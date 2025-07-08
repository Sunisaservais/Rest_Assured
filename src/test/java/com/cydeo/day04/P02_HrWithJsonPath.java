package com.cydeo.day04;

import com.cydeo.utilities.HrTestBase;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.List;

public class P02_HrWithJsonPath extends HrTestBase {

    /*
    When user sends a GET request to "/countries"
    Then status code should be 200
    And user should see ...
    */

    @DisplayName("GET All Countries with JSON Path")
    @Test
    public void test1() {
        Response response = RestAssured.get("/countries");
        //response.prettyPrint();

        //Verify status code
        Assertions.assertEquals(200, response.getStatusCode());

        //Create JsonPath object
        JsonPath jsonPath = response.jsonPath();

        //Get me the name of 3rd country
        System.out.println("Third country name: " + jsonPath.getString("items[2].country_name"));

        //Get me the names of 3rd and 4th countries
        System.out.println("Third country name: " + jsonPath.getString("items[2,3].country_name"));

        //Get me all country names where region_id is 20
        List<String> countryNameList = jsonPath.getList("items.findAll{it.region_id==20}.country_name");
        System.out.println("country name list: " + countryNameList);
    }

    /*
     Given Accepts type is "application/json"
     And query param "limit" is 200
     When user sends a GET request to "/employees"
     Then status code should be 200
     And user should see ...
     */

    @DisplayName("GET All /employees?limit=200 with JSON Path")
    @Test
    public void test2() {
        Response response = RestAssured.given()
                .accept(ContentType.JSON)
                .and()
                .queryParam("limit", 200)
                .when()
                .get("/employees");

        //response.prettyPrint();

        //verify status code
        Assertions.assertEquals(200, response.getStatusCode());

        //create JsonPath object
        JsonPath jsonPath = response.jsonPath();

        //get all emails from response
        List<String> emails = jsonPath.getList("items.email");
        System.out.println("emails: " + emails);

        //get all emails of individuals working as 'IT_PROG'
        List<String> emailsIT = jsonPath.getList("items.findAll{it.job_id=='IT_PROG'}.email");
        System.out.println("emailsIT: " + emailsIT);

        //get the first names of all employees whose salary is greater than 10000
        List<String> allEmpNameSalaryMoreThan10k = jsonPath.getList("items.findAll{it.salary>10000}.first_name");
        System.out.println("All employee name salary more than 10K: " + allEmpNameSalaryMoreThan10k);

        //get all information of the employee with the highest/max salary
        System.out.println("Information of employee with the highest salary: " + jsonPath.getString("items.max {it.salary}"));

        //get me the first name of the employee with the highest/max salary
        System.out.println("First name of employee with the highest salary: " + jsonPath.getString("items.max {it.salary}.first_name"));

        //get me the first name of the employee with the lowest/min salary
        System.out.println("First name of employee with the lowest salary: " + jsonPath.getString("items.min {it.salary}.first_name"));
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

    @DisplayName("GET All Locations with JSON Path")
    @Test
    public void test3() {
        Response response = RestAssured.given()
                .accept(ContentType.JSON)
                .when()
                .get("/locations");

        //response.prettyPrint();

        //verify status code
        Assertions.assertEquals(200, response.getStatusCode());

        //Using JsonPath:
        JsonPath jsonPath = response.jsonPath();

        //get the second city
        System.out.println("The second city: " + jsonPath.getString("items[1].city"));

        //get the last city
        System.out.println("The last city: " + jsonPath.getString("items[-1].city"));

        //get all the country IDs
        List<String> countryIDs = jsonPath.getList("items.country_id");
        System.out.println("country IDs = " + countryIDs);

        //get all the cities where their country ID is 'US'
        List<String> allCities = jsonPath.getList("items.findAll{it.country_id=='US'}.city");
        System.out.println("allCities = " + allCities);
    }
}
