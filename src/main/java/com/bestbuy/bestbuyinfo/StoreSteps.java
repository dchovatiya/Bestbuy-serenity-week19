package com.bestbuy.bestbuyinfo;

import com.bestbuy.constants.EndPoints;
import com.bestbuy.model.StorePojo;
import io.restassured.http.ContentType;
import io.restassured.response.ValidatableResponse;
import net.serenitybdd.rest.SerenityRest;
import net.thucydides.core.annotations.Step;

import java.util.HashMap;

/**
 * By Dimple Patel
 **/
public class StoreSteps
{
    @Step("Creating store with name:{0}, type:{1},address:{2},address2: {3}, city:{4},state:{5},zip:{6}, lat:{7},lng:{8},hours:{9}")
    public ValidatableResponse createStore(String name, String type, String address, String address2, String city,
                                           String state,String zip,double lat, double lng, String hours)
    {
        StorePojo storePojo=StorePojo.getStorePojo(name,type,address,address2,city,state,zip,lat,lng,hours);
        return SerenityRest.given().log().all()
                .contentType(ContentType.JSON)
                .body(storePojo)
                .when()
                .post()
                .then();
    }
    @Step("Getting the store information with name: {0}")
    public HashMap<String, Object>getStoreInfoByName(String name,String type,String city)
    {
        String p1="data.findAll{it.name=='";
        String p2="'}.get(0)";

        return SerenityRest.given().log().all()
                .queryParam("type", type)
                .queryParam("city", city)
                .when()
                .get()
                .then()
                .statusCode(200)
                .extract()
                .path(p1+name+p2);
    }
    @Step("Updating store information with id: {0}, name: {1}, type: {2}, address: {3},address2 : {4}, city: {5},state:{5},zip:{6}, lat:{7},lng:{8},hours:{9}")
    public ValidatableResponse updateStore ( int storeId, String name, String type, String address, String address2, String city,
                                             String state,String zip,double lat, double lng, String hours)
    {
        StorePojo storePojo = StorePojo.getStorePojo(name, type, address, address2, city,state,zip,lat,lng,hours);

        return SerenityRest.given().log().all()
                .header("Content-Type", "application/json")
                .pathParam("storeId", storeId)
                .body(storePojo)
                .when()
                .put(EndPoints.UPDATE_STORE_BY_ID)
                .then();
    }
    @Step("Deleting store information with id: {0}")
    public ValidatableResponse deleteStore (int storeId){
        return SerenityRest
                .given()
                .pathParam("storeId", storeId)
                .when()
                .delete(EndPoints.DELETE_STORE_BY_ID)
                .then();
    }
    @Step("Getting student information with studentId: {0}")
    public ValidatableResponse getStoreById (int storeId){
        return SerenityRest
                .given()
                .pathParam("storeId", storeId)
                .when()
                .get(EndPoints.GET_SINGLE_STORE_BY_ID)
                .then();
    }

}
