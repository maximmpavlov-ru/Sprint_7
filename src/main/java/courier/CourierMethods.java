package courier;

import io.qameta.allure.Step;
import io.restassured.response.ValidatableResponse;

import static io.restassured.RestAssured.given;

public class CourierMethods {

    @Step("Авторизация курьера")
    public ValidatableResponse loginCourier(CourierCredentials courierCredentials) {
        return given().
                spec(CourierRequestSpecification.requestSpec()).
                body(courierCredentials).
                when().
                post("/login").
                then();
    }

    @Step("Создание нового курьера")
    public ValidatableResponse createCourier(Courier courier) {
        return given().
                spec(CourierRequestSpecification.requestSpec()).
                body(courier).
                when().
                post().
                then();
    }

    @Step("Удаление курьера")
    public static void deleteCourierList(Integer courierID) {
        given().
                spec(CourierRequestSpecification.requestSpec()).
                pathParam("id", courierID).
                when().
                delete("/{id}").
                then();
    }

}
