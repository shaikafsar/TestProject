package com.gt.utils;

import io.restassured.RestAssured;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Map;

public class ApiUtils {

    String authorization = "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJhdWRpZW5jZSI6IlNFVkVOQk9TUy1HTE9CQUwiLCJpc3N1ZXIiOiI3LUVsZXZlbiIsInN1YmplY3QiOiJhY2Nlc3MiLCJhcHBsaWNhdGlvbklkIjoiZWJiMmMwMWQtYTFjZi00ZWE3LWI5ZWQtMzg5OWViY2U1NDA0IiwiaWF0IjoxNjk0MTQxNjMzLCJleHAiOjE2OTQxNjMyMzN9.6LjEc5QRHc2eOE7l76dWDP01pv2gB7HBIpcIBy94XvY";

    public void eddProcess(Map<Object, Object> data) {
        Response getOrderDetailResponse = getOrderDetailResponse("88801");
        String modifiedResponse = modifyOrderDetails(getOrderDetailResponse, "170611", "8"); // Pass the Data from Excel
        String createdEddResponse = createEDD(modifiedResponse);
        processEdd(createdEddResponse);
    }

    public void eddProcess(String storeNo , String productCode , String qualityVal) {
        Response getOrderDetailResponse = getOrderDetailResponse(storeNo);
        String modifiedResponse = modifyOrderDetails(getOrderDetailResponse, productCode, qualityVal); // Pass the Data from Excel
        String createdEddResponse = createEDD(modifiedResponse);
        processEdd(createdEddResponse);
    }


    // API 1 - EDD - Retry Method
    public Response getOrderDetailResponse( String storeIdNo) {
        String responseBody = null ;
        String date = currentDate("format1");
        System.out.println("**** API EDD Execution Started ****");
        Response response = getOrderDetails("Approved", storeIdNo,date);
        responseBody = response.getBody().asString(); // converting as String
        //System.out.println("API 1 - Tried with Approved : "+responseBody);

            if (responseBody.contains("body")){
                System.out.println("API 1 - Tried with Approved, Valid Response: "+responseBody);
            }else {
                for (int i = 0; i <= 3; i++) {
                    if (responseBody.contains("Internal server error")) {
                        response = getOrderDetails("Approved", storeIdNo, date);
                        responseBody = response.getBody().asString();
                        System.out.println("API 1 - Tried with Approved again : "+responseBody);
                    }

                    if (responseBody.contains("No orders found for given store")) {
                        response = getOrderDetails("Received", storeIdNo, date);
                        responseBody = response.getBody().asString();
                        System.out.println("API 1 - Tried with Received : "+responseBody);
                    }

                    if (!responseBody.contains("Internal server error") || !responseBody.contains("No orders found for given store")) {
                        System.out.println("API 1 - Inside Break Condition");
                        break;
                    }
                }
            }
        return response ;
    }

    //  API 1 - EDD - Get Order Details
    public Response getOrderDetails(String changeStatus, String storeIdNo, String date) {
        RequestSpecification requestSpec = null;
        requestSpec = RestAssured.given();
        requestSpec.baseUri("https://7boss-api-dev.gccmex.io/");
        requestSpec.basePath("/storeintegration/outbounds/store-order");
        requestSpec.header("Authorization", authorization);
        requestSpec.queryParam("storeId", storeIdNo).queryParam("date", date) // Date should be updated always
                .queryParam("orderChangeStatus", changeStatus).queryParam("timeZone", "EST"); // Approved / Received
        Response res = requestSpec.get();
        return res ;
    }

    // API 2 - Create EDD Request
    public String createEDD(String OrderDetailsResponse) {
        Response res = null;
        if (OrderDetailsResponse.contains("Internal server error") || OrderDetailsResponse.contains("No orders found for given store")) {
            System.out.println("$$$$$ CREATE EDD NOT CALLED $$$$$");
        } else {
            res = RestAssured
                    .given()
                    .contentType("application/json")
                    .body(OrderDetailsResponse)
                    .header("Authorization", authorization)
                    .when()
                    .post("https://7boss-api-dev.gccmex.io/storeintegration/outbounds/edd/json")
                    .then()
                    .statusCode(201).extract().response();
            System.out.println("API 2 Response : " + res.asString());
        }
        return res.asString();
    }

    // API 3 - Process EDD is working - Implement Hit api code
    public void processEdd(String createdEddResponse) {
        String reqTemplate = null;
        String filePath = "./testData/ProcessEdd.txt";
        try {

            // Read request Template File
            Path path = Paths.get(filePath);
            Charset charset = StandardCharsets.UTF_8;
            reqTemplate = new String(Files.readAllBytes(path), charset);

            // Replace the Template Values in Response
            String str = createdEddResponse.replaceFirst("\\{", reqTemplate);
            System.out.println("API 3 Request : " + str);
            if (createdEddResponse.contains("Internal server error") || createdEddResponse.contains("No orders found for given store")) {
                System.out.println("$$$$$ PROCESS EDD NOT CALLED $$$$$");
            } else {
                // Hit API Request
                Response res = null;
                res = RestAssured
                        .given()
                        .contentType("application/json")
                        .body(str)
                        .header("Authorization", authorization)
                        .when()
                        .post("https://7boss-api-dev.gccmex.io/storeintegration/inbounds")
                        .then()
                        .statusCode(201).extract().response();
                System.out.println("API 3 - Response : " + res.asString());
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }


    public String modifyOrderDetails(Response res , String ProdCode , String quantityVal) {
        String content = null;
        String filePath = "./testData/GetOrderDetails.txt";
            try {
                // Read request Template File
                Path path = Paths.get(filePath);
                Charset charset = StandardCharsets.UTF_8;
                content = new String(Files.readAllBytes(path), charset);

                // Read Response
                JsonPath jResp = res.jsonPath();
                int sizeOfArray = jResp.getInt("body.size()");
                String PRODUCT_CODE, DELIVERY_DATE, DELIVERY_NUMBER, ORDER_QUANTITY, ITEM_RETAIL_AMOUNT, ITEM_COST_AMOUNT, LDU_QUANTITY, VENDOR_CODE, DELIVERY_CDC_TYPES;
                // Replace -  Header Values
                content = content.replace("DATE", jResp.getString("header.date"));
                content = content.replace("TIME", jResp.getString("header.timeHHmmss"));
                content = content.replace("MS", jResp.getString("header.ms"));

                // Replace  - Order Detail Values
                for (int i = 0; i < sizeOfArray; i++) {
                    PRODUCT_CODE = jResp.getString("body[" + i + "].prod_cd");
                    ORDER_QUANTITY = jResp.getString("body[" + i + "].order_qty");

                    if (PRODUCT_CODE.equals(ProdCode) && ORDER_QUANTITY.equals(quantityVal)) {
                        content = content.replace("PRODUCT_CODE", jResp.getString("body[" + i + "].prod_cd"));
                        content = content.replace("ORDER_QUANTITY", jResp.getString("body[" + i + "].order_qty"));
                       // content = content.replace("DELIVERYDAY", jResp.getString("body[" + i + "].delivery_date"));
                        content = content.replace("DELIVERYDAY", currentDate("format2"));
                        content = content.replace("DELIVERY_NUMBER", jResp.getString("body[" + i + "].delivery_no"));
                        content = content.replace("ITEM_RETAIL_AMOUNT", jResp.getString("body[" + i + "].item_retail_amt"));
                        content = content.replace("ITEM_COST_AMOUNT", jResp.getString("body[" + i + "].item_cost_amt"));
                        content = content.replace("LDU_QUANTITY", jResp.getString("body[" + i + "].ldu_qty"));
                        content = content.replace("VENDOR_CODE", jResp.getString("body[" + i + "].vendor_cd"));
                        content = content.replace("DELIVERY_CDC_TYPES", jResp.getString("body[" + i + "].delivery_cdc_type"));
                    }
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        return content;
    }

    private String currentDate(String formatType){
        String date = null ;
        if (formatType.equals("format1")){
            DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy-MM-dd");
            LocalDateTime now = LocalDateTime.now();
            date = dtf.format(now);
        } else if (formatType.equals("format2")) {
            DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyyMMdd");
            LocalDateTime now = LocalDateTime.now();
            date = dtf.format(now);
        }
        //System.out.println(date);
        return date;
    }

}

