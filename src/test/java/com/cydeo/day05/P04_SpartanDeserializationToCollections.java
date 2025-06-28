package com.cydeo.day05;

import com.cydeo.utilities.SpartanTestBase;
import io.restassured.http.ContentType;
import io.restassured.path.json.JsonPath;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Map;

import static io.restassured.RestAssured.given;

public class P04_SpartanDeserializationToCollections extends SpartanTestBase {

    @DisplayName("GET Single Spartan with Java Collections")
    @Test
    public void test1() {

        JsonPath jsonPath = given()
                .accept(ContentType.JSON)
                .pathParam("id", 10)
                .when()
                .get("/api/v2/spartans/{id}")
                .then()
                .statusCode(200)
                .contentType("application/json")
                .extract().jsonPath();

        /*
        Deserialization:
        Converting JSON to Java collections (Map, List) or custom classes
        JSON --> JAVA

        For this, we need a JSON parsing library like Jackson or Gson
         */

        Map<String, Object> spartanMap = jsonPath.getMap("data");
        System.out.println("spartanMap = " + spartanMap);

        int id = (int) spartanMap.get("id");
        System.out.println("id = " + id);
    }

    @DisplayName("GET All Spartans with Java Collections")
    @Test
    public void test2() {

        JsonPath jsonPath = given()
                .accept(ContentType.JSON)
                .when()
                .get("/api/v2/spartans")
                .then()
                .statusCode(200)
                .contentType("application/json")
                .extract().jsonPath();

        List<Map<String, Object>> spartanList = jsonPath.getList("data");

        // Print the full info of the first spartan
        System.out.println("spartanList.get(0) = " + spartanList.get(0));

        // Print the name of the first spartan
        System.out.println("spartanList.get(0).get(\"name\") = " + spartanList.get(0).get("name"));

        // Print the id of the first spartan
        System.out.println("spartanList.get(0).get(\"id\") = " + spartanList.get(0).get("id"));
    }
}

