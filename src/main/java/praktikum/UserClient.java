package praktikum;
import io.qameta.allure.Step;
import io.restassured.response.ValidatableResponse;

import static io.restassured.RestAssured.given;

public class UserClient extends RestAssuredClient {
    private final String USER_AUTH_REGISTER_PATH = "/auth/register";
    private final String USER_AUTH_LOGIN_PATH = "/auth/login";
    private final String USER_AUTH_USER = "/auth/user";

    @Step("Создание пользователя")
    public ValidatableResponse createUser(UserGenerator user) {
        return given()
                .spec(requestSpecification())
                .body(user)
                .when()
                .post(USER_AUTH_REGISTER_PATH)
                .then().log().ifError();
    }

    @Step("Авторизация пользователя")
    public ValidatableResponse loginUser(UserGenerator user) {
        return given()
                .spec(requestSpecification())
                .body(user)
                .when()
                .post(USER_AUTH_LOGIN_PATH)
                .then().log().ifError();
    }
    @Step("Обновление пользователя без авторизации")
    public ValidatableResponse updateUserWithoutAuthorization(UserGenerator user) {
        return given()
                .spec(requestSpecification())
                .body(user)
                .when()
                .patch(USER_AUTH_USER)
                .then().log().ifError();
    }

    @Step("Обновление пользователя с авторизации")
    public ValidatableResponse updateUser(UserGenerator user, String token) {
        return given()
                .spec(requestSpecification())
                .header("Authorization", token)
                .body(user)
                .when()
                .patch(USER_AUTH_USER)
                .then().log().ifError();
    }

    @Step("Удаление пользователя")
    public ValidatableResponse deleteUser(String token) {
        return given()
                .spec(requestSpecification())
                .header("Authorization", token)
                .when()
                .delete(USER_AUTH_USER)
                .then().log().ifError();
    }
}
