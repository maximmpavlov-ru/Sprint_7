import io.qameta.allure.junit4.DisplayName;
import io.restassured.response.ValidatableResponse;
import orders.OrderMethods;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

import java.util.ArrayList;

public class OrderListReceivingTest {

    private static OrderMethods ORDER_METHODS;

    @BeforeClass
    public static void prepareTestData() {
        ORDER_METHODS = new OrderMethods();
    }

    @Test
    @DisplayName("Проверка кода ответа и наличия в нем данных при получении списка заказов")
    public void getAllOrders() {
        ValidatableResponse getAllOrdersRequestResponse = ORDER_METHODS.getAllOrders();
        int statusCode = getAllOrdersRequestResponse.extract().statusCode();
        ArrayList<String> orderBody = getAllOrdersRequestResponse.extract().path("orders");
        Assert.assertEquals("Вернулся некорректный код состояния, должен быть 200", 200, statusCode);
        Assert.assertTrue("Список заказов не должен быть пустым",
                (orderBody!=null) && (!orderBody.isEmpty()));
    }
}
