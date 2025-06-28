package com.cydeo.day01;

import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class P01_SpartanTest {

    String spartanBaseUrl = "http://cydeo.onthewifi.com:8000";

    @Test
    public void getAllSpartans() {
        //send request spartan url and get response as Response interface
        Response response = RestAssured.get(spartanBaseUrl+"/api/v2/spartans");

        //both methods do the same thing
        System.out.println("response.statusCode() = " + response.statusCode());
        System.out.println("response.getStatusCode() = " + response.getStatusCode());

        //verify that status code is 200
        int actualStatusCode = response.statusCode();

        Assertions.assertEquals(200, actualStatusCode);

        //printing the json response body on the console
        response.prettyPrint();
//        response.print();

    }
}
