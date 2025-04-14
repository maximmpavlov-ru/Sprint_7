package courier;

import common.RequestSpecification;
import io.qameta.allure.Step;
import io.restassured.response.ValidatableResponse;

import static io.restassured.RestAssured.given;

public class CourierMethods {

    @Step("Авторизация курьера")
    public ValidatableResponse loginCourier(CourierCredentials courierCredentials) {
        return given().
                spec(RequestSpecification.requestSpec()).
                body(courierCredentials).
                when().
                post("/courier/login").
                then();
    }

    @Step("Создание нового курьера")
    public ValidatableResponse createCourier(Courier courier) {
        return given().
                spec(RequestSpecification.requestSpec()).
                body(courier).
                when().
                post("/courier").
                then();
    }

    @Step("Удаление курьера")
    public static void deleteCourierList(Integer courierID) {
        given().
                spec(RequestSpecification.requestSpec()).
                pathParam("id", courierID).
                when().
                delete("/courier/{id}").
                then();
    }

}
