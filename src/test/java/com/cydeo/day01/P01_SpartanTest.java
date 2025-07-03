package com.cydeo.day01;

import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class P01_SpartanTest {

    String baseUrl = "http://cydeo.onthewifi.com:8000";

    @Test
    public void getAllSpartans() {

        //Send request spartan url and get response as Response interface
        Response response = RestAssured.get(baseUrl + "/api/v2/spartans");
        System.out.println(".statusCode(): " + response.statusCode());
        System.out.println(".getStatusCode(): " + response.getStatusCode());

        //Verify that status code is 200
        int actualStatusCode = response.getStatusCode();
        Assertions.assertEquals(200, actualStatusCode);

        //Printing the Json response body from console
        response.prettyPrint();
        //response.print();
    }
}
