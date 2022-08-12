package com.cydeo.tests;


import static io.restassured.RestAssured.*;

import com.cydeo.pojo.Spartan;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import static org.hamcrest.Matchers.*;

import org.hamcrest.Matchers;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Map;

public class APIBootcampTests {

    @BeforeEach
    public void setUp(){
        baseURI = "http://44.202.119.26:8000";
    }

    @Test
    public void pathTest(){

        Response response = given().accept(ContentType.JSON)
                .pathParam("id",9)
                .get("/api/spartans/{id}");
        response.prettyPrint();

    }

    @Test
    public void collectionTest(){

        Response response = given().accept(ContentType.JSON)
                .pathParam("id",9)
                .get("/api/spartans/{id}");
        response.prettyPrint();

        Map<String,Object> oneSpartan = response.as(Map.class); // de-serialization

        System.out.println("oneSpartan = " + oneSpartan);
        // response.prettyPrint();
        int id = (int) oneSpartan.get("id");
        Assertions.assertEquals(9,id);



    }

    @Test
    public void pojoTest(){
        Response response = given().accept(ContentType.JSON)
                .pathParam("id",9)
                .get("/api/spartans/{id}");
        response.prettyPrint();

        Spartan oneSpartan = response.as(Spartan.class);  // de-serialization

        System.out.println("oneSpartan = " + oneSpartan);

        String actualName = oneSpartan.getName();

        // With POJO classes we use getters to reach data

        Assertions.assertEquals("Florrie",actualName);

    }

    @Test
    public void postTestwithPojo(){

        Spartan newSpartan = new Spartan();
        newSpartan.setName("Selena");
        newSpartan.setGender("Female");
        newSpartan.setPhone(5557774422L);

        given().accept(ContentType.JSON)
                .and().contentType(ContentType.JSON)
                .body(newSpartan).log().all()
                .when()
                .post("/api/spartans")
                .then()
                .statusCode(201)
                .contentType("application/json")
                .body("success", is("A Spartan is Born!"))
                .and().log().all();





    }


}
