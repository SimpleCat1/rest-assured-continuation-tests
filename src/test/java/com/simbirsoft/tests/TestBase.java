package com.simbirsoft.tests;


import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.junit.jupiter.api.BeforeAll;

import static io.restassured.RestAssured.given;

public class TestBase {
    private static String baseUrl = "http://demowebshop.tricentis.com";
    public String cookie = "Nop.customer=" + getCookieWithoutApi() + ";";

    @BeforeAll
    public static void setup() {
        RestAssured.baseURI = baseUrl;
    }

    private static String getCookieWithoutApi() {
        Response response1 = given()
                .when()
                .post(baseUrl)
                .then()
                .statusCode(200)
                .extract()
                .response();
        return response1.cookies().get("Nop.customer");
    }
}
