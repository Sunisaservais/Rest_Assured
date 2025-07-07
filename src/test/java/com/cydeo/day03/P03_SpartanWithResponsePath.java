package com.cydeo.day03;

import com.cydeo.utilities.SpartanTestBase;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.List;

import static io.restassured.RestAssured.*;
import static org.junit.jupiter.api.Assertions.*;

public class P03_SpartanWithResponsePath extends SpartanTestBase {

     /*
     When user sends a GET request to "api/v2/spartans"
     Then status code should be 200
     And the "message" of the response body value must match the following:
        "Successfully retrieved all the Spartans."
     */

    @DisplayName("GET All Spartans - verify success  message")
    @Test
    public void test1() {
        Response response = RestAssured.get("api/v2/spartans");
        //response.prettyPrint();
        assertEquals(200, response.getStatusCode());

        //Verify that the value of "message" from response body is "Successfully retrieved all the Spartans."
        String actualMessage = response.path("message");
        String expectedMessage = "Successfully retrieved all the Spartans.";
        assertEquals(expectedMessage, actualMessage);

    }

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

    @DisplayName("GET Spartan with Response Path")
    @Test
    public void test2() {
        Response response = RestAssured.given()
                .accept(ContentType.JSON)
                .and()
                .pathParam("id", 10)
                .when()
                .get("api/v2/spartans/{id}");

        //response.prettyPrint();

        //Verify the status code
        assertEquals(200, response.getStatusCode());

        //Verify content type header
        assertEquals(ContentType.JSON.toString(), response.contentType());

        //And the "data" of the response body value must match the following:
        //  id: 10,
        //  name: "Lorenza",
        //  gender: "Female",
        //  phone: "3312820936"

        int id = response.path("data.id");
        String name = response.path("data.name");
        String gender = response.path("data.gender");
        String phone = response.path("data.phone");

        System.out.println("id = " + id);
        System.out.println("name = " + name);
        System.out.println("gender = " + gender);
        System.out.println("phone = " + phone);

        //Assertions
        assertEquals(10, id);
        assertEquals("Lorenza", name);
        assertEquals("Female", gender);
        assertEquals("3312820936", phone);

        //If the path is incorrect, it will return null
        String address = response.path("data.address");
        System.out.println("address = " + address);
    }

    @DisplayName("GET All Spartans")
    @Test
    public void test3() {
        Response response = get("/api/v2/spartans");

        //Get me the ID of the first spartan
        int firstId = response.path("data[0].id"); // First way - getting the first spartan, then finding its id
        System.out.println("1.ID for the first person = " + firstId);

        int idFirst = response.path("data.id[0]"); // Second way - getting the id list, then finding the first id
        System.out.println("2.ID for the first person = " + idFirst);

        //Get me the name of first spartan
        String firstName = response.path("data[0].name"); // First way - getting the first spartan, then finding its id
        System.out.println("Name of the first spartan = " + firstName);

        //Get the name of second spartan
        String firstName2 = response.path("data[1].name"); // First way - getting the first spartan, then finding its id
        System.out.println("Name of the second spartan = " + firstName2);

        //Get me the name of last spartan
        String nameOfLastSpartan = response.path("data.name[-1]");
        System.out.println("Name of last Spartan = " + nameOfLastSpartan);

        //Get the name of second-to-last spartan
        String nameOfSecondToLastSpartan = response.path("data.name[-2]");
        System.out.println("Name second-to-last spartan = " + nameOfSecondToLastSpartan);

        //Get me all the spartan names
        List<String> allName = response.path("data.name");
        System.out.println("All name = " + allName);

        //How to print all names one by one
        for (String eachName : allName) {
            System.out.println(eachName);
        }
    }
}
