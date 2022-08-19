package praktikum.user;
import io.qameta.allure.junit4.DisplayName;
import org.junit.Before;
import org.junit.Test;
import praktikum.UserClient;
import praktikum.UserGenerator;
import static org.apache.http.HttpStatus.*;
import static org.hamcrest.Matchers.equalTo;

public class UserRegistrationWithEmptyFieldsTest {
    private UserClient userClient;
    private UserGenerator user;

    @Before
    public void setUp() {
        userClient = new UserClient();
    }

    @Test
    @DisplayName("Создание пользователя без email")
    public void createUserWithEmptyEmail() {
        user = new UserGenerator("", "password", "Кшиштовский");
        userClient.createUser(user)
                .assertThat()
                .statusCode(SC_FORBIDDEN)
                .and()
                .body("message", equalTo("Email, password and name are required fields"))
                .extract()
                .path("success");

    }

    @Test
    @DisplayName("Создание пользователя без password")
    public void createUserWithEmptyPassword() {
        user = new UserGenerator("PoraValit@gmail.com", "", "Кир Агашов");
        userClient.createUser(user)
                .assertThat()
                .statusCode(SC_FORBIDDEN)
                .and()
                .body("message", equalTo("Email, password and name are required fields"))
                .extract()
                .path("success");

    }

    @Test
    @DisplayName("Создание пользователя без имени")
    public void createUserWithEmptyUsername() {
        user = new UserGenerator("PoraValitDavnoZakrilsya@gmail.com", "password", "");
        userClient.createUser(user)
                .assertThat()
                .statusCode(SC_FORBIDDEN)
                .and()
                .body("message", equalTo("Email, password and name are required fields"))
                .extract()
                .path("success");

    }
}
