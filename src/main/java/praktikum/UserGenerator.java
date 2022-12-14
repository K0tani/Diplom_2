package praktikum;
import com.github.javafaker.Faker;
import lombok.*;

@Data
@AllArgsConstructor
public class UserGenerator {

    private String email;
    private String password;
    private String name;

    public static UserGenerator getRandom() {
        Faker faker = new Faker();
        String email = faker.internet().emailAddress();
        String password = faker.lorem().characters(10, true);
        String name = faker.name().name();
        UserGenerator user = new UserGenerator(email, password, name);
        return user;
    }

}
