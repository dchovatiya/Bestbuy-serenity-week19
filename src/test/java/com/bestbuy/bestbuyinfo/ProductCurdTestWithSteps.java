package com.bestbuy.bestbuyinfo;

import com.bestbuy.constants.Path;
import com.bestbuy.testbase.TestBase;
import io.restassured.RestAssured;
import io.restassured.response.ValidatableResponse;
import net.serenitybdd.junit.runners.SerenityRunner;
import net.thucydides.core.annotations.Steps;
import net.thucydides.core.annotations.Title;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.HashMap;

import static org.hamcrest.Matchers.hasValue;

/**
 * By Dimple Patel
 **/
//@RunWith(SerenityRunner.class)
public class ProductCurdTestWithSteps extends TestBase
{
    @BeforeClass
    public static void endPath()
    {
        RestAssured.basePath= Path.PRODUCTS;
    }
    static String name="Kodak AA Batteries";
    static String type="HardSolid";
    static int price=8;
    static String upc="1235";
    static int shipping=2;
    static String description="Long term batteries with good Power technology:6-pack";
    static String manufacturer="Kodak";
    static String model="KDK12654";
    static String url="http://www.kodak.com";
    static String image="http://img.kodak.com/kk.jpg";
    static int productID;

    @Steps
    ProductsSteps productsSteps;
    @Title("This will create a new product")
    @Test
    public void test001()
    {
        ValidatableResponse response=productsSteps.createProduct(name,type,price,upc,shipping,description,manufacturer,model,url,image);
        response.log().all().statusCode(201);//status code for creating
    }
    @Title("Verify if the product was added to the application")
    @Test
    public void test002()
    {
        HashMap<String, Object> value = productsSteps.getProductInfoByName(name,type,upc);
        Assert.assertThat(value, hasValue(name));
        Assert.assertThat(value, hasValue(type));
        Assert.assertThat(value, hasValue(upc));
        productID = (int) value.get("id");
    }
    @Title("Update the product information and verify the updated information")
    @Test
    public void test003()
    {
        name=name+"_updated";
        productsSteps.updateProduct(productID, name, type, price, upc, shipping,description,manufacturer,model,url,image).log().all().statusCode(200);
        HashMap<String, Object> value = productsSteps.getProductInfoByName(name,type,upc);
        Assert.assertThat(value, hasValue(name));
        Assert.assertThat(value, hasValue(type));
        Assert.assertThat(value, hasValue(upc));
    }
    @Title("Delete the product and verify if the product is deleted!")
    @Test
    public void test004() {
        productsSteps.deleteProduct(productID).statusCode(200);
        productsSteps.getProductById(productID) .statusCode(404);
    }












}
