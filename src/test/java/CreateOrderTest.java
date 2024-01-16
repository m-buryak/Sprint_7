import org.apache.commons.lang3.RandomStringUtils;
import org.apache.http.HttpStatus;
import org.example.client.OrderClient;
import org.example.steps.OrderSteps;
import org.hamcrest.Matchers;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

import java.util.List;
import java.util.Random;

import static io.restassured.path.json.JsonPath.given;
import static org.hamcrest.CoreMatchers.notNullValue;

@RunWith(Parameterized.class)
public class CreateOrderTest {

    private List<String> color;
    private OrderSteps orderSteps;

    @Before
    public void setUp() {
        orderSteps = new OrderSteps(new OrderClient());
    }

    public CreateOrderTest(List<String> color) {
        this.color = color;
    }



    @Parameterized.Parameters
    public static Object[] getColor() {
        return new Object[]{
                List.of("BLACK"),
                List.of("GREY"),
                List.of("BLACK", "GREY"),
                List.of()
        };
    }

    @Test
    public void createOrderTest() {
        String firstName = RandomStringUtils.random(10);
        String lastName = RandomStringUtils.random(10);
        String address = RandomStringUtils.random(10);
        String metroStation = String.valueOf(new Random().nextInt(100 - 1 + 1) + 1);
        String phone = RandomStringUtils.random(10);
        Integer rentTime = new Random().nextInt(10 - 1 + 1) + 1;
        String deliveryDate = (new Random().nextInt(2024 - 2023 + 1) + 2023) + "-" +
                (new Random().nextInt( 12 - 1 + 1) + 1) + "-" +
                (new Random().nextInt( 28 - 1 + 1) + 1);
        String comment = RandomStringUtils.random(10);


        orderSteps.create(firstName, lastName, address, metroStation, phone, rentTime, deliveryDate, comment, color)
                .statusCode(HttpStatus.SC_CREATED)
                .body("track", Matchers.notNullValue());
    }
}
