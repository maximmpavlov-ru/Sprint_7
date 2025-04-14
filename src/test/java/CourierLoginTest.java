import courier.*;
import io.qameta.allure.junit4.DisplayName;
import io.restassured.response.ValidatableResponse;
import org.junit.After;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

import static org.apache.http.HttpStatus.*;

import java.util.ArrayList;
import java.util.List;

public class CourierLoginTest {

    private static Courier COURIER;
    private static Courier COURIER_WITHOUT_LOGIN;
    private CourierCredentials courierCredentials;
    private static CourierMethods COURIER_METHODS;
    private static final List<Integer> COURIERS_IDS = new ArrayList<>();

    @BeforeClass
    public static void prepareTestData() {
        COURIER = CourierData.getNormalCourierData(CourierLoginGenerator.generateRandomLogin());
        COURIER_WITHOUT_LOGIN = CourierData.getCourierWithoutLogin();
        COURIER_METHODS = new CourierMethods();
    }

    @Test
    @DisplayName("Проверка успешной попытки входа в систему существующего курьера")
    public void courierSuccessLoginTest() {
        COURIER_METHODS.createCourier(COURIER);
        courierCredentials = CourierCredentials.getCourierCredentials(COURIER);
        ValidatableResponse loginResponse = COURIER_METHODS.loginCourier(courierCredentials);
        int statusCode = loginResponse.extract().statusCode();
        Integer id = loginResponse.extract().path("id");
        Assert.assertEquals("Код ответа должен быть 200", SC_OK, statusCode);
        Assert.assertNotNull("Система должна возвращать ID в случае успешного логина", id);
        COURIERS_IDS.add(id);
    }

    @Test
    @DisplayName("Проверка попытки авторизации без указания всех обязательных полей")
    public void courierLoginWithoutAllRequiredCredentialData() {
        courierCredentials = CourierCredentials.getCourierCredentials(COURIER_WITHOUT_LOGIN);
        ValidatableResponse loginResponse = COURIER_METHODS.loginCourier(courierCredentials);
        int statusCode = loginResponse.extract().statusCode();
        String responseMessageText = loginResponse.extract().path("message");
        Assert.assertEquals("Код ответа должен быть 400", SC_BAD_REQUEST, statusCode);
        Assert.assertEquals(
                "Система должна возвращать следующий текст ошибки: \"Недостаточно данных для входа\"",
                "Недостаточно данных для входа", responseMessageText);
    }

    @Test
    @DisplayName("Проверка попытки авторизации с несуществующей парой логин-пароль")
    public void courierLoginWithNonExistingCredentials() {
        courierCredentials = CourierCredentials.getCourierCredentials(COURIER);
        ValidatableResponse loginResponse = COURIER_METHODS.loginCourier(courierCredentials);
        int statusCode = loginResponse.extract().statusCode();
        String responseMessageText = loginResponse.extract().path("message");
        Assert.assertEquals("Код ответа должен быть 404", SC_NOT_FOUND, statusCode);
        Assert.assertEquals(
                "Система должна возвращать следующий текст ошибки: \"Учетная запись не найдена\"",
                "Учетная запись не найдена", responseMessageText);
    }

    @After
    public void cleanUp() {
        for (Integer id : COURIERS_IDS) {
            CourierMethods.deleteCourierList(id);
        }
    }
}
