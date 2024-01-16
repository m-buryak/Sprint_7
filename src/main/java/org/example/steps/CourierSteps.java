package org.example.steps;

import io.qameta.allure.Step;
import io.restassured.response.ValidatableResponse;
import org.apache.commons.lang3.RandomStringUtils;
import org.example.client.CourierClient;
import org.example.dto.CourierCreateRequest;
import org.example.dto.CourierLoginRequest;

public class CourierSteps {
    private final CourierClient courierClient;

    public CourierSteps(CourierClient courierClient) {
        this.courierClient = courierClient;
    }

    @Step
    public ValidatableResponse create(String login, String password, String firstName) {
        CourierCreateRequest requestBody = new CourierCreateRequest(login, password, firstName);
        return courierClient.create(requestBody)
                .then();
    }

    @Step
    public ValidatableResponse login(String login, String password) {
        CourierLoginRequest requestBody = new CourierLoginRequest(login, password);
        return courierClient.login(requestBody)
                .then();
    }

    @Step
    public ValidatableResponse delete(int id) {
        return courierClient.delete(id)
                .then();
    }

}
