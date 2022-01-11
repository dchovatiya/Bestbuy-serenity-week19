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
public class StoreCURDTestWithSteps extends TestBase
{
    @BeforeClass
    public void endPath()
    {
        RestAssured.basePath= Path.STORE;
    }
    static String name="Glenwood store Heights";
    static String type="Off licence";
    static String address="398 Street";
    static String address2="Goose Lane";
    static String city="London";
    static String state="Others";
    static String zip="55026";
    static double lat=45.236541;
    static double lng=89.123587;
    static String hours="Mon: 9-9; Tue: 9-9; Wed: 9-9; Thurs: 9-9; Fri: 9-9; Sat: 9-9; Sun: 9-8";
    static int storeID;
    @Steps
    StoreSteps storeSteps;
    @Title("This will create a new store")
    @Test
    public void test001()
    {
        ValidatableResponse response=storeSteps.createStore(name,type,address,address2,city,state,zip,lat,lng,hours);
                response.log().all().statusCode(201);
    }
    @Title("Verify if the store was added to the application")
    @Test
    public void test002()
    {
        HashMap<String, Object> value = storeSteps.getStoreInfoByName(name,type,city);
        Assert.assertThat(value, hasValue(name));
        Assert.assertThat(value, hasValue(type));
        Assert.assertThat(value, hasValue(city));
        storeID = (int) value.get("id");
    }
    @Title("Update the store information and verify the updated information")
    @Test
    public void test003()
    {
        name=name+"_updated";
        storeSteps.updateStore(storeID, name, type, address, address2, city,state,zip,lat,lng,hours).log().all().statusCode(200);
        HashMap<String, Object> value = storeSteps.getStoreInfoByName(name,type,city);
        Assert.assertThat(value, hasValue(name));
        Assert.assertThat(value, hasValue(type));
        Assert.assertThat(value, hasValue(city));
    }
    @Title("Delete the store and verify if the store is deleted!")
    @Test
    public void test004() {
        storeSteps.deleteStore(storeID).statusCode(200);
        storeSteps.getStoreById(storeID) .statusCode(404);
    }
}
