package praktikum;
import io.qameta.allure.Step;
import io.restassured.response.ValidatableResponse;
import static io.restassured.RestAssured.given;

public class OrderClient extends RestAssuredClient {

    public final String PATH = "/orders";

    @Step("Cоздание заказа")
    public ValidatableResponse createOrder(IngredientsRequest ingredientsRequest, String token) {
        return given()
                .spec(requestSpecification())
                .header("Authorization", token)
                .body(ingredientsRequest)
                .when().log().all()
                .post(PATH)
                .then().log().ifError();
    }

    @Step("Cоздание заказа без авторизации")
    public ValidatableResponse createOrderWithIngredientWithoutAuthorization(IngredientsRequest ingredientsRequest) {
        return given()
                .spec(requestSpecification())
                .body(ingredientsRequest)
                .when()
                .post(PATH)
                .then().log().ifError();
    }

    @Step("Получить заказы. Клиент авторизован")
    public ValidatableResponse getUserOrderWithoutAuthorization() {
        return given()
                .spec(requestSpecification())
                .when()
                .get(PATH)
                .then().log().ifError();
    }

    @Step("Получить заказы. Клиент не авторизован")
    public ValidatableResponse getUserOrderWithAuthorization(String token) {
        return given()
                .spec(requestSpecification())
                .header("Authorization", token)
                .when()
                .get(PATH)
                .then().log().ifError();
    }
}