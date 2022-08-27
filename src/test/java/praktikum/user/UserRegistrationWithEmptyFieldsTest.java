package praktikum.user;
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

public class UserRegistrationWithEmptyFieldsTest {
    private UserClient userClient;
    private UserGenerator user;
    private String token;


    @Before
    public void setUp() {
        userClient = new UserClient();
    }

    @After
    public void clear() {
        if (token != null) {
            System.out.println("Pribvrt");
            userClient.deleteUser(token)
                    .assertThat()
                    .statusCode(SC_ACCEPTED).log().all()
                    .and()
                    .body("success", is(true)).log().all()
                    .and()
                    .body("message", is("User successfully removed"));
        }
    }

    @Test
    @DisplayName("Создание пользователя без email")
    public void createUserWithEmptyEmail() {
        user = new UserGenerator("", "password", "Кшиштовский");

        ValidatableResponse responseUser = userClient.createUser(user);
        token = responseUser.extract().path("accessToken");

        responseUser.assertThat()
                .statusCode(SC_FORBIDDEN)
                .and()
                .body("message", equalTo("Email, password and name are required fields"))
                .extract()
                .path("success");
    }

    @Test
    @DisplayName("Создание пользователя без password")
    public void createUserWithEmptyPassword() {
        user = new UserGenerator("PoraValit@gmail.com", "4324234343", "Кир Агашов");

        ValidatableResponse responseUser = userClient.createUser(user);
        token = responseUser.extract().path("accessToken");

        responseUser.assertThat()
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

        ValidatableResponse responseUser = userClient.createUser(user);
        token = responseUser.extract().path("accessToken");

        responseUser.assertThat()
                .statusCode(SC_FORBIDDEN)
                .and()
                .body("message", equalTo("Email, password and name are required fields"))
                .extract()
                .path("success");
    }

}
