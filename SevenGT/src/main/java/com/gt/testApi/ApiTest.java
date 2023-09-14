package com.gt.testApi;

import com.gt.config.ApiConfigFactory;
import com.gt.test.BaseTest;
import io.restassured.response.Response;
import org.testng.annotations.Test;
import java.util.Map;


public class ApiTest extends BaseTest {

    private static final String BASE_URL = ApiConfigFactory.getConfig().apiBaseUrl();
    private static final String LIST_USERS_ENDPOINT = ApiConfigFactory.getConfig().ListUserEndPoint();

    @Test( dataProvider = "getData",dataProviderClass = com.gt.utils.DataSupplierTest.class)
    public void SevenBossTest1(Map<Object,Object> data) throws InterruptedException {
        ApiRequests apiRequest = new ApiRequests();
        Response response =  apiRequest.getUsersApi();
        response.prettyPrint();

         // Can do Assertion Here
    }

    @Test( dataProvider = "getData",dataProviderClass = com.gt.utils.DataSupplierTest.class)
    public void SevenBossTest2(Map<Object,Object> data) throws InterruptedException {
        ApiRequests apiRequest = new ApiRequests();
        Response response =  apiRequest.getUsersApi();
        response.prettyPrint();

        // Can do Assertion Here
    }



}
