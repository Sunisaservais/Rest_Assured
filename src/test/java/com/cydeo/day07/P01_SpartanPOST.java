package com.cydeo.day07;

import com.cydeo.pojo.Spartan;
import com.cydeo.pojo.SpartanPOST;
import com.cydeo.utilities.SpartanTestBase;
import io.restassured.http.ContentType;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.LinkedHashMap;
import java.util.Map;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.is;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class P01_SpartanPOST extends SpartanTestBase {

    /**
     * Given Accept type is JSON
     * And Content type is JSON
     * And request JSON body is:
     * {
     * "name":"John Doe",
     * "gender":"Male",
     * "phone": "8877445596"
     * }
     * When user sends a POST request to "/api/v2/spartans"
     * Then status code should be 201
     * And content type should be "application/json"
     * And the "message" of the response body value should be "Successfully created the Spartan."
     * And the "data" of the response body value must include the following:

     * "name": "John Doe",
     * "gender": "Male",
     * "phone": "8877445596"
     */

    @DisplayName("POST Spartan with String Body")
    @Test
    public void test1() {

        String requestBody = "   {\n" +
                "     \"gender\":\"Male\",\n" +
                "     \"name\":\"John Doe\",\n" +
                "     \"phone\":8877445596\n" +
                "     }";

        JsonPath jsonPath = given()
                .log().all()
                .accept(ContentType.JSON)
                .and()
                .contentType(ContentType.JSON)
                .body(requestBody)
                .when()
                .post("/api/v2/spartans")
                .then()
                .statusCode(201)
                .contentType("application/json")
                .body("message", is("Successfully created the Spartan."))
                .extract().jsonPath();

        assertEquals("John Doe", jsonPath.getString("data.name"));
        assertEquals("Male", jsonPath.getString("data.gender"));
        assertEquals("8877445596", jsonPath.getString("data.phone"));
    }

    @DisplayName("POST Spartan with Map Body")
    @Test
    public void test2() {

        Map<String, Object> requestBodyMap = new LinkedHashMap<>();
        requestBodyMap.put("name", "John Doe");
        requestBodyMap.put("gender", "Male");
        requestBodyMap.put("phone", "8877445596");
        //we created a map, and put the info that we want to send as a JSON REQUEST BODY

        JsonPath jsonPath = given()
                .log().all()
                .accept(ContentType.JSON) //hey API, please send me JSON RESPONSE BODY
                .and()
                .contentType(ContentType.JSON) //hey API, I am sending you JSON REQUEST BODY
                .body(requestBodyMap)
                .when()
                .post("/api/v2/spartans")
                .then()
                .log().all()
                .statusCode(201)
                .contentType("application/json")
                .body("message", is("Successfully created the Spartan."))
                .extract().jsonPath();

        assertEquals("John Doe", jsonPath.getString("data.name"));
        assertEquals("Male", jsonPath.getString("data.gender"));
        assertEquals("8877445596", jsonPath.getString("data.phone"));
    }

    @DisplayName("POST Spartan with Spartan POJO")
    @Test
    public void test3() {

        SpartanPOST spartan = new SpartanPOST();
        spartan.setName("John Doe");
        spartan.setGender("Male");
        spartan.setPhone("8877445596");

        JsonPath jsonPath = given()
                .log().all()
                .accept(ContentType.JSON) //hey API, please send me JSON RESPONSE BODY
                .and()
                .contentType(ContentType.JSON) //hey API, I am sending you JSON REQUEST BODY
                .body(spartan)
                .when()
                .post("/api/v2/spartans")
                .then()
                .log().all()
                .statusCode(201)
                .contentType("application/json")
                .body("message", is("Successfully created the Spartan."))
                .extract().jsonPath();

        assertEquals("John Doe", jsonPath.getString("data.name"));
        assertEquals("Male", jsonPath.getString("data.gender"));
        assertEquals("8877445596", jsonPath.getString("data.phone"));
    }

    @DisplayName("POST Spartan with SpartanPOST POJO and GET the Same Spartan")
    @Test
    public void test4() {

        //we use an empty Spartan object, and the setters to set some value
        SpartanPOST spartanPOST = new SpartanPOST();
        spartanPOST.setName("John Doe");
        spartanPOST.setGender("Male");
        spartanPOST.setPhone("8877445596");

        JsonPath jsonPath = given()
                .log().all()
                .accept(ContentType.JSON) //hey API, please send me JSON RESPONSE BODY
                .and()
                .contentType(ContentType.JSON) //hey API, I am sending you JSON REQUEST BODY
                .body(spartanPOST) //it will take spartan object and serialize to JSON
                .when()
                .post("/api/v2/spartans")
                .then()
                .log().all()
                .statusCode(201)
                .contentType("application/json")
                .body("message", is("Successfully created the Spartan."))
                .extract().jsonPath();

        assertEquals("John Doe", jsonPath.getString("data.name"));
        assertEquals("Male", jsonPath.getString("data.gender"));
        assertEquals("8877445596", jsonPath.getString("data.phone"));

        //I want to get the ID out of the response body, to send a GET request with it later on
        int id = jsonPath.getInt("data.id");
        System.out.println("id = " + id);

        //send GET request to get the spartan that is created then deserialize to spartan class and compare
        Response response = given()
                .accept(ContentType.JSON)
                .pathParam("id", id)
                .when()
                .get("api/v2/spartans/{id}")
                .then()
                .statusCode(200)
                .extract()
                .response();

        //get json response and deserialize to spartan class
        Spartan spartanGET = response.jsonPath().getObject("data", Spartan.class);

        System.out.println("spartanGET = " + spartanGET);

        //verify that the name we had sent and the name we get is matching
        assertEquals(spartanPOST.getName(), spartanGET.getName());
    }
}
