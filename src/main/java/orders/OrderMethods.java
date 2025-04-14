package orders;

import common.RequestSpecification;
import io.qameta.allure.Step;
import io.restassured.response.ValidatableResponse;

import static io.restassured.RestAssured.given;

public class OrderMethods {

    @Step("Добавление нового заказа")
    public ValidatableResponse createNewOrder(Order order) {
        return given().
                spec(RequestSpecification.requestSpec()).
                body(order).
                when().
                post("/orders").
                then();
    }

    @Step("Получение списка всех заказов")
    public ValidatableResponse getAllOrders() {
        return given().
                spec(RequestSpecification.requestSpec()).
                when().
                get("orders").
                then();
    }
}
