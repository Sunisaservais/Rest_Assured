package com.cydeo.utilities;

import org.junit.jupiter.api.BeforeAll;

import static io.restassured.RestAssured.baseURI;

public abstract class CydeoTestBase {

    @BeforeAll
    public static void init() {
        baseURI = "http://3.86.13.225:8080";
    }
}
