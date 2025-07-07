package com.cydeo.live_weekend.week01;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.Response;

public class Test {

    @org.junit.jupiter.api.Test
    public void test(){
        Response response = RestAssured.given()
                .accept(ContentType.JSON)
                .get("https://library2.cydeo.com/dashboard");

        System.out.println("response.getBody() = " + response.getBody().toString());
    }
}
