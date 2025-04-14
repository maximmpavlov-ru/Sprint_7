package common;

import io.restassured.builder.RequestSpecBuilder;
import io.restassured.http.ContentType;

public class RequestSpecification {

    private static final String BASE_URL = "https://qa-scooter.praktikum-services.ru/api/v1";

    public static io.restassured.specification.RequestSpecification requestSpec() {
        return new RequestSpecBuilder()
                .setBaseUri(BASE_URL)
                .setContentType(ContentType.JSON)
                .build();
    }
}
