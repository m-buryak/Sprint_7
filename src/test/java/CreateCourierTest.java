import org.apache.commons.lang3.RandomStringUtils;
import org.apache.http.HttpStatus;
import org.example.client.CourierClient;
import org.example.steps.CourierSteps;
import org.hamcrest.Matchers;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

public class CreateCourierTest {
    private static CourierSteps courierSteps;
    private static List<Integer> ids = new ArrayList<>();

    @Before
    public void setUp() {
        courierSteps = new CourierSteps(new CourierClient());
    }

    @Test
    public void successfulCreateCourierTest() {
        String login = RandomStringUtils.random(10);
        String password = RandomStringUtils.random(10);
        String firstName = RandomStringUtils.random(10);

        courierSteps.create(login, password, firstName)
                .statusCode(HttpStatus.SC_CREATED);

        Integer id = courierSteps.login(login, password)
                .statusCode(HttpStatus.SC_OK)
                .extract().path("id");
        ids.add(id);
    }

    @Test
    public void messageWhenSuccessfulCreateCourierTest() {
        String login = RandomStringUtils.random(10);
        String password = RandomStringUtils.random(10);
        String firstName = RandomStringUtils.random(10);

        courierSteps.create(login, password, firstName)
                .body("ok", Matchers.is(true));

        Integer id = courierSteps.login(login, password)
                .statusCode(HttpStatus.SC_OK)
                .extract().path("id");
        ids.add(id);
    }


    @Test
    public void cannotCreateTwoIdenticalCouriersTest() {
        String login = RandomStringUtils.random(10);
        String password = RandomStringUtils.random(10);
        String firstName = RandomStringUtils.random(10);

        courierSteps.create(login, password, firstName);

        courierSteps.create(login, password, firstName)
                .statusCode(HttpStatus.SC_CONFLICT)
                .body("message", Matchers.is("Этот логин уже используется"));

        Integer id = courierSteps.login(login, password)
                .statusCode(HttpStatus.SC_OK)
                .extract().path("id");
        ids.add(id);
    }

    @Test
    public void createCourierWithoutLoginTest() {
        String login = null;
        String password = RandomStringUtils.random(10);
        String firstName = RandomStringUtils.random(10);

        courierSteps.create(login, password, firstName)
                .statusCode(HttpStatus.SC_BAD_REQUEST)
                .body("message", Matchers.is("Недостаточно данных для создания учетной записи"));
   }

    @Test
    public void createCourierWithoutPasswordTest() {
        String login = RandomStringUtils.random(10);
        String password = null;
        String firstName = RandomStringUtils.random(10);

        courierSteps.create(login, password, firstName)
                .statusCode(HttpStatus.SC_BAD_REQUEST)
                .body("message", Matchers.is("Недостаточно данных для создания учетной записи"));

    }

    @AfterClass
    public static void tearDown() {
        for (Integer id : ids) {
            if (id != null) {
                courierSteps.delete(id);
            }

        }
    }

}
