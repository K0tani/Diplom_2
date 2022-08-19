package praktikum;
import lombok.*;
import java.util.List;

@Data
@AllArgsConstructor
public class IngredientsRequest {

    List<String> ingredients;
}
