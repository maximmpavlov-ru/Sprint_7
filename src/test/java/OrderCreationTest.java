import io.qameta.allure.junit4.DisplayName;
import orders.Order;
import orders.OrderColors;
import orders.OrderGenerator;
import orders.OrderMethods;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

@RunWith(Parameterized.class)
public class OrderCreationTest {
    private Order order;
    private OrderMethods orderMethods;
    private final String[] color;

    @Parameterized.Parameters
    public static Object[][] orderColors() {
        return new Object[][]{
//                {"BLACK"},
//                {"BLACK", "GREY"},
//                {"GREY"},
//                {""}
                {OrderColors.ORDER_COLOR_BLACK},
                {OrderColors.ORDER_COLOR_GREY},
                {OrderColors.ORDER_COLOR_EMPTY},
                {OrderColors.ORDER_COLOR_BLACK_AND_GREY}
        };
    }

    public OrderCreationTest(String[] color) {
        this.color = color;
    }

    @Before
    public void prepareTestData() {
        order = OrderGenerator.generateOrder(color);
        orderMethods = new OrderMethods();
    }

    @Test
    @DisplayName("Проверка создания заказов с использованием разных цветов")
    public void createOrder() {
        int statusCode = orderMethods.createNewOrder(order).extract().statusCode();
        Assert.assertEquals("Вернулся некорректный код состояния, должен быть 201", 201, statusCode);
        int orderResponseMessage = orderMethods.createNewOrder(order).extract().path("track");
        Assert.assertTrue("Значение параметра \"track\" не должно быть пустым", orderResponseMessage > 0);

    }
}