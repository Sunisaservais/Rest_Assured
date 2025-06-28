package com.cydeo.day06;

import com.cydeo.pojo.Spartan;
import com.cydeo.utilities.SpartanTestBase;
import io.restassured.http.ContentType;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.List;

import static io.restassured.RestAssured.given;

public class P02_SpartanDeserializationPOJO extends SpartanTestBase {


    @DisplayName("GET Single Spartan - Deserialization to POJO using Spartan class")
    @Test
    public void test1() {

        Response response = given()
                .accept(ContentType.JSON)
                .pathParam("id", 15)
                .when()
                .get("/api/v2/spartans/{id}")
                .then()
                .statusCode(200)
                .extract().response();

        /*
          {
              "message": "Successfully retrieved the Spartan.",
              "totalElement": 1,
              "data": {
                  "id": 15,
                  "name": "Meta",
                  "gender": "Female",
                  "phone": "1938695106"
              }
          }
        */

        //Deserialization to convert the JSON "data" object to Spartan POJO
        JsonPath jsonPath = response.jsonPath();

        Spartan spartan = jsonPath.getObject("data", Spartan.class);

        System.out.println("spartan.getId() = " + spartan.getId());
        System.out.println("spartan.getName() = " + spartan.getName());
        System.out.println("spartan.getGender() = " + spartan.getGender());
        System.out.println("spartan.getPhone() = " + spartan.getPhone());
    }

    @DisplayName("GET All Spartans - Deserialize 10th Spartan to POJO")
    @Test
    public void test2() {

        Response response = given()
                .accept(ContentType.JSON)
                .when()
                .get("/api/v2/spartans")
                .then()
                .statusCode(200)
                .extract().response();

        //get the 10th Spartan object from the "data" array and map to Spartan POJO
        JsonPath jsonPath = response.jsonPath();

        Spartan spartan = jsonPath.getObject("data[9]", Spartan.class);
        System.out.println("spartan = " + spartan);

        System.out.println("spartan.getPhone() = " + spartan.getPhone());
    }

    @DisplayName("GET All Spartans - Deserialization to List of Spartans")
    @Test
    public void test3() {

        Response response = given()
                .accept(ContentType.JSON)
                .when()
                .get("/api/v2/spartans")
                .then()
                .statusCode(200)
                .extract().response();

        JsonPath jsonPath = response.jsonPath();

        List<Spartan> allSpartans = jsonPath.getList("data", Spartan.class);

        for (Spartan eachSpartan : allSpartans) {
            System.out.println("eachSpartan = " + eachSpartan);
        }

        Spartan spartan = allSpartans.get(0);

        System.out.println("spartan.getName() = " + spartan.getName());
    }
}
