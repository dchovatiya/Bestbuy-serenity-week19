package com.bestbuy.bestbuyinfo;

import com.bestbuy.constants.EndPoints;
import com.bestbuy.model.ProductsPojo;
import io.restassured.http.ContentType;
import io.restassured.response.ValidatableResponse;
import net.serenitybdd.rest.SerenityRest;
import net.thucydides.core.annotations.Step;

import java.util.HashMap;

/**
 * By Dimple Patel
 **/
public class ProductsSteps
{
    @Step("Creating products with name:{0},type:{1},price:{2},shipping:{3}, upc:{4},description:{5},manufacturer:{6}, model:{7},url:{8},image:{9}")
    public ValidatableResponse createProduct(String name, String type, int price, String upc,int shipping ,
                                           String description, String manufacturer, String model, String url, String image)
    {
        ProductsPojo productsPojo=ProductsPojo.getProductPojo(name,type,price,shipping,upc,description,manufacturer,model,url,image);
        return SerenityRest.given().log().all()
                .contentType(ContentType.JSON)
                .body(productsPojo)
                .when()
                .post()
                .then();
    }
    @Step("Getting the product information with name: {0}")
    public HashMap<String, Object> getProductInfoByName(String name, String type, String upc)
    {
        String p1="data.findAll{it.name=='";
        String p2="'}.get(0)";

        return SerenityRest.given().log().all()
                .queryParam("type", type)
                .queryParam("upc", upc)
                .when()
                .get()
                .then()
                .statusCode(200)
                .extract()
                .path(p1+name+p2);
    }
    @Step("Updating product information with id: {0}, name: {1}, type: {2}, price: {3},shipping : {4}, upc: {5},description:{5},manufacturer:{6}, model:{7},url:{8},image:{9}")
    public ValidatableResponse updateProduct( int id, String name, String type, int price,String upc,int  shipping,
                                             String description,String manufacturer,String model, String url, String image)
    {
        ProductsPojo productsPojo = ProductsPojo.getProductPojo(name, type, price, shipping, upc,description,manufacturer,model,url,image);

        return SerenityRest.given().log().all()
                .header("Content-Type", "application/json")
                .pathParam("id", id)
                .body(productsPojo)
                .when()
                .put(EndPoints.UPDATE_PRODUCT_BY_ID)
                .then();
    }
    @Step("Deleting product information with id: {0}")
    public ValidatableResponse deleteProduct (int id){
        return SerenityRest
                .given()
                .pathParam("id", id)
                .when()
                .delete(EndPoints.DELETE_PRODUCT_BY_ID)
                .then();
    }
    @Step("Getting product information with id: {0}")
    public ValidatableResponse getProductById (int id){
        return SerenityRest
                .given()
                .pathParam("id", id)
                .when()
                .get(EndPoints.GET_SINGLE_PRODUCT_BY_ID)
                .then();
    }
}
