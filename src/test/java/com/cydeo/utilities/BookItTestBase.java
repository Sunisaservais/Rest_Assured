package com.cydeo.utilities;

import org.junit.jupiter.api.BeforeAll;

import static io.restassured.RestAssured.baseURI;

public abstract class BookItTestBase {

    @BeforeAll
    public static void init() {

        baseURI = "https://api.qa.bookit.cydeo.com";

    }
}
