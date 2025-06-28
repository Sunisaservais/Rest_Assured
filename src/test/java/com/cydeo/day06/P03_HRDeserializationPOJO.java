package com.cydeo.day06;

import com.cydeo.pojo.Employee;
import com.cydeo.pojo.Region;
import com.cydeo.utilities.HrTestBase;
import io.restassured.path.json.JsonPath;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.get;

public class P03_HRDeserializationPOJO extends HrTestBase {

    @DisplayName("GET Regions and Deserialize to POJO - JSON Property")
    @Test
    public void test1() {

        JsonPath jsonPath = get("/regions")
                .then()
                .statusCode(200)
                .extract().jsonPath();

        //get the first region from the "items" array and convert it to Region class
        Region region = jsonPath.getObject("items[0]", Region.class);

        System.out.println("region = " + region);

//        System.out.println("region.getRegion_id() = " + region.getRegion_id());
//        System.out.println("region.getRegion_name() = " + region.getRegion_name());

        System.out.println("region.getRegionId() = " + region.getRegionId());
        System.out.println("region.getRegionName() = " + region.getRegionName());
        System.out.println("region.getLinks().get(0).getHref() = " + region.getLinks().get(0).getHref());

    }

    @DisplayName("GET Employee and Deserialize POJO with Only Required Fields")
    @Test
    public void test2() {

        JsonPath jsonPath = get("/employees")
                .then()
                .statusCode(200)
                .extract().jsonPath();

        Employee employee = jsonPath.getObject("items[0]", Employee.class);

        System.out.println("employee = " + employee);
    }


}
