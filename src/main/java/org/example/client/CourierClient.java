package org.example.client;

import io.restassured.response.Response;
import org.example.dto.CourierCreateRequest;
import org.example.dto.CourierLoginRequest;

public class CourierClient extends RestClient{
    public Response create(CourierCreateRequest courierCreateRequest) {
        return getDefaultRequestSpecification()
                .body(courierCreateRequest)
                .when()
                .post("courier");
    }

    public Response login(CourierLoginRequest courierLoginRequest) {
        return getDefaultRequestSpecification()
                .body(courierLoginRequest)
                .when()
                .post("courier/login");
    }

    public Response delete(int id) {
        return getDefaultRequestSpecification()
                .when()
                .delete("courier/" + id);
    }
}
