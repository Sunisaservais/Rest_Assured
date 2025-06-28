package com.cydeo.day05;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;

public class P01_HamCrestMatchersIntro {


    @Test
    public void testNumbers() {

        //Junit 5 assertions
        Assertions.assertEquals(9, 6 + 3);

        //Hamcrest comes included in the RestAssured dependency
        //We can use static imports to get rid of the class names - MatcherAssert, Matchers

        //Hamcrest Matchers:
        //Matchers have two overloaded methods
        // - First one takes a value for comparing
        // - Second one takes another Matcher to increase readability / to add new assertion functionality

        assertThat(6 + 3, is(9));
        assertThat(6 + 3, is(equalTo(9)));
        assertThat(6 + 3, equalTo(9));

        assertThat(5 + 5, not(9));
        assertThat(5 + 5, not(equalTo(9)));
        assertThat(5 + 5, is(not(9)));
        assertThat(5 + 5, is(not(equalTo(9))));


        /*
        is(someValue)
        is(equalTo(someValue))
        equalTo(someValue)

        not(someValue)
        not(equalTo(someValue))
        is(not(someValue))
        is(not(equalTo(someValue)))

        All of them perform the same assertion
         */

       assertThat(5 + 6, is(greaterThan(10)));
       assertThat(5 + 6, greaterThan(10));
       assertThat(5 + 6, lessThan(12));
    }

    @Test
    public void testStrings() {

        String msg = "API is fun!";

        assertThat(msg, is( "API is fun!"));
        assertThat(msg, equalTo( "API is fun!"));
        assertThat(msg, equalToIgnoringCase( "api is fun!"));

        assertThat(msg, startsWith("API"));
        assertThat(msg, endsWith("fun!"));
        assertThat(msg, containsString("is"));

        assertThat(msg, not("Hello World!"));
        assertThat(msg, is(not("Hello World!")));
    }


    @Test
    public void testCollections() {

        List<Integer> numberList = Arrays.asList(3, 5, 1, 77, 44, 76); //6 elements

        //how to check collection size
        assertThat(numberList, hasSize(6));

        //how to check 77 exists in the collection
        assertThat(numberList, hasItem(77));

        //how to check 44 and 76 exist in the collection
        assertThat(numberList, hasItems(44, 76));
//        assertThat(numberList, hasItems(44, 76, 4)); //fails, because 4 is not in the list

        //loop through each of the elements and make sure they are matching with the Matcher inside everyItem
        assertThat(numberList, everyItem(greaterThanOrEqualTo(1)));

        //check if it has all the values, order must be same
        assertThat(numberList, containsInRelativeOrder(3, 5, 44));

        //check if it has all the values, order might be different
        assertThat(numberList, containsInAnyOrder(76, 3, 5, 1, 77, 44));
    }
}
