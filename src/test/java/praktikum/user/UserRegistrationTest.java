package praktikum.user;
import io.qameta.allure.junit4.DisplayName;
import io.restassured.response.ValidatableResponse;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import praktikum.UserClient;
import praktikum.UserGenerator;
import static org.apache.http.HttpStatus.*;
import static org.hamcrest.Matchers.is;

public class UserRegistrationTest {
    private UserClient userClient;
    private UserGenerator user;
    private String token;

    @Before
    public void setUp() {
        userClient = new UserClient();
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
    @DisplayName("Создание пользователя")
    public void registrationUser() {
        user = UserGenerator.getRandom();

        ValidatableResponse responseUser = userClient.createUser(user);
        token = responseUser.extract().path("accessToken");

        responseUser.assertThat()
                .statusCode(SC_OK)
                .extract()
                .path("accessToken");
    }
}
