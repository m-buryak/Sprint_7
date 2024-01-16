package org.example.client;

import io.restassured.response.Response;
import org.example.dto.OrderCreateRequest;

public class OrderClient extends RestClient{

    public Response create(OrderCreateRequest orderCreateRequest) {
        return getDefaultRequestSpecification()
                .body(orderCreateRequest)
                .when()
                .post("orders");
    }
}
