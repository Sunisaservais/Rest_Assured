package com.cydeo.day04;

import com.cydeo.utilities.SpartanTestBase;
import io.restassured.http.ContentType;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.given;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class P01_SpartanWithJsonPath extends SpartanTestBase {

     /*
     Given Accept type is "application/json"
     And path param "id" is 10
     When user sends a GET request to "api/v2/spartans/{id}"
     Then status code should be 200
     And content-type should be "application/json"
     And the "data" of the response body value must match the following:
          id: 10,
          name: "Lorenza",
          gender: "Female",
          phone: "3312820936"
    */

    @DisplayName("GET Spartan with JSON Path")
    @Test
    public void test1() {

        Response response = given()
                .accept(ContentType.JSON)
                .and()
                .pathParam("id", 10)
                .when()
                .get("api/v2/spartans/{id}");

//        response.prettyPrint();

        //verify the status code
        assertEquals(200, response.statusCode());

        //verify content type header
        assertEquals("application/json", response.contentType());

        //     And the "data" of the response body value must match the following:
        //          id: 10,
        //          name: "Lorenza",
        //          gender: "Female",
        //          phone: "3312820936"

//        response.path("data.id");

        //we saved our response as JsonPath object
        JsonPath jsonPath = response.jsonPath();

//        String name1 = response.jsonPath().getString("data.name");

        int id = jsonPath.getInt("data.id");
        String name = jsonPath.getString("data.name");
        String gender = jsonPath.getString("data.gender");
        String phone = jsonPath.getString("data.phone");

        //assertions
        assertEquals(10, id);
        assertEquals("Lorenza", name);
        assertEquals("Female", gender);
        assertEquals("3312820936", phone);
//        assertEquals("3312820936", jsonPath.getString("data.phone"));

    }
}
