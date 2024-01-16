import io.restassured.http.ContentType;
import io.restassured.response.ValidatableResponse;
import org.apache.commons.lang3.RandomStringUtils;
import org.apache.http.HttpStatus;
import org.example.client.CourierClient;
import org.example.dto.CourierCreateRequest;
import org.example.dto.CourierLoginRequest;
import org.example.steps.CourierSteps;
import org.hamcrest.Matcher;
import org.hamcrest.Matchers;
import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static io.restassured.RestAssured.given;
import static io.restassured.RestAssured.rootPath;

public class LoginCourierTest {
    private static CourierSteps courierSteps;
    private static List<Integer> ids = new ArrayList<>();

    @Before
    public void setUp() {
        courierSteps = new CourierSteps(new CourierClient());
    }

    @Test
    public void loginCourierTest() {
        String login = RandomStringUtils.random(10);
        String password = RandomStringUtils.random(10);
        String firstName = RandomStringUtils.random(10);

        courierSteps.create(login, password, firstName);

        ids.add(courierSteps.login(login, password)
                .statusCode(HttpStatus.SC_OK)
                .body("id", Matchers.notNullValue()).extract().path("id"));
    }

    @Test
    public void loginWithIncorrectLoginTest() {
        String login = RandomStringUtils.random(10);
        String password = RandomStringUtils.random(10);
        String firstName = RandomStringUtils.random(10);

        courierSteps.create(login, password, firstName);

        courierSteps.login(login + RandomStringUtils.random(1), password)
                .statusCode(HttpStatus.SC_NOT_FOUND)
                .body("message", Matchers.is("Учетная запись не найдена"));
    }

    @Test
    public void loginWithIncorrectPasswordTest() {
        String login = RandomStringUtils.random(10);
        String password = RandomStringUtils.random(10);
        String firstName = RandomStringUtils.random(10);

        courierSteps.create(login, password, firstName);

        courierSteps.login(login, password + RandomStringUtils.random(1))
                .statusCode(HttpStatus.SC_NOT_FOUND)
                .body("message", Matchers.is("Учетная запись не найдена"));
    }

    @Test
    public void loginWithoutLoginTest() {
        String login = RandomStringUtils.random(10);
        String password = RandomStringUtils.random(10);
        String firstName = RandomStringUtils.random(10);

        courierSteps.create(login, password, firstName);

        courierSteps.login(null, password)
                .statusCode(HttpStatus.SC_BAD_REQUEST)
                .body("message", Matchers.is("Недостаточно данных для входа"));
    }

    @Test
    public void loginWithoutPasswordTest() {
        String login = RandomStringUtils.random(10);
        String password = RandomStringUtils.random(10);
        String firstName = RandomStringUtils.random(10);

        courierSteps.create(login, password, firstName);

        courierSteps.login(login, null)
                .statusCode(HttpStatus.SC_BAD_REQUEST)
                .body("message", Matchers.is("Недостаточно данных для входа"));
    }

    @Test
    public void loginWithNonExistingUserTest() {
        String login = RandomStringUtils.random(10);
        String password = RandomStringUtils.random(10);

        courierSteps.login(login, password)
                .statusCode(HttpStatus.SC_NOT_FOUND)
                .body("message", Matchers.is("Учетная запись не найдена"));
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
