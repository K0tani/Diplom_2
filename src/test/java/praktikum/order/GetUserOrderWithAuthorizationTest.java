package praktikum.order;
import io.qameta.allure.junit4.DisplayName;
import io.restassured.response.Response;
import io.restassured.response.ValidatableResponse;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import praktikum.UserClient;
import praktikum.IngredientsClient;
import praktikum.OrderClient;
import praktikum.Ingredients;
import praktikum.IngredientsRequest;
import praktikum.IngredientsResponse;
import praktikum.UserGenerator;
import java.util.ArrayList;
import static org.apache.http.HttpStatus.*;
import static org.hamcrest.Matchers.is;

public class GetUserOrderWithAuthorizationTest {
    private OrderClient orderClient;
    private UserClient userClient;
    private IngredientsClient ingredientsClient;
    private UserGenerator user;
    private String token;
    private IngredientsRequest ingredientsRequest;

    @Before
    public void setUp() {
        orderClient = new OrderClient();
        userClient = new UserClient();
        ingredientsClient = new IngredientsClient();
        user = UserGenerator.getRandom();

        ValidatableResponse responseUser = userClient.createUser(user);
        token = responseUser.extract().path("accessToken");
        responseUser.assertThat()
                .statusCode(SC_OK)
                .extract()
                .path("accessToken");

        Response ingredients = ingredientsClient.getIngredients();
        ingredients.then().assertThat().statusCode(SC_OK);
        Ingredients[] getIngredients = ingredients.body().as(IngredientsResponse.class).getData();
        ArrayList<String> ingredientsList = new ArrayList<>();
        ingredientsList.add(getIngredients[0]._id);
        ingredientsList.add(getIngredients[1]._id);
        ingredientsRequest = new IngredientsRequest(ingredientsList);
        orderClient.createOrder(ingredientsRequest, token)
                .assertThat()
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
    @DisplayName("Получить список заказов для авторизованного пользователя")
    public void getUserOrderWithAuthorization() {
        orderClient.getUserOrderWithAuthorization(token)
                .assertThat()
                .statusCode(SC_OK)
                .extract()
                .path("success");
    }
}
