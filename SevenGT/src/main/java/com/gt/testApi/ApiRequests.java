package com.gt.testApi;

import com.gt.config.ApiConfigFactory;
import io.restassured.RestAssured;
import io.restassured.response.Response;

public class ApiRequests {
    private static final String BASE_URL = ApiConfigFactory.getConfig().apiBaseUrl();
    private static final String LIST_USERS_ENDPOINT = ApiConfigFactory.getConfig().ListUserEndPoint();


    public Response getUsersApi() throws InterruptedException {
        Response response = null;
        response = RestAssured.given()
                .queryParam("page",2)
                .baseUri(BASE_URL)
                .get(LIST_USERS_ENDPOINT);
        return response ;

    }
}
