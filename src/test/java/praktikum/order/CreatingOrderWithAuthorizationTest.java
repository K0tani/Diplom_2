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
import praktikum.IngredientsResponse;
import praktikum.IngredientsRequest;
import praktikum.UserGenerator;
import java.util.ArrayList;
import static org.apache.http.HttpStatus.*;
import static org.hamcrest.Matchers.is;

public class CreatingOrderWithAuthorizationTest {
    private OrderClient orderClient;
    private UserClient userClient;
    private IngredientsClient ingredientsClient;
    private UserGenerator user;
    private IngredientsRequest ingredientsRequest;
    private String token;
    private Response ingredients;

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
    @DisplayName("Отправить запрос на создание заказа для авторизованного пользователя")
    public void createOrderWithAuthorization() {
        ingredients = ingredientsClient.getIngredients();
        ingredients.then().assertThat().statusCode(SC_OK);
        Ingredients[] getIngredients = ingredients.body().as(IngredientsResponse.class).getData();
        ArrayList<String> ingredientsList = new ArrayList<>();
        ingredientsList.add(getIngredients[0]._id);
        ingredientsList.add(getIngredients[1]._id);
        ingredientsList.add(getIngredients[2]._id);
        ingredientsRequest = new IngredientsRequest(ingredientsList);
        orderClient.createOrder(ingredientsRequest, token)
                .assertThat()
                .statusCode(SC_OK)
                .extract()
                .path("success");
    }

    @Test
    @DisplayName("Отправить запрос на создание заказа для авторизованного пользователя, но с невалидным хешем ингредиента")
    public void createOrderWithInvalidIngredientHash() {
        ArrayList<String> ingredients = new ArrayList<>();
        ingredients.add("0931234");
        ingredientsRequest = new IngredientsRequest(ingredients);
        orderClient.createOrder(ingredientsRequest, token)
                .assertThat()
                .statusCode(SC_INTERNAL_SERVER_ERROR);
    }

    @Test
    @DisplayName("Отправить запрос на создание заказа без ингредиентов")
    public void createEmptyOrder() {
        ingredientsRequest = new IngredientsRequest(new ArrayList<>());
        orderClient.createOrder(ingredientsRequest, token)
                .assertThat()
                .statusCode(SC_BAD_REQUEST)
                .extract()
                .path("success");
    }
}