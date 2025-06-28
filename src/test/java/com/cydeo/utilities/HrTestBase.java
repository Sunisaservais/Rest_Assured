package com.cydeo.utilities;

import org.junit.jupiter.api.BeforeAll;

import static io.restassured.RestAssured.baseURI;

public abstract class HrTestBase {

    @BeforeAll
    public static void init() {

        baseURI = "http://cydeo.onthewifi.com:1000/ords/hr";
        //MyIpAddress:1000/ords/hr

        //baseURI = "http://34.226.136.145:1000/ords/hr"; //APIShorts IP



    }
}
