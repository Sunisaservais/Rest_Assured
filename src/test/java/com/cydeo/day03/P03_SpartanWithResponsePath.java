package com.cydeo.day03;

import com.cydeo.utilities.SpartanTestBase;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.List;

import static io.restassured.RestAssured.get;
import static io.restassured.RestAssured.given;
import static org.junit.jupiter.api.Assertions.assertEquals;

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

        Response response = get("api/v2/spartans");

//        response.prettyPrint();

        //verify the status code
        assertEquals(200, response.statusCode());

        String message = response.path("message");
        System.out.println("message = " + message);

        //verify that the value of "message" from response body is "Successfully retrieved all the Spartans."
        assertEquals("Successfully retrieved all the Spartans.", message);
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

        Response response = given()
                .accept(ContentType.JSON)
                .and()
                .pathParam("id", 10)
                .when()
                .get("api/v2/spartans/{id}");

        //verify the status code
        assertEquals(200, response.statusCode());

        //verify content type header
        assertEquals("application/json", response.contentType());

        //     And the "data" of the response body value must match the following:
        //          id: 10,
        //          name: "Lorenza",
        //          gender: "Female",
        //          phone: "3312820936"

        int id = response.path("data.id");
        String name = response.path("data.name");
        String gender = response.path("data.gender");
        String phone = response.path("data.phone");

        System.out.println("id = " + id);
        System.out.println("name = " + name);
        System.out.println("gender = " + gender);
        System.out.println("phone = " + phone);

        //if the path is incorrect, it will return null
        String address = response.path("data.address");
        System.out.println("address = " + address);

        //assertions
        assertEquals(10, id);
        assertEquals("Lorenza", name);
        assertEquals("Female", gender);
        assertEquals("3312820936", phone);

    }


    @DisplayName("GET All Spartans")
    @Test
    public void test3() {

        Response response = get("/api/v2/spartans");

        //get me the ID of the first spartan
        int firstId = response.path("data[0].id"); // first way - getting the first spartan, then finding its id
        System.out.println("firstId = " + firstId);

        int idFirst = response.path("data.id[0]"); // second way - getting the id list, then finding the first id
        System.out.println("idFirst = " + idFirst);

        //get me the name of first spartan
        System.out.println("response.path(\"data[0].name\") = " + response.path("data[0].name"));

        //get the name of second spartan
        System.out.println("response.path(\"data[1].name\") = " + response.path("data[1].name"));

        //get me the name of last spartan
        System.out.println("response.path(\"data[-1].name\") = " + response.path("data[-1].name"));
        System.out.println("response.path(\"data.name[-1]\") = " + response.path("data.name[-1]"));

        //get the name of second-to-last spartan
        System.out.println("response.path(\"data[-2].name\") = " + response.path("data[-2].name"));

        //get me all the spartan names
        List<String> allNames = response.path("data.name");
        System.out.println(allNames);

        //how to print all names one by one
        for (String eachName : allNames) {
            System.out.println(eachName);
        }

    }
}
