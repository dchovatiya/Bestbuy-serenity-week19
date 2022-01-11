package com.bestbuy.bestbuyinfo;

import com.bestbuy.constants.EndPoints;
import com.bestbuy.model.ServicePojo;
import io.restassured.http.ContentType;
import io.restassured.response.ValidatableResponse;
import net.serenitybdd.rest.SerenityRest;
import net.thucydides.core.annotations.Step;

import java.util.HashMap;

/**
 * By Dimple Patel
 **/
public class ServiceSteps
{
    @Step("Creating service with name:{0}")
    public ValidatableResponse createService(String name)
    {
        ServicePojo servicePojo=ServicePojo.getServicePojo(name);
        return SerenityRest.given().log().all()
                .contentType(ContentType.JSON)
                .body(servicePojo)
                .when()
                .post()
                .then();
    }
    @Step("Getting the service information with name: {0}")
    public HashMap<String, Object> getServiceInfoByName(String name)
    {
        String p1="data.findAll{it.name=='";
        String p2="'}.get(0)";
        return SerenityRest.given().log().all()
                .queryParam("$limit",40)
                .queryParam("name",name)
                .when()
                .get()
                .then()
                .statusCode(200)
                .extract()
                .path(p1+name+p2);
    }
    @Step("Updating service information with id: {0}, name: {1}")
    public ValidatableResponse updateService( int id, String name)
    {
        ServicePojo servicePojo = ServicePojo.getServicePojo(name);

        return SerenityRest.given().log().all()
                .header("Content-Type", "application/json")
                .pathParam("id", id)
                .body(servicePojo)
                .when()
                .put(EndPoints.UPDATE_SERVICE_BY_ID)
                .then();
    }
    @Step("Deleting service information with id: {0}")
    public ValidatableResponse deleteService(int id){
        return SerenityRest
                .given()
                .pathParam("id", id)
                .when()
                .delete(EndPoints.DELETE_SERVICE_BY_ID)
                .then();
    }
    @Step("Getting service information with id: {0}")
    public ValidatableResponse getServiceById (int id){
        return SerenityRest
                .given()
                .pathParam("id", id)
                .when()
                .get(EndPoints.GET_SERVICE_PRODUCT_BY_ID)
                .then();
    }

}
