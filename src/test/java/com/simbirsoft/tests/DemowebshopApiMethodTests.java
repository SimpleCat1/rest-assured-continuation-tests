package com.simbirsoft.tests;

import com.simbirsoft.data.AddWishApiData;
import com.simbirsoft.data.CheckWishApiData;
import io.restassured.response.Response;
import org.junit.jupiter.api.Test;

import static com.simbirsoft.page.ReqresApiMethods.checkValue;
import static com.simbirsoft.page.ReqresApiMethods.getQuantity;
import static io.restassured.RestAssured.given;
import static org.assertj.core.api.Assertions.assertThat;

public class DemowebshopApiMethodTests extends TestBase {

    @Test
    void addingProductToTheCart() {
        AddWishApiData data = new AddWishApiData();

        Response response = given()
                .contentType(data.contentType)
                .body(data.body)
                .when()
                .post(data.url)
                .then()
                .statusCode(data.statusCode)
                .extract()
                .response();

        assertThat(response.path("success").toString()).isGreaterThanOrEqualTo(data.textSuccessBuy);
        assertThat(response.path("message").toString()).isGreaterThanOrEqualTo(data.textAnswer);
        assertThat(response.path("updatetopcartsectionhtml").toString()).isGreaterThanOrEqualTo(data.textCountItem);
    }

    @Test
    void addingMultipleProductsToTheCart() {
        AddWishApiData data = new AddWishApiData();
        CheckWishApiData data1 = new CheckWishApiData();

        Response response = given()
                .contentType(data.contentType)
                .cookie(cookie)
                .body(data.body)
                .when()
                .post(data.url)
                .then()
                .statusCode(data.statusCode)
                .extract()
                .response();
        Response response1 = given()
                .contentType(data.contentType)
                .cookie(cookie)
                .body(data.body)
                .when()
                .post(data.url)
                .then()
                .statusCode(data.statusCode)
                .extract()
                .response();
        Response response2 = given()
                .when()
                .cookie(cookie)
                .post(data1.url)
                .then()
                .statusCode(data1.statusCode)
                .extract()
                .response();

        assertThat(getQuantity(response2.getBody().print())).isEqualTo(data1.textQuantity);
    }

    @Test
    void checkingTheShoppingCart() {
        CheckWishApiData data1 = new CheckWishApiData();

        Response response1 = given()
                .when()
                .post(data1.url)
                .then()
                .statusCode(data1.statusCode)
                .extract()
                .response();

        assertThat(checkValue(response1.getBody().print())).isEqualTo(data1.availabilityOfTheProductInTheCart);
    }
}
