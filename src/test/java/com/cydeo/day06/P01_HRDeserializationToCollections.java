package com.cydeo.day06;

import com.cydeo.utilities.HrTestBase;
import io.restassured.http.ContentType;
import io.restassured.path.json.JsonPath;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Map;

import static io.restassured.RestAssured.given;

public class P01_HRDeserializationToCollections extends HrTestBase {

      /*
      Given Accept type is "application/json"
      When user sends a GET request to "/locations"
      Then status code should be 200

      System.out.println("====== GET FIRST LOCATION AS A MAP ======");

      System.out.println("====== GET ALL LOCATIONS AS LIST OF MAP======");
      System.out.println("====== GET FIRST LOCATION FROM THE LOCATIONS LIST OF MAP ======");
      System.out.println("====== GET FIRST LOCATION ID ======");
     */

    @DisplayName("GET /locations - Deserialization to Java Collections")
    @Test
    public void test1() {

        JsonPath jsonPath = given()
                .accept(ContentType.JSON)
                .when()
                .get("/locations")
                .then()
                .statusCode(200)
                .extract()
                .jsonPath();

        System.out.println("====== GET FIRST LOCATION AS A MAP ======");
        Map<String, Object> firstLocationMap = jsonPath.getMap("items[0]");
        System.out.println("firstLocationMap = " + firstLocationMap);

        System.out.println("firstLocationMap.get(\"location_id\") = " + firstLocationMap.get("location_id"));

        System.out.println("====== GET ALL LOCATIONS AS LIST OF MAP======");
        List<Map<String, Object>> allLocations = jsonPath.getList("items");

        for (Map<String, Object> eachLocationMap : allLocations) {
            System.out.println("eachLocationMap = " + eachLocationMap);
        }

        System.out.println("====== GET FIRST LOCATION FROM THE LOCATIONS LIST OF MAP ======");
        System.out.println("allLocations.get(0) = " + allLocations.get(0));

        System.out.println("====== GET FIRST LOCATION ID ======");
        System.out.println("allLocations.get(0).get(\"location_id\") = " + allLocations.get(0).get("location_id"));


    }
}
