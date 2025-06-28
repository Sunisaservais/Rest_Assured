package com.cydeo.day09;

import com.cydeo.utilities.BookItTestBase;
import com.cydeo.utilities.BookItUtilities;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import io.restassured.response.ValidatableResponse;
import io.restassured.specification.RequestSpecification;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.given;

public class P01_CucumberIntro extends BookItTestBase {

    String email = "lfinnisz@yolasite.com";
    String password = "lissiefinnis";
    String accessToken = BookItUtilities.getToken(email, password);

    /*
    Given I logged in Bookit api as a "team_leader"
    And request accept type is "application/json"
    When I sent GET request to "/api/campuses" endpoint
    Then status code should be 200
    And response content type is "application/json"
    And Each "id" field should not be null
     */

    @DisplayName("GET /api/campuses")
    @Test
    public void test1() {

        RequestSpecification givenPart = given().log().uri();

        //    Given I logged in Bookit api as a "team_leader"
        givenPart.header("Authorization", accessToken);

        //    And request accept type is "application/json"
        givenPart.accept(ContentType.JSON);

        //    When I sent GET request to "/api/campuses" endpoint
        Response response = givenPart.when().get("/api/campuses");

//        JsonPath jsonPath = response.jsonPath();
//        jsonPath.getString();

        //    Then status code should be 200
        ValidatableResponse thenPart = response.then();
        thenPart.statusCode(200);

        //    And response content type is "application/json"
        thenPart.contentType(ContentType.JSON);

        //    And Each "id" field should not be null
        thenPart.body("id", Matchers.everyItem(Matchers.notNullValue()));
    }
}
