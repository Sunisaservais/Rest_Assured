package com.cydeo.day02;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class P01_SpartanGetRequests {

    String baseUrl = "http://cydeo.onthewifi.com:8000";

    /*
     * Given content type is application/json
     * When user sends GET request to /api/v2/spartans endpoint
     * Then status code should be 200
     * And Content type should be application/json
     */

    @Test
    public void getAllSpartans() {
        Response response = RestAssured.given()
                //.accept("application/json")
                .accept(ContentType.JSON) //Communicate with API to send Json Response
                .when() //Syntactic sugar
                .get(baseUrl + "/api/v2/spartans");

        //Print the response body
        //response.prettyPrint();

        //Get the status code
        int actualStatusCode = response.getStatusCode();
        int expectedStatusCode = 200;
        Assertions.assertEquals(expectedStatusCode, actualStatusCode);

        String actualContentType = response.getContentType();
        String expectedContentType = ContentType.JSON.toString();

        System.out.println("actualContentType = " + actualContentType);
        System.out.println("expectedContentType = " + expectedContentType);

        Assertions.assertEquals(expectedContentType, actualContentType);

        //For any response header, we can use the header method .header() or .getHeader()
        //Parameters("headerName") which will give that header's value as String
        System.out.println("response.header(\"Date\") = " + response.header("Date"));
        System.out.println("response.getHeader(\"Connection\") = " + response.getHeader("Connection"));
        System.out.println("response.header(\"Keep-Alive\") = " + response.header("Keep-Alive"));
        System.out.println("response.getHeader(\"Transfer-Encoding\") = " + response.getHeader("Transfer-Encoding"));
        System.out.println("response.getHeader(\"Content-Type\") = " + response.getHeader("Content-Type"));

        //Check header if header exits
        boolean dateExit = response.headers().hasHeaderWithName("Date");
        Assertions.assertTrue(dateExit);

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
                .get(baseUrl + "/api/v2/spartans/3");

        //verify the status code
        int actualStatusCode = response.getStatusCode();
        int expectedStatusCode = 200;
        Assertions.assertEquals(expectedStatusCode, actualStatusCode);

        //verify the content type
        String actualContentType = response.contentType();
        String expectedContentType = ContentType.JSON.toString();
        Assertions.assertEquals(expectedContentType, actualContentType); //OPT1
        //Assertions.assertEquals("application/json", response.getContentType()); //OPT2
        //Assertions.assertEquals("application/json", response.header("Content-Type")); //OPT3
        //Assertions.assertEquals(ContentType.JSON.toString(), response.header("Content-Type")); //OPT4
        //Assertions.assertEquals("application/json", response.contentType()); //OPT4

        ////Verify that response body contains "Fidole"

        response.prettyPrint();
        Assertions.assertTrue(response.getBody().asString().contains("Fidole"));

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
        Response response = RestAssured.given()
                .when()
                .get(baseUrl + "/api/v2/hello");

        //Print the response body
        response.prettyPrint();

        //Verify the status code
        int actualStatusCode = response.statusCode();
        int expectedStatusCode = 200;
        Assertions.assertEquals(expectedStatusCode, actualStatusCode);

        //Content type header should be "text/plain;charset=UTF-8"
        String actualContentType = response.contentType();
        String expectedContentType = "text/plain;charset=UTF-8";
        Assertions.assertEquals(expectedContentType, actualContentType);

        //Headers should contain Date
        Assertions.assertTrue(response.headers().hasHeaderWithName("Date"));

        //Content-Length should be 12
        Assertions.assertEquals("12", response.getHeader("Content-Length"));

        //Response body should be "Hello World!"
        Assertions.assertTrue(response.getBody().asString().equals("Hello World!"));
        Assertions.assertEquals("Hello World!", response.getBody().asString());
    }
}
