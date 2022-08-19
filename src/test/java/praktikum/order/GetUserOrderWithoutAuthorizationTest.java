package praktikum.order;
import io.qameta.allure.junit4.DisplayName;
import org.junit.Before;
import org.junit.Test;
import praktikum.OrderClient;
import static org.apache.http.HttpStatus.SC_UNAUTHORIZED;

public class GetUserOrderWithoutAuthorizationTest {
    private OrderClient orderClient;

    @Before
    public void setUp() {
        orderClient = new OrderClient();
    }

    @Test
    @DisplayName("Получить список заказов для неавторизованного пользователя")
    public void getUserOrderWithoutAuthorization() {
        orderClient.getUserOrderWithoutAuthorization()
                .assertThat()
                .statusCode(SC_UNAUTHORIZED)
                .extract()
                .path("success");
    }
}
