package praktikum.user;
import com.github.javafaker.Faker;
import io.qameta.allure.junit4.DisplayName;
import io.restassured.response.ValidatableResponse;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import praktikum.UserClient;
import praktikum.UserGenerator;
import static org.apache.http.HttpStatus.*;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.is;

public class UserInfoUpdateTest {
    private UserClient userClient;
    private UserGenerator user;
    private String token;
    private Faker faker;

    @Before
    public void setUp() {
        userClient = new UserClient();
        user = UserGenerator.getRandom();
        faker = new Faker();

        ValidatableResponse responseUser = userClient.createUser(user);
        token = responseUser.extract().path("accessToken");
        responseUser.assertThat()
                .statusCode(SC_OK)
                .extract()
                .path("success");
    }

    @After
    public void clear() {
        userClient.deleteUser(token)
                .assertThat()
                .statusCode(SC_ACCEPTED)
                .and()
                .body("success", is(true))
                .and()
                .body("message", is("User successfully removed"));
    }

    @Test
    @DisplayName("Обновление имени пользователя")
    public void updateNameUser() {
        String name = faker.name().name();
        user.setName(name);
        userClient.updateUser(user, token)
                .assertThat()
                .statusCode(SC_OK)
                .and()
                .body("user.name", equalTo(name))
                .extract()
                .path("success");
    }

    @Test
    @DisplayName("Обновление email пользователя")
    public void updateEmailUser() {
        String email = faker.internet().emailAddress();
        user.setEmail(email);
        userClient.updateUser(user, token)
                .assertThat()
                .statusCode(SC_OK)
                .and()
                .body("user.email", equalTo(email))
                .extract()
                .path("success");
    }

    @Test
    @DisplayName("Обновление данных пользователя без авторизации")
    public void updateUserWithoutAuthorization() {
        userClient.updateUserWithoutAuthorization(user)
                .assertThat()
                .statusCode(SC_UNAUTHORIZED)
                .and()
                .body("message", equalTo("You should be authorised"))
                .extract()
                .path("success");
    }
}
