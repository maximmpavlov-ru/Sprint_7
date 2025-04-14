import courier.*;
import io.qameta.allure.junit4.DisplayName;
import io.restassured.response.ValidatableResponse;
import org.junit.*;

import java.util.ArrayList;
import java.util.List;

import static org.apache.http.HttpStatus.*;

public class CourierCreationTest {

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
    @DisplayName("Проверка успешной попытки создания курьера")
    public void courierCreationTest() {
        ValidatableResponse createResponse = COURIER_METHODS.createCourier(COURIER);
        int statusCode = createResponse.extract().statusCode();
        boolean isCreated = createResponse.extract().path("ok");
        Assert.assertEquals("Код ответа должен быть 201", SC_CREATED, statusCode);
        Assert.assertTrue("Сообщение о создании курьера не получено", isCreated);
        ;
        courierCredentials = CourierCredentials.getCourierCredentials(COURIER);
        ValidatableResponse loginResponse = COURIER_METHODS.loginCourier(courierCredentials);
        Integer id = loginResponse.extract().path("id");
        COURIERS_IDS.add(id);
    }

    @Test
    @DisplayName("Попытка создания курьера с неполными данными")
    public void courierWithoutLoginCreationTest() {
        ValidatableResponse createResponse = COURIER_METHODS.createCourier(COURIER_WITHOUT_LOGIN);
        int statusCode = createResponse.extract().statusCode();
        String responseMessageText = createResponse.extract().path("message");
        Assert.assertEquals("Код ответа должен быть 400", SC_BAD_REQUEST, statusCode);
        Assert.assertEquals(
                "Система должна возвращать следующий текст ошибки: \"Недостаточно данных для создания учетной записи\"",
                "Недостаточно данных для создания учетной записи", responseMessageText);
    }

    @Test
    @DisplayName("Попытка создания курьера с логином, который уже существует в системе")
    public void courierDuplicateCreationTest() {
        Courier COURIER_DUPLICATE = COURIER;
        COURIER_METHODS.createCourier(COURIER);
        ValidatableResponse createResponse = COURIER_METHODS.createCourier(COURIER_DUPLICATE);
        int statusCode = createResponse.extract().statusCode();
        String responseMessageText = createResponse.extract().path("message");
        Assert.assertEquals("Код ответа должен быть 409", SC_CONFLICT, statusCode);
        Assert.assertEquals(
                "Система должна возвращать следующий текст ошибки: \"Этот логин уже используется. Попробуйте другой.\"",
                "Этот логин уже используется. Попробуйте другой.", responseMessageText);
        courierCredentials = CourierCredentials.getCourierCredentials(COURIER);
        ValidatableResponse loginResponse = COURIER_METHODS.loginCourier(courierCredentials);
        Integer id = loginResponse.extract().path("id");
        COURIERS_IDS.add(id);
    }

    @After
    public void cleanUp() {
        for (Integer id : COURIERS_IDS) {
            CourierMethods.deleteCourierList(id);
        }
    }
}
