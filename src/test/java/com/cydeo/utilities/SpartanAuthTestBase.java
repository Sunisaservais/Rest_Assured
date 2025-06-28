package com.cydeo.utilities;

import org.junit.jupiter.api.BeforeAll;

import static io.restassured.RestAssured.baseURI;

public abstract class SpartanAuthTestBase {

    @BeforeAll
    public static void init() {

        baseURI = "http://cydeo.onthewifi.com:7000";

        //baseURI= "http://3.94.120.82:7000/";

    }
}
