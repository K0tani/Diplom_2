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

public class UserRegistrationAlreadyExistsTest {
    private UserClient userClient;
    private UserGenerator user;
    private String secondUserToken;
    private String firstUserToken;

    @Before
    public void setUp() {
        userClient = new UserClient();
        user = UserGenerator.getRandom();

        ValidatableResponse responseUser = userClient.createUser(user);
        secondUserToken = responseUser.extract().path("accessToken");
        responseUser.assertThat()
                .statusCode(SC_OK)
                .extract()
                .path("success");
    }

    @After
    public void clear() {
        if(firstUserToken != null){
            userClient.deleteUser(firstUserToken)
                    .assertThat()
                    .statusCode(SC_ACCEPTED)
                    .and()
                    .body("success", is(true))
                    .and()
                    .body("message", is("User successfully removed"));
        }
        userClient.deleteUser(secondUserToken)
                .assertThat()
                .statusCode(SC_ACCEPTED)
                .extract()
                .path("success");
    }

    @Test
    @DisplayName("Создание пользователя, который уже зарегистрирован")
    public void createUserAlreadyExists() {
        ValidatableResponse responseUser = userClient.createUser(user);
        int statusCode = responseUser.extract().statusCode();
        if (statusCode == SC_OK) {
            firstUserToken = responseUser.extract().path("accessToken");
        }
        responseUser.assertThat()
                .statusCode(SC_FORBIDDEN)
                .and()
                .body("message", equalTo("User already exists"))
                .extract()
                .path("success");

    }

}
