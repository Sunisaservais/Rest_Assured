package com.cydeo.day07;

import com.cydeo.utilities.SpartanTestBase;
import io.restassured.http.ContentType;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.LinkedHashMap;
import java.util.Map;

import static io.restassured.RestAssured.given;

public class P02_SpartanPUTPATCHDELETE extends SpartanTestBase {

    @DisplayName("PUT Spartan with Map")
    @Test
    public void test1() {

        /*
         {
            "id": 110,
            "name": "John Doe",
            "gender": "Male",
            "phone": "8877445596"
        }
         */

        Map<String, Object> requestBodyMap = new LinkedHashMap<>();
        requestBodyMap.put("name", "John Doe PUT");
        requestBodyMap.put("gender", "Male");
        requestBodyMap.put("phone", "8877445599");

        int id = 106; //PUT will update existing record, so we choose one of the existing IDs

        given()
                .log().body()
                .contentType(ContentType.JSON)
                .pathParam("id",id)
                .body(requestBodyMap) //this will be serialized
                .when()
                .put("/api/v2/spartans/{id}")
                .then()
                .log().all()
                .statusCode(200);

    }

    @DisplayName("PATCH Spartan with Map")
    @Test
    public void test2() {

        /*
         {
             "id": 106,
            "name": "John Doe",
            "gender": "Male",
            "phone": "8877445596"
        }
         */

        Map<String, Object> requestBodyMap = new LinkedHashMap<>();
        requestBodyMap.put("name", "John Doe PATCH");

        int id = 106; //PATCH will update existing record, so we choose one of the existing IDs

        given()
                .log().body()
                .contentType(ContentType.JSON)
                .pathParam("id",id)
                .body(requestBodyMap) //this will be serialized
                .when()
                .patch("/api/v2/spartans/{id}")
                .then()
                .log().all()
                .statusCode(200);

    }

    @DisplayName("DELETE Spartan")
    @Test
    public void test3(){

        //we can delete this id only one time
        int id = 106;

        given()
                .pathParam("id", id)
                .when()
                .delete("api/v2/spartans/{id}")
                .then()
                .statusCode(200);

        //after deletion, when we send get request to the id that we deleted, it needs to give 404 not found
        given()
                .pathParam("id", id)
                .when()
                .get("api/v2/spartans/{id}")
                .then()
                .statusCode(404);
    }
}
