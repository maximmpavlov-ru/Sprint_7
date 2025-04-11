package orders;

import io.qameta.allure.Step;
import io.restassured.response.ValidatableResponse;

import static io.restassured.RestAssured.given;
import static orders.OrderRequestSpecification.requestSpec;

public class OrderMethods {

    @Step("Добавление нового заказа")
    public ValidatableResponse createNewOrder(Order order) {
        return given().
                spec(requestSpec()).
                body(order).
                when().
                post().
                then();
    }

    @Step("Получение списка всех заказов")
    public ValidatableResponse getAllOrders() {
        return given().
                spec(requestSpec()).
                when().
                get().
                then();
    }
}
