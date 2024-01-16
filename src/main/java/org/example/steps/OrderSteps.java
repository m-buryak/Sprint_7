package org.example.steps;

import io.qameta.allure.Step;
import io.restassured.response.ValidatableResponse;
import org.example.client.OrderClient;
import org.example.dto.OrderCreateRequest;

import java.util.List;

public class OrderSteps {
    private final OrderClient orderClient;

    public OrderSteps(OrderClient orderClient) {
        this.orderClient = orderClient;
    }

    @Step
    public ValidatableResponse create(String firstName, String lastName, String address, String metroStation, String phone, Integer rentTime, String deliveryDate, String comment, List<String> color) {
        OrderCreateRequest requestBody = new OrderCreateRequest();
        requestBody.setFirstName(firstName);
        requestBody.setLastName(lastName);
        requestBody.setAddress(address);
        requestBody.setMetroStation(metroStation);
        requestBody.setPhone(phone);
        requestBody.setRentTime(rentTime);
        requestBody.setDeliveryDate(deliveryDate);
        requestBody.setComment(comment);
        requestBody.setColor(color);
        return orderClient.create(requestBody)
                .then();
    }
}
