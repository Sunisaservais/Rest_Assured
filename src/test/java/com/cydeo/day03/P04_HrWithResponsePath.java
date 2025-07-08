package com.cydeo.day03;

import com.cydeo.utilities.HrTestBase;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.List;

import static io.restassured.RestAssured.*;

public class P04_HrWithResponsePath extends HrTestBase {

     /*
    Given Accepts type is "application/json"
    And query param "region_id" is 20
    When user sends a GET request to "/countries"
    Then user should see ...
    And all region_ids should be 20
    */

    @DisplayName("GET Countries by Region ID with Response Path")
    @Test
    public void test1() {
        Response response = given()
                .accept(ContentType.JSON)
                .and()
                .queryParam("region_id", 20)
                .when()
                .get("/countries");

        //response.prettyPrint();

        List<Integer> allRegionIDs = response.path("items.region_id");
        System.out.println("All region IDs = " + allRegionIDs);

        //Verify that all region_ids should be 20
        for (Integer regionID : allRegionIDs) {
            Assertions.assertEquals(20, regionID);
        }

        //print limit
        System.out.println("limit: " + response.path("limit"));

        //print hasMore
        System.out.println("hasMore: " + response.path("hasMore"));

        //print second country name
        System.out.println("Second country name: " + response.path("items[1].country_name"));

        //print 4th country name
        System.out.println("Fourth country name: " + response.path("items[3].country_name"));

        //print 3rd country href
        System.out.println("Third href: " + response.path("items[2].links[0].href"));

        //print all countries names
        List<String> allCountriesName = response.path("items.country_name");
        System.out.println("All country names: " + allCountriesName);
        for (String countryName : allCountriesName) {
            System.out.println(countryName);
        }
    }
}
