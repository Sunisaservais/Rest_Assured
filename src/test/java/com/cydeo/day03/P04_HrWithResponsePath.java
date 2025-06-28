package com.cydeo.day03;

import com.cydeo.utilities.HrTestBase;
import io.restassured.response.Response;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.List;

import static io.restassured.RestAssured.given;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class P04_HrWithResponsePath extends HrTestBase {

    /*
    Given Accept type is "application/json"
    And query param "region_id" is 20
    When user sends a GET request to "/countries"
    Then user should see ...
    And all region_ids should be 20
    */

    @DisplayName("GET Countries by Region ID with Response Path")
    @Test
    public void test1() {

        Response response = given()
                .accept("application/json")
                .and()
                .queryParam("region_id", 20)
                .when()
                .get("/countries");

//        response.prettyPrint();

        //print limit
        System.out.println("response.path(\"limit\") = " + response.path("limit"));

        //print hasMore
        System.out.println("response.path(\"hasMore\") = " + response.path("hasMore"));

        //print second country name
        System.out.println("response.path(\"items[1].country_name\") = " + response.path("items[1].country_name"));

        //print 4th country name
        System.out.println("response.path(\"items[3].country_name\") = " + response.path("items[3].country_name"));

        //print 3rd country href
        System.out.println("response.path(\"items[2].links[0].href\") = " + response.path("items[2].links[0].href"));

        //print all countries names
        List<String> allCountryNames = response.path("items.country_name");
        System.out.println("allCountryNames = " + allCountryNames);

        //verify all region_ids equal to 20
        List<Integer> allRegionIDs = response.path("items.region_id");

        for (Integer regionID : allRegionIDs) {
            assertEquals(20, regionID);
            System.out.println("regionID = " + regionID);
        }

    }
}
