package com.cydeo.day02;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class P01_SpartanGetRequests {

    String spartanBaseUrl = "http://cydeo.onthewifi.com:8000";

    /*
     * Given content type is application/json
     * When user sends GET request to /api/v2/spartans endpoint
     * Then status code should be 200
     * And Content type should be application/json
     */

    @Test
    public void getAllSpartans() {

        Response response = RestAssured.given()
//                .accept("application/json")
                .accept(ContentType.JSON) // hey api, send me json response
                .when() //Syntactic sugar
                .get(spartanBaseUrl + "/api/v2/spartans");

        //print the response body
        response.prettyPrint();

        //how to get the status code
        int actualStatusCode = response.statusCode();

        Assertions.assertEquals(200, actualStatusCode);

        //how to get the response header for content type
        String actualContentType = response.contentType();

        System.out.println("actualContentType = " + actualContentType);

        //assert the content type header
//        Assertions.assertEquals("application/json", actualContentType);
        Assertions.assertEquals(ContentType.JSON.toString(), actualContentType);

        //for any response header, we can use the header("headerName") method, which will give that header's value as string
        System.out.println(response.header("Connection"));
        System.out.println(response.header("Content-Type"));
        System.out.println(response.header("Date"));

        //how to check header exists?
        boolean dateExists = response.headers().hasHeaderWithName("Date");
        Assertions.assertTrue(dateExists);
    }

    /*
     * Given content type is application/json
     * When user sends GET request to /api/v2/spartans/3 endpoint
     * Then status code should be 200
     * And Content type should be application/json
     * And response body needs to contain Fidole
     */

    @Test
    public void getSpartan() {

        Response response = RestAssured.given()
                .accept(ContentType.JSON)
                .when()
                .get(spartanBaseUrl + "/api/v2/spartans/3");

        //verify the status code
        Assertions.assertEquals(200, response.statusCode());

        //verify the content type
        Assertions.assertEquals("application/json", response.contentType());
//        Assertions.assertEquals("application/json", response.getContentType());
//        Assertions.assertEquals("application/json", response.header("Content-Type"));
//        Assertions.assertEquals(ContentType.JSON.toString(), response.header("Content-Type"));

        response.prettyPrint();

        //verify that response body contains "Fidole"
        Assertions.assertTrue(response.body().asString().contains("Fidole"));

        /*
        This is not a good way to make assertions. We should be able to get the value of "name" key and verify
        that one has the value "Fidole", instead of checking if it is contained in the whole response body.
         */

    }

    /*
     * Given no headers provided
     * When Users send GET request to /api/v2/hello
     * Then response status code should be 200
     * And Content type header should be "text/plain;charset=UTF-8"
     * And headers should contain Date
     * And Content-Length should be 12
     * And body should be "Hello World!"
     */

    @Test
    public void helloSpartan() {

        Response response = RestAssured.when().get(spartanBaseUrl + "/api/v2/hello");

        //print the response body
        response.prettyPrint();

        //verify the status code
        Assertions.assertEquals(200, response.statusCode());

        //content type header should be "text/plain;charset=UTF-8"
        Assertions.assertEquals("text/plain;charset=UTF-8", response.contentType());

        //headers should contain Date
        Assertions.assertTrue(response.headers().hasHeaderWithName("Date"));

        //Content-Length should be 12
        Assertions.assertEquals("12", response.header("Content-Length"));

        //response body should be "Hello World!"
        Assertions.assertTrue(response.body().asString().equals("Hello World!"));
    }
}
