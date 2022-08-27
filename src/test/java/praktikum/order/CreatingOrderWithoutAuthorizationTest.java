package praktikum.order;
import io.qameta.allure.junit4.DisplayName;
import io.restassured.response.Response;
import org.junit.Before;
import org.junit.Test;
import praktikum.IngredientsClient;
import praktikum.OrderClient;
import praktikum.Ingredients;
import praktikum.IngredientsRequest;
import praktikum.IngredientsResponse;
import java.util.ArrayList;
import static org.apache.http.HttpStatus.SC_OK;

public class CreatingOrderWithoutAuthorizationTest {
    private OrderClient orderClient;
    private IngredientsClient ingredientsClient;
    private IngredientsRequest ingredientsRequest;
    private Response ingredients;

    @Before
    public void setUp() {
        orderClient = new OrderClient();
        ingredientsClient = new IngredientsClient();

        ingredients = ingredientsClient.getIngredients();
        ingredients.then().assertThat().statusCode(SC_OK);
        Ingredients[] getIngredients = ingredients.body().as(IngredientsResponse.class).getData();
        ArrayList<String> ingredientsList = new ArrayList<>();
        ingredientsList.add(getIngredients[0].get_id());
        ingredientsList.add(getIngredients[1].get_id());
        ingredientsRequest = new IngredientsRequest(ingredientsList);
    }

    @Test
    @DisplayName("Запрос на создание заказа без авторизации")
    public void createOrderWithIngredientWithoutAuthorization() {
        orderClient.createOrderWithIngredientWithoutAuthorization(ingredientsRequest)
                .assertThat()
                .statusCode(SC_OK)
                .extract()
                .path("success");
    }
}
