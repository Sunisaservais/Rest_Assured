package com.cydeo.day05;

import com.cydeo.utilities.SpartanTestBase;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.given;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.is;

public class P02_HamcrestSpartan extends SpartanTestBase {

    /*
    Given Accept type is "application/json"
    And path param "id" is 15
    When user sends a GET request to "api/v2/spartans/{id}"
    Then status code should be 200
    And content-type should be "application/json"
    And the "data" of the response body value must match the following:
        "id": 15,
        "name": "Meta",
        "gender": "Female",
        "phone": "1938695106"
     */


    @DisplayName("Get Single Spartan with Hamcrest")
    @Test
    public void test1() {

        given()
                .accept("application/json")
                .pathParam("id", 15)
        .when()
                .get("api/v2/spartans/{id}")
        .then()
                .statusCode(200) // .statusCode(is(200)) --> optional to increase readability
                .contentType("application/json")
                .body("data.id", is(15),
                        "data.name", is("Meta"),
                        "data.gender", is("Female"),
                        "data.phone", is("1938695106")); //you can also use equalTo(), is(equalTo()) etc.
    }

    @DisplayName("Get Single Spartan with Hamcrest - second way with syntactic sugar/filler keywords")
    @Test
    public void test2() {

        given()
                .accept("application/json")
                .and()
                .pathParam("id", 15)
                .when()
                .get("api/v2/spartans/{id}")
                .then()
                .assertThat() //assertThat(), and() these are syntactic sugar methods just to increase readability
                .statusCode(200)
                .and()
                .contentType("application/json")
                .and()
                .assertThat()
                .body("data.id", is(15))
                .and()
                .body("data.name", is("Meta")) //we can either assert all in one body() method, or we can call it multiple times
                .body("data.gender", is("Female"))
                .body("data.phone", is("1938695106"));
    }

    @DisplayName("Get Single Spartan with Hamcrest - Logs")
    @Test
    public void test3() {

        given()
                .log().ifValidationFails() //to see all request details, if validation fails
                .accept("application/json")
                .pathParam("id", 15)
                .when()
                .get("api/v2/spartans/{id}")
//                .get("api/v2/spartans/{id}").prettyPeek()
                .then()
                .log().ifValidationFails() //to see all response details, if validation fails
                .statusCode(200) // .statusCode(is(200)) --> optional to increase readability
                .contentType("application/json")
                .body("data.id", is(15),
                        "data.name", is("Meta"),
                        "data.gender", is("Female"),
                        "data.phone", is("1938695106")); //you can also use equalTo(), is(equalTo()) etc.

        /*
                REQUEST LOGS:

                given()
                       .log().all() --> prints all request details (path/query params, URI, body, etc.)
                             .method()
                             .uri()
                             .parameters()
                             .body()
                             .headers()...

                RESPONSE LOGS:

                then()
                      .log().all() --> prints all response details


                - ifValidationFails() --> logs all request/response details, only when validation fails
                - ifValidationFails(LogDetail.HEADERS) --> logs only headers if validation fails

                we can change LogDetail scope to see specific parts of the request/response
         */

        /*
                HOW TO PRINT RESPONSE BODY:

                - response.prettyPrint() --> prints response body on console, returns String
                - response.prettyPeek() --> prints response body on console, returns Response

                since prettyPeek() returns Response, it allows us to continue method chaining
         */
    }

    @DisplayName("Get Single Spartan with Hamcrest - Extract Response")
    @Test
    public void test4() {

        Response response = given()
                .accept("application/json")
                .pathParam("id", 15)
                .when()
                .get("api/v2/spartans/{id}")
                .then()
                .statusCode(200)
                .contentType("application/json")
                .body("data.id", is(15),
                        "data.name", is("Meta"),
                        "data.gender", is("Female"),
                        "data.phone", is("1938695106"))
                .extract().response();

        int id = response.path("data.id");
        System.out.println("id = " + id);

        JsonPath jsonPath = response.jsonPath();
        System.out.println("jsonPath.getInt(\"data.id\") = " + jsonPath.getInt("data.id"));

    }

    @DisplayName("Get Single Spartan with Hamcrest - Extract JsonPath")
    @Test
    public void test5() {

        JsonPath jsonPath = given()
                .accept("application/json")
                .pathParam("id", 15)
                .when()
                .get("api/v2/spartans/{id}")
                .then()
                .statusCode(200)
                .contentType("application/json")
                .body("data.id", is(15),
                        "data.name", is("Meta"),
                        "data.gender", is("Female"),
                        "data.phone", is("1938695106"))
                .extract().jsonPath();
//                .extract().response().jsonPath();

//        JsonPath jsonPath = response.jsonPath();

        //actual data from the API response
        int id = jsonPath.getInt("data.id");
        String name = jsonPath.getString("data.name");

        //expected data from the database (retrieved using DB utilities)
        int expectedIdDb = 15;
        String expectedNameDb = "Meta";

        //comparing API response with database values
        //we can use Hamcrest or Junit 5 assertions
        assertThat(id,is(expectedIdDb));
        assertThat(name, is(equalTo(expectedNameDb)));

        //junit 5
        Assertions.assertEquals(expectedIdDb, id);
        Assertions.assertEquals(expectedNameDb, name);

        /*
            HOW TO EXTRACT DATA AFTER VALIDATION USING then() AND HAMCREST?

            extract() --> it allows us to STORE data after doing verification.

            We can extract data in the following ways:

            1. response() --> to get the response object.
            Ex:
            extract().response();

            2. jsonPath() --> to get the response body as a JsonPath object directly.
            Ex:
            extract.response().jsonPath()
            or
            extract().jsonPath();
        */

        /*
            Why do we need to extract data when we can verify everything
            (status code, headers, body) directly using then() and Hamcrest matchers?

           - In some cases, we need to compare API response data with values from a database or UI.
           - After completing API validation, we may need to retrieve specific fields (for ex list of names, IDs)
            for more verification against DB/UI.
           - So we extract the response as a Response or JsonPath object to access the required data.
         */
    }
}
