package praktikum.user;
import praktikum.UserClient;
import praktikum.UserGenerator;
import io.qameta.allure.junit4.DisplayName;
import io.restassured.response.ValidatableResponse;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import static org.apache.http.HttpStatus.*;
import static org.hamcrest.Matchers.*;

public class UserLoginTest {
    private UserClient userClient;
    private UserGenerator user;
    private String token;

    @Before
    public void setUp() {
        userClient = new UserClient();
        user = UserGenerator.getRandom();

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
    @DisplayName("Вход авторизованного пользователя")
    public void loginUser() {
        userClient.loginUser(user)
                .assertThat()
                .statusCode(SC_OK)
                .extract()
                .path("success");
    }

    @Test
    @DisplayName("Вход с невалидным паролем")
    public void loginUserPasswordInvalid() {
        user.setPassword("invalid password");
        userClient.loginUser(user)
                .assertThat()
                .statusCode(SC_UNAUTHORIZED)
                .and()
                .body("message", equalTo("email or password are incorrect"))
                .extract()
                .path("success");
    }

    @Test
    @DisplayName("Вход с невалидным Email")
    public void loginUserEmailInvalid() {
        user.setEmail("lolKekovich@gmail.ru");
        userClient.loginUser(user)
                .assertThat()
                .statusCode(SC_UNAUTHORIZED)
                .and()
                .body("message", equalTo("email or password are incorrect"))
                .extract()
                .path("success");
    }
}
