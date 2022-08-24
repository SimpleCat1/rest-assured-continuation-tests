package com.simbirsoft.tests;

import com.simbirsoft.data.AddWishApiData;
import com.simbirsoft.data.CheckWishApiData;
import io.restassured.response.Response;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

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
                .post("/addproducttocart/details/72/1")
                .then()
                .statusCode(200)
                .extract()
                .response();

        assertThat(response.path("success").toString()).isGreaterThanOrEqualTo(data.textSuccessBuy);
        assertThat(response.path("message").toString()).isGreaterThanOrEqualTo(data.textAnswer);
        assertThat(response.path("updatetopcartsectionhtml").toString()).isGreaterThanOrEqualTo(data.textCountItem);
    }

    @CsvSource({
            "1, 1",
            "3, 2",
    })
    @ParameterizedTest
    void addingMultipleProductsToTheCart(int amountItem, int amountTest) {
        AddWishApiData data = new AddWishApiData();
        CheckWishApiData data1 = new CheckWishApiData();
        int numberOfCycles = 0;

        do {
            given()
                    .contentType(data.contentType)
                    .cookie(cookie)
                    .body(data.body)
                    .when()
                    .post("/addproducttocart/details/72/1");
            numberOfCycles += 1;
        } while (numberOfCycles < amountTest);
        Response response2 = given()
                .when()
                .cookie(cookie)
                .post("//cart")
                .then()
                .statusCode(200)
                .extract()
                .response();

        assertThat(getQuantity(response2.getBody().print())).isEqualTo(amountItem);
    }

    @Test
    void checkingTheShoppingCart() {
        CheckWishApiData data1 = new CheckWishApiData();

        Response response1 = given()
                .when()
                .post("//cart")
                .then()
                .statusCode(200)
                .extract()
                .response();

        assertThat(checkValue(response1.getBody().print())).isEqualTo(data1.availabilityOfTheProductInTheCart);
    }
}
